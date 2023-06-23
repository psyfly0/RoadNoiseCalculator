/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modifiers.Repository;

import Modifiers.Parameters.CalculationType;
import Calculators.Parameters.NoiseEmissionParameters;
import Calculators.SortingAndDifferencesOfResults.NoiseEmissionSortAndDifferences.DifferenceBy;
import Calculators.SortingAndDifferencesOfResults.NoiseEmissionSortAndDifferences.SortBy;
import FileReadingAndDataStore.DataStore.DbfData;
import FileReadingAndDataStore.DataStore.ShapeData;
import java.util.List;
import java.util.Map;
import org.locationtech.jts.geom.Geometry;

/**
 *
 * @author szaboa
 */
/**
 * The `ModifierRepository` interface defines the contract for a repository that manages modifications and calculations related to noise emissions.
 * Interacts with CalculatorRepository, DbfData and ShapeData classes
 * It provides methods for retrieving, manipulating, and removing data and parameters associated with noise emission calculations.
 */
public interface ModifierRepository {
    /**
     * Retrieves the filtered DBF (dBASE) data for a given index.
     *
     * @param index   The index of the data.
     * @return The filtered DBF data.
     */
    DbfData getFilteredDbfData(int index);
    
    /**
     * Retrieves the shape data for a given index.
     *
     * @param index   The index of the data.
     * @return The shape data.
     */
    ShapeData getShapeData(int index);
    
    /**
     * Retrieves the calculated values for a specific calculation type and index.
     *
     * @param calculationType   The type of calculation.
     * @param index             The index of the data.
     * @return The list of calculated values.
     */
    List<Double> getCalculatedValues(CalculationType calculationType, int index);
    
    /**
     * Retrieves the column name for a specific calculation type.
     *
     * @param calculationType   The type of calculation.
     * @return The column name.
     */
    String getColumnName(CalculationType calculationType);
    
    /**
     * Invokes the noise emission difference calculation based on a specific difference criterion and time state indices.
     *
     * @param differenceBy         The difference criterion.
     * @param timeStateIndex1      The index of the first time state.
     * @param timeStateIndex2      The index of the second time state.
     * @return The list of noise emission differences.
     */
    List<Double> invokeNoiseEmissionDifferenceBy(DifferenceBy differenceBy, 
            int timeStateIndex1, int timeStateIndex2);
    
    /**
     * Invokes the noise emission sorting based on a specific sorting criterion and time state index.
     *
     * @param sortBy            The sorting criterion.
     * @param timeStateIndex    The index of the time state.
     * @return The sorted list of noise emissions.
     */
    List<List<Object>> invokeNoiseEmissionSortBy(SortBy sortBy, int timeStateIndex);
    
    /**
     * Creates a new geometry object based on the provided values, index, and time state.
     *
     * @param valuesDay       The list of values for daytime.
     * @param valuesNight     The list of values for nighttime.
     * @param index           The index of the data.
     * @return The created geometry object.
     */
    List<Geometry> createNewGeometry(List<Double> valuesDay, 
            List<Double> valuesNight, int index);
    
    /**
     * Adds calculated columns to the DBF data using the specified column name and new column values.
     *
     * @param dbfData           The DBF data.
     * @param columnName        The name of the new column.
     * @param newColumnValues   The list of values for the new column.
     */
    void addCalculatedColumns(DbfData dbfData, String columnName,
            List<Double> newColumnValues);
    
    /**
     * Calculates the noise emission values for daytime at the given distance and index.
     *
     * @param distance   The distance at which the noise emissions are calculated.
     * @param index      The index of the data.
     * @return The list of noise emission values for daytime.
     */
    List<Double> dayCalculateNoiseEmissionAtGivenDistance(double distance, int index);
    
    /**
     * Calculates the noise emission values for nighttime at the given distance and index.
     *
     * @param distance   The distance at which the noise emissions are calculated.
     * @param index      The index of the data.
     * @return The list of noise emission values for nighttime.
     */
    List<Double> nightCalculateNoiseEmissionAtGivenDistance(double distance, int index);
    
    /**
     * Updates the cell value at the specified column index and row index in the provided DBF data.
     *
     * @param dbfData       The DBF data.
     * @param columnIndex   The index of the column.
     * @param rowIndex      The index of the row.
     * @param newValue      The new value to be set in the cell.
     */
    void updateCellValue(DbfData dbfData, int columnIndex, int rowIndex, Object newValue);
    
    /**
     * Deletes the cell value at the specified column name and row index in the provided DBF data.
     *
     * @param dbfData       The DBF data.
     * @param columnName    The name of the column.
     * @param rowIndex      The index of the row.
     */
    void deleteCellValue(DbfData dbfData, String columnName, int rowIndex);
    
    /**
     * Deletes the columns with the specified names from the provided DBF data.
     *
     * @param dbfData       The DBF data.
     * @param columnNames   The names of the columns to be deleted.
     */
    void deleteColumns(DbfData dbfData, List<String> columnNames);
    
    /**
     * Deletes the row at the specified index from the provided DBF data.
     *
     * @param dbfData       The DBF data.
     * @param rowIndex      The index of the row to be deleted.
     */
    void deleteRow(DbfData dbfData, int rowIndex);
    
    /**
     * Removes the result at the specified index from the repository.
     *
     * @param index   The index of the result to be removed.
     */
    void removeResult(int index);
    
    /**
     * Removes all results from the repository.
     */
    void removeAllResult();
    
    /**
     * Sets the parameter values for all rows at the given index using the provided parameters and total number of rows.
     *
     * @param index         The index of the data.
     * @param parameters    The parameter values to be set.
     * @param totalRows     The total number of rows.
     */
    void setParameterValueForAllRows(int index, Map<NoiseEmissionParameters,
            Double> parameters, int totalRows);
    
    
    /**
     * Sets the parameter values for the specified row at the given index using the provided parameters.
     *
     * @param index         The index of the data.
     * @param rowIndex      The index of the row.
     * @param parameters    The parameter values to be set.
     */
    void setParameterValue(int index, int rowIndex, Map<NoiseEmissionParameters, 
            Double> parameters);
    
    /**
     * Retrieves the parameter value at the specified file index, row index, and parameter type.
     *
     * @param fileIndex     The index of the file.
     * @param rowIndex      The index of the row.
     * @param parameter     The parameter type.
     * @return The parameter value.
     */
    double getParameterValue(int fileIndex, int rowIndex, 
            NoiseEmissionParameters parameter);
    
    /**
     * Removes the parameter values associated with the file at the specified index.
     *
     * @param index   The index of the file.
     */
    void removeFileParameters(int index);
    
    /**
     * Removes all parameter values from the repository.
     */
    void removeAllParameters();
}
