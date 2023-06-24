/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modifiers.Service;

import Modifiers.Parameters.CalculationType;
import Calculators.Parameters.NoiseEmissionParameters;
import Calculators.SortingAndDifferencesOfResults.NoiseEmissionSortAndDifferences.DifferenceBy;
import Calculators.SortingAndDifferencesOfResults.NoiseEmissionSortAndDifferences.SortBy;
import FileReadingAndDataStore.DataStore.DbfData;
import Modifiers.Repository.ModifierRepository;
import java.util.List;
import java.util.Map;
import org.locationtech.jts.geom.Geometry;

/**
 *
 * @author szaboa
 */
/**
 * Service class that provides various modification operations using a ModifierRepository.
 */
public class ModifierService {
    private final ModifierRepository modifierRepository;
    
    /**
     * Constructs a new ModifierService instance.
     *
     * @param modifierRepository the repository for accessing modification operations
     */
    public ModifierService(ModifierRepository modifierRepository) {
        this.modifierRepository = modifierRepository;
    }
    
    /**
     * Retrieves calculated values based on the specified calculation type and index.
     *
     * @param calculationType the calculation type
     * @param index           the index
     * @return the list of calculated values
     */
    public List<Double> getCalculatedValues(CalculationType calculationType, int index) {
        return modifierRepository.getCalculatedValues(calculationType, index);
    }
    
    /**
     * Retrieves the column name associated with the specified calculation type.
     *
     * @param calculationType the calculation type
     * @return the column name
     */
    public String getColumnName(CalculationType calculationType) {
        return modifierRepository.getColumnName(calculationType);
    }
    
    /**
     * Invokes the noise emission difference calculation based on the specified difference type and time state indices.
     *
     * @param differenceBy       the difference type
     * @param timeStateIndex1    the first time state index
     * @param timeStateIndex2    the second time state index
     * @return the list of difference data
     */
    public List<Double> invokeNoiseEmissionDifferenceBy(
            DifferenceBy differenceBy, 
            int timeStateIndex1,
            int timeStateIndex2) {
        
        List<Double> differenceData = 
                modifierRepository.
                        invokeNoiseEmissionDifferenceBy(differenceBy,
                                timeStateIndex1, timeStateIndex2);
        return differenceData;
    }
    
    /**
     * Invokes the noise emission sorting operation based on the specified sorting type and time state index.
     *
     * @param sortBy            the sorting type
     * @param timeStateIndex    the time state index
     * @return the sorted data as a list of lists
     */
    public List<List<Object>> invokeNoiseEmissionSortBy(SortBy sortBy, int timeStateIndex) {
        List<List<Object>> sortedData = 
                modifierRepository.invokeNoiseEmissionSortBy(sortBy, timeStateIndex);
        return sortedData;
    }
    
    /**
     * Creates new geometries by buffering the shapefile geometries using the provided values.
     *
     * @param valuesDay     the list of day values for buffering
     * @param valuesNight   the list of night values for buffering
     * @param index         the index of the shapefile
     * @return the list of newly created geometries after buffering
     */
    public List<Geometry> createNewGeometry(
            List<Double> valuesDay,
            List<Double> valuesNight,
            int index) {
        List<Geometry> newGeometries =
                modifierRepository.
                        createNewGeometry(valuesDay, valuesNight, index);
        return newGeometries;
    }
    
    /**
     * Adds calculated columns to the specified DbfData object with the given column name and new column values.
     *
     * @param dbfData        the DbfData object to add calculated columns to
     * @param columnName     the name of the new column
     * @param newColumnValues the list of values for the new column
     */
    public void addCalculatedColumns(
            DbfData dbfData,
            String columnName, 
            List<Double> newColumnValues) {
        
        modifierRepository.
                addCalculatedColumns(dbfData, columnName, newColumnValues);
    }
    
    /**
     * Calculates noise emission values for the specified distance during the day at the given index.
     *
     * @param distance the distance for calculating noise emission
     * @param index    the index
     * @return the list of noise emission values during the day
     */
    public List<Double> dayCalculateNoiseEmissionAtGivenDistance(double distance, int index) {
        return modifierRepository.dayCalculateNoiseEmissionAtGivenDistance(distance, index);
    }
    
    /**
     * Calculates noise emission values for the specified distance during the night at the given index.
     *
     * @param distance the distance for calculating noise emission
     * @param index    the index
     * @return the list of noise emission values during the night
     */
    public List<Double> nightCalculateNoiseEmissionAtGivenDistance(double distance, int index) {
        return modifierRepository.nightCalculateNoiseEmissionAtGivenDistance(distance, index);
    }
    
    /**
     * Updates the cell value at the specified column and row index in the DbfData object with the new value.
     *
     * @param dbfData       the DbfData object containing the cell to update
     * @param columnIndex  the index of the column
     * @param rowIndex     the index of the row
     * @param newValue      the new value to set
     */
    public void updateCellValue(DbfData dbfData, int columnIndex, int rowIndex, Object newValue) {
        modifierRepository.updateCellValue(dbfData, columnIndex, rowIndex, newValue);
    }
    
    /**
     * Deletes the cell value at the specified column and row index in the DbfData object.
     *
     * @param dbfData       the DbfData object containing the cell to delete
     * @param columnName   the name of the column
     * @param rowIndex     the index of the row
     */
    public void deleteCellValue(DbfData dbfData, String columnName, int rowIndex) {
        modifierRepository.deleteCellValue(dbfData, columnName, rowIndex);
    }
    
    /**
     * Deletes the columns with the specified names from the DbfData object.
     *
     * @param dbfData        the DbfData object to delete columns from
     * @param columnNames    the names of the columns to delete
     */
    public void deleteColumns(DbfData dbfData, List<String> columnNames) {
        modifierRepository.deleteColumns(dbfData, columnNames);
    }
    
    /**
     * Deletes the row at the specified index from the DbfData object.
     *
     * @param dbfData        the DbfData object to delete the row from
     * @param rowIndex       the index of the row to delete
     */
    public void deleteRow(DbfData dbfData, int rowIndex) {
        modifierRepository.deleteRow(dbfData, rowIndex);
    }
    
    /**
     * Removes the result at the specified index from the ModifierRepository.
     *
     * @param index the index of the result to remove
     */
    public void removeResult(int index) {
        modifierRepository.removeResult(index);
    }
    
    /**
     * Removes all results from the ModifierRepository.
     */
    public void removeAllResult() {
        modifierRepository.removeAllResult();
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
        
        modifierRepository.
                setParameterValueForAllRows(index, parameters, totalRows);
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
        modifierRepository.setParameterValue(index, rowIndex, parameters);
    }
    
    /**
     * Retrieves the parameter value at the specified file index and row index for the given NoiseEmissionParameters.
     *
     * @param fileIndex   the index of the file
     * @param rowIndex    the index of the row
     * @param parameter   the NoiseEmissionParameters to retrieve the value for
     * @return the parameter value
     */
    public double getParameterValue(
            int fileIndex,
            int rowIndex, 
            NoiseEmissionParameters parameter) {
        return modifierRepository.
                getParameterValue(fileIndex, rowIndex, parameter);
    }
    
    /**
     * Removes the file parameters at the specified index from the ModifierRepository.
     *
     * @param index the index of the file parameters to remove
     */
    public void removeFileParameters(int index) {
        modifierRepository.removeFileParameters(index);
    }
    
    /**
     * Removes all parameters from the ModifierRepository.
     */
    public void removeAllParameters() {
        modifierRepository.removeAllParameters();
    }
}
