/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver.MainPanel;

import Driver.DialogWindows.FileChooser;
import Driver.ControllerManager.ControllerManager;
import Driver.OptionPane.OptionPane;
import Driver.MainGUI.GUI;
import Calculators.Parameters.NoiseEmissionParameters;
import FileReadingAndDataStore.Controllers.DbfController;
import Modifiers.Controller.ModifierController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author szaboa
 */
/**
 * The ProjectPanel class represents a panel component that displays and manages project data
 * in a graphical user interface (GUI).
 * It provides functionality for displaying database records, updating table data, and managing column properties.
 */
public class ProjectPanel extends JPanel {
    private List<JTable> tables;
    private List<DefaultTableModel> tableModels;
    private Map<Integer, Object> originalIndices; // Map to store original row indices and corresponding values
    private List<Boolean> isSortedFlags = new ArrayList<>(); // List to store sorting status for each file
    private Map<Integer, Integer> addedColumnsMap = new HashMap<>();
    private GUI gui;
    private ControllerManager controllerManager;

    /**
    * Creates a new instance of ProjectPanel with the specified ControllerManager and GUI.
    *
    * @param controllerManager The ControllerManager instance to use.
    * @param gui The GUI instance to use.
    */
    public ProjectPanel(ControllerManager controllerManager, GUI gui) {
        setBackground(new Color(224, 224, 224));
        setLayout(new BorderLayout());
        tables = new ArrayList<>();
        tableModels = new ArrayList<>();
        this.controllerManager = controllerManager;
        this.gui = gui;
    }

    /**
    * Displays the database records in a table and returns a JScrollPane.
    *
    * @param controllerManager The ControllerManager instance to use.
    * @param index The index of the record to display.
    * @return A JScrollPane containing the displayed table.
    */
    public JScrollPane displayDbfData(ControllerManager controllerManager, int index) {
        // Access the controllers from the ControllerManager
        DbfController dbfController = controllerManager.getDbfController();
        
        List<String> columnNames = dbfController.getColumnNames(index);
        List<List<Object>> data = dbfController.getRecord(index);

        DefaultTableModel tableModel = 
                new DefaultTableModel(data.size(), columnNames.size());
        tableModel.setColumnIdentifiers(columnNames.toArray());
        tableModels.add(tableModel);

        originalIndices = new HashMap<>(); // Initialize the map

        // Populate the table model with data
        for (int row = 0; row < data.size(); row++) {
            List<Object> rowData = data.get(row);
            for (int col = 0; col < columnNames.size(); col++) {
                tableModel.setValueAt(rowData.get(col), row, col);
            }
            originalIndices.put(row, rowData.get(0)); // Store original row indices and Azonosito column's values
        }
        // Initialize the sorting status for the current file
        isSortedFlags.add(false);

        // Create the JTable and add it to the ProjectPanel
        JTable table = new JTable(tableModel) {
            // Allow editing only when Enter is pressed
            @Override
            public boolean editCellAt(int row, int column, EventObject e) {
                boolean result = super.editCellAt(row, column, e);
                if (result && e instanceof KeyEvent) {
                    JTextField editor = (JTextField) getEditorComponent();
                    editor.requestFocusInWindow();
                    editor.selectAll();
                }
                return result;
            }

        };
 
        addRightClickMenu(controllerManager, table);

        tables.add(table);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.
                HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.
                VERTICAL_SCROLLBAR_ALWAYS);

        // Set the column width based on the length of the column names
        setColumnWidths(table);
        setColumnHeight(table);

        removeAll();
        add(scrollPane, BorderLayout.CENTER);

        revalidate();
        repaint();

