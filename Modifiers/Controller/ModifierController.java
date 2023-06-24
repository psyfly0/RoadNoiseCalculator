/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modifiers.Controller;

import Modifiers.Parameters.CalculationType;
import Calculators.Parameters.NoiseEmissionParameters;
import Calculators.SortingAndDifferencesOfResults.NoiseEmissionSortAndDifferences.DifferenceBy;
import Calculators.SortingAndDifferencesOfResults.NoiseEmissionSortAndDifferences.SortBy;
import FileReadingAndDataStore.DataStore.DbfData;
import Modifiers.Service.ModifierService;
import java.util.List;
import java.util.Map;
import org.locationtech.jts.geom.Geometry;

/**
 *
 * @author szaboa
 */
/**
 * Controller class that provides access to modification operations through a ModifierService.
 */
public class ModifierController {
    private final ModifierService modifierService;
    
    /**
     * Constructs a new ModifierController with the given ModifierService.
     *
     * @param modifierService the ModifierService to use for modification operations
     */
    public ModifierController(ModifierService modifierService) {
        this.modifierService = modifierService;
    }
    
    /**
     * Retrieves the calculated values for the specified CalculationType and index.
     *
     * @param calculationType the CalculationType
     * @param index           the index
     * @return the calculated values
     */
    public List<Double> getCalculatedValues(CalculationType calculationType, int index) {
        return modifierService.getCalculatedValues(calculationType, index);
    }
    
    /**
     * Retrieves the column name for the specified CalculationType.
     *
     * @param calculationType the CalculationType
     * @return the column name
     */
    public String getColumnName(CalculationType calculationType) {
        return modifierService.getColumnName(calculationType);
    }
    
    /**
     * Invokes the noise emission difference calculation using the specified DifferenceBy, time state indices.
     *
     * @param differenceBy       the DifferenceBy
     * @param timeStateIndex1    the first time state index
     * @param timeStateIndex2    the second time state index
     * @return the difference data
     */
    public List<Double> invokeNoiseEmissionDifferenceBy(
            DifferenceBy differenceBy,
            int timeStateIndex1, 
            int timeStateIndex2) {
        
        List<Double> differenceData = 
                modifierService.invokeNoiseEmissionDifferenceBy(
                        differenceBy, 
                        timeStateIndex1, 
                        timeStateIndex2);
        return differenceData;
    }
    
    /**
     * Invokes the noise emission sorting operation using the specified SortBy and time state index.
     *
     * @param sortBy         the SortBy
     * @param timeStateIndex the time state index
     * @return the sorted data
     */
    public List<List<Object>> invokeNoiseEmissionSortBy(
            SortBy sortBy, 
            int timeStateIndex) {
        
        List<List<Object>> sortedData =
                modifierService.invokeNoiseEmissionSortBy(sortBy, timeStateIndex);
        return sortedData;
    }
    
    /**
     * Creates new geometries using the specified values for day and night and the given index.
     *
     * @param valuesDay    the values for day
     * @param valuesNight  the values for night
     * @param index        the index
     * @return the created geometries
     */
    public List<Geometry> createNewGeometry(
            List<Double> valuesDay,
            List<Double> valuesNight,
            int index) {
        
        List<Geometry> newGeometries = 
                modifierService.createNewGeometry(valuesDay, valuesNight, index);
        return newGeometries;
    }
    
    /**
     * Adds calculated columns to the specified DbfData using the given column name and new column values.
     *
     * @param dbfData         the DbfData
     * @param columnName      the column name
     * @param newColumnValues the new column values
     */
    public void addCalculatedColumns(
            DbfData dbfData, 
            String columnName,
            List<Double> newColumnValues) {
        
        modifierService.addCalculatedColumns(dbfData, columnName, newColumnValues);
    }
    
        /**
     * Calculates the noise emission at a given distance during the day for the specified index.
     *
     * @param distance the distance
     * @param index    the index
     * @return the calculated noise emission values
     */
    public List<Double> dayCalculateNoiseEmissionAtGivenDistance(double distance, int index) {
        return modifierService.dayCalculateNoiseEmissionAtGivenDistance(distance, index);
    }
    
