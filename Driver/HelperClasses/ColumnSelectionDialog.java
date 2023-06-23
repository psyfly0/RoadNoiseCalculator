/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver.HelperClasses;

import Driver.ControllerManager.ControllerManager;
import Driver.OptionPane.OptionPane;
import FileReadingAndDataStore.Controllers.DbfController;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.TreeMap;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 *
 * @author szaboa
 */
/**
 * A dialog for selecting pre-defined column names for the input columns from a file.
 * Saves the selection to a configuration file.
 * Can be load pre-defined configuration file.
 * Returns the selected column names to the DbfRepository and uses them for displaying it in the tables.
 */
public class ColumnSelectionDialog extends JDialog {
    private JPanel panel;
    /**S
    * Stores the selected column names.
    * The map is structured as follows: input column names &rarr; selected column names
    */
    private Map<String, String> selectedColumnMap = new HashMap<>();
    
    /**
    * Stores the previously selected column names.
    * Returns them in the new dialog window when opening a new file
    */
    private Map<String, String> previousSelection;
    
    /**
    * Stores the input column names.
    */
    private Map<String, String> columnNames;
    
    /**
    * Stores the pre-defined column names.
    */
    private List<String> columnNamesToChoose;
    private JCheckBox usePreviousSelectionCheckBox;
    private boolean usePreviousSelection;
    private boolean isAccepted = false;
    private int selectedIndex;
    private File file;
    private ControllerManager controllerManager;

