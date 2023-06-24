/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculators.Parameters;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author szaboa
 */
/**
 * The `NoiseEmissionParametersManager` class is responsible for managing and manipulating the modifiable parameter values for noise emission calculations.
 * It provides methods for setting, getting, and removing parameter values for specific files and rows.
 */
public class NoiseEmissionParametersManager {
    /**
     * A map that stores the parameter values for each file, row, and parameter combination.
     * The map is structured as follows: fileIndex &rarr; rowIndex &rarr; parameter &rarr; value
     */
    private Map<Integer, Map<Integer, Map<NoiseEmissionParameters, Double>>> parameterValues;

    /**
     * Constructs a new `NoiseEmissionParametersManager` object.
     * Initializes the `parameterValues` map.
     */
    public NoiseEmissionParametersManager() {
        parameterValues = new HashMap<>();
    }

    /**
     * Sets the parameter values for a specific file and row.
     * Overrides any existing values for the specified file and row.
     *
     * @param fileIndex   The index of the file.
     * @param rowIndex    The index of the row.
     * @param parameters  A map containing the parameter-value pairs.
     */
    public void setParameterValue(
            int fileIndex,
            int rowIndex,
            Map<NoiseEmissionParameters,
            Double> parameters) {
        
        Map<Integer, Map<NoiseEmissionParameters, Double>> fileParameters = 
                parameterValues.computeIfAbsent(fileIndex, k -> new HashMap<>());
        Map<NoiseEmissionParameters, Double> rowParameters = 
                fileParameters.computeIfAbsent(rowIndex, k -> new HashMap<>());
        
        double lejtesEmelkedes = 0.0;
        double forgalomTipusa = 0.0;
              
        for (Map.Entry<NoiseEmissionParameters, Double> entry : parameters.entrySet()) {
            NoiseEmissionParameters parameter = entry.getKey();
            double value = entry.getValue();
            
            if (parameter == NoiseEmissionParameters.LEJTES_EMELKEDES) {
                lejtesEmelkedes = value;
            }            
            if (parameter == NoiseEmissionParameters.FORGALOM_TIPUSA) {
                forgalomTipusa = value;
            }
            
            if (isParameterModifiable(parameter)) {
                rowParameters.put(parameter, value);                
            } else {
                throw new IllegalArgumentException("Parameter cannot "
                        + "be modified: " + parameter);
            }
        }
        double IajkP = calculateIAJK_PARAMETER_P(lejtesEmelkedes, forgalomTipusa);
        double II_IIIajkP = calculateII_ES_IIIAJK_PARAMETER_P(lejtesEmelkedes, forgalomTipusa);
        rowParameters.put(NoiseEmissionParameters.IAJK_PARAMETER_P, IajkP);
        rowParameters.put(NoiseEmissionParameters.II_ES_IIIAJK_PARAMETER_P, II_IIIajkP);
        
    }

    /**
     * Sets the parameter values for all rows of a specific file.
     * Overrides any existing values for the specified file.
     *
     * @param fileIndex    The index of the file.
     * @param parameters   A map containing the parameter-value pairs.
     * @param totalRows    The total number of rows in the file.
     */
    public void setParameterValueForAllRows(
            int fileIndex,
            Map<NoiseEmissionParameters,
            Double> parameters,
            int totalRows) {
        
        Map<Integer, Map<NoiseEmissionParameters, Double>> fileParameters =
                parameterValues.computeIfAbsent(fileIndex, k -> new HashMap<>());
        fileParameters.clear(); // Clear the existing values
        
        double lejtesEmelkedes = 0.0;
        double forgalomTipusa = 0.0;
        
        for (int rowIndex = 0; rowIndex < totalRows; rowIndex++) {
            Map<NoiseEmissionParameters, Double> rowParameters = new HashMap<>();

            for (Map.Entry<NoiseEmissionParameters, Double> parameterEntry : parameters.entrySet()) {
                NoiseEmissionParameters parameter = parameterEntry.getKey();
                double value = parameterEntry.getValue();
            if (parameter == NoiseEmissionParameters.LEJTES_EMELKEDES) {
                lejtesEmelkedes = value;
            }            
            if (parameter == NoiseEmissionParameters.FORGALOM_TIPUSA) {
                forgalomTipusa = value;
            }
                if (isParameterModifiable(parameter)) {
                    rowParameters.put(parameter, value);
                }
            }
        double IajkP = calculateIAJK_PARAMETER_P(lejtesEmelkedes, forgalomTipusa);
        double II_IIIajkP = calculateII_ES_IIIAJK_PARAMETER_P(lejtesEmelkedes, forgalomTipusa);
        rowParameters.put(NoiseEmissionParameters.IAJK_PARAMETER_P, IajkP);
        rowParameters.put(NoiseEmissionParameters.II_ES_IIIAJK_PARAMETER_P, II_IIIajkP);
            fileParameters.put(rowIndex, rowParameters);
        }
    }

