/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculators.NoiseEmissionCalculators;

import FileReadingAndDataStore.DataStore.DbfData;
import Calculators.Repository.CalculatorRepository;
import Calculators.PartialResultsForCalculations.KDFactorCalculator;
import Calculators.PartialResultsForCalculations.KtFactorCalculator;
import Calculators.Parameters.NoiseEmissionParameters;
import Calculators.Parameters.NoiseEmissionParametersManager;
import Calculators.Results.NoiseEmissionResult;
import Calculators.PartialResultsForCalculations.VehicleCountAndSpeed;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author szaboa
 */
/**
 * The NoiseEmissionCalculator class calculates the noise emission from a given set of traffic data and parameters,
 * and provides methods for calculating protective distance and impact area distance.
 */
public class NoiseEmissionCalculator {    
    private final CalculatorRepository calculatorRepository;
    private NoiseEmissionResult noiseEmissionResult;
    private NoiseEmissionParameters parameters;
    private NoiseEmissionParametersManager parametersManager;
    
    private List<Double> dayDecibelValues;
    private List<Double> nightDecibelValues;
    private List<Double> protectiveDistanceNappal;
    private List<Double> protectiveDistanceEjjel;
    private List<Double> impactAreaDistanceNappal;
    private List<Double> impactAreaDistanceEjjel;
    private List<Double> soundPowerLevelNappal;
    private List<Double> soundPowerLevelEjjel;
    private int rowIndex = 0;
    private boolean isValidRowForDay;
    private boolean isValidRowForNight;
    private int index;
    /**
    * Represents the vehicle hours for different time periods.
    * The array stores the vehicle hours in the following order: [0-3]: day hours (6am - 10pm), [4-6] night hours (10pm - 6am).
    * Summarizes the two traffic directions for each vehicle category.
    */
    private double[] vehicleHours = new double[6];
    
    /**
    * Represents the vehicle speeds for different categories.
    * The array stores the vehicle speeds in the following order: [light vehicles, medium vehicles, heavy vehicles].
    * Summarizes the two traffic directions and uses the bigger value for each vehicle category.
    */
    private double[] vehicleSpeed = new double[3];
    
    /**
    * Represents the Kt factors for different categories.
    * The array stores the Kt factors in the following order: [KtI, KtII, KtIII].
    * Partial result for calculating LAeq [dB].
    */
    private double[] KtFactor = new double[3];
    
    /**
    * Represents the KD factors for different categories.
    * The array stores the KD factors in the following order: [KDNappalI, KDNappalII, KDNappalIII, KDEjjelI, KDEjjelII, KDEjjelIII].
    * Partial result for calculating LAeq [dB].
    */
    private double[] KDFactor = new double[6];
                      
    /**
     * Constructor for the NoiseEmissionCalculator class.
     *
     * @param calculatorRepository The repository to retrieve the necessary data for calculations.
     * @param noiseEmissionResult  The result object to store the calculated noise emission values.
     * @param index                The index value for data retrieval for the specified indexed file.
     * @param parametersManager    The manager for handling noise emission parameters.
     */
    public NoiseEmissionCalculator(
            CalculatorRepository calculatorRepository,
            NoiseEmissionResult noiseEmissionResult,
            int index,
            NoiseEmissionParametersManager parametersManager) {
        
        this.calculatorRepository = calculatorRepository;
        this.noiseEmissionResult = noiseEmissionResult;
        this.parametersManager = parametersManager;
        
        this.index = index;
        
        this.dayDecibelValues = new ArrayList<>();
        this.nightDecibelValues = new ArrayList<>();
        this.protectiveDistanceNappal = new ArrayList<>();
        this.protectiveDistanceEjjel = new ArrayList<>();
        this.impactAreaDistanceNappal = new ArrayList<>();
        this.impactAreaDistanceEjjel = new ArrayList<>();
        this.soundPowerLevelNappal = new ArrayList<>();
        this.soundPowerLevelEjjel = new ArrayList<>();
         
      //  this.filteredDbfData = calculatorRepository.getFilteredDbfData(index);
    }
    
