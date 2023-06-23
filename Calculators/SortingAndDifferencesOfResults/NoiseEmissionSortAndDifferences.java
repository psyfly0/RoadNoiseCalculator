/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculators.SortingAndDifferencesOfResults;

import Calculators.Repository.CalculatorRepository;
import Calculators.Results.NoiseEmissionResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author szaboa
 */
public class NoiseEmissionSortAndDifferences {
    public enum DifferenceBy {
        DAY_DECIBEL_DIFFERENCE, NIGHT_DECIBEL_DIFFERENCE,
        PROTECTIVE_DISTANCE_DAY_DIFFERENCE, PROTECTIVE_DISTANCE_NIGHT_DIFFERENCE,
        IMPACT_AREA_DISTANCE_DAY_DIFFERENCE, IMPACT_AREA_DISTANCE_NIGHT_DIFFERENCE
    }
    
    public enum SortBy {
        DAY_DECIBEL_SORT, NIGHT_DECIBEL_SORT,
        PROTECTIVE_DISTANCE_DAY_SORT, PROTECTIVE_DISTANCE_NIGHT_SORT,
        IMPACT_AREA_DISTANCE_DAY_SORT, IMPACT_AREA_DISTANCE_NIGHT_SORT,
        ORIGINAL_ORDER, DAY_DECIBEL_DIFFERENCE_SORT, NIGHT_DECIBEL_DIFFERENCE_SORT
    }
    
    private final static double NEW_ROAD = 100.0;
    private final static double CLOSED_ROAD = -100.0;
    
    private DifferenceBy differenceBy;
    private SortBy sortBy;
    private final CalculatorRepository calculatorRepository;
    //private int timeStateIndex1;
    //private int timeStateIndex2;
  //  private int timeStateIndex;

    public NoiseEmissionSortAndDifferences(CalculatorRepository calculatorRepository) {
        this.calculatorRepository = calculatorRepository;
     //   this.differenceBy = differenceBy;
      //  this.timeStateIndex1 = timeStateIndex1;
      //  this.timeStateIndex2 = timeStateIndex2;
    }
  //  public NoiseEmissionSortAndDifferences(CalculatorRepository calculatorRepository) {
    //    this.calculatorRepository = calculatorRepository;
      //  this.sortBy = sortBy;
      //  this.timeStateIndex = timeStateIndex;
  //  }
  
