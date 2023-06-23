/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Saving.Service;

import Saving.Repository.ShapeNoiseOutputRepository;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.locationtech.jts.geom.Geometry;

/**
 *
 * @author szaboa
 */
/**
 * The ShapeNoiseOutputService class provides a service for saving shapefiles and new geometries shapefiles
 * related to shape noise output.
 */
public class ShapeNoiseOutputService {
    private final ShapeNoiseOutputRepository shapeNoiseOutputRepository;
    
    /**
     * Constructs a new ShapeNoiseOutputService with the specified ShapeNoiseOutputRepository.
     *
     * @param shapeNoiseOutputRepository the ShapeNoiseOutputRepository used for saving shapefiles and new geometries shapefiles
     */
    public ShapeNoiseOutputService(ShapeNoiseOutputRepository shapeNoiseOutputRepository) {
        this.shapeNoiseOutputRepository = shapeNoiseOutputRepository;
    }
    
    /**
     * Saves the shapefile at the specified output file path for the given index.
     *
     * @param outputShapeFile the output file to save the shapefile
     * @param index           the index of the shapefile
     * @throws IOException if an I/O error occurs during the save operation
     */
    public void saveShapefile(File outputShapeFile, int index) throws IOException {
        shapeNoiseOutputRepository.saveShapefile(outputShapeFile, index);
    }
    
    /**
     * Saves the new geometries shapefile at the specified output file for the given list of geometries,
     * column name, and index.
     *
     * @param outputShapeFile the output file to save the new geometries shapefile
     * @param newGeometries   the list of new geometries to be saved
     * @param columnName      the name of the column associated with the new geometries
     * @param index           the index of the new geometries shapefile
     * @throws IOException if an I/O error occurs during the save operation
     */
    public void saveNewGeometriesShapefile(
            File outputShapeFile,
            List<Geometry> newGeometries,
            String columnName,
            int index) throws IOException {
        
        shapeNoiseOutputRepository.saveNewGeometriesShapefile(outputShapeFile,
                newGeometries, columnName, index);
    }
   
}
