/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculators.PartialResultsForCalculations;

import Calculators.Parameters.NoiseEmissionParameters;
import FileReadingAndDataStore.DataStore.DbfData;

/**
 *
 * @author szaboa
 */
/**
 * The VehicleCountAndSpeed class is responsible for calculating vehicle hours and speeds based on
 * the provided filtered DbfData, row index, and noise emission parameters.
 */
public class VehicleCountAndSpeed {
    private final DbfData filteredDbfData;
    private final int rowIndex;
    private final NoiseEmissionParameters parameters;

    public VehicleCountAndSpeed(
            DbfData filteredDbfData,
            int rowIndex,
            NoiseEmissionParameters parameters) {
        
        this.filteredDbfData = filteredDbfData;
        this.rowIndex = rowIndex;
        this.parameters = parameters;
    }

    /**
     * Calculates the vehicle hours for different time periods.
     * 
     * The array stores the vehicle hours in the following order: [0-3]: day hours (6am - 10pm), [4-6] night hours (10pm - 6am).
     * Summarizes the two traffic directions for each vehicle category.
     * 
     * @return An array of doubles representing the vehicle hours for different time periods.
     */
    public double[] calculateVehicleHours() {
        double[] vehicleHours = new double[6];
        vehicleHours[0] = Math.ceil((double) filteredDbfData.
                getValue("1akNappal", rowIndex) / parameters.DAY_HOURS.getDefaultValue() +
                (double) filteredDbfData.getValue("R1akNappal", rowIndex) 
                / parameters.DAY_HOURS.getDefaultValue());
        vehicleHours[1] = Math.ceil((double) filteredDbfData.
                getValue("2akNappal", rowIndex) / parameters.DAY_HOURS.getDefaultValue() +
                (double) filteredDbfData.getValue("R2akNappal", rowIndex) 
                / parameters.DAY_HOURS.getDefaultValue());
        vehicleHours[2] = Math.ceil((double) filteredDbfData.
                getValue("3akNappal", rowIndex) / parameters.DAY_HOURS.getDefaultValue() +
                (double) filteredDbfData.getValue("R3akNappal", rowIndex) 
                / parameters.DAY_HOURS.getDefaultValue());
        vehicleHours[3] = Math.ceil((double) filteredDbfData.
                getValue("1akEjjel", rowIndex) / parameters.NIGHT_HOURS.getDefaultValue() +
                (double) filteredDbfData.getValue("R1akEjjel", rowIndex) 
                / parameters.NIGHT_HOURS.getDefaultValue());
        vehicleHours[4] = Math.ceil((double) filteredDbfData.
                getValue("2akEjjel", rowIndex) / parameters.NIGHT_HOURS.getDefaultValue() +
                (double) filteredDbfData.getValue("R2akEjjel", rowIndex) 
                / parameters.NIGHT_HOURS.getDefaultValue());
        vehicleHours[5] = Math.ceil((double) filteredDbfData.
                getValue("3akEjjel", rowIndex) / parameters.NIGHT_HOURS.getDefaultValue() +
                (double) filteredDbfData.getValue("R3akEjjel", rowIndex) 
                / parameters.NIGHT_HOURS.getDefaultValue());

        return vehicleHours;
    }

    /**
     * Calculates the vehicle speeds for different vehicle types.
     *
     * The array stores the vehicle speeds in the following order: [light vehicles, medium vehicles, heavy vehicles].
     * Summarizes the two traffic directions and uses the bigger value for each vehicle category.
     * 
     * @return An array of doubles representing the vehicle speeds for different vehicle types.
     */
    public double[] calculateVehicleSpeed() {
        double[] vehicleSpeed = new double[3];
        vehicleSpeed[0] = Math.max(filteredDbfData.
                getValue("1akSebesseg", rowIndex), filteredDbfData.
                getValue("R1akSebesseg", rowIndex));
        vehicleSpeed[1] = Math.max(filteredDbfData.
                getValue("2akSebesseg", rowIndex), filteredDbfData.
                getValue("R2akSebesseg", rowIndex));
        vehicleSpeed[2] = Math.max(filteredDbfData.
                getValue("3akSebesseg", rowIndex), filteredDbfData.
                getValue("R3akSebesseg", rowIndex));

        return vehicleSpeed;
    }    
}