    public List<Double> calculateDifferences(
            DifferenceBy differenceBy,
            NoiseEmissionResult result1,
            NoiseEmissionResult result2,
            int resultIndex1,
            int resultIndex2,
            int timeStateIndex1,
            int timeStateIndex2) {
        
        List<Double> differences = new ArrayList<>();
        // Get the values for "Azonosito" column for both file
        List<Object> azonositoColumn1 = calculatorRepository.
                getFilteredDbfData(timeStateIndex1).getColumnValues("Azonosito");
        List<Object> azonositoColumn2 = calculatorRepository.
                getFilteredDbfData(timeStateIndex2).getColumnValues("Azonosito");
        switch (differenceBy) {
            case DAY_DECIBEL_DIFFERENCE:
                // Get the values for both file
                List<Double> dayDecibelValues1 = result1.
                        getDayDecibelValues(resultIndex1);
                List<Double> dayDecibelValues2 = result2.
                        getDayDecibelValues(resultIndex2);
      
                differences = differencesCalculator(azonositoColumn1, 
                        azonositoColumn2, dayDecibelValues1, dayDecibelValues2);
                break;
                
            case NIGHT_DECIBEL_DIFFERENCE:
                // Get the values for both file
                List<Double> nightDecibelValues1 = result1.
                        getNightDecibelValues(timeStateIndex1);
                List<Double> nightDecibelValues2 = result2.
                        getNightDecibelValues(timeStateIndex2);
                
                differences = differencesCalculator(azonositoColumn1,
                        azonositoColumn2, nightDecibelValues1, nightDecibelValues2);
                break; 

            case PROTECTIVE_DISTANCE_DAY_DIFFERENCE:
                // Get the values for both file
                List<Double> protectiveDistanceDay1 = result1.
                        getProtectiveDistanceNappal(timeStateIndex1);
                List<Double> protectiveDistanceDay2 = result2.
                        getProtectiveDistanceNappal(timeStateIndex2);
                
                differences = differencesCalculator(azonositoColumn1, 
                        azonositoColumn2, protectiveDistanceDay1, protectiveDistanceDay2);
                break;                
                
            case PROTECTIVE_DISTANCE_NIGHT_DIFFERENCE:
                List<Double> protectiveDistanceNight1 = result1.
                        getProtectiveDistanceEjjel(timeStateIndex1);
                List<Double> protectiveDistanceNight2 = result2.
                        getProtectiveDistanceEjjel(timeStateIndex2);
                
                differences = differencesCalculator(azonositoColumn1,
                        azonositoColumn2, protectiveDistanceNight1, protectiveDistanceNight2);
                break;  
                
            case IMPACT_AREA_DISTANCE_DAY_DIFFERENCE:
                List<Double> impactAreaDistanceDay1 = result1.
                        getImpactAreaDistanceNappal(timeStateIndex1);
                List<Double> impactAreaDistanceDay2 = result2.
                        getImpactAreaDistanceNappal(timeStateIndex2);
                
                differences = differencesCalculator(azonositoColumn1,
                        azonositoColumn2, impactAreaDistanceDay1, impactAreaDistanceDay2);
                break;
                
            case IMPACT_AREA_DISTANCE_NIGHT_DIFFERENCE:
                List<Double> impactAreaDistanceNight1 = result1.
                        getImpactAreaDistanceEjjel(timeStateIndex1);
                List<Double> impactAreaDistanceNight2 = result2.
                        getImpactAreaDistanceEjjel(timeStateIndex2);
                
                differences = differencesCalculator(azonositoColumn1, 
                        azonositoColumn2, impactAreaDistanceNight1, impactAreaDistanceNight2);
                break;
                
            default:
                 throw new IllegalArgumentException("Nem megfelelõ választott opció");                  
        }
        return differences;
    }

