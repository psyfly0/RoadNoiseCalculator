/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileReadingAndDataStore.Services;

import FileReadingAndDataStore.DataStore.DbfData;
import FileReadingAndDataStore.Repositories.DbfRepository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
/**
 *
 * @author szaboa
 */
/**
 * The DbfService class provides methods for interacting with the DbfRepository and retrieving
 * DbfData from a given DBF file.
 */
public class DbfService {
    
    private final DbfRepository dbfRepository;
    /**
     * Constructs a new DbfService object with the given DbfRepository dependency.
     *
     * @param dbfRepository the DbfRepository dependency used to retrieve DbfData from a DBF file
     */
    
    public DbfService(DbfRepository dbfRepository) {
        this.dbfRepository = dbfRepository;
    }
    
    /**
     * Reads the given DBF file and loads the data into the repository.
     *
     * @param file the DBF file to read
     * @throws IOException if there is an error reading the DBF file
     */
    // open the files
    public void loadDbfFile(File file) throws IOException {
        dbfRepository.readDbfFile(file);
    }
    
    /**
     * Returns the number of rows in the loaded DBF data.
     *
     * @return the number of rows in the DBF data
     */
    public int getDbfRowCount() {
        return dbfRepository.getDbfData().getRowCount();
    }
    
    /**
     * Returns the header names of the loaded DBF data.
     *
     * @param file the DBF file to retrieve the header names from
     * @return a list of header names
     * @throws IOException if there is an error reading the DBF file
     */
    public List<String> getDbfHeader(File file) throws IOException {
       List<String> headerNames = new ArrayList<>();
       
       DbfData dbfData = dbfRepository.getDbfData();
       if (dbfData == null) {
           throw new IllegalArgumentException("Nem található .dbf file");
       }
       headerNames.addAll(dbfData.getColumns());

       return headerNames;
   }
    
    /**
     * Updates the DBF file with the user-selected columns.
     *
     * @param file              the DBF file to update
     * @param selectedColumnMap the map of selected columns and their new names
     */
    // Gets the updated column names back from the Controller
    public void updateDbfFile(File file, Map<String, String> selectedColumnMap) {
        dbfRepository.updateDbfFile(file, selectedColumnMap);
    }
    
    /**
     * Returns a list of filtered DbfData objects.
     *
     * @return a list of filtered DbfData objects
     */
    // Gets the modified data and passes to the Controller
    public List<DbfData> getFilteredDbfDataList() {
       // return dbfDataManager.getAllDbfData();
       return dbfRepository.getFilteredDbfDataList();
    }
    
    /**
     * Returns the filtered DbfData object at the specified index.
     *
     * @param index the index of the filtered DbfData object
     * @return the filtered DbfData object at the specified index, or null if not found
     */
    public DbfData getFilteredDbfData(int index) {
        if (index >= 0 && index < dbfRepository.getFilteredDbfDataList().size()) {
            return dbfRepository.getFilteredDbfData(index);
        } else {
            return null;
        }
    } 
    
    /**
     * Returns the column names of the filtered DbfData object at the specified index.
     *
     * @param index the index of the filtered DbfData object
     * @return a list of column names, or an empty list if not found
     */    
    public List<String> getColumnNames(int index) {
        if (index >= 0 && index < dbfRepository.getFilteredDbfDataList().size()) {
            DbfData filteredDbfData = getFilteredDbfData(index);
            if (filteredDbfData != null) {
                return filteredDbfData.getColumns();
            } else {        
                return Collections.emptyList();
            }
        }
        return Collections.emptyList();
    }
    
    /**
     * Returns the records of the filtered DbfData object at the specified index.
     *
     * @param index the index of the filtered DbfData object
     * @return a list of records, or an empty list if not found
     */    
    public List<List<Object>> getRecord(int index) {
        if (index >= 0 && index < dbfRepository.getFilteredDbfDataList().size()) {
            DbfData filteredDbfData = getFilteredDbfData(index);
            if (filteredDbfData != null) {   
                return filteredDbfData.getRecord();
            } else {        
                return Collections.emptyList();
            }
        }
        return Collections.emptyList();  
    }
    
    /**
     * Deletes the filtered DbfData object at the specified index.
     *
     * @param index the index of the filtered DbfData object to delete
     */    
    public void deleteDbfData(int index) {
        dbfRepository.deleteDbfData(index);
    }
    
    /**
     * Deletes all the filtered DbfData objects.
     */    
    public void deleteAllDbfData() {
        dbfRepository.deleteAllDbfData();
    }
      
}
