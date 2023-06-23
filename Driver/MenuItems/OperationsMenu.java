/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver.MenuItems;

import Driver.ControllerManager.ControllerManager;
import Driver.DialogWindows.FileChooser;
import Driver.MainGUI.GUI;
import Driver.OptionPane.OptionPane;
import Driver.MainPanel.ProjectPanel;
import FileReadingAndDataStore.Controllers.DbfController;
import Modifiers.Parameters.CalculationType;
import Modifiers.Controller.ModifierController;
import Saving.Controller.ShapeNoiseOutputController;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import org.locationtech.jts.geom.Geometry;

/**
 *
 * @author szaboa
 */
/**
 * The OperationsMenu class represents a menu component for performing various 
 * operations in a graphical user interface (GUI).
 * It provides functionality for calculating values, deleting columns, and saving data.
 * 
 * <p>The OperationsMenu class contains methods to handle user interactions with the
 * menu items, such as performing calculations on data, deleting columns, and saving
 * results. It relies on the ControllerManager, ProjectPanel, and GUI classes to access
 * necessary controllers and display GUI elements.</p>
 */
public class OperationsMenu extends javax.swing.JMenu {
    private JMenu menu; 
    private JMenu calculateSubMenu;
    private JMenuItem calculateAllMenuItem;
    private JMenuItem laeqCalculationMenuItem;
    private JMenuItem lwCalculationMenuItem;
    private JMenuItem calculateProtectiveDistanceMenuItem;
    private JMenuItem calculateImpactAreaDistanceMenuItem;
    private JMenuItem NoiseEmissionAtGivenDistanceMenuItem;
    private JMenuItem deleteColumnsMenuItem;   
    private JMenuItem saveProtectiveDistanceMenuItem;
    private JMenuItem saveImpactAreaDistanceMenuItem;
    private boolean isContainsColumn = false;
    private ControllerManager controllerManager;
    
    /**
     * Constructs an OperationsMenu object with the specified ControllerManager,
     * ProjectPanel, and GUI objects.
     * 
     * @param controllerManager the ControllerManager object for accessing controllers
     * @param projectPanel the ProjectPanel object for managing project-related operations
     * @param gui the GUI object for accessing GUI elements
     */
    public OperationsMenu(
            ControllerManager controllerManager,
            ProjectPanel projectPanel,
            GUI gui) {
        
        this.controllerManager = controllerManager;       
        
        calculateSubMenu = 
                new JMenu("Számítási mûveletek");
        calculateAllMenuItem = 
                new JMenuItem("Számítás minden értékre");
        laeqCalculationMenuItem = 
                new JMenuItem("Egyenértékû A-hangnyomásszint számítása");
        lwCalculationMenuItem = 
                new JMenuItem("Hangteljesítményszint számítása");
        calculateProtectiveDistanceMenuItem = 
                new JMenuItem("Védõtávolság számítása");
        calculateImpactAreaDistanceMenuItem = 
                new JMenuItem("Hatásterület számítása");
        NoiseEmissionAtGivenDistanceMenuItem = 
                new JMenuItem("Adott távolságon adódó zajterhelés");
        deleteColumnsMenuItem = 
                new JMenuItem("Oszlopok törlése");
        saveProtectiveDistanceMenuItem = 
                new JMenuItem("Védõtávolság mentése");
        saveImpactAreaDistanceMenuItem = 
                new JMenuItem("Hatásterület mentése");

        // Listeners
        calculateAllMenuItem.addActionListener(e -> 
                calculateAll(projectPanel, gui));     
        laeqCalculationMenuItem.addActionListener(e -> 
                calculateLAeq(projectPanel, gui));      
        lwCalculationMenuItem.addActionListener(e -> 
                calculateLw(projectPanel, gui));      
        calculateProtectiveDistanceMenuItem.addActionListener(e -> 
                calculateProtectiveDistance(projectPanel, gui));    
        calculateImpactAreaDistanceMenuItem.addActionListener(e -> 
                calculateImpactAreaDistance(projectPanel, gui));       
        NoiseEmissionAtGivenDistanceMenuItem.addActionListener(e -> 
                calculateNoiseAtGivenDistance(projectPanel, gui));       
        deleteColumnsMenuItem.addActionListener(e -> 
                deleteColumns(projectPanel, gui));      
        saveProtectiveDistanceMenuItem.addActionListener(e -> 
                saveProtectiveD(gui));     
        saveImpactAreaDistanceMenuItem.addActionListener(e -> 
                saveImpactD(gui));  
           
         // Add sub menus to the Számítási Mûveletek menu
        calculateSubMenu.add(calculateAllMenuItem);
        calculateSubMenu.add(Box.createVerticalStrut(1));
        calculateSubMenu.addSeparator();
        calculateSubMenu.add(Box.createVerticalStrut(2));
        calculateSubMenu.add(laeqCalculationMenuItem);
        calculateSubMenu.add(Box.createVerticalStrut(1));
        calculateSubMenu.add(lwCalculationMenuItem);
        calculateSubMenu.add(Box.createVerticalStrut(1));
        calculateSubMenu.addSeparator();
        calculateSubMenu.add(Box.createVerticalStrut(2));
        calculateSubMenu.add(calculateProtectiveDistanceMenuItem);
        calculateSubMenu.add(Box.createVerticalStrut(1));
        calculateSubMenu.add(calculateImpactAreaDistanceMenuItem);
        calculateSubMenu.add(Box.createVerticalStrut(1));
        calculateSubMenu.addSeparator();
        calculateSubMenu.add(Box.createVerticalStrut(2));
        calculateSubMenu.add(NoiseEmissionAtGivenDistanceMenuItem);
        
        // Add menu items to the menu
        menu = new JMenu("Mûveletek");
        menu.add(calculateSubMenu);
        menu.add(Box.createVerticalStrut(2));
        menu.addSeparator();
        menu.add(Box.createVerticalStrut(2));
        menu.add(deleteColumnsMenuItem);
        menu.add(Box.createVerticalStrut(2));
        menu.addSeparator();
        menu.add(Box.createVerticalStrut(2));
        menu.add(saveProtectiveDistanceMenuItem);
        menu.add(Box.createVerticalStrut(1));
        menu.add(saveImpactAreaDistanceMenuItem); 

    }
    