    public List<List<Object>> sort(SortBy sortBy, int resultIndex) {
        switch (sortBy) {
            case DAY_DECIBEL_SORT:
             // Get the data from the file
                List<List<Object>> dayDecibelData = 
                        new ArrayList<>(calculatorRepository.
                                getFilteredDbfData(resultIndex).getRecord());

                // Get the column index by its name
                String dayDecibelColumnName = "LAeq Nappal";
                int dayDecibelColumnIndex = getColumnIndex(dayDecibelColumnName, 
                        resultIndex);

                // Sort the data based on the dayDecibelValues column
                dayDecibelData.sort((row1, row2) -> compareValues(row1.
                        get(dayDecibelColumnIndex), row2.get(dayDecibelColumnIndex)));
                
                return reorderRows(dayDecibelData);
            
            case NIGHT_DECIBEL_SORT:
                // Get the data from the file
                List<List<Object>> nightDecibelData = 
                        new ArrayList<>(calculatorRepository.
                                getFilteredDbfData(resultIndex).getRecord());

                // Get the column index by its name
                String nightDecibelColumnName = "LAeq Ejjel";
                int nightDecibelColumnIndex = 
                        getColumnIndex(nightDecibelColumnName, resultIndex);

                // Sort the data based on the dayDecibelValues column
                nightDecibelData.sort((row1, row2) -> 
                        compareValues(row1.get(nightDecibelColumnIndex), 
                                row2.get(nightDecibelColumnIndex)));

                return reorderRows(nightDecibelData);

            case PROTECTIVE_DISTANCE_DAY_SORT:
                // Get the data from the file
                List<List<Object>> dayProtDistData = 
                        new ArrayList<>(calculatorRepository.
                                getFilteredDbfData(resultIndex).getRecord());

                // Get the column index by its name
                String dayProtDistColumnName = "Vedotavolsag Nappal";
                int dayProtDistColumnIndex = 
                        getColumnIndex(dayProtDistColumnName, resultIndex);

                // Sort the data based on the dayDecibelValues column
                dayProtDistData.sort((row1, row2) -> 
                        compareValues(row1.get(dayProtDistColumnIndex), 
                                row2.get(dayProtDistColumnIndex)));

                return reorderRows(dayProtDistData);

            case PROTECTIVE_DISTANCE_NIGHT_SORT:
                // Get the data from the file
                List<List<Object>> nightProtDistData = 
                        new ArrayList<>(calculatorRepository.
                                getFilteredDbfData(resultIndex).getRecord());

                // Get the column index by its name
                String nightProtDistColumnName = "Vedotavolsag Ejjel";
                int nightProtDistColumnIndex = 
                        getColumnIndex(nightProtDistColumnName, resultIndex);

                // Sort the data based on the dayDecibelValues column
                nightProtDistData.sort((row1, row2) -> 
                        compareValues(row1.get(nightProtDistColumnIndex), 
                                row2.get(nightProtDistColumnIndex)));

                return reorderRows(nightProtDistData);
                
            case IMPACT_AREA_DISTANCE_DAY_SORT:
                // Get the data from the file
                List<List<Object>> dayImpDistData = 
                        new ArrayList<>(calculatorRepository.
                                getFilteredDbfData(resultIndex).getRecord());

                // Get the column index by its name
                String dayImpDistColumnName = "Hatasterulet Nappal";
                int dayImpDistColumnIndex = 
                        getColumnIndex(dayImpDistColumnName, resultIndex);

                // Sort the data based on the dayDecibelValues column
                dayImpDistData.sort((row1, row2) -> 
                        compareValues(row1.get(dayImpDistColumnIndex),
                                row2.get(dayImpDistColumnIndex)));

                return reorderRows(dayImpDistData);
                
            case IMPACT_AREA_DISTANCE_NIGHT_SORT:
                // Get the data from the file
                List<List<Object>> nightImpDistData = 
                        new ArrayList<>(calculatorRepository.
                                getFilteredDbfData(resultIndex).getRecord());

                // Get the column index by its name
                String nightImpDistColumnName = "Hatasterulet Ejjel";
                int nightImpDistColumnIndex = 
                        getColumnIndex(nightImpDistColumnName, resultIndex);

                // Sort the data based on the dayDecibelValues column
                nightImpDistData.sort((row1, row2) -> 
                        compareValues(row1.get(nightImpDistColumnIndex), 
                                row2.get(nightImpDistColumnIndex)));

                return reorderRows(nightImpDistData);
                
            case ORIGINAL_ORDER:
                // Get the data from the file
                List<List<Object>> originalData = 
                        new ArrayList<>(calculatorRepository.
                                getFilteredDbfData(resultIndex).getRecord());
                return originalData;

            case DAY_DECIBEL_DIFFERENCE_SORT:
             // Get the data from the file
                List<List<Object>> dayDecibelDataDifference = 
                        new ArrayList<>(calculatorRepository.
                                getFilteredDbfData(resultIndex).getRecord());

                // Get the column index by its name
                String dayDecibelDifferenceColumnName = "LAeq Nappal különbsége";
                int dayDecibelDifferenceColumnIndex = 
                        getColumnIndex(dayDecibelDifferenceColumnName, resultIndex);

                // Sort the data based on the dayDecibelValues column
                dayDecibelDataDifference.sort((row1, row2) -> 
                        compareValues(row1.get(dayDecibelDifferenceColumnIndex),
                                row2.get(dayDecibelDifferenceColumnIndex)));
                
                return reorderRows(dayDecibelDataDifference);
            
            case NIGHT_DECIBEL_DIFFERENCE_SORT:
                // Get the data from the file
                List<List<Object>> nightDecibelDataDifference = 
                        new ArrayList<>(calculatorRepository.
                                getFilteredDbfData(resultIndex).getRecord());

                // Get the column index by its name
                String nightDecibelDifferenceColumnName = "LAeq Ejjel különbsége";
                int nightDecibelDifferenceColumnIndex = 
                        getColumnIndex(nightDecibelDifferenceColumnName, resultIndex);

                // Sort the data based on the dayDecibelValues column
                nightDecibelDataDifference.sort((row1, row2) -> 
                        compareValues(row1.get(nightDecibelDifferenceColumnIndex),
                                row2.get(nightDecibelDifferenceColumnIndex)));

                return reorderRows(nightDecibelDataDifference);
            default:
                throw new IllegalArgumentException("Nem megfelelõ választott opció");
        }       
    }
    