    /**
     * Calculates the noise emission at a given distance during the night for the specified index.
     *
     * @param distance the distance
     * @param index    the index
     * @return the calculated noise emission values
     */
    public List<Double> nightCalculateNoiseEmissionAtGivenDistance(double distance, int index) {
        return modifierService.nightCalculateNoiseEmissionAtGivenDistance(distance, index);
    }
    
    /**
     * Updates the cell value at the specified column index and row index in the given DbfData with the new value.
     *
     * @param dbfData       the DbfData
     * @param columnIndex  the column index
     * @param rowIndex     the row index
     * @param newValue     the new value
     */
    public void updateCellValue(DbfData dbfData, int columnIndex, int rowIndex, Object newValue) {
        modifierService.updateCellValue(dbfData, columnIndex, rowIndex, newValue);
    }
    
    /**
     * Deletes the cell value at the specified column name and row index in the given DbfData.
     *
     * @param dbfData     the DbfData
     * @param columnName  the column name
     * @param rowIndex    the row index
     */
    public void deleteCellValue(DbfData dbfData, String columnName, int rowIndex) {
        modifierService.deleteCellValue(dbfData, columnName, rowIndex);
    }
    
    /**
     * Deletes the specified columns from the given DbfData.
     *
     * @param dbfData      the DbfData
     * @param columnNames  the names of the columns to delete
     */
    public void deleteColumns(DbfData dbfData, List<String> columnNames) {
        modifierService.deleteColumns(dbfData, columnNames);
    }   
    
    /**
     * Deletes the row at the specified index from the given DbfData.
     *
     * @param dbfData    the DbfData
     * @param rowIndex   the row index
     */
    public void deleteRow(DbfData dbfData, int rowIndex) {
        modifierService.deleteRow(dbfData, rowIndex);
    }
    
    /**
     * Removes the result at the specified index from the ModifierService.
     *
     * @param index the index of the result to remove
     */
    public void removeResult(int index) {
        modifierService.removeResult(index);
    }
    
    /**
     * Removes all results from the ModifierService.
     */
    public void removeAllResult() {
        modifierService.removeAllResult();
    }
    
    /**
     * Sets the parameter value for all rows at the specified index using the given parameters map and total number of rows.
     *
     * @param index       the index of the parameter
     * @param parameters  the map of NoiseEmissionParameters and their corresponding values
     * @param totalRows   the total number of rows
     */
    public void setParameterValueForAllRows(
            int index,
            Map<NoiseEmissionParameters, Double> parameters,
            int totalRows) {
        
        modifierService.setParameterValueForAllRows(index, parameters, totalRows);
    }
    
    /**
     * Sets the parameter value at the specified index and row index using the given parameters map.
     *
     * @param index       the index of the parameter
     * @param rowIndex    the index of the row
     * @param parameters  the map of NoiseEmissionParameters and their corresponding values
     */
    public void setParameterValue(
            int index, 
            int rowIndex,
            Map<NoiseEmissionParameters, Double> parameters) {
        
        modifierService.setParameterValue(index, rowIndex, parameters);
    }
    
    /**
     * Retrieves the parameter value at the specified file index, row index, and parameter.
     *
     * @param fileIndex   the index of the file
     * @param rowIndex    the index of the row
     * @param parameter   the NoiseEmissionParameters
     * @return the parameter value
     */
    public double getParameterValue(
            int fileIndex,
            int rowIndex, 
            NoiseEmissionParameters parameter) {
        
        return modifierService.getParameterValue(fileIndex, rowIndex, parameter);
    }
    
    /**
     * Removes the file parameters at the specified index from the ModifierService.
     *
     * @param index the index of the file parameters to remove
     */
    public void removeFileParameters(int index) {
        modifierService.removeFileParameters(index);
    }
    
    /**
     * Removes all parameters from the ModifierService.
     */
    public void removeAllParameters() {
        modifierService.removeAllParameters();
    }

}
