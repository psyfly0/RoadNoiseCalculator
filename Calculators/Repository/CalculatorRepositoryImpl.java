/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculators.Repository;

import Calculators.Parameters.NoiseEmissionParametersManager;
import Calculators.Parameters.NoiseEmissionParameters;
import Calculators.Results.NoiseEmissionResult;
import Calculators.SortingAndDifferencesOfResults.NoiseEmissionSortAndDifferences;
import Calculators.NoiseEmissionCalculators.NoiseEmissionAtGivenDistanceCalculator;
import Calculators.NoiseEmissionCalculators.NoiseEmissionCalculator;
import Calculators.SortingAndDifferencesOfResults.NoiseEmissionSortAndDifferences.DifferenceBy;
import Calculators.SortingAndDifferencesOfResults.NoiseEmissionSortAndDifferences.SortBy;
import FileReadingAndDataStore.DataStore.DbfData;
import FileReadingAndDataStore.Repositories.DbfRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author szaboa
 */
/**
 * Implementation of the {@link CalculatorRepository} interface that provides functionality for noise emission calculations
 * and management of calculation results.
 */
public class CalculatorRepositoryImpl implements CalculatorRepository {
    private final DbfRepository dbfRepository;
    private final List<NoiseEmissionResult> noiseEmissionResults;
    private NoiseEmissionResult noiseEmissionResult;
    private final NoiseEmissionParametersManager parametersManager;
    private NoiseEmissionParameters parameters;
    private NoiseEmissionAtGivenDistanceCalculator distanceCalculator;
    private NoiseEmissionSortAndDifferences differences;
    private NoiseEmissionSortAndDifferences sort;
    
    /**
    * A map that connects the file indexes to the corresponding calculated result indexes.
    * It allows retrieving the result index based on the file index for efficient result management.
    */
    private final Map<Integer, Integer> fileToResultIndexMap;

    /**
     * Constructs a new {@code CalculatorRepositoryImpl} object with the specified {@link DbfRepository} dependency.
     *
     * @param dbfRepository the {@link DbfRepository} implementation to use for retrieving data
     */
    public CalculatorRepositoryImpl(DbfRepository dbfRepository) {
        this.dbfRepository = dbfRepository;
        this.noiseEmissionResults = new ArrayList<>();
        this.noiseEmissionResult = new NoiseEmissionResult();
        this.fileToResultIndexMap = new HashMap<>();
        this.parametersManager = new NoiseEmissionParametersManager();
        this.distanceCalculator = new NoiseEmissionAtGivenDistanceCalculator(
                parametersManager,
                parameters
        );
        this.differences = new NoiseEmissionSortAndDifferences(this);
        this.sort = new NoiseEmissionSortAndDifferences(this);
    }