        return scrollPane;
    }

    /**
    * Updates the table data based on changes in the underlying data source and returns a JScrollPane.
    *
    * @param controllerManager The ControllerManager instance to use.
    * @param index The index of the record to update.
    * @return A JScrollPane containing the updated table.
    */
    public JScrollPane updateTableData(ControllerManager controllerManager, int index) {
        // Access the controllers from the ControllerManager
        DbfController dbfController = controllerManager.getDbfController();
        
        if (index >= 0 && index < tableModels.size()) {

            boolean isSorted = isSortedFlags.get(index); // Get the sorting status for the current file
            if (isSorted) {
                restoreOriginalOrder(index);
                isSortedFlags.set(index, false); // Reset the sorting status for the current file
            }

            JTable table = tables.get(index);
            DefaultTableModel tableModel = tableModels.get(index);
            List<String> columnNames = dbfController.getColumnNames(index);
            List<List<Object>> data = dbfController.getRecord(index);

            int existingColumnCount = tableModel.getColumnCount();
            int columnsToAdd = columnNames.size() - existingColumnCount;

            if (!addedColumnsMap.containsKey(index)) {
                addedColumnsMap.put(index, 0); // Initialize the count for the index
            }

            int addedColumns = addedColumnsMap.get(index); // Retrieve the count for the index

            // Update the addedColumns count for the index
            addedColumnsMap.put(index, addedColumns + columnsToAdd);
            // Add new columns and populate data
            for (int i = existingColumnCount; i < columnNames.size(); i++) {
                String columnName = columnNames.get(i);
                tableModel.addColumn(columnName);
            }

            for (int i = 0; i < columnNames.size(); i++) {
                for (int row = 0; row < data.size(); row++) {
                    Object cellData = data.get(row).get(i);
                    tableModel.setValueAt(cellData, row, i);
                }
            };

            setColumnWidths(table);
            setColumnHeight(table);
            table.setModel(tableModel); // Set the updated table model
            revalidate();
            repaint();
            
            
            TableColumnModel columnModel = table.getColumnModel();
            int columnCount = tableModel.getColumnCount();
            
            List<String> inputColumnNames = inputColumnNames(); // Define the list of input column names
            for (int col = 0; col < columnCount; col++) {
                TableColumn column = columnModel.getColumn(col);
                String columnName = column.getHeaderValue().toString();

                // Check if the column name is not in the input column names list
                if (!inputColumnNames.contains(columnName)) {
                    column.setHeaderRenderer(new DefaultTableCellRenderer() {
                        @Override
                        public Component getTableCellRendererComponent(JTable table,
                                Object value, boolean isSelected, boolean hasFocus, 
                                int row, int column) {
                            JLabel label = (JLabel) super.
                                    getTableCellRendererComponent(table, value, 
                                            isSelected, hasFocus, row, column);
                            label.setFont(new Font("Arial", Font.BOLD, 12));
                            label.setBackground(new Color(186, 214, 222));
                            label.setForeground(Color.BLACK);
                            return label;
                        }
                    });
                }
            }

            //set isSorted to false
            isSortedFlags.set(index, false);
            return new JScrollPane(table);
        }
        return null;
    }

    /**
    * Refreshes the table data with the provided data and returns a JScrollPane.
    *
    * @param data The new data to populate the table with.
    * @param index The index of the record to refresh.
    * @return A JScrollPane containing the refreshed table.
    */
    public JScrollPane refreshTableData(List<List<Object>> data, int index) {
        if (index >= 0 && index < tableModels.size()) {
            DefaultTableModel tableModel = tableModels.get(index);
            JTable table = tables.get(index);

            // Clear existing data from the table model
            tableModel.setRowCount(0);

            // Populate the table model with the new data
            for (List<Object> rowData : data) {
                tableModel.addRow(rowData.toArray());
            }

            table.setModel(tableModel); // Set the updated table model
            revalidate();
            repaint();

            // set isSorted to true
            isSortedFlags.set(index, true);
            return new JScrollPane(table);
        }

        return null;
    }

    /**
    * Refreshes the data of a specific table cell at the given row and column indices with a new value and returns a JScrollPane.
    *
    * @param rowIndex The index of the row containing the cell to refresh.
    * @param columnIndex The index of the column containing the cell to refresh.
    * @param newValue The new value to set in the cell.
    * @param index The index of the table to refresh.
    * @return A JScrollPane containing the refreshed table.
    */
    public JScrollPane refreshTableCellData(int rowIndex, int columnIndex, 
            Object newValue, int index) {
        if (index >= 0 && index < tableModels.size()) {
            DefaultTableModel tableModel = tableModels.get(index);
            JTable table = tables.get(index);
            tableModel.setValueAt(newValue, rowIndex, columnIndex);
            table.setModel(tableModel);

            revalidate();
            repaint();

            return new JScrollPane(table);
        }
        return null;
    }

    /**
    * Applies column deletion to the table based on the provided ControllerManager and index, and returns a JScrollPane.
    *
    * @param controllerManager The ControllerManager instance to use.
    * @param index The index of the table to apply column deletion.
    * @return A JScrollPane containing the updated table after column deletion.
    */
    public JScrollPane applyColumnDeletion(ControllerManager controllerManager, int index) {
        // Access the controllers from the ControllerManager
        DbfController dbfController = controllerManager.getDbfController();
        
        if (index >= 0 && index < tableModels.size()) {
            DefaultTableModel tableModel = tableModels.get(index);
            JTable table = tables.get(index);
            List<List<Object>> dbfRecord = 
                    dbfController.getFilteredDbfData(index).getRecord();
            List<String> dbfColumns = 
                    dbfController.getFilteredDbfData(index).getColumns();

            Vector<Vector<Object>> newData = new Vector<>();
            for (List<Object> rowData : dbfRecord) {
                newData.add(new Vector<>(rowData));
            }
            Vector<Object> newColumnIdentifiers = new Vector<>(dbfColumns);

            tableModel.setDataVector(newData, newColumnIdentifiers);

            setColumnWidths(table);
            setColumnHeight(table);

            table.setModel(tableModel);

            revalidate();
            repaint();

            TableColumnModel columnModel = table.getColumnModel();
            int columnCount = tableModel.getColumnCount();
            List<String> inputColumnNames = inputColumnNames(); // Define the list of input column names
            for (int col = 0; col < columnCount; col++) {
                TableColumn column = columnModel.getColumn(col);
                String columnName = column.getHeaderValue().toString();

                // Check if the column name is not in the input column names list
                if (!inputColumnNames.contains(columnName)) {
                    column.setHeaderRenderer(new DefaultTableCellRenderer() {
                        @Override
                        public Component getTableCellRendererComponent(JTable table,
                                Object value, boolean isSelected, 
                                boolean hasFocus, int row, int column) {
                            JLabel label = (JLabel) 
                                    super.getTableCellRendererComponent(table, 
                                            value, isSelected, hasFocus, row, column);
                            label.setFont(new Font("Arial", Font.BOLD, 12));
                            label.setBackground(new Color(186, 214, 222));
                            label.setForeground(Color.BLACK);
                            return label;
                        }
                    });
                }
            }
            
            return new JScrollPane(table);

        }
        return null;
    }

    /**
    * Restores the original order of the table data for the specified index and returns the updated DefaultTableModel.
    *
    * @param index The index of the table to restore the original order.
    * @return The updated DefaultTableModel with the restored original order.
    */
    private DefaultTableModel restoreOriginalOrder(int index) {
        if (index >= 0 && index < tableModels.size()) {
            DefaultTableModel tableModel = tableModels.get(index);
            Vector<Vector<Object>> originalData = tableModel.getDataVector(); // Get the original data from the table model

            Vector<Vector<Object>> restoredData = new Vector<>(); // Temporary DataVector

            // Restore original order
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                Object originalValue = originalIndices.get(row);
                int originalIndex = -1;
                for (int i = 0; i < originalData.size(); i++) {
                    if (originalValue.equals(originalData.get(i).get(0))) {
                        originalIndex = i;
                        break;
                    }
                }
                Vector<Object> rowData = originalData.get(originalIndex);
                restoredData.add(rowData);
            }

            // Get the column names
            Vector<String> columnNames = new Vector<>();
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                columnNames.add(tableModel.getColumnName(col));
            }

            // Update the table model with the restored data
            tableModel.setDataVector(restoredData, columnNames);

            return tableModel;
        }
        return null;
    }

    /**
    * Sets the column widths of the given JTable based on the table header and the content in each column.
    *
    * @param table The JTable to set the column widths for.
    */
    private void setColumnWidths(JTable table) {
        JTableHeader tableHeader = table.getTableHeader();
        TableColumnModel columnModel = table.getColumnModel();

        for (int col = 0; col < columnModel.getColumnCount(); col++) {
            TableColumn column = columnModel.getColumn(col);
            Object headerValue = column.getHeaderValue();

            Component headerComp = tableHeader.getDefaultRenderer().
                    getTableCellRendererComponent(table, headerValue, false, 
                            false, 0, col);
            int headerWidth = headerComp.getPreferredSize().width + 5;

            column.setMinWidth(headerWidth);
        }
    }

    /**
    * Sets the column height of the given JTable based on the table header and the content in each column.
    *
    * @param table The JTable to set the column height for.
    */
    private void setColumnHeight(JTable table) {
        JTableHeader tableHeader = table.getTableHeader();
        TableColumnModel columnModel = table.getColumnModel();

        FontMetrics fontMetrics = tableHeader.getFontMetrics(tableHeader.getFont());
        int lineHeight = fontMetrics.getHeight();

        for (int col = 0; col < columnModel.getColumnCount(); col++) {
            TableColumn column = columnModel.getColumn(col);
            Object headerValue = column.getHeaderValue();

            if (headerValue instanceof String) {
                String columnName = (String) headerValue;
                int numLines = calculateNumLines(columnName);
                int headerHeight = (lineHeight * numLines) + 5;
                Dimension preferredSize = tableHeader.getPreferredSize();
                preferredSize.height = headerHeight + 5;
                column.setHeaderRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table,
                            Object value, boolean isSelected, boolean hasFocus,
                            int row, int column) {
                        JLabel label = (JLabel) 
                                super.getTableCellRendererComponent(table, value,
                                        isSelected, hasFocus, row, column);
                        label.setFont(new Font("Arial", Font.BOLD, 12)); // Set the font
                        label.setForeground(Color.BLACK); // Set the foreground color
                        return label;
                    }

                    @Override
                    public Dimension getPreferredSize() {
                        return preferredSize;
                    }
                });
            }
        }
    }

    /**
    * Calculates the number of lines in the given text by counting the occurrences of the "<br>" tag.
    *
    * @param text The text to calculate the number of lines for.
    * @return The number of lines in the text.
    */
    private int calculateNumLines(String text) {
        if (text == null || text.isEmpty()) {
            return 1;
        }
        String[] lines = text.split("<br>");
        return lines.length;
    }

    /**
    * Returns the JTable instance at the specified index.
    *
    * @param index The index of the table to retrieve.
    * @return The JTable instance at the specified index, or null if the index is out of bounds.
    */
    public JTable getTable(int index) {
        if (index >= 0 && index < tables.size()) {
            return tables.get(index);
        }
        return null;
    }

    /**
    * Adds a right-click context menu to the given JTable, allowing setting parameters for individual rows and deletion of rows.
    *
    * @param controllerManager The ControllerManager instance to use.
    * @param table The JTable to add the right-click context menu to.
    */
    private void addRightClickMenu(ControllerManager controllerManager, JTable table) {
        // Access the controllers from the ControllerManager
        DbfController dbfController = controllerManager.getDbfController();
        ModifierController modifierController = controllerManager.getModifierController();
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < table.getRowCount()) {
                        // Highlight the clicked row
                        table.setRowSelectionInterval(row, row);
                        

                        JPopupMenu popupMenu = new JPopupMenu();

                        // Menu item for modifying parameters
                        JMenuItem modifyItem = 
                                new JMenuItem("Számítási paraméterek módosítása");
                        modifyItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Get the necessary parameters for the setParameterValue method
                                int selectedIndex = gui.getTabbedPane().getSelectedIndex();
                                int rowIndex = table.convertRowIndexToModel(row);
                                // parameter and value come from the selectParametersForFile method

                                // Get the current parameters for the selected row
                                Map<NoiseEmissionParameters, Double> currentParameters = 
                                        new HashMap<>();
                                for (NoiseEmissionParameters parameter : 
                                        NoiseEmissionParameters.getModifiableParameters()) {
                                    double value =
                                            modifierController.
                                                    getParameterValue(selectedIndex,
                                                            rowIndex, parameter);
                                    currentParameters.put(parameter, value);
                                }
                                // Call the selectParametersForFile method
                                Map<NoiseEmissionParameters, Double> modifiedParameters = 
                                        FileChooser.selectParametersForFile(currentParameters);

                                // Call the setParameterValue method
                                modifierController.
                                        setParameterValue(selectedIndex, rowIndex,
                                                modifiedParameters);
                            }
                        });
                        popupMenu.add(modifyItem);
                        // Add a horizontal line separator
                        popupMenu.addSeparator();

                        // Menu item for deleting a row
                        JMenuItem deleteItem = new JMenuItem("Sor törlése");
                        deleteItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int selectedIndex = 
                                        gui.getTabbedPane().getSelectedIndex();
                                int rowIndex = table.convertRowIndexToModel(row);
                                
                                 // Show a confirmation dialog
                                boolean confirmed = 
                                        OptionPane.showConfirmDialog("Biztos törölni akarod a sort?");
                                
                                if (confirmed) {
                                    modifierController.deleteRow(dbfController.
                                            getFilteredDbfData(selectedIndex),
                                            rowIndex);

                                    // Refresh the display of the modified data in the GUI
                                    List<List<Object>> recordAfterDeletingRow = 
                                            dbfController.getRecord(selectedIndex);
                                    JScrollPane scrollPane = 
                                            refreshTableData(recordAfterDeletingRow, 
                                                    selectedIndex);

                                    gui.updateFileTab(selectedIndex, scrollPane);
                                }
                                
                            }
                        });
                        popupMenu.add(deleteItem);

                        popupMenu.show(table, e.getX() + 10, e.getY() + 10);
                    }
                }
            }
        });
    }
       
    /**
    * Retrieves a list of input column names.
    *
    * @return A list of input column names.
    */
    private List<String> inputColumnNames() {
        List<String> columnNames = 
                Arrays.asList("Azonosito", "1akSebesseg", "1akNappal", 
                                "2akNappal", "3akNappal", "1akEjjel", 
                                "2akEjjel", "3akEjjel", "RAzonosito", 
                                "R1akSebesseg", "R1akNappal", "R2akNappal", 
                                "R3akNappal", "R1akEjjel", "R2akEjjel", 
                                "R3akEjjel", "2akSebesseg", "3akSebesseg",
                                "R2akSebesseg", "R3akSebesseg");
        return columnNames; 
    }
    
    /**
    * Removes the table model at the specified index from the list of table models.
    *
    * @param index The index of the table model to remove.
    */
    public void removeTableModel(int index) {
        tableModels.remove(index);
    }
    
    /**
    * Removes all table models from the list of table models.
    */
    public void removeAllTableModels() {
        tableModels = new ArrayList<>();
    }

}