    /**
     * Constructs a ColumnSelectionDialog.
     *
     * @param owner            the Frame from which the dialog is displayed
     * @param file             the input file
     * @param controllerManager the ControllerManager instance
     * @param previousSelection the previous column selection
     * @throws IOException if an I/O error occurs
     */
    public ColumnSelectionDialog(
            Frame owner,
            File file,
            ControllerManager controllerManager,
            Map<String, String> previousSelection) throws IOException { 
        
        super(owner, "Oszlopok párosítása", true);
        
        this.controllerManager = controllerManager;
        this.previousSelection = previousSelection;
        this.file = file;
        
        // Access the controllers from the ControllerManager
        DbfController dbfController = controllerManager.getDbfController();
        
        // Get the file name without the extension
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        String baseFileName = (dotIndex == -1) ? fileName : 
                fileName.substring(0, dotIndex);

        // Construct the config file name
        String configFileName = baseFileName + "_config.txt";

        // Check if config file exists in the same directory as the input file
        File configFile = new File(file.getParentFile(), configFileName);
        if (configFile.exists()) {
            // Load the values from the config file
            selectedColumnMap = loadConfigValues(configFile);
        }

        columnNamesToChoose = 
                Arrays.asList("","Azonosito", "1akSebesseg", "1akNappal", 
                                "2akNappal", "3akNappal", "1akEjjel", 
                                "2akEjjel", "3akEjjel", "RAzonosito", 
                                "R1akSebesseg", "R1akNappal", "R2akNappal", 
                                "R3akNappal", "R1akEjjel", "R2akEjjel", "R3akEjjel");
         
        // Get the column names from the file header
        columnNames = dbfController.getColumnNames(file);
        // Create a panel to hold the column selection components
        panel = new JPanel(new GridLayout(0, 2));
        
//**********************************************************************************
//          Gyorsabb tesztelés miatt automatikus map feltöltés.
//**********************************************************************************
   /*
selectedColumnMap.put("NO", "Azonosito");
selectedColumnMap.put("Z1_N", "1akNappal");
selectedColumnMap.put("Z2_N", "2akNappal");
selectedColumnMap.put("Z3_N", "3akNappal");
selectedColumnMap.put("Z1_E", "1akEjjel");
selectedColumnMap.put("Z2_E", "2akEjjel");
selectedColumnMap.put("Z3_E", "3akEjjel");
selectedColumnMap.put("R_NO", "RAzonosito");
selectedColumnMap.put("R_Z1_N", "R1akNappal");
selectedColumnMap.put("R_Z2_N", "R2akNappal");
selectedColumnMap.put("R_Z3_N", "R3akNappal");
selectedColumnMap.put("R_Z1_E", "R1akEjjel");
selectedColumnMap.put("R_Z2_E", "R2akEjjel");
selectedColumnMap.put("R_Z3_E", "R3akEjjel");
selectedColumnMap.put("VKMH", "1akSebesseg");
selectedColumnMap.put("R_VKMH", "R1akSebesseg");
*/

        
/*
************************************************************************************

  |                   Csak a tesztelések miatt van kicsillagozva                  |
  V                                                                               V
************************************************************************************
*/        
      

    // Declare and initialize the availableColumnNames map
    Map<Integer, String> availableColumnNames = new TreeMap<>();

    // Populate the availableColumnNames map with the column names and their corresponding indexes
    for (int index = 0; index < columnNamesToChoose.size(); index++) {
        String columnName = columnNamesToChoose.get(index);
        availableColumnNames.put(index, columnName);
    }

    // Create a final variable to hold the sorted map
    final Map<Integer, String> sortedAvailableColumnNames = 
            new TreeMap<>(availableColumnNames);

    // Add a drop-down list for each column name in the columnNames map
    for (Map.Entry<String, String> entry : columnNames.entrySet()) {
        String columnName = entry.getValue();

        JComboBox<String> comboBox;

        // Set the default selection to empty string
        if (configFile.exists()) {
            String selectedValue = selectedColumnMap.get(columnName);
            if (selectedValue != null) {
                comboBox = new JComboBox<>(new String[]{selectedValue, ""});
                // Remove the selected value from availableColumnNames
                for (Map.Entry<Integer, String> availEntry : 
                        availableColumnNames.entrySet()) {
                    if (availEntry.getValue().equals(selectedValue)) {
                        availableColumnNames.remove(availEntry.getKey());
                        break;
                    }
                }
            } else {
                comboBox = new JComboBox<>(sortedAvailableColumnNames.
                        values().toArray(new String[0]));
                comboBox.setSelectedItem("");
            }
        } else {
            comboBox = new JComboBox<>(sortedAvailableColumnNames.
                    values().toArray(new String[0]));
            comboBox.setSelectedItem("");
        }

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> cb = (JComboBox<String>) e.getSource();
                String selectedValue = (String) cb.getSelectedItem();

                if (!selectedValue.equals("") && !selectedColumnMap.
                        containsKey(columnName)) {
                    // Check if the selected name has already been used in another cell
                    if (selectedColumnMap.containsValue(selectedValue)) {
                        // If the selected name has already been used, reset the combo box to the original value
                        cb.setSelectedItem("");
                    } else {
                        // If the selected name is available, add it to the selectedColumnMap and remove it from availableColumnNames
                        selectedIndex = cb.getSelectedIndex();

                        selectedColumnMap.put(columnName, selectedValue);

                        // Remove the selected value from availableColumnNames
                        availableColumnNames.remove(selectedIndex);

                        // Update all the combo boxes with the updated availableColumnNames
                        for (Component component : panel.getComponents()) {
                            if (component instanceof JComboBox) {
                                JComboBox<String> otherComboBox = 
                                        (JComboBox<String>) component;
                                if (!otherComboBox.equals(cb)) {
                                    // Remove the selected value from other combo boxes
                                    otherComboBox.removeItem(selectedValue);
                                }
                            }
                        }
                    }
                } else if (selectedValue.equals("")) {
                    // If the combo box was deselected
                    String previousValue = selectedColumnMap.remove(columnName);
                    if (previousValue != null) {
                        // Put and remove the value
                        selectedColumnMap.put(columnName, selectedValue);

                        // Add the deselected value back to availableColumnNames
                        availableColumnNames.put(selectedIndex, previousValue);

                        // Update all the combo boxes with the updated availableColumnNames
                        for (Component component : panel.getComponents()) {
                            if (component instanceof JComboBox) {
                                JComboBox<String> otherComboBox = 
                                        (JComboBox<String>) component;
                                if (!otherComboBox.equals(cb)) {
                                    otherComboBox.addItem(previousValue);
                                }
                            }
                        }
                    }
                }
            }
        });

        // Add the column names and combo boxes to the panel
        panel.add(new JLabel(columnName));
        panel.add(comboBox);
    }

    // Create the checkbox for using the previous selection
    usePreviousSelectionCheckBox = new JCheckBox("Használjuk az elõzõ párostást?");
    usePreviousSelectionCheckBox.addActionListener(e -> {
        usePreviousSelection = usePreviousSelectionCheckBox.isSelected();

        if (usePreviousSelection && previousSelection != null 
                && !previousSelection.isEmpty()) {
            // Populate the comboboxes and selectedColumnMap with the previous selection
            for (String columnName : previousSelection.keySet()) {
                String previousValue = previousSelection.get(columnName);

                for (Component component : panel.getComponents()) {
                    if (component instanceof JComboBox) {
                        JComboBox<String> comboBox = (JComboBox<String>) component;
                        String comboBoxColumnName = 
                                ((JLabel) panel.getComponent(panel.
                                        getComponentZOrder(comboBox) - 1)).getText();
                        if (comboBoxColumnName.equals(columnName)) {
                            comboBox.setSelectedItem(previousValue);
                            selectedColumnMap.put(columnName, previousValue);
                            break;
                        }
                    }
                }
            }
        } 
    });


    // Add some space after the last column name
    panel.add(Box.createVerticalStrut(5));

    // Create a panel to hold the checkbox
    JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    checkBoxPanel.add(usePreviousSelectionCheckBox);

    // Add the checkbox panel to the main panel
    panel.add(checkBoxPanel);      

     
