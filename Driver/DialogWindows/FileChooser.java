/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver.DialogWindows;

import Driver.OptionPane.OptionPane;
import Calculators.Parameters.NoiseEmissionParameters;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author szaboa
 */
/**
* A utility class for selecting files using a file chooser dialog.
*/
public class FileChooser {
    private static String lastAccessedDirectory = null;
    private static boolean isAccepted = false;

    /**
     * Displays a file chooser dialog for selecting a file to open.
     *
     * @param openedFileNames a set of already opened file names
     * @return the selected file, or null if no file was selected or if the selected file is already open
     */
    public static File selectFile(Set<String> openedFileNames) {
        FileNameExtensionFilter filter = 
                new FileNameExtensionFilter("Shapefile", "shp");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);

        // Set the initial directory if it is not null
        if (lastAccessedDirectory != null) {
            fileChooser.setCurrentDirectory(new File(lastAccessedDirectory));
        }

        // Increase the size of the file chooser dialog
        Dimension dialogSize = new Dimension(700, 500);
        fileChooser.setPreferredSize(dialogSize);

        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Check if a file with the same name is already open
            if (openedFileNames.contains(selectedFile.getName())) {
                OptionPane.showErrorDialog("A \"" + selectedFile.
                        getName() + "\" fájl már meg van nyitva.");
                return null;
            }            

            // Store the directory path for future use
            lastAccessedDirectory = selectedFile.getParent();

