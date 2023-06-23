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
 * The ProtectiveDistanceCalculator class is responsible for calculating protective distances
 * (the distance at which the limit values ??are met)
 * based on noise emission parameters and decibel values for both day and night periods.
 */
public class ProtectiveDistanceCalculator {
    private final NoiseEmissionParameters parameters;
    private final NoiseEmissionParametersManager parametersManager;
    private int index;
    private List<Double> dayDecibelValues;
    private List<Double> nightDecibelValues;
    private List<Double> protectiveDistanceNappal;
    private List<Double> protectiveDistanceEjjel;

    /**
     * Constructs a ProtectiveDistanceCalculator object with the specified parameters.
     *
     * @param parametersManager    The manager for noise emission parameters.
     * @param parameters           The noise emission parameters used for calculation.
     * @param index                The index value used for parameter lookup in the parametersManager.
     * @param dayDecibelValues     The list of decibel values for the day period.
     * @param nightDecibelValues   The list of decibel values for the night period.
     */
    public ProtectiveDistanceCalculator(
            NoiseEmissionParametersManager parametersManager, 
            NoiseEmissionParameters parameters, 
            int index,
            List<Double> dayDecibelValues, 
            List<Double> nightDecibelValues) {
        
        this.parametersManager = parametersManager;
        this.parameters = parameters;
        this.index = index;
        this.dayDecibelValues = dayDecibelValues;
        this.nightDecibelValues = nightDecibelValues;
        this.protectiveDistanceNappal = new ArrayList<>();
        this.protectiveDistanceEjjel = new ArrayList<>();
    }

    /**
     * Calculates the protective distances [m] based on the provided decibel values and parameters.
     * The calculated distances are stored in the protectiveDistanceNappal and protectiveDistanceEjjel lists.
     */
    public void calculateProtectiveDistance() {
        for (int i = 0; i < dayDecibelValues.size(); i++) {
            double dayLimit = parametersManager.
                    getParameterValue(index, i, parameters.LTHNAPPAL);
            double viewingAngle = parametersManager.
                    getParameterValue(index, i, parameters.LATOSZOG);
            double reflection = parametersManager.
                    getParameterValue(index, i, parameters.KR);
            double soundAbsorptionFactor = parametersManager.
                    getParameterValue(index, i, parameters.C);

            double protDistanceNappal = parameters.REF_DISTANCE.getDefaultValue() /
                    Math.pow(10, ((dayLimit - dayDecibelValues.get(i) - 
                    (10 * Math.log10(viewingAngle / parameters.ANGLE_OF_VIEW.
                    getDefaultValue()))) - reflection) / soundAbsorptionFactor);
            protDistanceNappal = Math.round(protDistanceNappal * 10) / 10.0;
            protectiveDistanceNappal.add(protDistanceNappal);
        }

        for (int i = 0; i < nightDecibelValues.size(); i++) {
            double nightLimit = parametersManager.
                    getParameterValue(index, i, parameters.LTHEJJEL);
            double viewingAngle = parametersManager.
                    getParameterValue(index, i, parameters.LATOSZOG);
            double reflection = parametersManager.
                    getParameterValue(index, i, parameters.KR);
            double soundAbsorptionFactor = parametersManager.
                    getParameterValue(index, i, parameters.C);

            double protDistanceEjjel = parameters.REF_DISTANCE.getDefaultValue() /
                    Math.pow(10, ((nightLimit - nightDecibelValues.get(i) - 
                    (10 * Math.log10(viewingAngle / parameters.ANGLE_OF_VIEW.
                    getDefaultValue()))) - reflection) / soundAbsorptionFactor);
            protDistanceEjjel = Math.round(protDistanceEjjel * 10) / 10.0;
            protectiveDistanceEjjel.add(protDistanceEjjel);
        }
    }

    /**
     * Returns the list of protective distances for the day period.
     *
     * @return The list of protective distances for the day period.
     */
    public List<Double> getProtectiveDistanceNappal() {
        return protectiveDistanceNappal;
    }

    /**
     * Returns the list of protective distances for the night period.
     *
     * @return The list of protective distances for the night period.
     */
    public List<Double> getProtectiveDistanceEjjel() {
        return protectiveDistanceEjjel;
    }
}
