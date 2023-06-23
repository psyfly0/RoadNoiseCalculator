/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculators.NoiseEmissionCalculators;

import Calculators.Parameters.NoiseEmissionParameters;
import Calculators.Parameters.NoiseEmissionParametersManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author szaboa
 */
/**
 * The NoiseEmissionAtGivenDistanceCalculator class is responsible for calculating noise emission
 * at a given distance based on provided decibel values, distance, and noise emission parameters.
 */
public class NoiseEmissionAtGivenDistanceCalculator {
    private NoiseEmissionParametersManager parametersManager;
    private NoiseEmissionParameters parameters;

    /**
     * Constructs a NoiseEmissionAtGivenDistanceCalculator object with the specified parameters.
     *
     * @param parametersManager    The manager for noise emission parameters.
     * @param parameters           The noise emission parameters used for calculation.
     */
    public NoiseEmissionAtGivenDistanceCalculator(
            NoiseEmissionParametersManager parametersManager,
            NoiseEmissionParameters parameters) {
        
        this.parametersManager = parametersManager;
        this.parameters = parameters;
    }

        /**
     * Calculates the noise emission at a given distance for the day period.
     *
     * @param dayDecibelValues   The list of decibel values for the day period.
     * @param distance           The distance at which to calculate the noise emission.
     * @param index              The index value used for parameter lookup in the parametersManager.
     * @return The list of decibel values at the given distance for the day period.
     */
    public List<Double> dayCalculateNoiseEmissionAtGivenDistance(
            List<Double> dayDecibelValues,
            double distance,
            int index) {
        
        List<Double> dayDecibelValuesAtDistance = new ArrayList<>();

        for (int i = 0; i < dayDecibelValues.size(); i++) {
            double viewingAngle = parametersManager.
                    getParameterValue(index, i, parameters.LATOSZOG);
            double reflection = parametersManager.
                    getParameterValue(index, i, parameters.KR);
            double soundAbsorptionFactor = parametersManager.
                    getParameterValue(index, i, parameters.C);

            double dBValue = dayDecibelValues.get(i) +
                    (soundAbsorptionFactor * Math.log10(parameters.REF_DISTANCE.
                            getDefaultValue() / distance)) +
                    (10 * Math.log10(viewingAngle / parameters.ANGLE_OF_VIEW.
                            getDefaultValue())) + reflection;
            dBValue = Math.round(dBValue * 10) / 10.0;

            dayDecibelValuesAtDistance.add(dBValue);
        }
        return dayDecibelValuesAtDistance;
    }

    /**
     * Calculates the noise emission at a given distance for the night period.
     *
     * @param nightDecibelValues   The list of decibel values for the night period.
     * @param distance             The distance at which to calculate the noise emission.
     * @param index                The index value used for parameter lookup in the parametersManager.
     * @return The list of decibel values at the given distance for the night period.
     */
    public List<Double> nightCalculateNoiseEmissionAtGivenDistance(
            List<Double> nightDecibelValues,
            double distance,
            int index) {
        
        List<Double> nightDecibelValuesAtDistance = new ArrayList<>();

        for (int i = 0; i < nightDecibelValues.size(); i++) {
            double viewingAngle = parametersManager.
                    getParameterValue(index, i, parameters.LATOSZOG);
            double reflection = parametersManager.
                    getParameterValue(index, i, parameters.KR);
            double soundAbsorptionFactor = parametersManager.
                    getParameterValue(index, i, parameters.C);

            double dBValue = nightDecibelValues.get(i) +
                    (soundAbsorptionFactor * Math.log10(parameters.REF_DISTANCE.
                            getDefaultValue() / distance)) +
                    (10 * Math.log10(viewingAngle / parameters.ANGLE_OF_VIEW.
                            getDefaultValue())) + reflection;
            dBValue = Math.round(dBValue * 10) / 10.0;

            nightDecibelValuesAtDistance.add(dBValue);
        }
        return nightDecibelValuesAtDistance;
    }
}
