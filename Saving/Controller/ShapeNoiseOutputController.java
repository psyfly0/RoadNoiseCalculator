/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Saving.Controller;

import Saving.Service.ShapeNoiseOutputService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.locationtech.jts.geom.Geometry;

/**
 *
 * @author szaboa
 */
/**
 * The ShapeNoiseOutputController class acts as a controller for handling shape noise output operations.
 * It delegates the save operations to the ShapeNoiseOutputService.
 */
public class ShapeNoiseOutputController {
    private final ShapeNoiseOutputService shapeNoiseOutputService;
    
    /**
     * Constructs a new ShapeNoiseOutputController with the specified ShapeNoiseOutputService.
     *
     * @param shapeNoiseOutputService the ShapeNoiseOutputService used for handling shape noise output operations
     */
    public ShapeNoiseOutputController(ShapeNoiseOutputService shapeNoiseOutputService) {
        this.shapeNoiseOutputService = shapeNoiseOutputService;
    }
    
    /**
     * Saves the shapefile at the specified output file path for the given index.
     *
     * @param outputShapeFile the output file to save the shapefile
     * @param index           the index of the shapefile
     * @throws IOException if an I/O error occurs during the save operation
     */
    public void saveShapefile(File outputShapeFile, int index) throws IOException {
        shapeNoiseOutputService.saveShapefile(outputShapeFile, index);
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
        
        shapeNoiseOutputService.saveNewGeometriesShapefile(outputShapeFile, 
                newGeometries, columnName, index);
    }

}
