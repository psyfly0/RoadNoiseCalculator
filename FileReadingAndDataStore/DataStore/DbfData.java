/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileReadingAndDataStore.DataStore;


import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author szaboa
 */
/**
 * Represents the data of a DBF file, including the records and columns.
 */
public class DbfData {
    private List<List<Object>> record;
    private List<String> columns;

    /**
     * Constructs a new DbfData object with the specified record and column data.
     *
     * @param record  the list of records containing the data
     * @param columns the list of column names
     */
    public DbfData(List<List<Object>> record, List<String> columns) {       
        this.record = record;
        this.columns = columns;
    }

    /**
     * Retrieves the value at the specified column and row index as an integer.
     *
     * @param column   the name of the column
     * @param rowIndex the index of the row
     * @return the integer value at the specified column and row index, or null if the value cannot be parsed as an integer
     */
    public Integer getValue(String column, int rowIndex) {
        int columnIndex = columns.indexOf(column);
        if (columnIndex >= 0 && rowIndex >= 0 && rowIndex < record.size() 
                && columnIndex < record.get(rowIndex).size()) {
            Object value = record.get(rowIndex).get(columnIndex);
            if (value instanceof Integer) {
                return (Integer) value;
            } else if (value instanceof Double) {
                return ((Double) value).intValue();
            } else if (value instanceof Long) {
                return ((Long) value).intValue();
            } else if (value instanceof String) {
                try {
                    return Integer.parseInt((String) value);
                } catch (NumberFormatException e) {
                    // Handle the case where the text value cannot be parsed as an integer
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    
    /**
     * Retrieves a list of values from the specified column.
     *
     * @param columnName the name of the column
     * @return the list of values from the specified column
     */
    public List<Object> getColumnValues(String columnName) {
        List<Object> columnValues = new ArrayList<>();
        int columnIndex = getColumns().indexOf(columnName);
        if (columnIndex != -1) {
            for (List<Object> row : record) {
                if (columnIndex < row.size()) {
                    Object columnValue = row.get(columnIndex);
                    columnValues.add(columnValue);
                }
            }
        }
        return columnValues;
    }

    /**
     * Sets the value at the specified column and row index.
     *
     * @param columnIndex the index of the column
     * @param rowIndex    the index of the row
     * @param value       the value to be set
     */
    public void setValue(int columnIndex, int rowIndex, Object value) {
        if (columnIndex >= 0 && rowIndex >= 0 && rowIndex < record.size() 
                && columnIndex < record.get(rowIndex).size()) {
            record.get(rowIndex).set(columnIndex, value);
        }
    }

    /**
     * Removes the value at the specified column and row index.
     *
     * @param columnName the name of the column
     * @param rowIndex   the index of the row
     */
    public void removeValue(String columnName, int rowIndex) {
        int columnIndex = columns.indexOf(columnName);
        if (columnIndex < 0) {
            throw new IllegalArgumentException("Invalid column name: " + columnName);
        }
        List<Object> row = record.get(rowIndex);
        row.remove(columnIndex);
    }   
    
    /**
     * Removes the column at the specified index from the data.
     *
     * @param columnIndex the index of the column to remove
     */
    public void removeColumn(int columnIndex) {
        columns.remove(columnIndex);
        for (List<Object> row : record) {
            row.remove(columnIndex);
        }
    }
    
    /**
     * Deletes the row at the specified index from the data.
     *
     * @param rowIndex the index of the row to delete
     */
    public void deleteRow(int rowIndex) {
        record.remove(rowIndex);
    }
    
    /**
     * Retrieves the list of records.
     *
     * @return the list of records
     */
    public List<List<Object>> getRecord() {
        return record;
    }
    
    /**
     * Retrieves the number of rows in the data.
     *
     * @return the number of rows
     */
    public int getRowCount() {
        return record.size();
    }
    
    /**
     * Retrieves the list of column names.
     *
     * @return the list of column names
     */
    public List<String> getColumns() {
        return columns;
    }
    
    /**
     * Sets the column with the specified name and populates it with the provided column values.
     *
     * @param columnName    the name of the column
     * @param columnValues  the list of column values
     */
    public void setColumns(String columnName, List<Double> columnValues) {
        // Loop through each column name      
        int columnIndex = -1;

        // Check if the column name already exists in the columns list
        if (!columns.contains(columnName)) {
            // Add the new column name to the columns list
            columns.add(columnName);
            columnIndex = columns.indexOf(columnName);

            // Extend the record list to accommodate the new column values
            for (List<Object> row : record) {
                while (row.size() <= columnIndex) {
                    row.add(null);
                }
            }
            // Populate the new column with the provided column values
            for (int i = 0; i < columnValues.size(); i++) {
                Double value = columnValues.get(i);
                List<Object> row = record.get(i);
                row.set(columnIndex, value);
            }
        } else {
            columnIndex = columns.indexOf(columnName);
            // Populate the new column with the provided column values
            for (int i = 0; i < columnValues.size(); i++) {
                Double value = columnValues.get(i);
                List<Object> row = record.get(i);
                row.set(columnIndex, value);
        }
        }
    }

}
