/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculators.Parameters;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author szaboa
 */
/**
 * This enum class represents the different noise emission parameters that are used in the noise emission calculation.
 * Each parameter has a default value and some of them can be overridden by the user.
 */
public enum NoiseEmissionParameters {
    AI_CATEGORY1(2.00),
    AI_CATEGORY2(2.40),
    AI_CATEGORY3(2.70),
    BI_CATEGORY1(2.92),
    BI_CATEGORY2(2.92),
    BI_CATEGORY3(2.92),
    CI_CATEGORY1(3.03),
    CI_CATEGORY2(3.17),
    CI_CATEGORY3(3.90),
    DI_CATEGORY1(2.00),
    DI_CATEGORY2(2.10),
    DI_CATEGORY3(1.86),
    EI_CATEGORY1(2.62),
    EI_CATEGORY2(3.15),
    EI_CATEGORY3(5.07),
    FI_CATEGORY1(3.92),
    FI_CATEGORY2(3.79),
    FI_CATEGORY3(2.53),
    KDFACTOR(16.3),
    REF_DISTANCE(7.5),
    ANGLE_OF_VIEW(180),
    PRESSURE_TO_INTENSITY(12.7),
    DAY_HOURS(16.0),
    NIGHT_HOURS(8.0),
    IAJK_PARAMETER_P(0.0),
    II_ES_IIIAJK_PARAMETER_P(0.0),   
    UTBURKOLAT_ERDESEG(0.29, new Double[]{0.29, 0.0, 0.49, 0.67, 0.78},
            "Útburkolat érdesség"),
    LTHNAPPAL(65.0, new Double[]{65.0, 60.0, 55.0, 50.0}, "Határérték Nappal"),
    LTHEJJEL(55.0, new Double[]{55.0, 50.0, 45.0, 40.0}, "Határérték Éjjel"),
    C(12.5, new Double[]{12.5, 15.0}, "Hangelnyelési tényezõ"),
    KR(0.5, new Double[]{0.5, 1.0, 1.5, 2.0, 2.5, 3.5}, "Reflexió"),
    LATOSZOG(180.0, null, "Látószög"),
    LEJTES_EMELKEDES(0, null, "Emelkedés / Lejtés (%-ban megadva, pozitív "
            + "vagy negatív)"),
    FORGALOM_TIPUSA(0, new Double[]{0.0, 1.0, 2.0}, "Forgalom típusa "
            + "(0 - egyenletes, 1 - gyorsuló, 2 - lassuló");

    private Double defaultValue;
    private Double[] allowedValues;
    private String parameterName;

    /**
     * Constructs a NoiseEmissionParameters enum constant with the given default value.
     *
     * @param defaultValue The default value of the parameter.
     */
    NoiseEmissionParameters(double defaultValue) {
        this.defaultValue = defaultValue;
        this.allowedValues = null;
    }

    /**
     * Constructs a NoiseEmissionParameters enum constant with the given default value, allowed values, and parameter name.
     *
     * @param defaultValue  The default value of the parameter.
     * @param allowedValues The allowed values for the parameter (if applicable).
     * @param parameterName The name of the parameter.
     */
    NoiseEmissionParameters(
            double defaultValue,
            Double[] allowedValues,
            String parameterName) {
        
        this.defaultValue = defaultValue;
        this.allowedValues = allowedValues;
        this.parameterName = parameterName;
    }

    /**
     * Returns the default value of the parameter.
     *
     * @return The default value of the parameter.
     */
    public double getDefaultValue() {
        return defaultValue;
    }

    /**
     * Returns a list of modifiable parameters.
     *
     * @return The list of modifiable parameters.
     */
    public static List<NoiseEmissionParameters> getModifiableParameters() {
        List<NoiseEmissionParameters> modifiableParameters = new ArrayList<>();

        modifiableParameters.add(UTBURKOLAT_ERDESEG);
        modifiableParameters.add(LTHNAPPAL);
        modifiableParameters.add(LTHEJJEL);
        modifiableParameters.add(LATOSZOG);
        modifiableParameters.add(C);
        modifiableParameters.add(KR);
        modifiableParameters.add(LEJTES_EMELKEDES);
        modifiableParameters.add(FORGALOM_TIPUSA);
        
        return modifiableParameters;
    }

    /**
     * Returns the allowed values for the parameter.
     *
     * @return The allowed values for the parameter.
     */
    public Double[] getAllowedValues() {
        return allowedValues;
    }
    
    /**
     * Returns the name of the parameter.
     *
     * @return The name of the parameter.
     */
    public String getParameterName() {
        return parameterName;
    }

}