    /**
     * Returns the JMenu component representing the OperationsMenu.
     * 
     * @return the JMenu component
     */
    public JMenu getMenu() {
        return menu;
    }
    
    /**
     * Performs calculations on all values in the selected project.
     * 
     * @param projectPanel the ProjectPanel object for managing project-related operations
     * @param gui the GUI object for accessing GUI elements
     */
    private void calculateAll(
            ProjectPanel projectPanel,
            GUI gui) {
        
        // Access the controllers from the ControllerManager
        DbfController dbfController = controllerManager.getDbfController();
        ModifierController modifierController =
                controllerManager.getModifierController();
        
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();

        // Check if a file is selected
        if (selectedIndex >= 0) {
            if (checkRequiredColumnsExist(gui, selectedIndex)) {
                // Error message from the method
            } else {
                // If the columns are fine, start the calculations
                List<Double> dayLAEQColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                DAY_DECIBEL, selectedIndex);
                List<Double> nightLAEQColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                NIGHT_DECIBEL, selectedIndex);
                List<Double> dayLWColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                SOUND_POWER_LEVEL_NAPPAL, selectedIndex);
                List<Double> nightLWColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                SOUND_POWER_LEVEL_EJJEL, selectedIndex);
                List<Double> dayPROTDISTColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                PROTECTIVE_DISTANCE_NAPPAL, selectedIndex);
                List<Double> nightPROTDISTColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                PROTECTIVE_DISTANCE_EJJEL, selectedIndex);
                List<Double> dayIMPACTDISTColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                IMPACT_AREA_DISTANCE_NAPPAL, selectedIndex);
                List<Double> nightIMPACTDISTColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                IMPACT_AREA_DISTANCE_EJJEL, selectedIndex);

                String dayLAEQColumnName = modifierController.
                        getColumnName(CalculationType.DAY_DECIBEL);
                String nightLAEQColumnName = modifierController.
                        getColumnName(CalculationType.NIGHT_DECIBEL);
                String dayLWColumnName = modifierController.
                        getColumnName(CalculationType.SOUND_POWER_LEVEL_NAPPAL);
                String nightLWColumnName = modifierController.
                        getColumnName(CalculationType.SOUND_POWER_LEVEL_EJJEL);  
                String dayPROTDISTColumnName = modifierController
                        .getColumnName(CalculationType.PROTECTIVE_DISTANCE_NAPPAL);
                String nightPROTDISTColumnName = modifierController.
                        getColumnName(CalculationType.PROTECTIVE_DISTANCE_EJJEL);                 
                String dayIMPACTDISTColumnName = modifierController.
                        getColumnName(CalculationType.IMPACT_AREA_DISTANCE_NAPPAL);
                String nightIMPACTDISTColumnName = modifierController.
                        getColumnName(CalculationType.IMPACT_AREA_DISTANCE_EJJEL);                 

                // Perform the calculation and modification
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), dayLAEQColumnName, 
                        dayLAEQColumnValues);
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), nightLAEQColumnName, 
                        nightLAEQColumnValues);
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), dayLWColumnName, 
                        dayLWColumnValues);
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), nightLWColumnName, 
                        nightLWColumnValues);                
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), dayPROTDISTColumnName, 
                        dayPROTDISTColumnValues);
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), nightPROTDISTColumnName, 
                        nightPROTDISTColumnValues);                 
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), dayIMPACTDISTColumnName, 
                        dayIMPACTDISTColumnValues);
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), nightIMPACTDISTColumnName, 
                        nightIMPACTDISTColumnValues);                

                // Refresh the display of the modified data in the GUI
                JScrollPane scrollPane = projectPanel.
                        updateTableData(controllerManager, selectedIndex);
                gui.updateFileTab(selectedIndex, scrollPane);

               // Set the viewport to the rightmost columns
                JViewport viewport = scrollPane.getViewport();
                JTable table = (JTable) viewport.getView();
                Rectangle cellRect = table.getCellRect(0, 
                        table.getColumnCount() - 1, true);
                table.scrollRectToVisible(cellRect);

                OptionPane.showInfoDialog("Minden számítható adat sikeresen "
                        + "hozzáadva a táblához!");
            }
        }
    }
    /**
    * Calculates the LAeq values [dB] for the selected file and adds them as calculated columns to the table.
    *
    * @param projectPanel The project panel where the table is displayed.
    * @param gui The GUI instance.
    */
    private void calculateLAeq(
            ProjectPanel projectPanel,
            GUI gui) {

        // Access the controllers from the ControllerManager
        DbfController dbfController = controllerManager.getDbfController();
        ModifierController modifierController = 
                controllerManager.getModifierController();        
        
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();

        // Check if a file is selected
        if (selectedIndex >= 0) {
            if (checkRequiredColumnsExist(gui, selectedIndex)) {
                // Error message from the method
            } else {
                // If the columns are fine, start the calculations               
                List<Double> dayColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                DAY_DECIBEL, selectedIndex);
                List<Double> nightColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                NIGHT_DECIBEL, selectedIndex);

                String dayColumnName = modifierController.
                        getColumnName(CalculationType.DAY_DECIBEL);
                String nightColumnName = modifierController.
                        getColumnName(CalculationType.NIGHT_DECIBEL);

                // Perform the calculation and modification for Day_decibel
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), dayColumnName, 
                        dayColumnValues);

                // Perform the calculation and modification for Night_decibel
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), nightColumnName, 
                        nightColumnValues);

                // Refresh the display of the modified data in the GUI
                JScrollPane scrollPane = projectPanel.
                        updateTableData(controllerManager, selectedIndex);
                gui.updateFileTab(selectedIndex, scrollPane);

               // Set the viewport to the rightmost columns
                JViewport viewport = scrollPane.getViewport();
                JTable table = (JTable) viewport.getView();
                Rectangle cellRect = table.getCellRect(0, 
                        table.getColumnCount() - 1, true);
                table.scrollRectToVisible(cellRect);

                OptionPane.showInfoDialog("Egyenértékû A-hangnyomásszint "
                        + "nappalra és éjjelre sikeresen hozzáadva a táblához!");
            }
        }
    }
    
    /**
    * Calculates the Lw values [dB] for the selected file and adds them as calculated columns to the table.
    *
    * @param projectPanel The project panel where the table is displayed.
    * @param gui The GUI instance.
    */
    private void calculateLw(
            ProjectPanel projectPanel,
            GUI gui) {
        
        // Access the controllers from the ControllerManager
        DbfController dbfController = controllerManager.getDbfController();
        ModifierController modifierController = controllerManager.
                getModifierController(); 
        
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();

        // Check if a file is selected
        if (selectedIndex >= 0) {
            if (checkRequiredColumnsExist(gui, selectedIndex)) {
                // Error message from the method
            } else {
                // If the columns are fine, start the calculations  
                List<Double> dayColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                SOUND_POWER_LEVEL_NAPPAL, selectedIndex);
                List<Double> nightColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                SOUND_POWER_LEVEL_EJJEL, selectedIndex);

                String dayColumnName = modifierController.
                        getColumnName(CalculationType.SOUND_POWER_LEVEL_NAPPAL);
                String nightColumnName = modifierController
                        .getColumnName(CalculationType.SOUND_POWER_LEVEL_EJJEL);

                // Perform the calculation and modification for Day_decibel
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), dayColumnName, 
                        dayColumnValues);

                // Perform the calculation and modification for Night_decibel
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), nightColumnName, 
                        nightColumnValues);

                // Refresh the display of the modified data in the GUI
                JScrollPane scrollPane = projectPanel.
                        updateTableData(controllerManager, selectedIndex);
                gui.updateFileTab(selectedIndex, scrollPane);

               // Set the viewport to the rightmost columns
                JViewport viewport = scrollPane.getViewport();
                JTable table = (JTable) viewport.getView();
                Rectangle cellRect = table.getCellRect(0, 
                        table.getColumnCount() - 1, true);
                table.scrollRectToVisible(cellRect);

                OptionPane.showInfoDialog("Hangteljesítményszint nappalra és "
                        + "éjjelre sikeresen hozzáadva a táblához!");
            }
        }
        
    }
    
    /**
    * Calculates the protective distance [m] values for the selected file and adds them as calculated columns to the table.
    *
    * @param projectPanel The project panel where the table is displayed.
    * @param gui The GUI instance.
    */
    private void calculateProtectiveDistance(
            ProjectPanel projectPanel,
            GUI gui) {

        // Access the controllers from the ControllerManager
        DbfController dbfController = controllerManager.getDbfController();
        ModifierController modifierController = 
                controllerManager.getModifierController();         
        
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();

        // Check if a file is selected
        if (selectedIndex >= 0) {
            if (checkRequiredColumnsExist(gui, selectedIndex)) {
                // Error message from the method
            } else {
                // If the columns are fine, start the calculations  
                List<Double> dayColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                PROTECTIVE_DISTANCE_NAPPAL, selectedIndex);
                List<Double> nightColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                PROTECTIVE_DISTANCE_EJJEL, selectedIndex);

                String dayColumnName = modifierController.
                        getColumnName(CalculationType.PROTECTIVE_DISTANCE_NAPPAL);
                String nightColumnName = modifierController.
                        getColumnName(CalculationType.PROTECTIVE_DISTANCE_EJJEL);

                // Perform the calculation and modification for Day_decibel
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), dayColumnName, 
                        dayColumnValues);

                // Perform the calculation and modification for Night_decibel
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), nightColumnName, 
                        nightColumnValues);

                // Refresh the display of the modified data in the GUI
                JScrollPane scrollPane = projectPanel.
                        updateTableData(controllerManager, selectedIndex);
                gui.updateFileTab(selectedIndex, scrollPane);

               // Set the viewport to the rightmost columns
                JViewport viewport = scrollPane.getViewport();
                JTable table = (JTable) viewport.getView();
                Rectangle cellRect = table.getCellRect(0, 
                        table.getColumnCount() - 1, true);
                table.scrollRectToVisible(cellRect);

                OptionPane.showInfoDialog("Védõtávolság nappalra és éjjelre "
                        + "sikeresen hozzáadva a táblához!");
            }
        }
        
    }
    
    /**
    * Calculates the impact area distance [m] values for the selected file and adds them as calculated columns to the table.
    *
    * @param projectPanel The project panel where the table is displayed.
    * @param gui The GUI instance.
    */
    private void calculateImpactAreaDistance(
            ProjectPanel projectPanel,
            GUI gui) {
        
        // Access the controllers from the ControllerManager
        DbfController dbfController = controllerManager.getDbfController();
        ModifierController modifierController = 
                controllerManager.getModifierController();         
        
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();

        // Check if a file is selected
        if (selectedIndex >= 0) {
            if (checkRequiredColumnsExist(gui, selectedIndex)) {
                // Error message from the method
            } else {
                // If the columns are fine, start the calculations  
                List<Double> dayColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                IMPACT_AREA_DISTANCE_NAPPAL, selectedIndex);
                List<Double> nightColumnValues = modifierController.
                        getCalculatedValues(CalculationType.
                                IMPACT_AREA_DISTANCE_EJJEL, selectedIndex);

                String dayColumnName = modifierController.
                        getColumnName(CalculationType.IMPACT_AREA_DISTANCE_NAPPAL);
                String nightColumnName = modifierController.
                        getColumnName(CalculationType.IMPACT_AREA_DISTANCE_EJJEL);

                // Perform the calculation and modification for Day_decibel
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), dayColumnName, 
                        dayColumnValues);

                // Perform the calculation and modification for Night_decibel
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), nightColumnName, 
                        nightColumnValues);

                // Refresh the display of the modified data in the GUI
                JScrollPane scrollPane = projectPanel.
                        updateTableData(controllerManager, selectedIndex);
                gui.updateFileTab(selectedIndex, scrollPane);

               // Set the viewport to the rightmost columns
                JViewport viewport = scrollPane.getViewport();
                JTable table = (JTable) viewport.getView();
                Rectangle cellRect = table.getCellRect(0, 
                        table.getColumnCount() - 1, true);
                table.scrollRectToVisible(cellRect);

                OptionPane.showInfoDialog("Hatásterület nappalra és éjjelre "
                        + "sikeresen hozzáadva a táblához!");
            }
        }
        
    }
    
    /**
    * Calculates the noise [dB] at a given distance and adds the calculated columns to the table.
    *
    * @param projectPanel The ProjectPanel instance.
    * @param gui          The GUI instance.
    */
    private void calculateNoiseAtGivenDistance(
            ProjectPanel projectPanel,
            GUI gui) {
        
        // Access the controllers from the ControllerManager
        DbfController dbfController = controllerManager.getDbfController();
        ModifierController modifierController = 
                controllerManager.getModifierController();  
        
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();

        // Get the column names of the file
        List<String> columnNamesOfSelectedIndex = 
                gui.getColumnNamesForTabIndex(selectedIndex); 
        List<String> columnNamesForCalculation = 
                Arrays.asList("LAeq Nappal", "LAeq Ejjel");

        // Check if a file is selected
        if (selectedIndex >= 0) {
            if (columnNamesOfSelectedIndex.containsAll(columnNamesForCalculation)) {
                // Get the distances for day and night
                List<Double> distances = new ArrayList<>();
                distances = FileChooser.getDistancesForNoiseEmission();

                double dayDistance = distances.get(0);
                dayDistance = Math.round(dayDistance * 10) / 10.0; 
                double nightDistance = distances.get(1);
                nightDistance = Math.round(nightDistance * 10) / 10.0; 

                // Get the column names
                String dayColumnName = "<html>Nappali LAM,kö szint<br>" + 
                        dayDistance + " méteren</html>";
                String nightColumnName = "<html>Éjjeli LAM,kö szint<br>" +
                        nightDistance + " méteren</html>";

                // Perform the calculations
                List<Double> dayDecibelValuesAtGivenDIstance = 
                        modifierController.
                                dayCalculateNoiseEmissionAtGivenDistance
                                (dayDistance, selectedIndex);
                List<Double> nightDecibelValuesAtGivenDIstance = 
                        modifierController.
                                nightCalculateNoiseEmissionAtGivenDistance
                                (nightDistance, selectedIndex);

                // Add the new columns
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), dayColumnName, 
                        dayDecibelValuesAtGivenDIstance);
                modifierController.addCalculatedColumns(dbfController.
                        getFilteredDbfData(selectedIndex), nightColumnName, 
                        nightDecibelValuesAtGivenDIstance);


                // Refresh the display of the modified data in the GUI
                JScrollPane scrollPane = projectPanel.
                        updateTableData(controllerManager, selectedIndex);
                gui.updateFileTab(selectedIndex, scrollPane);

               // Set the viewport to the rightmost columns
                JViewport viewport = scrollPane.getViewport();
                JTable table = (JTable) viewport.getView();
                Rectangle cellRect = table.getCellRect(0, 
                        table.getColumnCount() - 1, true);
                table.scrollRectToVisible(cellRect);

                OptionPane.showInfoDialog("Adott távolságra számított "
                        + "zajterhelés sikeresen hozzáadva a táblához!");
            } else {
                OptionPane.showErrorDialog("LAeq számítások hiányzonak, "
                        + "nem lehet adott távolságra számolni!");
            }
        }
        
    }
    
    /**
    * Deletes the selected columns from the table.
    *
    * @param projectPanel The ProjectPanel instance.
    * @param gui          The GUI instance.
    */
    private void deleteColumns(
            ProjectPanel projectPanel,
            GUI gui) {      
        
        // Access the controllers from the ControllerManager
        DbfController dbfController = controllerManager.getDbfController();
        ModifierController modifierController = 
                controllerManager.getModifierController();  
        
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();

        // Check if a file is selected
        if (selectedIndex >= 0) {
            List<String> columnNamesOfSelectedIndex = 
                    gui.getColumnNamesForTabIndex(selectedIndex);
            List<String> columnNamesToDelete = FileChooser.
                    selectColumnsToDelete(columnNamesOfSelectedIndex);

            // get the column names for the info dialog
            String columnNamesForInfo = "";
            for (int i = 0; i < columnNamesToDelete.size(); i++) {
                if (i != columnNamesToDelete.size() - 1) {
                    columnNamesForInfo += columnNamesToDelete.get(i) + ", ";
                } else {
                    columnNamesForInfo += columnNamesToDelete.get(i);
                }
            }

            // Show a confirmation dialog                                            
            boolean confirmed = OptionPane.showConfirmDialog("<html>Törlésre"
                    + " kiválasztott oszlopok:<br><br>" + columnNamesForInfo +
                    ".<br><br>Biztos törölni akarod?</html>");

            if (confirmed) {

                // Perform the deletion
                modifierController.deleteColumns(dbfController.
                        getFilteredDbfData(selectedIndex), columnNamesToDelete);

                // Refresh the display of the modified data in the GUI
                JScrollPane scrollPane = projectPanel.
                        applyColumnDeletion(controllerManager, selectedIndex);
                gui.updateFileTab(selectedIndex, scrollPane);

                OptionPane.showInfoDialog(columnNamesForInfo + 
                        " oszlop(ok) törlése sikeres!");
            }
        }
    }
    
    /**
    * Saves the protective distance values to a shapefile.
    *
    * @param gui The GUI instance.
    */
    private void saveProtectiveD(
            GUI gui) {
        
        // Access the controllers from the ControllerManager
        ModifierController modifierController = 
                controllerManager.getModifierController(); 
        ShapeNoiseOutputController shapeNoiseOutputController = 
                controllerManager.getShapeNoiseOutputController();
        
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();
        List<String> columnNamesOfSelectedIndex = 
                gui.getColumnNamesForTabIndex(selectedIndex);

        if (columnNamesOfSelectedIndex.contains("Vedotavolsag Nappal") 
                && columnNamesOfSelectedIndex.contains("Vedotavolsag Ejjel")) {
            isContainsColumn = true;
        }

        // Check if a file is selected
        if (selectedIndex >= 0 && isContainsColumn) {
            File outputShapeFile = FileChooser.selectSaveFile();
            if (outputShapeFile != null) {         
                if (outputShapeFile.exists()) {
                    // File already exists, show a confirmation dialog
                    boolean confirmed = OptionPane.
                            showConfirmDialog("A fájl már létezik! Felülírod?");
                    if (confirmed) {
                        try {                                                             
                            List<Double> dayValues = modifierController.
                                    getCalculatedValues(CalculationType.
                                            PROTECTIVE_DISTANCE_NAPPAL, selectedIndex);
                            List<Double> nightValues = modifierController.
                                    getCalculatedValues(CalculationType.
                                            PROTECTIVE_DISTANCE_EJJEL, selectedIndex);   
                            List<Geometry> newGeometries = 
                                    modifierController.createNewGeometry(dayValues, 
                                            nightValues, selectedIndex);  
                            String columnName = "Vedotav";

                            shapeNoiseOutputController.
                                    saveNewGeometriesShapefile(outputShapeFile, 
                                            newGeometries, columnName, selectedIndex);                

                            OptionPane.showInfoDialog("Fájl sikeresen mentve: "
                                    + outputShapeFile.getPath());
                            isContainsColumn = false;
                        } catch (IOException ex) {
                            OptionPane.showErrorDialog("Sikertelen mentés!");
                            isContainsColumn = false;
                            // Handle the IOException (e.g., show an error message)
                            ex.printStackTrace();
                        }
                    } else {
                        OptionPane.showInfoDialog("Mentés megszakítva!");
                        isContainsColumn = false;
                    }
                } else {
                    try {                            
                        List<Double> dayValues = modifierController.
                                getCalculatedValues(CalculationType.
                                        PROTECTIVE_DISTANCE_NAPPAL, selectedIndex);
                        List<Double> nightValues = modifierController.
                                getCalculatedValues(CalculationType.
                                        PROTECTIVE_DISTANCE_EJJEL, selectedIndex);                    
                        List<Geometry> newGeometries = 
                                modifierController.createNewGeometry(dayValues, 
                                        nightValues, selectedIndex); 
                        String columnName = "Vedotav";

                        shapeNoiseOutputController.
                                saveNewGeometriesShapefile(outputShapeFile, 
                                        newGeometries, columnName, selectedIndex);

                        OptionPane.showInfoDialog("Fájl sikeresen mentve: " +
                                outputShapeFile.getPath());
                        isContainsColumn = false;
                    } catch (IOException ex) {
                        OptionPane.showErrorDialog("Sikertelen mentés!");
                        isContainsColumn = false;
                        // Handle the IOException (e.g., show an error message)
                        ex.printStackTrace();
                    }
                }
            } else {
                OptionPane.showErrorDialog("Sikertelen mentés!");
                isContainsColumn = false;
            }  
        } else {
            OptionPane.showErrorDialog("Nincsenek menthetõ Védõtváolság "
                    + "eredmények. Végezz el számítást elõször!");
        }
    }
    
    /**
    * Saves the impact area distance values to a shapefile.
    *
    * @param gui The GUI instance.
    */
    private void saveImpactD(
            GUI gui) {
        // Access the controllers from the ControllerManager
        ModifierController modifierController = 
                controllerManager.getModifierController(); 
        ShapeNoiseOutputController shapeNoiseOutputController = 
                controllerManager.getShapeNoiseOutputController();        
        
        // Get the selected index from the GUI or retrieve it from your application's context
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();
        List<String> columnNamesOfSelectedIndex = 
                gui.getColumnNamesForTabIndex(selectedIndex);

        if (columnNamesOfSelectedIndex.contains("Hatasterulet Nappal") 
                && columnNamesOfSelectedIndex.contains("Hatasterulet Ejjel")) {
            isContainsColumn = true;
        }

        // Check if a file is selected
        if (selectedIndex >= 0 && isContainsColumn) {
            File outputShapeFile = FileChooser.selectSaveFile();
            if (outputShapeFile != null) {         
                if (outputShapeFile.exists()) {
                    // File already exists, show a confirmation dialog
                    boolean confirmed = OptionPane.
                            showConfirmDialog("A fájl már létezik! Felülírod?");
                    if (confirmed) {
                        try {                                                             
                            List<Double> dayValues = modifierController.
                                    getCalculatedValues(CalculationType.
                                            IMPACT_AREA_DISTANCE_NAPPAL, selectedIndex);
                            List<Double> nightValues = modifierController.
                                    getCalculatedValues(CalculationType.
                                            IMPACT_AREA_DISTANCE_EJJEL, selectedIndex);   
                            List<Geometry> newGeometries = 
                                    modifierController.createNewGeometry(dayValues, 
                                            nightValues, selectedIndex);  
                            String columnName = "Hataster";

                            shapeNoiseOutputController.
                                    saveNewGeometriesShapefile(outputShapeFile,
                                            newGeometries, columnName, selectedIndex);                

                            OptionPane.showInfoDialog("Fájl sikeresen mentve: " 
                                    + outputShapeFile.getPath());
                            isContainsColumn = false;
                        } catch (IOException ex) {
                            OptionPane.showErrorDialog("Sikertelen mentés!");
                            isContainsColumn = false;
                            // Handle the IOException (e.g., show an error message)
                            ex.printStackTrace();
                        }
                    } else {
                        OptionPane.showInfoDialog("Mentés megszakítva!");
                        isContainsColumn = false;
                    }
                } else {
                    try {                            
                        List<Double> dayValues = modifierController.
                                getCalculatedValues(CalculationType.
                                        IMPACT_AREA_DISTANCE_NAPPAL, selectedIndex);
                        List<Double> nightValues = modifierController.
                                getCalculatedValues(CalculationType.
                                        IMPACT_AREA_DISTANCE_EJJEL, selectedIndex);                    
                        List<Geometry> newGeometries = 
                                modifierController.createNewGeometry(dayValues, 
                                        nightValues, selectedIndex); 
                        String columnName = "Hataster";

                        shapeNoiseOutputController.
                                saveNewGeometriesShapefile(outputShapeFile, 
                                        newGeometries, columnName, selectedIndex);

                        OptionPane.showInfoDialog("Fájl sikeresen mentve: " +
                                outputShapeFile.getPath());
                        isContainsColumn = false;
                    } catch (IOException ex) {
                        OptionPane.showErrorDialog("Sikertelen mentés!");
                        isContainsColumn = false;
                        // Handle the IOException (e.g., show an error message)
                        ex.printStackTrace();
                    }
                }
            } else {
                OptionPane.showErrorDialog("Sikertelen mentés!");
                isContainsColumn = false;
            }  
        } else {
            OptionPane.showErrorDialog("Nincsenek menthetõ Hatásterület "
                    + "eredmények. Végezz el számítást elõször!");
        }
    }
    
    /**
    * Returns a list of input column names.
    *
    * @return the list of input column names
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
    * Checks if the required columns exist in the selected file.
    *
    * @param gui           the GUI object
    * @param selectedIndex the index of the selected file
    * @return {@code true} if all required columns exist, {@code false} otherwise
    */
    private boolean checkRequiredColumnsExist(GUI gui, int selectedIndex) {
        // Get the column names of the opened file and convert to Collection
        Collection<String> tmpcolumnNamesOfSelectedIndex = 
                gui.getColumnNamesForTabIndex(selectedIndex);
        List<String> columnNamesOfSelectedIndex = 
                new ArrayList<>(tmpcolumnNamesOfSelectedIndex);

        // Get the input column names and convert to Colection               
        Collection<String> tmpinputColumns = inputColumnNames();
        List<String> inputColumns = new ArrayList<>(tmpinputColumns);

        String columnNamesForInfo = "";
        // Check if the user deleted necessary column(s) and stop the calculation if so
        if (!columnNamesOfSelectedIndex.containsAll(inputColumns)) {
            inputColumns.removeAll(tmpcolumnNamesOfSelectedIndex);
            for (int i = 0; i < inputColumns.size(); i++) {
                if (i != inputColumns.size() -1 ) {
                    columnNamesForInfo += inputColumns.get(i) + ", ";
                } else {
                    columnNamesForInfo += inputColumns.get(i);
                }
            }
            OptionPane.showErrorDialog(columnNamesForInfo + 
                    " oszlop(ok) nem léteznek! Olvasd be újra a fájlt "
                            + "számítás indításához!");
            return true;
        } else {
            return false;
        }

    }

}
