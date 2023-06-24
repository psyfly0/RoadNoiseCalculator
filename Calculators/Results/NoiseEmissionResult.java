/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculators.Results;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author szaboa
 */
/**
 * A class that represents the result of a noise emission calculation, containing the calculated
 * values for various parameters such as day and night decibel levels, protective distances, and
 * impact area distances.
 */
public class NoiseEmissionResult {
    private List<List<Double>> dayDecibelValues;
    private List<List<Double>> nightDecibelValues;
    private List<List<Double>> protectiveDistanceNappal;
    private List<List<Double>> protectiveDistanceEjjel;
    private List<List<Double>> impactAreaDistanceNappal;
    private List<List<Double>> impactAreaDistanceEjjel;
    private List<List<Double>> soundPowerLevelNappal;
    private List<List<Double>> soundPowerLevelEjjel;

    /**
     * Constructs a NoiseEmissionResult object with empty lists for all parameters.
     */
    public NoiseEmissionResult() {
        this.dayDecibelValues = new ArrayList<>();
        this.nightDecibelValues = new ArrayList<>();
        this.protectiveDistanceNappal = new ArrayList<>();
        this.protectiveDistanceEjjel = new ArrayList<>();
        this.impactAreaDistanceNappal = new ArrayList<>();
        this.impactAreaDistanceEjjel = new ArrayList<>();
        this.soundPowerLevelNappal = new ArrayList<>();
        this.soundPowerLevelEjjel = new ArrayList<>();
    }
    
    /**
     * Clears the results at the specified index by removing the corresponding lists.
     *
     * @param index The index of the results to be cleared.
     */
    public void clearResults(int index) {
        dayDecibelValues.remove(index);
        nightDecibelValues.remove(index);
        protectiveDistanceNappal.remove(index);
        protectiveDistanceEjjel.remove(index);
        impactAreaDistanceNappal.remove(index);
        impactAreaDistanceEjjel.remove(index);
        soundPowerLevelNappal.remove(index);
        soundPowerLevelEjjel.remove(index);
    }
    
    /**
     * Clears all the results by resetting all lists to empty lists.
     */
    public void clearAllResults() {
        dayDecibelValues = new ArrayList<>();
        nightDecibelValues = new ArrayList<>();
        protectiveDistanceNappal = new ArrayList<>();
        protectiveDistanceEjjel = new ArrayList<>();
        impactAreaDistanceNappal = new ArrayList<>();
        impactAreaDistanceEjjel = new ArrayList<>();
        soundPowerLevelNappal = new ArrayList<>();
        soundPowerLevelEjjel = new ArrayList<>();
    }
    
    // Setter methods

    /**
     * Sets the day decibel values at the specified index.
     *
     * @param dayDecibelValues The list of day decibel values.
     * @param index            The index at which to set the values.
     */
    public void setDayDecibelValues(List<Double> dayDecibelValues, int index) {
        if (index >= this.dayDecibelValues.size()) {
            this.dayDecibelValues.add(dayDecibelValues);
        } else if (!this.dayDecibelValues.get(index).isEmpty()) {
            this.dayDecibelValues.set(index, dayDecibelValues);
        }
 
    }
    
    /**
     * Sets the night decibel values at the specified index.
     *
     * @param nightDecibelValues The list of night decibel values.
     * @param index            The index at which to set the values.
     */
    public void setNightDecibelValues(List<Double> nightDecibelValues, int index) {
        if (index >= this.nightDecibelValues.size()) {
        this.nightDecibelValues.add(nightDecibelValues);
        } else {
            this.nightDecibelValues.set(index, nightDecibelValues);
        }
    }

    /**
     * Sets the day protective distance values at the specified index.
     *
     * @param protectiveDistanceNappal The list of day protective distance values.
     * @param index            The index at which to set the values.
     */
    public void setProtectiveDistanceNappal(List<Double> protectiveDistanceNappal, int index) {
        if (index >= this.protectiveDistanceNappal.size()) {
        this.protectiveDistanceNappal.add(protectiveDistanceNappal);
        } else {
            this.protectiveDistanceNappal.set(index, protectiveDistanceNappal);
        }
    }    
    
    /**
     * Sets the night protective distance values at the specified index.
     *
     * @param protectiveDistanceEjjel The list of night protective distance values.
     * @param index            The index at which to set the values.
     */
    public void setProtectiveDistanceEjjel(List<Double> protectiveDistanceEjjel, int index) {
        if (index >= this.protectiveDistanceEjjel.size()) {
        this.protectiveDistanceEjjel.add(protectiveDistanceEjjel);
        } else {
            this.protectiveDistanceEjjel.set(index, protectiveDistanceEjjel);
        }
    }      
    
