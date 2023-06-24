/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileReadingAndDataStore.Services;

import FileReadingAndDataStore.Repositories.ShapeFileRepository;
import java.io.File;
import java.io.IOException;
/**
 *
 * @author szaboa
 */
/**
 * Service class for handling shapefile data.
 * This class uses a ShapeFileRepository to read and retrieve shapefile data.
 */
public class ShapefileService {
    private ShapeFileRepository shapeFileRepository;
    
    /**
     * Constructs a new ShapefileService with the specified ShapeFileRepository.
     *
     * @param shapeFileRepository the ShapeFileRepository to use
     */
    public ShapefileService(ShapeFileRepository shapeFileRepository) {
        this.shapeFileRepository = shapeFileRepository;
    }
    
    /**
     * Retrieves the shapefile data from the specified file using the configured ShapeFileRepository.
     *
     * @param file the file containing the shapefile data
     * @throws IOException if there is an error reading the shapefile
     */ 
    public void loadShapeFile(File file) throws IOException {
        shapeFileRepository.readShapeFile(file);
    }
}
