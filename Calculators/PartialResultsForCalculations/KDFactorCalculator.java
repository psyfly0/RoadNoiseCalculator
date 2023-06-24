/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculators.PartialResultsForCalculations;

import Calculators.Parameters.NoiseEmissionParameters;

/**
 *
 * @author szaboa
 */
/**
 * The KDFactorCalculator class is responsible for calculating the KD factor based on the provided vehicle hours,
 * vehicle speed, and noise emission parameters.
 */
public class KDFactorCalculator {
    private final double[] vehicleHours;
    private final double[] vehicleSpeed;
    private final NoiseEmissionParameters parameters;

    /**
     * Constructs a KDFactorCalculator object with the specified parameters.
     *
     * @param vehicleHours   The array of vehicle hours used for calculation.
     * @param vehicleSpeed   The array of vehicle speeds used for calculation.
     * @param parameters     The noise emission parameters used for calculation.
     */
    public KDFactorCalculator(
            double[] vehicleHours,
            double[] vehicleSpeed,
            NoiseEmissionParameters parameters) {
        
        this.vehicleHours = vehicleHours;
        this.vehicleSpeed = vehicleSpeed;
        this.parameters = parameters;
    }

    /**
     * Calculates the KD factor for different vehicle categories.
     * The array stores the KD factors in the following order: [KDNappalI, KDNappalII, KDNappalIII, KDEjjelI, KDEjjelII, KDEjjelIII].
     * Partial result for calculating LAeq [dB].
     * 
     * @return An array of doubles representing the KD factor for different vehicle categories.
     */
    public double[] calculateKDFactor() {
        double[] KD = new double[6];

        KD[0] = (vehicleHours[0] != 0 && vehicleSpeed[0] != 0) ? (10 * 
                Math.log10(vehicleHours[0] /
                vehicleSpeed[0]) - parameters.KDFACTOR.getDefaultValue()) : Double.NaN;
        KD[1] = (vehicleHours[1] != 0 && vehicleSpeed[1] != 0) ? (10 * 
                Math.log10(vehicleHours[1] /
                vehicleSpeed[1]) - parameters.KDFACTOR.getDefaultValue()) : Double.NaN;
        KD[2] = (vehicleHours[2] != 0 && vehicleSpeed[2] != 0) ? (10 * 
                Math.log10(vehicleHours[2] /
                vehicleSpeed[2]) - parameters.KDFACTOR.getDefaultValue()) : Double.NaN;
        KD[3] = (vehicleHours[3] != 0 && vehicleSpeed[0] != 0) ? (10 * 
                Math.log10(vehicleHours[3] /
                vehicleSpeed[0]) - parameters.KDFACTOR.getDefaultValue()) : Double.NaN;
        KD[4] = (vehicleHours[4] != 0 && vehicleSpeed[1] != 0) ? (10 * 
                Math.log10(vehicleHours[4] /
                vehicleSpeed[1]) - parameters.KDFACTOR.getDefaultValue()) : Double.NaN;
        KD[5] = (vehicleHours[5] != 0 && vehicleSpeed[2] != 0) ? (10 * 
                Math.log10(vehicleHours[5] /
                vehicleSpeed[2]) - parameters.KDFACTOR.getDefaultValue()) : Double.NaN;

        return KD;
    }  
}
