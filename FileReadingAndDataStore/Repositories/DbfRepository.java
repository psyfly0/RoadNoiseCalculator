/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileReadingAndDataStore.Repositories;

import FileReadingAndDataStore.DataStore.DbfData;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
/**
 *
 * @author szaboa
 */
/**
 * An interface for reading and manipulating data from a dBase (DBF) file.
 * Implementing classes must provide methods for reading, updating, and deleting data
 * from a DBF file, as well as filtering and retrieving specific records.
 */
public interface DbfRepository {
    /**
     * Reads data from the specified DBF file and processes it.
     *
     * @param file the DBF file to read
     * @throws IOException if an I/O error occurs while reading the file
     */
    void readDbfFile(File file) throws IOException;
    
    /**
     * Retrieves the DbfData object representing the read data.
     *
     * @return the DbfData object containing the records read from the file
     */   
    DbfData getDbfData();
    
    /**
     * Updates the specified DBF file with user-selected columns based on the provided column map.
     *
     * @param file             the DBF file to update
     * @param selectedColumnMap a map of selected column names and their new values
     */
    void updateDbfFile(File file, Map<String, String> selectedColumnMap);
    
    /**
     * Retrieves a list of DbfData objects representing the records in the file,
     * after applying any specified filters.
     *
     * @return a list of DbfData objects representing the filtered records in the file
     */
    List<DbfData> getFilteredDbfDataList();
    
    /**
     * Retrieves a specific filtered DbfData object at the given index.
     *
     * @param index the index of the filtered DbfData object to retrieve
     * @return the filtered DbfData object at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    DbfData getFilteredDbfData(int index);
    
    /**
     * Deletes the DbfData object at the given index from the data list.
     *
     * @param index the index of the DbfData object to delete
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    void deleteDbfData(int index);
    
    /**
     * Deletes all DbfData objects from the data list.
     */
    void deleteAllDbfData();
}
