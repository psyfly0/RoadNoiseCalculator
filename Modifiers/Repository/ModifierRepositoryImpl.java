/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modifiers.Repository;

import Modifiers.ShapeModifier.ShapefileModifier;
import Modifiers.Parameters.CalculationType;
import Calculators.Repository.CalculatorRepository;
import Calculators.Parameters.NoiseEmissionParameters;
import Calculators.SortingAndDifferencesOfResults.NoiseEmissionSortAndDifferences.DifferenceBy;
import Calculators.SortingAndDifferencesOfResults.NoiseEmissionSortAndDifferences.SortBy;
import FileReadingAndDataStore.DataStore.DbfData;
import FileReadingAndDataStore.Repositories.DbfRepository;
import FileReadingAndDataStore.DataStore.ShapeData;
import FileReadingAndDataStore.Repositories.ShapeFileRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.locationtech.jts.geom.Geometry;

/**
 *
 * @author szaboa
 */
/**
 * Implementation of the {@link ModifierRepository} interface that provides methods
 * for modifying and managing data in the repository.
 */
public class ModifierRepositoryImpl implements ModifierRepository {
    private final DbfRepository dbfRepository;
    private final ShapeFileRepository shapeFileRepository;
    private final CalculatorRepository calculatorRepository;
    private ShapefileModifier shapefileModifier;
    
    /**
     * Constructs a new instance of the {@code ModifierRepositoryImpl} class.
     *
     * @param shapeFileRepository    The repository for shape file data.
     * @param dbfRepository          The repository for DBF data.
     * @param calculatorRepository   The repository for calculator data.
     */
    public ModifierRepositoryImpl (
            ShapeFileRepository shapeFileRepository,
            DbfRepository dbfRepository,
            CalculatorRepository calculatorRepository) {
        
        this.shapeFileRepository = shapeFileRepository;
        this.dbfRepository = dbfRepository;
        this.calculatorRepository = calculatorRepository;
        this.shapefileModifier =  new ShapefileModifier(this);
    }
   