            return selectedFile;
        }
        return null;
    }
    
    /**
     * Displays a file chooser dialog for selecting a file to save.
     *
     * @return the selected file, or null if no file was selected
     */
    public static File selectSaveFile() {
        FileNameExtensionFilter filter = 
                new FileNameExtensionFilter("Shapefile", "shp");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);

        // Set the initial directory if it is not null
        if (lastAccessedDirectory != null) {
            fileChooser.setCurrentDirectory(new File(lastAccessedDirectory));
        }

        // Increase the size of the file chooser dialog
        Dimension dialogSize = new Dimension(700, 500);
        fileChooser.setPreferredSize(dialogSize);

        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Append the file extension if not already present
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".shp")) {
                selectedFile = new File(filePath + ".shp");
            }

            // Store the directory path for future use
            lastAccessedDirectory = selectedFile.getParent();

            return selectedFile;
        }
        return null;
    }
   
    /**
     * Displays a dialog for selecting columns to delete from a list.
     *
     * @param columnNames the list of column names to choose from
     * @return the selected columns to delete
     */
    public static List<String> selectColumnsToDelete(List<String> columnNames) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Törölni kívánt oszlopok kiválasztása");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);

        // Create a panel to hold the column names
        JPanel panel = new JPanel(new GridLayout(0, 3, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create checkboxes for each column name
        JCheckBox[] checkBoxes = new JCheckBox[columnNames.size()];
        for (int i = 0; i < columnNames.size(); i++) {
            checkBoxes[i] = new JCheckBox(columnNames.get(i));
            panel.add(checkBoxes[i]);
        }

        // Create OK and Cancel buttons
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        // Action listener for OK button
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        // Action listener for Cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        // Create a scroll pane to hold the panel with checkboxes
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        // Create the main dialog content
        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);
        dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        // Display the dialog
        dialog.setVisible(true);

        // Retrieve the selected column names
        List<String> selectedColumns = new ArrayList<>();
        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].isSelected()) {
                selectedColumns.add(columnNames.get(i));
            }
        }

        return selectedColumns;
    }
       
    /**
     * Displays a dialog for selecting two files and a difference case.
     *
     * @param openedFiles the list of already opened files
     * @return a list containing the selected files and the difference case
     */
    public static List<Object> selectFiles(List<File> openedFiles) {
        JDialog fileSelectionDialog = new JDialog();
        fileSelectionDialog.setTitle("Fájlok kiválasztása");
        fileSelectionDialog.setModal(true);
        fileSelectionDialog.setLayout(new BorderLayout());

        // Create a panel to hold the components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Set padding for the components
        Insets componentInsets = new Insets(6, 6, 6, 6);

        // Create the left dropdown list with filenames
        JComboBox<String> leftComboBox = new JComboBox<>();
        openedFiles.forEach(file -> leftComboBox.addItem(file.getName()));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = componentInsets; // Apply insets
        panel.add(leftComboBox, constraints);

        // Create the "és" label
        JLabel andLabel = new JLabel("és");
        constraints.gridx = 1;
        constraints.insets = componentInsets; // Apply insets
        panel.add(andLabel, constraints);

        // Create the right dropdown list with filenames
        JComboBox<String> rightComboBox = new JComboBox<>();
        openedFiles.forEach(file -> rightComboBox.addItem(file.getName()));
        constraints.gridx = 2;
        constraints.insets = componentInsets; // Apply insets
        panel.add(rightComboBox, constraints);

        // Create the "különbsége" label
        JLabel differenceLabel = new JLabel("különbsége");
        constraints.gridx = 3;
        constraints.insets = componentInsets; // Apply insets
        panel.add(differenceLabel, constraints);
        
        // Create the combo box for difference cases
        JComboBox<String> differenceCasesComboBox = new JComboBox<>();
        differenceCasesComboBox.addItem("LAeq nappal különbsége");
        differenceCasesComboBox.addItem("LAeq éjjel különbsége");
        differenceCasesComboBox.addItem("Védõtávolság nappal különbsége");
        differenceCasesComboBox.addItem("Védõtávolság éjjel különbsége");
        differenceCasesComboBox.addItem("Hatásterület nappal különbsége");
        differenceCasesComboBox.addItem("Hatásterület éjjel különbsége");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 4;
        constraints.insets = componentInsets; // Apply insets
        constraints.anchor = GridBagConstraints.CENTER; // Center align
        panel.add(differenceCasesComboBox, constraints);

        // Add the panel to the file selection dialog
        fileSelectionDialog.add(panel, BorderLayout.NORTH);

        // Create a button panel for OK and Cancel buttons
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        fileSelectionDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Set the size and position of the file selection dialog
        fileSelectionDialog.setSize(400, 150);
        fileSelectionDialog.setLocationRelativeTo(null);

        List<File> selectedFiles = new ArrayList<>();
       // final String[] selectedDifferenceCase = {null};
        List<String> selectedDifferenceCase = new ArrayList<>();
                
        // Handle OK button action
        okButton.addActionListener(okEvent -> {
            // Get the selected files from the dropdown lists
            int leftSelectedIndex = leftComboBox.getSelectedIndex();
            int rightSelectedIndex = rightComboBox.getSelectedIndex();
            File leftFile = openedFiles.get(leftSelectedIndex);
            File rightFile = openedFiles.get(rightSelectedIndex);
            
            if (leftFile == rightFile) {
                OptionPane.showErrorDialog("Válaszz két különbözõ fájlt!");
            } else {           
                // Check if two files are selected
                if (leftSelectedIndex != -1 && rightSelectedIndex != -1) {
                    selectedFiles.add(leftFile);
                    selectedFiles.add(rightFile);
                    String tmpselectedDifferenceCase = 
                            (String) differenceCasesComboBox.getSelectedItem();
                    selectedDifferenceCase.add(tmpselectedDifferenceCase);

                    // Close the file selection dialog
                    isAccepted = true;
                    fileSelectionDialog.dispose();

                } else {
                    // Display an error message indicating that two files should be selected
                    OptionPane.showErrorDialog("Válassz két fájlt!");
                }
            }
        });

        // Handle Cancel button action
        cancelButton.addActionListener(cancelEvent -> {
            // Close the file selection dialog
            fileSelectionDialog.dispose();

        });

        // Show the file selection dialog
        fileSelectionDialog.setVisible(true);

        if (selectedFiles.size() == 2 && selectedDifferenceCase != null) {
            // Return the selected files and the difference case as a list
            List<Object> selection = new ArrayList<>();
            selection.add(selectedFiles);
            selection.add(selectedDifferenceCase);
            return selection;
        } else {
            return Collections.emptyList();
        }
    }
    
    /**
     * Displays a dialog for selecting parameters for a file.
     *
     * @param currentParameters the current parameter values
     * @return the modified parameters
     */
    public static Map<NoiseEmissionParameters, Double> selectParametersForFile(
            Map<NoiseEmissionParameters,Double> currentParameters) {
        Map<NoiseEmissionParameters, Double> modifiedParameters = 
                new HashMap<>();
        Map<NoiseEmissionParameters, JComponent> parameterComponents = 
                new HashMap<>();

        // Create the dialog window
        JDialog dialog = new JDialog();
        dialog.setTitle("Számítási paraméterek módosítása");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        
        // Add a WindowListener to handle window closing event
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (NoiseEmissionParameters parameter : 
                        NoiseEmissionParameters.getModifiableParameters()) {
                    modifiedParameters.put(parameter, parameter.getDefaultValue());
                }
                dialog.dispose();
                isAccepted = false;
            }
        });
        
        
        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add empty border

        // Create the label for the additional text
        JLabel additionalTextLabel = new JLabel("(Az egyes sorok jobb-klikkel"
                + " módosíthatóak.)");
        // Create a panel for the additional text label
        JPanel additionalTextPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        additionalTextPanel.add(additionalTextLabel);

        mainPanel.add(additionalTextPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Create a panel for each modifiable parameter
        for (NoiseEmissionParameters parameter : NoiseEmissionParameters.
                getModifiableParameters()) {
            JPanel parameterPanel = new JPanel(new BorderLayout());

            // Create a label for the parameter name
            JLabel parameterLabel = new JLabel(parameter.getParameterName());

            parameterLabel.setHorizontalAlignment(JLabel.LEFT);
            
            // Add space between the parameter label and the drop-down list
            Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 30);
            parameterLabel.setBorder(emptyBorder);

            parameterPanel.add(parameterLabel, BorderLayout.WEST);

            // Create a component for selecting the parameter value
            JComponent valueComponent;
            if (parameter.getAllowedValues() != null) {
                JComboBox<Double> valueComboBox = 
                        new JComboBox<>(parameter.getAllowedValues());
                valueComboBox.setSelectedItem(currentParameters.
                        getOrDefault(parameter, parameter.getDefaultValue()));
                valueComponent = valueComboBox;
            } else {
                JTextField valueTextField = new JTextField();
                valueTextField.setText(Double.toString(currentParameters.
                        getOrDefault(parameter, parameter.getDefaultValue())));
                valueComponent = valueTextField;
            }
            // Store the component in the parameterComponents map
            parameterComponents.put(parameter, valueComponent);
            
            valueComponent.setPreferredSize(new Dimension(100, valueComponent.
                    getPreferredSize().height));
            if (valueComponent instanceof JTextField) {
                ((JTextField) valueComponent).
                        setHorizontalAlignment(JTextField.RIGHT);
            }
            parameterPanel.add(valueComponent, BorderLayout.EAST);

            // Add the parameter panel to the main panel
            mainPanel.add(parameterPanel);
        }

        // Create the OK button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            for (NoiseEmissionParameters parameter : NoiseEmissionParameters.
                    getModifiableParameters()) {
                JComponent valueComponent = parameterComponents.get(parameter);
                double selectedValue;

                if (valueComponent instanceof JTextField) {
                    JTextField textField = (JTextField) valueComponent;
                    String valueText = textField.getText().trim();
                    selectedValue = Double.parseDouble(valueText);
                } else if (valueComponent instanceof JComboBox) {
                    JComboBox<Double> comboBox = (JComboBox<Double>) valueComponent;
                    selectedValue = (double) comboBox.getSelectedItem();
                } else {
                    continue; // Skip non-supported component types
                }

                modifiedParameters.put(parameter, selectedValue);
            }

            dialog.dispose();
            isAccepted = true;
        });

        // Add the main panel and OK button to the dialog window
        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.add(okButton, BorderLayout.SOUTH);

        // Set the size and make the dialog window visible
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return modifiedParameters;
    }
 
    /**
     * Displays a dialog for getting distances for noise emission.
     *
     * @return the list of distances
     */
    public static List<Double> getDistancesForNoiseEmission() {
        List<Double> distances = new ArrayList<>();

        // Create the dialog window
        JDialog dialog = new JDialog();
        dialog.setTitle("Adott távolságon adódó zajszint");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);

        // Create the labels
        JLabel dayLabel = new JLabel("Nappal");
        JLabel nightLabel = new JLabel("Éjjel");

        // Create the text fields
        JTextField dayTextField = new JTextField(10);
        JTextField nightTextField = new JTextField(10);

        // Create the buttons
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

       // Set the layout
        dialog.setLayout(new GridLayout(3, 3, 5, 5));

        // Add the components to the dialog window
        dialog.add(dayLabel);
        dialog.add(dayTextField);
        dialog.add(new JLabel("méter"));
        dialog.add(Box.createHorizontalStrut(20)); // Add some space between the text fields
        dialog.add(nightLabel);
        dialog.add(nightTextField);
        dialog.add(new JLabel("méter"));
        dialog.add(new JLabel()); // Empty label for spacing
        dialog.add(okButton);
        dialog.add(cancelButton);
        
        // ActionListener for OK button
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the values from the text fields
                String dayText = dayTextField.getText().replace(',', '.');
                String nightText = nightTextField.getText().replace(',', '.');

                // Parse the values
                double dayValue = Double.parseDouble(dayText);
                double nightValue = Double.parseDouble(nightText);

                // Add the values to the distances list
                distances.add(dayValue);
                distances.add(nightValue);

                // Close the dialog
                dialog.dispose();
            }
        });

        // ActionListener for Cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the dialog without adding any values
                dialog.dispose();
            }
        });
    dialog.pack();
    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);
        return distances;
    }
     
    /**
     * Checks if the file selection was accepted.
     *
     * @return true if the file selection was accepted, false otherwise
     */
    public static boolean isAccepted() {
        return isAccepted;
    }
}