/*
Ettõl felfele kell kikommentelni a gyorsabb teszteléshez        
*/        
        
        // Create OK and Cancel buttons
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if all names are selected
                int nonNullValueCount = 0;
                for (String value : selectedColumnMap.values()) {
                    if (!value.equals("")) {
                        nonNullValueCount++;
                    }
                }
                if (nonNullValueCount == 16) {
                    // Pass the selected column names back to the controller
                    dbfController.updateData(file, selectedColumnMap);
                    
                try {
                    // Save the selected values to the config file
                    saveConfigValues();
                    } catch (IOException ex) {
                        ex.getCause();
                    }
            
                    isAccepted = true;
                    dispose();
                } else {
                    OptionPane.showInfoDialog("Minden választható oszlopnevet "
                            + "fel kell használni!");
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        // Create a scrollable panel to hold the content
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.
                VERTICAL_SCROLLBAR_AS_NEEDED);
        // Set a larger scroll unit increment value
        int scrollUnitIncrement = 25; // 
        scrollPane.getVerticalScrollBar().setUnitIncrement(scrollUnitIncrement);

        // Add the scroll pane and button panel to the dialog's content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Set the dialog's size to fit the column names

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        int panelWidth = (int) (screenWidth * 0.3); 
        int panelHeight = (int) (screenHeight * 0.7); 

        // Set the dialog's size and make it visible
        setSize(panelWidth, panelHeight);
        setLocationRelativeTo(owner);
        setVisible(true);
    }
    
    /**
     * Returns the selected column map.
     *
     * @return the selected column map
     */
    public Map<String, String> getSelectedColumnMap() {
        return selectedColumnMap;
    }
    
    /**
     * Returns the previous column selection.
     *
     * @return the previous column selection
     */
    public Map<String, String> getPreviousSelection() {
        if (selectedColumnMap != null && !selectedColumnMap.isEmpty()) {
            return new HashMap<>(selectedColumnMap);
        } else if (usePreviousSelection && previousSelection != null) {
            return new HashMap<>(previousSelection);
        } else {
            return new HashMap<>();
        }
    }
    
    /**
     * Sets the selected value for a specific column.
     *
     * @param columnName    the name of the column
     * @param selectedValue the selected value
     */
    public void setSelectedValue(String columnName, String selectedValue) {
        // Iterate over the panel components to find the corresponding combo box
        for (Component component : panel.getComponents()) {
            if (component instanceof JComboBox) {
                JComboBox<String> comboBox = (JComboBox<String>) component;
                String comboBoxColumnName = ((JLabel) panel.
                        getComponent(panel.getComponentZOrder(comboBox) - 1)).getText();
                if (comboBoxColumnName.equals(columnName)) {
                    // Set the selected value for the combo box
                    comboBox.setSelectedItem(selectedValue);
                    break;
                }
            }
        }
    }
    
    /**
     * Checks if the dialog was accepted.
     *
     * @return true if the dialog was accepted, false otherwise
     */
    public boolean isAccepted() {
        return isAccepted;
    }
    
    /**
    * Loads configuration values from a file.
    *
    * @param configFile The file from which to load the configuration values.
    * @return A map containing the loaded configuration values, where the keys are column names and the values are selected values.
    * @throws IOException If an I/O error occurs while reading the file.
    */
    private Map<String, String> loadConfigValues(File configFile) throws IOException {
        Map<String, String> columnMap = new HashMap<>();

        List<String> lines = Files.readAllLines(configFile.toPath());
        for (String line : lines) {
            String[] parts = line.split("=");
            if (parts.length == 2) {
                String columnName = parts[0].trim();
                String selectedValue = parts[1].trim();
                columnMap.put(columnName, selectedValue);
            }
        }

        return columnMap;
    }
    
    /**
    * Saves configuration values to a file.
    *
    * @return The file in which the configuration values are saved.
    * @throws IOException If an I/O error occurs while writing to the file.
    */
    private File saveConfigValues() throws IOException {
        // Get the file name without the extension
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        String baseFileName = (dotIndex == -1) ? fileName : 
                fileName.substring(0, dotIndex);

        // Construct the config file name
        String configFileName = baseFileName + "_config.txt";

        // Create the config file in the same directory as the input file
        File configFile = new File(file.getParentFile(), configFileName);

        // Write the selectedColumnMap and usePreviousSelection to the config file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
            for (Map.Entry<String, String> entry : selectedColumnMap.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
     
        }

        return configFile;
    }
}