/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Saving.Writer;

import FileReadingAndDataStore.DataStore.DbfData;
import FileReadingAndDataStore.Repositories.DbfRepository;
import FileReadingAndDataStore.DataStore.ShapeData;
import FileReadingAndDataStore.Repositories.ShapeFileRepository;
import java.util.List;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiLineString;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;


/**
 *
 * @author szaboa
 */
/**
 * The ShapeFileWriter class is responsible for writing shapefile data based on the provided input and repositories.
 */
public class ShapeFileWriter {
    private final ShapeFileRepository shapeFileRepository;
    private final DbfRepository dbfRepository;
    
    /**
     * Constructs a new ShapeFileWriter object with the specified DbfRepository and ShapeFileRepository.
     *
     * @param dbfRepository        the DbfRepository to retrieve DBF data from
     * @param shapeFileRepository  the ShapeFileRepository to retrieve shapefile data from
     */
    public ShapeFileWriter(
            DbfRepository dbfRepository, 
            ShapeFileRepository shapeFileRepository) {
        this.dbfRepository = dbfRepository;
        this.shapeFileRepository = shapeFileRepository;
    }

    /**
     * Writes the shapefile to the specified output file path using the provided index.
     *
     * @param outputFilePath  the file path of the output shapefile
     * @param index           the index of the shapefile data
     * @throws IOException if an I/O error occurs during the writing process
     */
    public void writeShapefile(String outputFilePath, int index) throws IOException {
        File outputShapeFile = new File(outputFilePath);
        writeShapefile(outputShapeFile, index);
    }

    /**
     * Writes the shapefile to the specified output file using the provided index.
     *
     * @param outputShapeFile  the output shapefile
     * @param index            the index of the shapefile data
     * @throws IOException if an I/O error occurs during the writing process
     */
    public void writeShapefile(File outputShapeFile, int index) throws IOException {
        ShapeData shapeData = shapeFileRepository.getShapeData(index);
        DbfData dbfData = dbfRepository.getFilteredDbfData(index);
        
        List<String> columns = dbfData.getColumns();
        List<List<Object>> records = dbfData.getRecord();
        List<Geometry> geometries = shapeData.getGeometries();
        
        // Create the feature type
        SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
        typeBuilder.setName("Shapefile");
        typeBuilder.setCRS(shapeData.getCoordinateReferenceSystem());
        typeBuilder.add("geometry", MultiLineString.class); // Geometry attribute
            
        for (String columnName : columns) {
            columnName = WriterHelper.truncateNames(columnName);
            typeBuilder.add(columnName, Object.class);
        }
        SimpleFeatureType featureType = typeBuilder.buildFeatureType();

        // Create the shapefile data store
        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
        ShapefileDataStore dataStore = 
                (ShapefileDataStore) dataStoreFactory.
                        createDataStore(outputShapeFile.toURI().toURL());

        // Set the charset for shapefile attribute encoding
        Charset charset = Charset.forName("UTF-8");
        dataStore.setCharset(charset);

        // Create the feature collection
        DefaultFeatureCollection featureCollection = new DefaultFeatureCollection();

        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featureType);
        for (int i = 0; i < records.size(); i++) {
            Geometry geometry = geometries.get(i);
            Geometry densifiedGeometry = WriterHelper.densifyGeometry(geometry, 2.0); // Densify with a step of 2 meters
        featureBuilder.add(densifiedGeometry);
            List<Object> record = records.get(i);
            for (int j = 0; j < record.size(); j++) {
                Object attributeValue = record.get(j);
             //   String columnName = columns.get(j);
                featureBuilder.add(attributeValue);
            }
            
            SimpleFeature feature = featureBuilder.buildFeature(null);
            featureCollection.add(feature);
        }

        // Write the features to the shapefile
        dataStore.createSchema(featureType);
        dataStore.forceSchemaCRS(shapeData.getCoordinateReferenceSystem());
        dataStore.createSchema(featureType);
        
        String typeName = dataStore.getTypeNames()[0];
        Transaction transaction = new DefaultTransaction();
        try (
             FeatureWriter<SimpleFeatureType, SimpleFeature> writer =
                     dataStore.getFeatureWriterAppend(typeName, transaction);
             FeatureIterator<SimpleFeature> featureIterator = 
                     featureCollection.features()) {

            while (featureIterator.hasNext()) {
                SimpleFeature feature = featureIterator.next();
                SimpleFeature newFeature = writer.next();
                newFeature.setAttributes(feature.getAttributes());
                writer.write();
            }

            writer.close();
            featureIterator.close();
            transaction.commit();
        } finally {
            transaction.close();
        }
        dataStore.dispose();
    }
    
    /**
     * Writes a shapefile with new geometries and an additional attribute.
     *
     * @param outputShapeFile  the output shapefile
     * @param newGeometries    the list of new geometries to write
     * @param columnName      the name of the additional attribute
     * @param index            the index of the shapefile data
     * @throws IOException if an I/O error occurs during the writing process
     */
    public void writeNewGeometriesShapeFile(
            File outputShapeFile,
            List<Geometry> newGeometries,
            String columnName, 
            int index) throws IOException {
        
        ShapeData shapeData = shapeFileRepository.getShapeData(index);

        // Create the feature type
        SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
        typeBuilder.setName("Shapefile");
        typeBuilder.setCRS(shapeData.getCoordinateReferenceSystem());
        typeBuilder.add("geometry", MultiLineString.class); // Geometry attribute
        typeBuilder.add(columnName, String.class);
        SimpleFeatureType featureType = typeBuilder.buildFeatureType();

        // Create the shapefile data store
        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
        ShapefileDataStore dataStore = 
                (ShapefileDataStore) dataStoreFactory.
                        createDataStore(outputShapeFile.toURI().toURL());

        // Set the charset for shapefile attribute encoding
        Charset charset = Charset.forName("UTF-8");
        dataStore.setCharset(charset);

        // Create the feature collection
        DefaultFeatureCollection featureCollection = new DefaultFeatureCollection();

        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featureType);
        for (int i = 0; i < newGeometries.size(); i++) {
            Geometry geometry = newGeometries.get(i);
            String attributeValue = i == 0 ? "nappal" : "ejjel"; // Set the attribute value based on the index

            featureBuilder.add(geometry);
            featureBuilder.add(attributeValue); // Add the attribute value
            SimpleFeature feature = featureBuilder.buildFeature(null);
            featureCollection.add(feature);
        }

        // Write the features to the shapefile
        dataStore.createSchema(featureType);
        String typeName = dataStore.getTypeNames()[0];
        Transaction transaction = new DefaultTransaction();
        try (FeatureWriter<SimpleFeatureType, SimpleFeature> writer = 
                dataStore.getFeatureWriterAppend(typeName, transaction);
             FeatureIterator<SimpleFeature> featureIterator = 
                     featureCollection.features()) {
            while (featureIterator.hasNext()) {
                SimpleFeature feature = featureIterator.next();
                SimpleFeature newFeature = writer.next();
                newFeature.setAttributes(feature.getAttributes());
                writer.write();
            }

            featureIterator.close();
            writer.close();
            transaction.commit();
        } finally {
            transaction.close();
        }
        dataStore.dispose();
    }


    

}