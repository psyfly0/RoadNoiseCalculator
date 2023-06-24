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
 * The ImpactAreaDistanceCalculator class is responsible for calculating impact area distances
 * based on noise emission parameters and decibel values for both day and night periods.
 */
public class ImpactAreaDistanceCalculator {
    private NoiseEmissionParametersManager parametersManager;
    private NoiseEmissionParameters parameters;
    private int index;
    private List<Double> dayDecibelValues;
    private List<Double> nightDecibelValues;
    private List<Double> impactAreaDistanceNappal;
    private List<Double> impactAreaDistanceEjjel;

    /**
     * Constructs an ImpactAreaDistanceCalculator object with the specified parameters.
     *
     * @param parametersManager    The manager for noise emission parameters.
     * @param parameters           The noise emission parameters used for calculation.
     * @param index                The index value used for parameter lookup in the parametersManager.
     * @param dayDecibelValues     The list of decibel values for the day period.
     * @param nightDecibelValues   The list of decibel values for the night period.
     */
    public ImpactAreaDistanceCalculator(
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
        this.impactAreaDistanceNappal = new ArrayList<>();
        this.impactAreaDistanceEjjel = new ArrayList<>();
    }

    /**
     * Calculates the impact area distances [m] based on the provided decibel values and parameters.
     * The calculated distances are stored in the impactAreaDistanceNappal and impactAreaDistanceEjjel lists.
     */
    public void calculateImpactAreaDistance() {
        for (int i = 0; i < dayDecibelValues.size(); i++) {
            double dayLimit = parametersManager.
                    getParameterValue(index, i, parameters.LTHNAPPAL);
            double viewingAngle = parametersManager.
                    getParameterValue(index, i, parameters.LATOSZOG);
            double reflection = parametersManager.
                    getParameterValue(index, i, parameters.KR);
            double soundAbsorptionFactor = parametersManager.
                    getParameterValue(index, i, parameters.C);

            double impAreaDistanceNappal = parameters.REF_DISTANCE.getDefaultValue() /
                    Math.pow(10, (((dayLimit - 10) - dayDecibelValues.get(i) - 
                    (10 * Math.log10(viewingAngle / parameters.ANGLE_OF_VIEW.
                    getDefaultValue()))) - reflection) / soundAbsorptionFactor);
            
            impAreaDistanceNappal = Math.round(impAreaDistanceNappal * 10) / 10.0;
            impactAreaDistanceNappal.add(impAreaDistanceNappal);
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

            double impAreaDistanceEjjel = parameters.REF_DISTANCE.getDefaultValue() /
                    Math.pow(10, (((nightLimit - 10) - nightDecibelValues.get(i) - 
                    (10 * Math.log10(viewingAngle / parameters.ANGLE_OF_VIEW.
                    getDefaultValue()))) - reflection) / soundAbsorptionFactor);
            
            impAreaDistanceEjjel = Math.round(impAreaDistanceEjjel * 10) / 10.0;
            impactAreaDistanceEjjel.add(impAreaDistanceEjjel);
        }
    }

    /**
     * Returns the list of impact area distances for the day period.
     *
     * @return The list of impact area distances for the day period.
     */
    public List<Double> getImpactAreaDistanceNappal() {
        return impactAreaDistanceNappal;
    }

    /**
     * Returns the list of impact area distances for the night period.
     *
     * @return The list of impact area distances for the night period.
     */
    public List<Double> getImpactAreaDistanceEjjel() {
        return impactAreaDistanceEjjel;
    }    
}