    /**
     * Sets the day impact area distance values at the specified index.
     *
     * @param impactAreaDistanceNappal The list of day impact area distance values.
     * @param index            The index at which to set the values.
     */
    public void setImpactAreaDistanceNappal(List<Double> impactAreaDistanceNappal, int index) {
        if (index >= this.impactAreaDistanceNappal.size()) {
        this.impactAreaDistanceNappal.add(impactAreaDistanceNappal);
        } else {
            this.impactAreaDistanceNappal.set(index, impactAreaDistanceNappal);
        }
    }
    
    /**
     * Sets the night impact area distance values at the specified index.
     *
     * @param impactAreaDistanceEjjel The list of night impact area distance values.
     * @param index            The index at which to set the values.
     */
    public void setImpactAreaDistanceEjjel(List<Double> impactAreaDistanceEjjel, int index) {
        if (index >= this.impactAreaDistanceEjjel.size()) {
        this.impactAreaDistanceEjjel.add(impactAreaDistanceEjjel);
        } else {
            this.impactAreaDistanceEjjel.set(index, impactAreaDistanceEjjel);
        }
    }    
    
    /**
     * Sets the day sound power level values at the specified index.
     *
     * @param soundPowerLevelNappal The list of day sound power level values.
     * @param index            The index at which to set the values.
     */
    public void setSoundPowerLevelNappal(List<Double> soundPowerLevelNappal, int index) {
        if (index >= this.soundPowerLevelNappal.size()) {
        this.soundPowerLevelNappal.add(soundPowerLevelNappal);
        } else {
            this.soundPowerLevelNappal.set(index, soundPowerLevelNappal);
        }
    }
    
    /**
     * Sets the night sound power level values at the specified index.
     *
     * @param soundPowerLevelEjjel The list of night sound power level values.
     * @param index            The index at which to set the values.
     */
    public void setSoundPowerLevelEjjel(List<Double> soundPowerLevelEjjel, int index) {
        if (index >= this.soundPowerLevelEjjel.size()) {
        this.soundPowerLevelEjjel.add(soundPowerLevelEjjel);
        } else {
            this.soundPowerLevelEjjel.set(index, soundPowerLevelEjjel);
        }
    }
    
    // Getter methods

    /**
     * Returns the day decibel values at the specified index.
     *
     * @param index The index from which to retrieve the values.
     * @return The list of day decibel values.
     */
    public List<Double> getDayDecibelValues(int index) {
        return dayDecibelValues.get(index);
    }

    /**
     * Returns the night decibel values at the specified index.
     *
     * @param index The index from which to retrieve the values.
     * @return The list of night decibel values.
     */
    public List<Double> getNightDecibelValues(int index) {
        return nightDecibelValues.get(index);
    }

    /**
     * Returns the day protective distance values at the specified index.
     *
     * @param index The index from which to retrieve the values.
     * @return The list of day protective distance values.
     */
    public List<Double> getProtectiveDistanceNappal(int index) {
        return protectiveDistanceNappal.get(index);
    }

    /**
     * Returns the night protective distance values at the specified index.
     *
     * @param index The index from which to retrieve the values.
     * @return The list of night protective distance values.
     */
    public List<Double> getProtectiveDistanceEjjel(int index) {
        return protectiveDistanceEjjel.get(index);
    }

    /**
     * Returns the day impact area distance values at the specified index.
     *
     * @param index The index from which to retrieve the values.
     * @return The list of day impact area distance values.
     */
    public List<Double> getImpactAreaDistanceNappal(int index) {
        return impactAreaDistanceNappal.get(index);
    }

    /**
     * Returns the night impact area distance values at the specified index.
     *
     * @param index The index from which to retrieve the values.
     * @return The list of night impact area distance values.
     */
    public List<Double> getImpactAreaDistanceEjjel(int index) {
        return impactAreaDistanceEjjel.get(index);
    }
    
    /**
     * Returns the day sound power level values at the specified index.
     *
     * @param index The index from which to retrieve the values.
     * @return The list of day sound power level values.
     */
    public List<Double> getSoundPowerLevelNappal(int index) {
        return soundPowerLevelNappal.get(index);
    }

    /**
     * Returns the night sound power level values at the specified index.
     *
     * @param index The index from which to retrieve the values.
     * @return The list of night sound power level values.
     */
    public List<Double> getSoundPowerLevelEjjel(int index) {
        return soundPowerLevelEjjel.get(index);
    }
}
