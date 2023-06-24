/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculators.PartialResultsForCalculations;

import Calculators.Parameters.NoiseEmissionParameters;
import Calculators.Parameters.NoiseEmissionParametersManager;

/**
 *
 * @author szaboa
 */
/**
 * The KtFactorCalculator class is responsible for calculating the Kt factor based on the provided vehicle speed,
 * noise emission parameters, noise emission parameters manager, index, and row index.
 */
public class KtFactorCalculator {
    private final double[] vehicleSpeed;
    private double soundAbsorptionFactor;
    private double IajkParameterP;
    private double II_IIIajkParameterP;
    private final NoiseEmissionParameters parameters;
    private final NoiseEmissionParametersManager parametersManager;
    private final int index;
    private final int rowIndex;

    /**
     * Constructs a KtFactorCalculator object with the specified parameters.
     *
     * @param vehicleSpeed         The array of vehicle speeds used for calculation.
     * @param parameters           The noise emission parameters used for calculation.
     * @param parametersManager    The noise emission parameters manager used for calculation.
     * @param index                The index used for accessing specific data in the parametersManager.
     * @param rowIndex             The row index used for accessing specific data in the parametersManager.
     */
    public KtFactorCalculator(
            double[] vehicleSpeed,
            NoiseEmissionParameters parameters,
            NoiseEmissionParametersManager parametersManager,
            int index,
            int rowIndex) {
        
        this.vehicleSpeed = vehicleSpeed;
        this.parameters = parameters;
        this.parametersManager = parametersManager;
        this.index = index;
        this.rowIndex = rowIndex;


    }

    /**
     * Calculates the Kt factor for different vehicle categories.
     * The array stores the Kt factors in the following order: [KtI, KtII, KtIII].
     * Partial result for calculating LAeq [dB].
     * 
     * @return An array of doubles representing the Kt factor for different vehicle categories.
     */
    public double[] calculateKtFactor() {
        double[] Kt = new double[3];
        
        soundAbsorptionFactor = parametersManager.
                getParameterValue(index, rowIndex, parameters.UTBURKOLAT_ERDESEG);
        IajkParameterP = parametersManager.
                getParameterValue(index, rowIndex, parameters.IAJK_PARAMETER_P);
        II_IIIajkParameterP = parametersManager.
                getParameterValue(index, rowIndex, parameters.II_ES_IIIAJK_PARAMETER_P);

        Kt[0] = (vehicleSpeed[0] != 0) ? (10 * Math.log10(Math.pow(10, 
                parameters.AI_CATEGORY1.getDefaultValue() + soundAbsorptionFactor
                + parameters.BI_CATEGORY1.getDefaultValue() * Math.log10(vehicleSpeed[0])) +
                Math.pow(10, parameters.CI_CATEGORY1.getDefaultValue() + 
                parameters.DI_CATEGORY1.getDefaultValue() *
                Math.log10(vehicleSpeed[0])) + Math.pow(10, parameters.
                EI_CATEGORY1.getDefaultValue() +
                parameters.FI_CATEGORY1.getDefaultValue() * 
                Math.log10(11 + IajkParameterP)))) : 0;

        Kt[1] = (vehicleSpeed[1] != 0) ? (10 * Math.log10(Math.pow(10, 
                parameters.AI_CATEGORY2.getDefaultValue() + soundAbsorptionFactor
                + parameters.BI_CATEGORY2.getDefaultValue() * Math.log10(vehicleSpeed[1])) +
                Math.pow(10, parameters.CI_CATEGORY2.getDefaultValue() + 
                parameters.DI_CATEGORY2.getDefaultValue() *
                Math.log10(vehicleSpeed[1])) + Math.pow(10, parameters.
                EI_CATEGORY2.getDefaultValue() +
                parameters.FI_CATEGORY2.getDefaultValue() * 
                Math.log10(11 + II_IIIajkParameterP)))) : 0;

        Kt[2] = (vehicleSpeed[2] != 0) ? (10 * Math.log10(Math.pow(10, 
                parameters.AI_CATEGORY3.getDefaultValue() + soundAbsorptionFactor
                + parameters.BI_CATEGORY3.getDefaultValue() * Math.log10(vehicleSpeed[2])) +
                Math.pow(10, parameters.CI_CATEGORY3.getDefaultValue() + 
                parameters.DI_CATEGORY3.getDefaultValue() *
                Math.log10(vehicleSpeed[2])) + Math.pow(10, parameters.
                EI_CATEGORY3.getDefaultValue() +
                parameters.FI_CATEGORY3.getDefaultValue() * 
                Math.log10(11 + II_IIIajkParameterP)))) : 0;

        return Kt;
    }  
}