    /**
     * Sets the noise emission parameters for the calculator.
     *
     * @param parameters The parameters used in the noise emission calculation.
     */
    public void setNoiseEmissionParameters(NoiseEmissionParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Performs the noise emission calculations and updates the noiseEmissionResult with the calculated values.
     *
     * @param resultIndex The index of the result to be updated.
     */
    public void calculations(int resultIndex) {
        calculateEquivalentNoiseEmission();
        calculateProtectiveDistance();
        calculateImpactAreaDistance();

        // Perform calculations and set the values in noiseEmissionResult
        noiseEmissionResult.setDayDecibelValues(dayDecibelValues, resultIndex);
        noiseEmissionResult.setNightDecibelValues(nightDecibelValues, resultIndex);
        noiseEmissionResult.setProtectiveDistanceNappal(protectiveDistanceNappal, resultIndex);
        noiseEmissionResult.setProtectiveDistanceEjjel(protectiveDistanceEjjel, resultIndex);
        noiseEmissionResult.setImpactAreaDistanceNappal(impactAreaDistanceNappal, resultIndex);
        noiseEmissionResult.setImpactAreaDistanceEjjel(impactAreaDistanceEjjel, resultIndex);
        noiseEmissionResult.setSoundPowerLevelNappal(soundPowerLevelNappal, resultIndex);
        noiseEmissionResult.setSoundPowerLevelEjjel(soundPowerLevelEjjel, resultIndex);

    }

    /**
     * Calculates the equivalent noise emission [dB] for the given traffic data.
     */
    public void calculateEquivalentNoiseEmission() {
        DbfData filteredDbfData = calculatorRepository.getFilteredDbfData(index);
        // Calculate equivalent noise emission     
        if (filteredDbfData != null) {
            // Iterate over each row and calculate the decibel values
            for (int i = 0; i < filteredDbfData.getRowCount(); i++) {
                rowIndex = i; // Set the rowIndex
                
                // Calculate vehicle hours and speed
                VehicleCountAndSpeed vehicleCountAndSpeed = 
                        new VehicleCountAndSpeed(filteredDbfData, rowIndex, parameters);
                vehicleHours = vehicleCountAndSpeed.calculateVehicleHours();
                vehicleSpeed = vehicleCountAndSpeed.calculateVehicleSpeed();
 
                // Check for valid rows
                checkValidRowForDay(filteredDbfData);
                checkValidRowForNight(filteredDbfData);
                
                // Calculate Kt Factor
                KtFactorCalculator ktFactorCalculator = new KtFactorCalculator(
                        vehicleSpeed,
                        parameters,
                        parametersManager,
                        index,
                        rowIndex);
                KtFactor = ktFactorCalculator.calculateKtFactor();
                
                // Calculate KD Factor
                KDFactorCalculator kdFactorCalculator = new KDFactorCalculator(
                        vehicleHours,
                        vehicleSpeed,
                        parameters);
                KDFactor = kdFactorCalculator.calculateKDFactor();

                // Name the datas
                double KtI = KtFactor[0];
                double KtII = KtFactor[1];
                double KtIII = KtFactor[2];
                
                double KDNappalI = KDFactor[0];
                double KDNappalII = KDFactor[1];
                double KDNappalIII = KDFactor[2];
                double KDEjjelI = KDFactor[3];
                double KDEjjelII = KDFactor[4];
                double KDEjjelIII = KDFactor[5];

                // Caulculate noise emission for each direction AND check for valid rows
                double LAeqNappal = 0.0;
                double LAeqEjjel = 0.0;

                if (isValidRowForDay) {
                    LAeqNappal = calculateLAeqNappal(KDNappalI, KDNappalII, 
                            KDNappalIII, KtI, KtII, KtIII);
                    LAeqNappal = Math.round(LAeqNappal * 10) / 10.0;
                }
                if (isValidRowForNight) {
                    LAeqEjjel = calculateLAeqEjjel(KDEjjelI, KDEjjelII, 
                            KDEjjelIII, KtI, KtII, KtIII);
                    LAeqEjjel = Math.round(LAeqEjjel * 10) / 10.0;
                }                                      
                
                // Calculate sound power level for day and night
                double soPowerLevelNappal = LAeqNappal + parameters.
                        PRESSURE_TO_INTENSITY.getDefaultValue();
                soPowerLevelNappal = Math.round(soPowerLevelNappal * 10) / 10.0;
                double soPowerLevelEjjel = LAeqEjjel + parameters.
                        PRESSURE_TO_INTENSITY.getDefaultValue();
                soPowerLevelEjjel = Math.round(soPowerLevelEjjel * 10) / 10.0;

                // Add the day and night decibel values to the lists
                dayDecibelValues.add(LAeqNappal);
                nightDecibelValues.add(LAeqEjjel);

                // Add the day and night sound power level to the lists
                soundPowerLevelNappal.add(soPowerLevelNappal);
                soundPowerLevelEjjel.add(soPowerLevelEjjel);  
                
                // clear vehicleHours and speed
                vehicleHours = new double[6];
                vehicleSpeed = new double[3];
                KtFactor = new double[3];
                KDFactor = new double[6];
            }
        }
    }  
    
    /**
     * Calculates the protective distance [m].
     */
    public void calculateProtectiveDistance() {
        ProtectiveDistanceCalculator distanceCalculator = 
                new ProtectiveDistanceCalculator(
            parametersManager,
            parameters,
            index,
            dayDecibelValues,
            nightDecibelValues
        );

        distanceCalculator.calculateProtectiveDistance();
        protectiveDistanceNappal = distanceCalculator.getProtectiveDistanceNappal();
        protectiveDistanceEjjel = distanceCalculator.getProtectiveDistanceEjjel();
    }

   /**
     * Calculates the impact area distance [m].
     */
    public void calculateImpactAreaDistance() {
        ImpactAreaDistanceCalculator distanceCalculator = 
                new ImpactAreaDistanceCalculator(
                parametersManager,
                parameters,
                index,
                dayDecibelValues,
                nightDecibelValues
        );
        
        distanceCalculator.calculateImpactAreaDistance();
        impactAreaDistanceNappal = distanceCalculator.getImpactAreaDistanceNappal();
        impactAreaDistanceEjjel = distanceCalculator.getImpactAreaDistanceEjjel();
    }
    
    /**
     * Checks if the row is valid for day calculations:
     *  traffic or speed is not null for the given three vehicle category.
     *
     * @param filteredDbfData The filtered DBF data
     * @return true if the row is valid for day calculations, false otherwise
     */
    public boolean checkValidRowForDay(DbfData filteredDbfData) {  
        if ((vehicleHours[0] + vehicleHours[1] + vehicleHours[2] == 0)
            ||
            (vehicleSpeed[0] + vehicleSpeed[1] + vehicleSpeed[2] == 0)) {
            
            return isValidRowForDay = false;
        }
        return isValidRowForDay = true;
    }
    
    /**
     * Checks if the row is valid for night calculations:
     *  traffic or speed is not null for the given three vehicle category.
     *
     * @param filteredDbfData The filtered DBF data
     * @return true if the row is valid for night calculations, false otherwise
     */
    public boolean checkValidRowForNight(DbfData filteredDbfData) {
        if ((vehicleHours[3] + vehicleHours[4] + vehicleHours[5] == 0)
            ||
            (vehicleSpeed[0] + vehicleSpeed[1] + vehicleSpeed[2] == 0)) {        
                   
            return isValidRowForNight = false;
        }
        return isValidRowForNight = true;
    }
    
    /**
     * Calculates the LAeq value [dB] for the day.
     * Using the KD and Kt factors for each vehicle category.
     *
     * @param KDNappalI   The KD factor for Nappal I
     * @param KDNappalII  The KD factor for Nappal II
     * @param KDNappalIII The KD factor for Nappal III
     * @param KtI         The Kt factor for I
     * @param KtII        The Kt factor for II
     * @param KtIII       The Kt factor for III
     * @return the calculated LAeq value for the day
     */
    public double calculateLAeqNappal(double KDNappalI, double KDNappalII, 
            double KDNappalIII, double KtI, double KtII, double KtIII) {
        double LAeqNappal = 0.0;
        if (!Double.isNaN(KDNappalI)) {
            LAeqNappal += Math.pow(10, 0.1 * (KtI + KDNappalI));
        }

        if (!Double.isNaN(KDNappalII)) {
            LAeqNappal += Math.pow(10, 0.1 * (KtII + KDNappalII));
        }

        if (!Double.isNaN(KDNappalIII)) {
            LAeqNappal += Math.pow(10, 0.1 * (KtIII + KDNappalIII));
        }
        return 10 * Math.log10(LAeqNappal);
    }
    
    /**
     * Calculates the LAeq value for the night.
     * Using the KD and Kt factors for each vehicle category.
     *
     * @param KDEjjelI   The KD factor for Ejjel I
     * @param KDEjjelII  The KD factor for Ejjel II
     * @param KDEjjelIII The KD factor for Ejjel III
     * @param KtI         The Kt factor for I
     * @param KtII        The Kt factor for II
     * @param KtIII       The Kt factor for III
     * @return the calculated LAeq value for the night
     */
    public double calculateLAeqEjjel(double KDEjjelI, double KDEjjelII, 
            double KDEjjelIII, double KtI, double KtII, double KtIII) {
        double LAeqEjjel = 0.0;
        if (!Double.isNaN(KDEjjelI)) {
            LAeqEjjel += Math.pow(10, 0.1 * (KtI + KDEjjelI));
        }

        if (!Double.isNaN(KDEjjelII)) {
            LAeqEjjel += Math.pow(10, 0.1 * (KtII + KDEjjelII));
        }

        if (!Double.isNaN(KDEjjelIII)) {
            LAeqEjjel += Math.pow(10, 0.1 * (KtIII + KDEjjelIII));
        }
        return 10 * Math.log10(LAeqEjjel);
    } 
    
}
