/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileReadingAndDataStore.Controllers;

import FileReadingAndDataStore.DataStore.DbfData;
import FileReadingAndDataStore.Services.DbfService;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author szaboa
 */
/**
 * The controller for managing interactions between the UI and the DbfService.
 */
public class DbfController {
    private final DbfService dbfService;
    private int selectedRow = -1;
    private int selectedColumn = -1;
    
    /**
     * Constructs a new DbfController with the specified DbfService.
     *
     * @param dbfService the DbfService to use for loading and saving DBF files.
     */
    public DbfController(DbfService dbfService) {
        this.dbfService = dbfService;
    }
    
    /**
     * Loads the specified DBF file and updates the list of DBF data.
     *
     * @param file the DBF file to load.
     * @throws IOException if there is an error loading the DBF file.
     */
    // open the files
    public void onDbfSelected(File file) throws IOException {
        if (file != null) {
            dbfService.loadDbfFile(file);
        }
    }
    
    /**
     * Returns the number of rows in the loaded DBF file.
     *
     * @return the number of rows in the DBF file.
     */
    public int getDbfRowCount() {
        return dbfService.getDbfRowCount();
    }
    
    /**
     * Returns the column names of the specified DBF file.
     *
     * @param file the DBF file.
     * @return a map of column names, where the keys and values are the same.
     * @throws IOException if there is an error retrieving the column names.
     */
    public Map<String, String> getColumnNames(File file) throws IOException {
        List<String> columnNames = dbfService.getDbfHeader(file);
        Map<String, String> columnMap = new LinkedHashMap<>();
        for (String columnName: columnNames) {
            columnMap.put(columnName, columnName);
        }      
        return columnMap;
    }
    
    /**
     * Updates the DBF file with the selected column mappings.
     *
     * @param file              the DBF file to update.
     * @param selectedColumnMap the map of selected column mappings.
     */
    // Gets the updated column names back from the GUI
    public void updateData(File file, Map<String, String> selectedColumnMap) {
        // Pass the file and selectedColumnMap to the DbfService
        dbfService.updateDbfFile(file, selectedColumnMap);
    }
    
    /**
     * Returns the filtered list of DBF data.
     *
     * @return the filtered list of DBF data.
     */
    // Gets the modified data and passes to the GUI
    public List<DbfData> getFilteredDbfDataList() {
        return dbfService.getFilteredDbfDataList();
    }

    /**
     * Returns the filtered DBF data at the specified index.
     *
     * @param index the index of the filtered DBF data.
     * @return the filtered DBF data at the specified index, or null if the index is out of bounds.
     */
    public DbfData getFilteredDbfData(int index) {
        if (index >= 0 && index < dbfService.getFilteredDbfDataList().size()) {
            return dbfService.getFilteredDbfData(index);
        } else {
            return null;
        }
    } 
    
    /**
     * Returns the column names of the filtered DBF data at the specified index.
     *
     * @param index the index of the filtered DBF data.
     * @return the column names of the filtered DBF data at the specified index,
     *         or an empty list if the index is out of bounds.
     */
    public List<String> getColumnNames(int index) {
        if (index >= 0 && index < getFilteredDbfDataList().size()) {
            return dbfService.getColumnNames(index);
        } 
        return Collections.emptyList();
    }
    
    /**
     * Returns the record data of the filtered DBF data at the specified index.
     *
     * @param index the index of the filtered DBF data.
     * @return the record data of the filtered DBF data at the specified index,
     *         or an empty list if the index is out of bounds.
     */
    public List<List<Object>> getRecord(int index) {
        if (index >= 0 && index < getFilteredDbfDataList().size()) {  
            return dbfService.getRecord(index);
        }
        return Collections.emptyList();  
    }    
    
    /**
     * Deletes the DBF data at the specified index.
     *
     * @param index the index of the DBF data to delete.
     */
    public void deleteDbfData(int index) {
        dbfService.deleteDbfData(index);
    }
    
    /**
     * Deletes all the DBF data.
     */
    public void deleteAllDbfData() {
        dbfService.deleteAllDbfData();
    }
}