    public List<Double> differencesCalculator(
            List<Object> azonosito1, 
            List<Object> azonosito2, 
            List<Double> values1, 
            List<Double> values2) {
        
        List<Double> differences = new ArrayList<>();

        // Create a map to store the indexes of azonosito2 values
        Map<Object, Integer> azonosito2IndexMap = new HashMap<>();
        for (int i = 0; i < azonosito2.size(); i++) {
            Object azonositoValue = azonosito2.get(i);
            azonosito2IndexMap.put(azonositoValue, i);
        }

        int remainingCount = azonosito1.size();

        for (int i = 0; i < values1.size(); i++) {
            long azonositoValue1 = (Long) azonosito1.get(i);

            // Lookup the matching index in azonosito2 list
            Integer matchingIndex = azonosito2IndexMap.get(azonositoValue1);

            if (matchingIndex != null) {
                double difference = values1.get(i) - values2.get(matchingIndex);
                difference = Math.round(difference * 10) / 10.0;
                differences.add(difference);
                remainingCount--;
            } else {
                // Check if the value is present in azonosito1 but not in azonosito2
                if (azonosito2.contains(azonositoValue1)) {
                    differences.add(CLOSED_ROAD);
                    remainingCount--;
                } else {
                    differences.add(NEW_ROAD);
                    remainingCount--;
                }
            }

            if (remainingCount == 0) {
                break;
            }
        }

        // Add remaining CLOSED_ROAD values
        for (int i = 0; i < remainingCount; i++) {
            differences.add(CLOSED_ROAD);
        }

        return differences;
    }

    
    

    private int getColumnIndex(String columnName, int resultIndex) {
        List<String> columnNames = calculatorRepository.
                getFilteredDbfData(resultIndex).getColumns();  
        // Default behavior for other cases
        int columnIndex = columnNames.indexOf(columnName);
        
        if (columnIndex != -1) {
            return columnIndex;
        } else {
            for (int i = 0; i < columnNames.size(); i++) {
                String currentColumnName = columnNames.get(i);
                if (currentColumnName.contains(columnName)) {
                    return i;
                }
            }       
        }
        // Column not found
        return -1;
    }
    
    private int compareValues(Object value1, Object value2) {
        if (value1 instanceof Double && value2 instanceof Double) {
            return Double.compare((Double) value2, (Double) value1);
        } else if (value1 instanceof Integer && value2 instanceof Integer) {
            return Integer.compare((Integer) value2, (Integer) value1);
        } else if (value1 instanceof Long && value2 instanceof Long) {
            return Long.compare((Long) value2, (Long) value1);
        } else {
            String stringValue1 = String.valueOf(value1);
            String stringValue2 = String.valueOf(value2);
            return stringValue2.compareTo(stringValue1);
        }
    }
    
    private List<List<Object>> reorderRows(List<List<Object>> originalFile) {
        List<List<Object>> sortedData = new ArrayList<>(originalFile.size());
        for (int i = 0; i < originalFile.size(); i++) {
            int originalIndex = i;
            int sortedIndex = originalFile.indexOf(originalFile.get(originalIndex));
            sortedData.add(originalFile.get(sortedIndex));
        }
        return sortedData;
    }
}
