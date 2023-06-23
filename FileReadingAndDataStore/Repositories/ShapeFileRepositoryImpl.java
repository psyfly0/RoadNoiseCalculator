/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileReadingAndDataStore.Repositories;

import FileReadingAndDataStore.DataStore.ShapeData;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.GeometryDescriptor;
/**
 *
 * @author szaboa
 */
/**
 * Implementation of the {@link ShapeFileRepository} interface that reads shapefiles
 * and stores the shape data in a list of {@link ShapeData} objects.
 */
public class ShapeFileRepositoryImpl implements ShapeFileRepository {
    private List<ShapeData> shapeDataList = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void readShapeFile(File file) throws IOException {
        ShapefileDataStore dataStore = new ShapefileDataStore(file.toURI().toURL());
        String typeName = dataStore.getTypeNames()[0];
        SimpleFeatureType schema;
        List<Geometry> geometries;
        List<SimpleFeature> featureList;
            try (SimpleFeatureIterator featureIterator = 
                    dataStore.getFeatureSource(typeName).getFeatures().features()) {
                schema = dataStore.getSchema(typeName);
                GeometryDescriptor geometryDescriptor = schema.getGeometryDescriptor();
                SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
                builder.setName(schema.getName());
                builder.setCRS(schema.getCoordinateReferenceSystem());
                builder.setDefaultGeometry(geometryDescriptor.getName().getLocalPart());
                builder.add(geometryDescriptor);
                geometries = new ArrayList<>();
                featureList = new ArrayList<>();

                while (featureIterator.hasNext()) {
                    SimpleFeature feature = featureIterator.next();
                    Geometry geometry = (Geometry) feature.getDefaultGeometry();
                    geometries.add(geometry);
                    featureList.add(feature);
                }  
                featureIterator.close();
            }


        if (!geometries.isEmpty()) {
            ShapeData shapeData = new ShapeData(geometries, schema);
            shapeDataList.add(shapeData);
        }

        dataStore.dispose();
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public ShapeData getShapeData(int index) {
        if (index >= 0 && index < shapeDataList.size()) {
            return shapeDataList.get(index);
        } else {
            return null;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<ShapeData> getShapeData() {
        return shapeDataList;
    }

}
