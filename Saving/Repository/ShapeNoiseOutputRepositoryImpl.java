/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Saving.Repository;

import Saving.Writer.ShapeFileWriter;
import FileReadingAndDataStore.Repositories.DbfRepository;
import FileReadingAndDataStore.Repositories.ShapeFileRepository;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.locationtech.jts.geom.Geometry;

/**
 *
 * @author szaboa
 */
/**
 * Implementation of the {@link ShapeNoiseOutputRepository} interface that provides methods
 * for saving shapefiles and new geometries shapefiles related to shape noise output.
 */
public class ShapeNoiseOutputRepositoryImpl implements ShapeNoiseOutputRepository {
    private ShapeFileWriter shapeFileWriter;
    private DbfRepository dbfRepository;
    private ShapeFileRepository shapeFileRepository;
    
    public ShapeNoiseOutputRepositoryImpl(
            DbfRepository dbfRepository, 
            ShapeFileRepository shapeFileRepository) {
        
        this.dbfRepository = dbfRepository;
        this.shapeFileRepository = shapeFileRepository;
        this.shapeFileWriter = 
                new ShapeFileWriter(dbfRepository, shapeFileRepository);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void saveShapefile(File outputShapeFile, int index) throws IOException {
        shapeFileWriter.writeShapefile(outputShapeFile, index);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void saveNewGeometriesShapefile(
            File outputShapeFile, 
            List<Geometry> newGeometries, 
            String columnName, 
            int index) throws IOException {
        
        shapeFileWriter.writeNewGeometriesShapeFile(outputShapeFile, 
                newGeometries, columnName, index);
    }
}