    /**
     * Sets the noise emission parameters for calculations.
     *
     * @param parameters the noise emission parameters to set
     */
    public void setNoiseEmissionParameters(NoiseEmissionParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invokeNoiseEmissionCalculator(int index) {
        int resultIndex;

        if (fileToResultIndexMap.containsKey(index)) {
            resultIndex = fileToResultIndexMap.get(index);
            noiseEmissionResult = noiseEmissionResults.get(resultIndex);
        } else {
            resultIndex = noiseEmissionResults.size();
            noiseEmissionResults.add(noiseEmissionResult);
            fileToResultIndexMap.put(index, resultIndex);
        }

        NoiseEmissionCalculator noiseEmissionCalculator = 
                new NoiseEmissionCalculator(this, noiseEmissionResult, 
                        index, parametersManager);
        noiseEmissionCalculator.calculations(resultIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> dayCalculateNoiseEmissionAtGivenDistance(
            double distance, int index) {
        List<Double> dayDecibelValuesAtGivenIndex = getDayDecibelValues(index);
        return distanceCalculator.
                dayCalculateNoiseEmissionAtGivenDistance(dayDecibelValuesAtGivenIndex,
                        distance, index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> nightCalculateNoiseEmissionAtGivenDistance(
            double distance, int index) {
        List<Double> nightDecibelValuesAtGivenIndex = getNightDecibelValues(index);
        return distanceCalculator.
                nightCalculateNoiseEmissionAtGivenDistance(nightDecibelValuesAtGivenIndex,
                        distance, index);
    }

    /**
     * {@inheritDoc}
     */
    public NoiseEmissionResult getNoiseEmissionResult(int index) {
        if (fileToResultIndexMap.containsKey(index)) {
            int resultIndex = fileToResultIndexMap.get(index);
            return noiseEmissionResults.get(resultIndex);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> invokeNoiseEmissionDifferenceBy(
            DifferenceBy differenceBy,
            int timeStateIndex1,
            int timeStateIndex2) {
        if (noiseEmissionResults.size() > 1) {
            // Retrieve the corresponding result index
            int resultIndex1 = fileToResultIndexMap.get(timeStateIndex1);
            int resultIndex2 = fileToResultIndexMap.get(timeStateIndex2);
            
            // calculate differences
            List<Double> differenceData = differences.
                    calculateDifferences(differenceBy, noiseEmissionResults.
                            get(resultIndex1), noiseEmissionResults.
                            get(resultIndex2), resultIndex1, resultIndex2, 
                            timeStateIndex1, timeStateIndex2);
            return differenceData;
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<List<Object>> invokeNoiseEmissionSortBy(SortBy sortBy, int timeStateIndex) {
        // Retrieve the corresponding result index
        int resultIndex = fileToResultIndexMap.get(timeStateIndex);

        // Sort the data
        List<List<Object>> sortedData = sort.sort(sortBy, resultIndex);
        return sortedData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeResult(int index) {
        if (fileToResultIndexMap.containsKey(index)) {
            int resultIndex = fileToResultIndexMap.get(index);
            int fileIndex = -1;  // Initialize fileIndex variable
            for (Map.Entry<Integer, Integer> entry : fileToResultIndexMap.entrySet()) {
                if (entry.getKey() == index) {
                    fileIndex = entry.getKey();
                    break;
                }
            }
            fileToResultIndexMap.remove(index);
            noiseEmissionResult.clearResults(resultIndex);
            noiseEmissionResults.remove(resultIndex);

            // Update the indices in the fileToResultIndexMap for files with higher indices
            for (Map.Entry<Integer, Integer> entry : fileToResultIndexMap.entrySet()) {
                int fileIdx = entry.getKey();
                int resultIdx = entry.getValue();
                if (resultIdx > resultIndex && fileIdx > fileIndex) {
                    fileToResultIndexMap.put(fileIdx - 1, resultIdx - 1);
                    fileToResultIndexMap.remove(fileIdx);
                } else if (resultIdx > resultIndex && fileIdx < fileIndex) {
                    fileToResultIndexMap.put(fileIdx, resultIdx - 1);
                } else if (resultIdx < resultIndex && fileIdx > fileIndex) {
                    fileToResultIndexMap.put(fileIdx - 1, resultIdx);
                    fileToResultIndexMap.remove(fileIdx);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllResult() {
        noiseEmissionResults.removeAll(noiseEmissionResults);
        noiseEmissionResult.clearAllResults();
        fileToResultIndexMap.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameterValueForAllRows(
            int index, 
            Map<NoiseEmissionParameters, 
            Double> parameters, 
            int totalRows) {
        parametersManager.setParameterValueForAllRows(index, parameters, totalRows);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameterValue(
            int index, 
            int rowIndex, 
            Map<NoiseEmissionParameters, 
            Double> parameters) {
        parametersManager.setParameterValue(index, rowIndex, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getParameterValue(
            int fileIndex, 
            int rowIndex,
            NoiseEmissionParameters parameter) {
        return parametersManager.getParameterValue(fileIndex, rowIndex, parameter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeFileParameters(int index) {
        parametersManager.removeFileParameters(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllParameters() {
        parametersManager.removeAllParameters();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DbfData getFilteredDbfData(int index) {
        return dbfRepository.getFilteredDbfData(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> getDayDecibelValues(int index) {
        noiseEmissionResult = getNoiseEmissionResult(index);
        int resultIndex = fileToResultIndexMap.get(index); // Retrieve the corresponding result index
        return noiseEmissionResult.getDayDecibelValues(resultIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> getNightDecibelValues(int index) {
        noiseEmissionResult = getNoiseEmissionResult(index);
        int resultIndex = fileToResultIndexMap.get(index); // Retrieve the corresponding result index
        return noiseEmissionResult.getNightDecibelValues(resultIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> getProtectiveDistanceNappal(int index) {
        noiseEmissionResult = getNoiseEmissionResult(index);
        int resultIndex = fileToResultIndexMap.get(index); // Retrieve the corresponding result index
        return noiseEmissionResult.getProtectiveDistanceNappal(resultIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> getProtectiveDistanceEjjel(int index) {
        noiseEmissionResult = getNoiseEmissionResult(index);
        int resultIndex = fileToResultIndexMap.get(index); // Retrieve the corresponding result index
        return noiseEmissionResult.getProtectiveDistanceEjjel(resultIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> getImpactAreaDistanceNappal(int index) {
        noiseEmissionResult = getNoiseEmissionResult(index);
        int resultIndex = fileToResultIndexMap.get(index); // Retrieve the corresponding result index
        return noiseEmissionResult.getImpactAreaDistanceNappal(resultIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> getImpactAreaDistanceEjjel(int index) {
        noiseEmissionResult = getNoiseEmissionResult(index);
        int resultIndex = fileToResultIndexMap.get(index); // Retrieve the corresponding result index
        return noiseEmissionResult.getImpactAreaDistanceEjjel(resultIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> getSoundPowerLevelNappal(int index) {
        noiseEmissionResult = getNoiseEmissionResult(index);
        int resultIndex = fileToResultIndexMap.get(index); // Retrieve the corresponding result index
        return noiseEmissionResult.getSoundPowerLevelNappal(resultIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> getSoundPowerLevelEjjel(int index) {
        noiseEmissionResult = getNoiseEmissionResult(index);
        int resultIndex = fileToResultIndexMap.get(index); // Retrieve the corresponding result index
        return noiseEmissionResult.getSoundPowerLevelEjjel(resultIndex);
    }

}