    /**
     * Returns the value of a specific parameter for a given file and row.
     * If the parameter is not modifiable, or if no value is found, the default value of the parameter is returned.
     *
     * @param fileIndex   The index of the file.
     * @param rowIndex    The index of the row.
     * @param parameter   The parameter for which to retrieve the value.
     * @return The value of the parameter.
     */
    public double getParameterValue(
            int fileIndex,
            int rowIndex,
            NoiseEmissionParameters parameter) {
        
        if (isParameterModifiable(parameter)) {
            Map<Integer, Map<NoiseEmissionParameters, Double>> fileParameters =
                    parameterValues.get(fileIndex);
            if (fileParameters != null) {
                Map<NoiseEmissionParameters, Double> rowParameters = 
                        fileParameters.get(rowIndex);
                if (rowParameters != null) {
                    Double value = rowParameters.get(parameter);
                    if (value != null) {
                        return value;
                    }
                }
            }
        }
        return parameter.getDefaultValue();
    }
    
    /**
     * Removes all parameter values for a specific file.
     *
     * @param fileIndex   The index of the file.
     */
    public void removeFileParameters(int fileIndex) {
        parameterValues.remove(fileIndex);

        // Update remaining file indexes
        List<Integer> indexesToUpdate = new ArrayList<>();
        for (Map.Entry<Integer, Map<Integer, Map<NoiseEmissionParameters, 
                Double>>> entry : parameterValues.entrySet()) {
            int currentIndex = entry.getKey();
            if (currentIndex > fileIndex) {
                indexesToUpdate.add(currentIndex);
            }
        }

        // Decrement file indexes in parameterValues map
        for (int indexToUpdate : indexesToUpdate) {
            Map<Integer, Map<NoiseEmissionParameters, Double>> fileParameters =
                    parameterValues.remove(indexToUpdate);
            parameterValues.put(indexToUpdate - 1, fileParameters);
        }
    }
    
    /**
     * Removes all parameter values from the manager.
     */
    public void removeAllParameters() {
        parameterValues.clear();
    }
    
    /**
     * Calculates the value of the `IAJK_PARAMETER_P` based on the `LEJTES_EMELKEDES` and `FORGALOM_TIPUSA` values.
     *
     * @param LEJTES_EMELKEDES   The value of the `LEJTES_EMELKEDES` parameter.
     * @param FORGALOM_TIPUSA    The value of the `FORGALOM_TIPUSA` parameter.
     * @return The calculated value of `IAJK_PARAMETER_P`.
     */
    private double calculateIAJK_PARAMETER_P(double LEJTES_EMELKEDES, double FORGALOM_TIPUSA) {
        if (LEJTES_EMELKEDES == 0 && FORGALOM_TIPUSA == 0) {
                return 0;
            } else {
                switch ((int) FORGALOM_TIPUSA) {
                    case 0:
                        return LEJTES_EMELKEDES;
                    case 1:
                        return LEJTES_EMELKEDES + 2;
                    case 2:
                        return LEJTES_EMELKEDES - 1;
                    default:
                        return 0;
                }
            }
    }

    /**
     * Calculates the value of the `II_ES_IIIAJK_PARAMETER_P` based on the `LEJTES_EMELKEDES` and `FORGALOM_TIPUSA` values.
     *
     * @param LEJTES_EMELKEDES   The value of the `LEJTES_EMELKEDES` parameter.
     * @param FORGALOM_TIPUSA    The value of the `FORGALOM_TIPUSA` parameter.
     * @return The calculated value of `II_ES_IIIAJK_PARAMETER_P`.
     */
    private double calculateII_ES_IIIAJK_PARAMETER_P(double LEJTES_EMELKEDES, double FORGALOM_TIPUSA) {
        if (LEJTES_EMELKEDES == 0 && FORGALOM_TIPUSA == 0) {
            return 0;
        } else if (LEJTES_EMELKEDES > 0) {
            switch ((int) FORGALOM_TIPUSA) {
                case 0:
                    return LEJTES_EMELKEDES;
                case 1:
                    return LEJTES_EMELKEDES + 4;
                case 2:
                    return LEJTES_EMELKEDES - 3;
                default:
                    return 0;
            }
        } else {
            switch ((int) FORGALOM_TIPUSA) {
                case 0:
                    return -LEJTES_EMELKEDES;
                case 1:
                    return LEJTES_EMELKEDES + 4;
                case 2:
                    return -LEJTES_EMELKEDES - 3;
                default:
                    return 0;
            }
        }
    }
    
    /**
     * Checks if a parameter is modifiable or not.
     *
     * @param parameter   The parameter to check.
     * @return `true` if the parameter is modifiable, `false` otherwise.
     */
    private boolean isParameterModifiable(NoiseEmissionParameters parameter) {
        switch (parameter) {
            case UTBURKOLAT_ERDESEG:
            case LTHNAPPAL:
            case LTHEJJEL:
            case LATOSZOG:
            case C:
            case KR:
            case LEJTES_EMELKEDES:
            case FORGALOM_TIPUSA:
            case IAJK_PARAMETER_P:
            case II_ES_IIIAJK_PARAMETER_P:
                return true;
            default:
                return false;
        }
    }
}

