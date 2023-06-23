/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileReadingAndDataStore.DbfDataModifyHelper;

import FileReadingAndDataStore.DataStore.DbfData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author szaboa
 */
/**
 * The DbfDataHelper class provides utility methods for updating the user-selected columns in the DBF data.
 */
public class DbfDataHelper {
    private List<String> columns;
    /**
     * Constructs a DbfDataHelper with the specified list of columns.
     *
     * @param columns the list of column names
     */
    public DbfDataHelper(List<String> columns) {
        this.columns = columns;
    }
    
    /**
     * Returns the values of a specific column from a list of records.
     *
     * @param records    the list of records
     * @param columnName the name of the column
     * @return the values of the specified column
     */
    public List<Object> getColumnValues(List<List<Object>> records, String columnName) {
        int columnIndex = columns.indexOf(columnName);
        if (columnIndex != -1) {
            return records.stream()
                    .map(row -> row.get(columnIndex))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
    
    /**
     * Calculates new 'speed' column values based on the source column values.
     *
     * @param sourceColumn the source column values
     * @return the calculated new column values
     */
    public List<Object> calculateNewColumnValues(List<Object> sourceColumn) {
        List<Object> newColumnValues = new ArrayList<>();
        if (sourceColumn.isEmpty() || !areAllNumeric(sourceColumn)) {
            // Populate with zeros for all rows
            for (int i = 0; i < sourceColumn.size(); i++) {
                newColumnValues.add(0);
            }
        } else {
            for (Object value : sourceColumn) {
                int speed = ((Number) value).intValue();
                int newValue;
                if (speed <= 70) {
                    newValue = speed;
                } else if (speed > 70 && speed <= 90) {
                    newValue = 70;
                } else {
                    newValue = 80;
                }
                newColumnValues.add(newValue);
            }
        }
        return newColumnValues;
    }
    
    /**
     * Checks if all the values in a column are numeric.
     *
     * @param column the column to check
     * @return true if all values are numeric, false otherwise
     */
    public boolean areAllNumeric(List<Object> column) {
        for (Object value : column) {
            if (!(value instanceof Number)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Updates the DBF file data based on the user-selected column map.
     *
     * @param dbfData            the original DBF data
     * @param selectedColumnMap  the map of selected columns and their new names
     * @return the updated DbfData object
     */
    public DbfData updateDbfFile(DbfData dbfData, Map<String, String> selectedColumnMap) {
        // Modify the DbfData object based on the selectedColumnMap
        List<String> tmpColumns = dbfData.getColumns();
        columns = new ArrayList<>(tmpColumns);
        List<List<Object>> records = dbfData.getRecord();

        // Update column names and remove columns not in selectedColumnMap
        for (int i = columns.size() - 1; i >= 0; i--) {
            String columnName = columns.get(i);
            if (selectedColumnMap.containsKey(columnName)) {
                String newColumnName = selectedColumnMap.get(columnName);
                columns.set(i, newColumnName);
            } else {
                columns.remove(i);
                for (int j = 0; j < records.size(); j++) {
                    records.get(j).remove(i);
                }
            }
        }

        List<Object> firstSpeedColumn = getColumnValues(records, "1akSebesseg");
        List<Object> rFirstSpeedColumn = getColumnValues(records, "R1akSebesseg");

        List<Object> secondSpeedColumn = calculateNewColumnValues(firstSpeedColumn);
        List<Object> rSecondSpeedColumn = calculateNewColumnValues(rFirstSpeedColumn);

        List<Object> thirdSpeedColumn = calculateNewColumnValues(firstSpeedColumn);
        List<Object> rThirdSpeedColumn = calculateNewColumnValues(rFirstSpeedColumn);

        // Create a new list of records with additional columns and calculated values
        List<List<Object>> updatedRecords = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            List<Object> row = new ArrayList<>(records.get(i));
            row.add(secondSpeedColumn.get(i));
            row.add(thirdSpeedColumn.get(i));
            row.add(rSecondSpeedColumn.get(i));
            row.add(rThirdSpeedColumn.get(i));
            updatedRecords.add(row);
        }

        // Add the new column names and updated records to the DbfData object
        columns.addAll(Arrays.asList("2akSebesseg", "3akSebesseg", "R2akSebesseg", 
                "R3akSebesseg"));

        // Create a new DbfData object with the modified records and column names    
        DbfData modifiedDbfData = new DbfData(updatedRecords, columns);
        return modifiedDbfData;
    }
}
