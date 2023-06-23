/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileReadingAndDataStore.Repositories;

import FileReadingAndDataStore.DataStore.ShapeData;
import java.io.File;
import java.io.IOException;
import java.util.List;
/**
 *
 * @author szaboa
 */
/**
 * The ShapeFileRepository interface defines the contract for a repository that can read a Shapefile.
 * This interface provides methods for reading and retrieving Shapefile data.
 */
public interface ShapeFileRepository {
    /**
     * Reads the Shapefile data from the specified file.
     *
     * @param file the file to read the Shapefile data from
     * @throws IOException if an I/O error occurs while reading the file
     */
    void readShapeFile(File file) throws IOException;
    
    /**
     * Retrieves the ShapeData object at the specified index.
     *
     * @param index the index of the ShapeData object to retrieve
     * @return the ShapeData object at the specified index
     */   
    ShapeData getShapeData(int index);
    /**
     * Retrieves a list of all ShapeData objects read from the Shapefile.
     *
     * @return a list of ShapeData objects representing the data from the Shapefile
     */
    
    List<ShapeData> getShapeData();
}