    /**
     * {@inheritDoc}
     */
    @Override
    public ShapeData getShapeData(int index) {
        return shapeFileRepository.getShapeData(index);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public DbfData getFilteredDbfData(int index) {
        return dbfRepository.getFilteredDbfData(index);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> getCalculatedValues(CalculationType calculationType, int index) {
        calculatorRepository.invokeNoiseEmissionCalculator(index); 
        List<Double> calculatedValues = new ArrayList<>();
        switch (calculationType) {
            case DAY_DECIBEL:         
               
                calculatedValues.addAll(calculatorRepository.
                        getDayDecibelValues(index));
                break;
            case NIGHT_DECIBEL:
                calculatedValues.addAll(calculatorRepository.
                        getNightDecibelValues(index));
                break;
            case PROTECTIVE_DISTANCE_NAPPAL:
                calculatedValues.addAll(calculatorRepository.
                        getProtectiveDistanceNappal(index));
                break;
            case PROTECTIVE_DISTANCE_EJJEL:
                calculatedValues.addAll(calculatorRepository.
                        getProtectiveDistanceEjjel(index));
                break;
            case IMPACT_AREA_DISTANCE_NAPPAL:
                calculatedValues.addAll(calculatorRepository.
                        getImpactAreaDistanceNappal(index));
                break;
            case IMPACT_AREA_DISTANCE_EJJEL:
                calculatedValues.addAll(calculatorRepository.
                        getImpactAreaDistanceEjjel(index));
                break;
            case SOUND_POWER_LEVEL_NAPPAL:
                calculatedValues.addAll(calculatorRepository.
                        getSoundPowerLevelNappal(index));
                break;
            case SOUND_POWER_LEVEL_EJJEL:
                calculatedValues.addAll(calculatorRepository.
                        getSoundPowerLevelEjjel(index));
                break;
            default:
                throw new IllegalArgumentException("Hibás érték választás: " 
                        + calculationType);
        }
        return calculatedValues;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getColumnName(CalculationType calculationType) {
        switch (calculationType) {
            case DAY_DECIBEL:
                return "LAeq Nappal";
            case NIGHT_DECIBEL:
                return "LAeq Ejjel";
            case PROTECTIVE_DISTANCE_NAPPAL:
                return "Vedotavolsag Nappal";
            case PROTECTIVE_DISTANCE_EJJEL:
                return "Vedotavolsag Ejjel";
            case IMPACT_AREA_DISTANCE_NAPPAL:
                return "Hatasterulet Nappal";
            case IMPACT_AREA_DISTANCE_EJJEL:
                return "Hatasterulet Ejjel";
            case SOUND_POWER_LEVEL_NAPPAL:
                return "Lw Nappal";
            case SOUND_POWER_LEVEL_EJJEL:
                return "Lw Ejjel";
            default:
                throw new IllegalArgumentException("Hibás érték választás: "
                        + calculationType);
        } 
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> invokeNoiseEmissionDifferenceBy(
            DifferenceBy differenceBy,
            int timeStateIndex1,
            int timeStateIndex2) {
        
        List<Double> differenceData = 
                calculatorRepository.
                        invokeNoiseEmissionDifferenceBy(differenceBy, 
                                timeStateIndex1, timeStateIndex2);
        return differenceData;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<List<Object>> invokeNoiseEmissionSortBy(SortBy sortBy, int timeStateIndex) {
        List<List<Object>> sortedData = 
                calculatorRepository.invokeNoiseEmissionSortBy(sortBy, timeStateIndex);
        return sortedData;
    }
        
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Geometry> createNewGeometry(
            List<Double> valuesDay, 
            List<Double> valuesNight, 
            int index) {      
        List<Geometry> newGeometries = 
                shapefileModifier.createNewGeometry(valuesDay, valuesNight, index);
        return newGeometries;
        }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void addCalculatedColumns(
            DbfData dbfData, 
            String columnName, 
            List<Double> newColumnValues) {
        dbfData.setColumns(columnName, newColumnValues);       
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> dayCalculateNoiseEmissionAtGivenDistance(double distance, int index) {
        return calculatorRepository.dayCalculateNoiseEmissionAtGivenDistance(distance, index);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Double> nightCalculateNoiseEmissionAtGivenDistance(double distance, int index) {
        return calculatorRepository.nightCalculateNoiseEmissionAtGivenDistance(distance, index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCellValue(DbfData dbfData, int columnIndex, int rowIndex, Object newValue) {
        dbfData.setValue(columnIndex, rowIndex, newValue);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCellValue(DbfData dbfData, String columnName, int rowIndex) {
        dbfData.removeValue(columnName, rowIndex);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteColumns(DbfData dbfData, List<String> columnNames) {
        for (String columnName : columnNames) {
            int columnIndex = dbfData.getColumns().indexOf(columnName);
            if (columnIndex >= 0) {
                dbfData.removeColumn(columnIndex);
            } else {
                throw new IllegalArgumentException("Invalid column name: " 
                        + columnName);
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteRow(DbfData dbfData, int rowIndex) {
        dbfData.deleteRow(rowIndex);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeResult(int index) {
        calculatorRepository.removeResult(index);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllResult() {
        calculatorRepository.removeAllResult();
    }
      
    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameterValueForAllRows(
            int index,
            Map<NoiseEmissionParameters, Double> parameters,
            int totalRows) {
        
        calculatorRepository.
                setParameterValueForAllRows(index, parameters, totalRows);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameterValue(
            int index,
            int rowIndex,
            Map<NoiseEmissionParameters, Double> parameters) {
        
        calculatorRepository.setParameterValue(index, rowIndex, parameters);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double getParameterValue(
            int fileIndex,
            int rowIndex,
            NoiseEmissionParameters parameter) {
        
        return calculatorRepository.
                getParameterValue(fileIndex, rowIndex, parameter);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeFileParameters(int index) {
        calculatorRepository.removeFileParameters(index);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllParameters() {
        calculatorRepository.removeAllParameters();
    }
   
}
       
