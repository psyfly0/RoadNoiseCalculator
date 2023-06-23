/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculators.Repository;

import Calculators.Parameters.NoiseEmissionParameters;
import Calculators.SortingAndDifferencesOfResults.NoiseEmissionSortAndDifferences.DifferenceBy;
import Calculators.SortingAndDifferencesOfResults.NoiseEmissionSortAndDifferences.SortBy;
import FileReadingAndDataStore.DataStore.DbfData;
import java.util.List;
import java.util.Map;

/**
 *
 * @author szaboa
 */
/**
* Represents a repository for calculator-related operations, parameter settings and data retrieval.
*/
public interface CalculatorRepository {
    /**
    * Retrieves the filtered DBF data at the specified index.
    *
    * @param index the index of the data
    * @return the filtered DBF data
    */
    DbfData getFilteredDbfData(int index);
    
    /**
    * Invokes the noise emission calculator for the specified index.
    *
    * @param index the index of the data
    */
    void invokeNoiseEmissionCalculator(int index);
    
   // boolean isCalculated(int index);
 //   void setCalculated(int index, boolean calculated);
    /**
    * Calculates the noise emission during the day at the given distance for the specified index.
    *
    * @param distance the distance at which to calculate the noise emission
    * @param index    the index of the data
    * @return the list of calculated noise emission values during the day
    */
    List<Double> dayCalculateNoiseEmissionAtGivenDistance(double distance, int index);
    
    /**
    * Calculates the noise emission during the night at the given distance for the specified index.
    *
    * @param distance the distance at which to calculate the noise emission
    * @param index    the index of the data
    * @return the list of calculated noise emission values during the night
    */
    List<Double> nightCalculateNoiseEmissionAtGivenDistance(double distance, int index);
    
    /**
    * Invokes the noise emission difference calculation based on the specified difference criteria for the given time state indices (given files).
    *
    * @param differenceBy      the criteria by which to calculate the difference
    * @param timeStateIndex1   the index of the first time state (first file)
    * @param timeStateIndex2   the index of the second time state (second file)
    * @return the list of calculated noise emission differences
    */
    List<Double> invokeNoiseEmissionDifferenceBy(DifferenceBy differenceBy, int timeStateIndex1, int timeStateIndex2);
    
    /**
    * Invokes the noise emission sorting based on the specified sorting criteria for the given time state index (given file).
    *
    * @param sortBy           the criteria by which to sort the noise emission data
    * @param timeStateIndex   the index of the time state (file)
    * @return the sorted noise emission data
    */
    List<List<Object>> invokeNoiseEmissionSortBy(SortBy sortBy, int timeStateIndex);
    
    /**
    * Removes the result at the specified index.
    *
    * @param index the index of the result to remove
    */
    void removeResult(int index);
    
    /**
    * Removes all the results.
    */
    void removeAllResult();
    
    /**
    * Sets the parameter values for all rows at the specified index.
    *
    * @param index       the index of the data
    * @param parameters  the map of parameter values to set
    * @param totalRows   the total number of rows in the data
    */
    void setParameterValueForAllRows(int index, Map<NoiseEmissionParameters, Double> parameters, int totalRows);
    
    /**
     * Sets the parameter values for the specified row at the specified index.
     *
     * @param index      the index of the data
     * @param rowIndex   the index of the row
     * @param parameters the map of parameter values to set
     */
    void setParameterValue(int index, int rowIndex, Map<NoiseEmissionParameters, Double> parameters);
    
    /**
     * Retrieves the parameter value for the specified file index, row index, and parameter.
     *
     * @param fileIndex   the index of the data file
     * @param rowIndex    the index of the row
     * @param parameter   the parameter for which to retrieve the value
     * @return the parameter value
     */
    double getParameterValue(int fileIndex, int rowIndex, NoiseEmissionParameters parameter);
    
    /**
     * Removes the parameter values for the specified file index.
     *
     * @param index the index of the data file
     */
    void removeFileParameters(int index);
    
    /**
     * Removes all the parameter values.
     */
    void removeAllParameters();
    
    /**
     * Retrieves the decibel values during the day for the specified index.
     *
     * @param index the index of the data
     * @return the list of decibel values during the day
     */
    List<Double> getDayDecibelValues(int index);
    
    /**
     * Retrieves the decibel values during the night for the specified index.
     *
     * @param index the index of the data
     * @return the list of decibel values during the night
     */
    List<Double> getNightDecibelValues(int index);
    
    /**
     * Retrieves the protective distance values during the day for the specified index.
     *
     * @param index the index of the data
     * @return the list of protective distance values during the day
     */
    List<Double> getProtectiveDistanceNappal(int index);
    
    /**
     * Retrieves the protective distance values during the night for the specified index.
     *
     * @param index the index of the data
     * @return the list of protective distance values during the night
     */
    List<Double> getProtectiveDistanceEjjel(int index);
    
    /**
     * Retrieves the impact area distance values during the day for the specified index.
     *
     * @param index the index of the data
     * @return the list of impact area distance values during the day
     */
    List<Double> getImpactAreaDistanceNappal(int index);
    
    /**
     * Retrieves the impact area distance values during the night for the specified index.
     *
     * @param index the index of the data
     * @return the list of impact area distance values during the night
     */
    List<Double> getImpactAreaDistanceEjjel(int index);
    
    /**
     * Retrieves the sound power level values during the day for the specified index.
     *
     * @param index the index of the data
     * @return the list of sound power level values during the day
     */
    List<Double> getSoundPowerLevelNappal(int index);
    
    /**
     * Retrieves the sound power level values during the night for the specified index.
     *
     * @param index the index of the data
     * @return the list of sound power level values during the night
     */
    List<Double> getSoundPowerLevelEjjel(int index);
    
}
