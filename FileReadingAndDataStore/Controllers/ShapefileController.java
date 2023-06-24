/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileReadingAndDataStore.Controllers;

import FileReadingAndDataStore.Services.ShapefileService;
import FileReadingAndDataStore.DataStore.ShapeData;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author szaboa
 */
/**
 * The controller class for managing shapefile-related functionality.
 */
public class ShapefileController {
    private final ShapefileService shapefileService;
    private List<ShapeData> shapeDataList;
    
    /**
     * Constructs a new ShapefileController object.
     *
     * @param shapefileService The ShapefileService to use for retrieving shapefile data.
     */
    public ShapefileController(ShapefileService shapefileService) {
        this.shapefileService = shapefileService;
        this.shapeDataList = new ArrayList<>();
    }
    
    /**
     * Sets the currently selected shapefile and retrieves the corresponding shape data.
     *
     * @param file The selected shapefile.
     * @throws IOException if there is an error loading the shapefile
     */
    public void onShapefileSelected(File file) throws IOException {
        if (file != null) {
            shapefileService.loadShapeFile(file);
        }
    }
    
    /**
     * Returns the list of shape data for the currently selected shapefile.
     *
     * @return The list of shape data.
     */
    public List<ShapeData> getShapeData() {
        return shapeDataList;
    }
}
