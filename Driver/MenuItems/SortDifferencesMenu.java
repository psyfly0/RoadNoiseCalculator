/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver.MenuItems;

import Driver.MainGUI.GUI;
import Calculators.SortingAndDifferencesOfResults.NoiseEmissionSortAndDifferences;
import Driver.ControllerManager.ControllerManager;
import Driver.DialogWindows.FileChooser;
import Driver.OptionPane.OptionPane;
import Driver.MainPanel.ProjectPanel;
import FileReadingAndDataStore.Controllers.DbfController;
import Modifiers.Controller.ModifierController;
import java.awt.Rectangle;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;

/**
 *
 * @author szaboa
 */
/**
 * The SortDifferencesMenu class represents a menu component for sorting and managing differences
 * in a graphical user interface (GUI).
 * It provides functionality for sorting data based on different criteria and displaying differences
 * in various columns.
 *
 * <p>The SortDifferencesMenu class contains methods to handle user interactions with the menu items,
 * such as sorting data by LAeq (day/night), protective distance (day/night), and impact area (day/night),
 * as well as calculating and displaying differences between different columns.
 * It relies on the ControllerManager, ProjectPanel, and GUI classes to access the necessary controllers
 * and update the GUI elements accordingly.</p>
 */
public class SortDifferencesMenu {
    private JMenu menu;
    private JMenu sortSubMenu;    
    private JMenuItem sortByLAEQDAYMenuItem;
    private JMenuItem sortByLAEQNIGHTMenuItem;
    private JMenuItem sortByProtectiveDAYMenuItem;
    private JMenuItem sortByProtectiveNIGHTMenuItem;
    private JMenuItem sortByImpactDAYMenuItem;
    private JMenuItem sortByImpactNIGHTMenuItem;
    private JMenuItem restoreOriginalOrderMenuItem;
    private JMenuItem sortByLAEQDAYDIFFERENCEMenuItem;
    private JMenuItem sortByLAEQNIGHTDIFFERENCEMenuItem;
    private JMenuItem calculateDifferencesMenuItem;   
    private boolean isContainsColumn = false;
    private ControllerManager controllerManager;
    
    /**
    * Constructor for the SortDifferencesMenu class.
    * @param controllerManager The controller manager used to access the controllers.
    * @param projectPanel The project panel.
    * @param gui The GUI instance.
    */
    public SortDifferencesMenu(
            ControllerManager controllerManager,
            ProjectPanel projectPanel,
            GUI gui) {
        
        this.controllerManager = controllerManager;
        
        sortSubMenu = new JMenu("Rendezés");
        // Create menu items for the Rendezés submenu
        sortByLAEQDAYMenuItem = 
                new JMenuItem("...nappali LAeq alapján");
        sortByLAEQNIGHTMenuItem = 
                new JMenuItem("...éjjeli LAeq alapján");
        sortByProtectiveDAYMenuItem = 
                new JMenuItem("...nappali védõtávolság alapján");
        sortByProtectiveNIGHTMenuItem = 
                new JMenuItem("...éjjeli védõtávolság alapján");
        sortByImpactDAYMenuItem = 
                new JMenuItem("...nappali hatásterület alapján");
        sortByImpactNIGHTMenuItem = 
                new JMenuItem("...éjjeli hatásterület alapján");
        sortByLAEQDAYDIFFERENCEMenuItem = 
                new JMenuItem("Nappali LAeq különbségek rendezése");
        sortByLAEQNIGHTDIFFERENCEMenuItem = 
                new JMenuItem("Éjjeli LAeq különbségek rendezése");
        restoreOriginalOrderMenuItem = 
                new JMenuItem("Eredeti sorrend visszaállítása");
        calculateDifferencesMenuItem = 
                new JMenuItem("Állapotok különbsége");
        
        // Listeners
        sortByLAEQDAYMenuItem.addActionListener(e -> 
                sortByLAeqDay(projectPanel, gui));       
        sortByLAEQNIGHTMenuItem.addActionListener(e -> 
                sortByLAeqNight(projectPanel, gui));      
        sortByProtectiveDAYMenuItem.addActionListener(e -> 
                sortByProtectiveDay(projectPanel, gui));    
        sortByProtectiveNIGHTMenuItem.addActionListener(e -> 
                sortByProtectiveNight(projectPanel, gui));       
        sortByImpactDAYMenuItem.addActionListener(e -> 
                sortByImpactDay(projectPanel, gui));       
        sortByImpactNIGHTMenuItem.addActionListener(e -> 
                sortByImpactNight(projectPanel, gui));      
        sortByLAEQDAYDIFFERENCEMenuItem.addActionListener(e -> 
                sortByLAeqDifferenceDay(projectPanel, gui));       
        sortByLAEQNIGHTDIFFERENCEMenuItem.addActionListener(e -> 
                sortByLAeqDifferenceNight(projectPanel, gui));       
        restoreOriginalOrderMenuItem.addActionListener(e -> 
                restoreOriginalOrder(projectPanel, gui));       
        calculateDifferencesMenuItem.addActionListener(e -> 
                calculateDifferences(projectPanel, gui));
              
        // Add sub menus to the Rendezés menu
        sortSubMenu.add(sortByLAEQDAYMenuItem);
        sortSubMenu.add(Box.createVerticalStrut(1));
        sortSubMenu.add(sortByLAEQNIGHTMenuItem);
        sortSubMenu.addSeparator();
        sortSubMenu.add(Box.createVerticalStrut(1));
        sortSubMenu.add(sortByProtectiveDAYMenuItem);
        sortSubMenu.add(Box.createVerticalStrut(1));
        sortSubMenu.add(sortByProtectiveNIGHTMenuItem);
        sortSubMenu.addSeparator();
        sortSubMenu.add(Box.createVerticalStrut(1));
        sortSubMenu.add(sortByImpactDAYMenuItem);
        sortSubMenu.add(Box.createVerticalStrut(1));
        sortSubMenu.add(sortByImpactNIGHTMenuItem);
        sortSubMenu.add(Box.createVerticalStrut(2));
        sortSubMenu.addSeparator();
        sortSubMenu.add(Box.createVerticalStrut(2));
        sortSubMenu.add(sortByLAEQDAYDIFFERENCEMenuItem);
        sortSubMenu.add(Box.createVerticalStrut(1));
        sortSubMenu.add(sortByLAEQNIGHTDIFFERENCEMenuItem);
        sortSubMenu.add(Box.createVerticalStrut(2));
        sortSubMenu.addSeparator();        
        sortSubMenu.add(restoreOriginalOrderMenuItem);
        
        // Add menu items to the menu
        menu = new JMenu("Rendezés / Különbség");
        menu.add(sortSubMenu);
        menu.add(Box.createVerticalStrut(2));
        menu.add(calculateDifferencesMenuItem);
               
    }
    
    /**
    * Getter method to retrieve the menu.
    * @return The menu.
    */
    public JMenu getMenu() {
        return menu;
    }
    
    /**
    * Sorts the data by LAeq Day.
    * @param projectPanel The project panel.
    * @param gui The GUI instance.
    */
    private void sortByLAeqDay(
            ProjectPanel projectPanel,
            GUI gui) {
        
        // Access the controllers from the ControllerManager
        ModifierController modifierController = 
                controllerManager.getModifierController();         
                
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();
        List<String> columnNamesOfSelectedIndex = 
                gui.getColumnNamesForTabIndex(selectedIndex);

        if (columnNamesOfSelectedIndex.contains("LAeq Nappal")) {
            isContainsColumn = true;
        }
        // Check if a file is selected
        if (selectedIndex >= 0 && isContainsColumn) {
            List<List<Object>> sortedData = modifierController.
                    invokeNoiseEmissionSortBy(NoiseEmissionSortAndDifferences.
                            SortBy.DAY_DECIBEL_SORT, selectedIndex);

            // Refresh the display of the modified data in the GUI
            JScrollPane scrollPane = projectPanel.
                    refreshTableData(sortedData, selectedIndex);

            gui.updateFileTab(selectedIndex, scrollPane);
            isContainsColumn = false;

           // Set the viewport to the rightmost columns
            JViewport viewport = scrollPane.getViewport();
            JTable table = (JTable) viewport.getView();
            Rectangle cellRect = table.getCellRect(0, 
                    table.getColumnCount() - 1, true);
            table.scrollRectToVisible(cellRect);

            OptionPane.showInfoDialog("Egyenértékû A-hangnyomásszint rendezése"
                    + " nappalra sikeresen elvégezve!");
        } else {
            OptionPane.showErrorDialog("Nincsenek rendezhetõ LAeq Nappal "
                    + "eredmények. Végezz el számítást elõször!");
        }
    }
    
    /**
    * Sorts the data by LAeq Night.
    * @param projectPanel The project panel.
    * @param gui The GUI instance.
    */
    private void sortByLAeqNight(
            ProjectPanel projectPanel,
            GUI gui) {

        // Access the controllers from the ControllerManager
        ModifierController modifierController = 
                controllerManager.getModifierController();         
        
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();
        List<String> columnNamesOfSelectedIndex = 
                gui.getColumnNamesForTabIndex(selectedIndex);

        if (columnNamesOfSelectedIndex.contains("LAeq Ejjel")) {
            isContainsColumn = true;
        }
        // Check if a file is selected
        if (selectedIndex >= 0 && isContainsColumn) {
                List<List<Object>> sortedData = modifierController.
                        invokeNoiseEmissionSortBy(NoiseEmissionSortAndDifferences.
                                SortBy.NIGHT_DECIBEL_SORT, selectedIndex);

                // Refresh the display of the modified data in the GUI
                JScrollPane scrollPane = projectPanel.
                        refreshTableData(sortedData, selectedIndex);

                gui.updateFileTab(selectedIndex, scrollPane);
                isContainsColumn = false;

           // Set the viewport to the rightmost columns
            JViewport viewport = scrollPane.getViewport();
            JTable table = (JTable) viewport.getView();
            Rectangle cellRect = table.getCellRect(0, 
                    table.getColumnCount() - 1, true);
            table.scrollRectToVisible(cellRect);

            OptionPane.showInfoDialog("Egyenértékû A-hangnyomásszint rendezése"
                    + " éjjelre sikeresen elvégezve!");
        } else {
            OptionPane.showErrorDialog("Nincsenek rendezhetõ LAeq Éjjel"
                    + " eredmények. Végezz el számítást elõször!");
        }
    }
    
    /**
    * Sorts the data by Protective distance Day.
    * @param projectPanel The project panel.
    * @param gui The GUI instance.
    */
    private void sortByProtectiveDay(
            ProjectPanel projectPanel,
            GUI gui) {
        
        // Access the controllers from the ControllerManager
        ModifierController modifierController = 
                controllerManager.getModifierController();     
        
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();
        List<String> columnNamesOfSelectedIndex = 
                gui.getColumnNamesForTabIndex(selectedIndex);

        if (columnNamesOfSelectedIndex.contains("Vedotavolsag Nappal")) {
            isContainsColumn = true;
        }
        // Check if a file is selected
        if (selectedIndex >= 0 && isContainsColumn) {
                List<List<Object>> sortedData = modifierController.
                        invokeNoiseEmissionSortBy(NoiseEmissionSortAndDifferences.
                                SortBy.PROTECTIVE_DISTANCE_DAY_SORT, selectedIndex);

                // Refresh the display of the modified data in the GUI
                JScrollPane scrollPane = 
                        projectPanel.refreshTableData(sortedData, selectedIndex);

                gui.updateFileTab(selectedIndex, scrollPane);
                isContainsColumn = false;

           // Set the viewport to the rightmost columns
            JViewport viewport = scrollPane.getViewport();
            JTable table = (JTable) viewport.getView();
            Rectangle cellRect = table.getCellRect(0, 
                    table.getColumnCount() - 1, true);
            table.scrollRectToVisible(cellRect);

            OptionPane.showInfoDialog("Védõtávolság rendezése nappalra "
                    + "sikeresen elvégezve!");
        } else {
            OptionPane.showErrorDialog("Nincsenek rendezhetõ Védõtávolság "
                    + "Nappal eredmények. Végezz el számítást elõször!");
        }
    }
    
    /**
    * Sorts the data by Protective distance Night.
    * @param projectPanel The project panel.
    * @param gui The GUI instance.
    */
    private void sortByProtectiveNight(
            ProjectPanel projectPanel,
            GUI gui) {
        
        // Access the controllers from the ControllerManager
        ModifierController modifierController =
                controllerManager.getModifierController();          
        
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();
        List<String> columnNamesOfSelectedIndex = 
                gui.getColumnNamesForTabIndex(selectedIndex);

        if (columnNamesOfSelectedIndex.contains("Vedotavolsag Ejjel")) {
            isContainsColumn = true;
        }
        // Check if a file is selected
        if (selectedIndex >= 0 && isContainsColumn) {
            List<List<Object>> sortedData = modifierController.
                    invokeNoiseEmissionSortBy(NoiseEmissionSortAndDifferences.SortBy.
                            PROTECTIVE_DISTANCE_NIGHT_SORT, selectedIndex);

            // Refresh the display of the modified data in the GUI
            JScrollPane scrollPane = 
                    projectPanel.refreshTableData(sortedData, selectedIndex);

            gui.updateFileTab(selectedIndex, scrollPane);
            isContainsColumn = false;

           // Set the viewport to the rightmost columns
            JViewport viewport = scrollPane.getViewport();
            JTable table = (JTable) viewport.getView();
            Rectangle cellRect = table.getCellRect(0, 
                    table.getColumnCount() - 1, true);
            table.scrollRectToVisible(cellRect);

            OptionPane.showInfoDialog("Védõtávolság rendezése éjjelre sikeresen"
                    + " elvégezve!");
        } else {
            OptionPane.showErrorDialog("Nincsenek rendezhetõ Védõtávolság Éjjel"
                    + " eredmények. Végezz el számítást elõször!");
        }
    }
    
    /**
    * Sorts the data by Impact area distance Day.
    * @param projectPanel The project panel.
    * @param gui The GUI instance.
    */
    private void sortByImpactDay(
            ProjectPanel projectPanel,
            GUI gui) {
        
        // Access the controllers from the ControllerManager
        ModifierController modifierController = 
                controllerManager.getModifierController();        
        
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();
        List<String> columnNamesOfSelectedIndex = 
                gui.getColumnNamesForTabIndex(selectedIndex);

        if (columnNamesOfSelectedIndex.contains("Hatasterulet Nappal")) {
            isContainsColumn = true;
        }
        // Check if a file is selected
        if (selectedIndex >= 0 && isContainsColumn) {
            List<List<Object>> sortedData = modifierController.
                    invokeNoiseEmissionSortBy(NoiseEmissionSortAndDifferences.SortBy.
                            IMPACT_AREA_DISTANCE_DAY_SORT, selectedIndex);

            // Refresh the display of the modified data in the GUI
            JScrollPane scrollPane = 
                    projectPanel.refreshTableData(sortedData, selectedIndex);

            gui.updateFileTab(selectedIndex, scrollPane);
            isContainsColumn = false;

           // Set the viewport to the rightmost columns
            JViewport viewport = scrollPane.getViewport();
            JTable table = (JTable) viewport.getView();
            Rectangle cellRect = table.getCellRect(0, 
                    table.getColumnCount() - 1, true);
            table.scrollRectToVisible(cellRect);

            OptionPane.showInfoDialog("Hatásterület rendezése nappalra "
                    + "sikeresen elvégezve!");
        } else {
            OptionPane.showErrorDialog("Nincsenek rendezhetõ Hatásterület Nappal"
                    + " eredmények. Végezz el számítást elõször!");
        }
    }
    
    /**
    * Sorts the data by Impact area distance Night.
    * @param projectPanel The project panel.
    * @param gui The GUI instance.
    */
    private void sortByImpactNight(
            ProjectPanel projectPanel,
            GUI gui) {
        
        // Access the controllers from the ControllerManager
        ModifierController modifierController = 
                controllerManager.getModifierController();            
        
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();
        List<String> columnNamesOfSelectedIndex = 
                gui.getColumnNamesForTabIndex(selectedIndex);

        if (columnNamesOfSelectedIndex.contains("Hatasterulet Ejjel")) {
            isContainsColumn = true;
        }
        // Check if a file is selected
        if (selectedIndex >= 0 && isContainsColumn) {
                List<List<Object>> sortedData = modifierController.
                        invokeNoiseEmissionSortBy(NoiseEmissionSortAndDifferences.
                                SortBy.IMPACT_AREA_DISTANCE_NIGHT_SORT, selectedIndex);

                // Refresh the display of the modified data in the GUI
                JScrollPane scrollPane = 
                        projectPanel.refreshTableData(sortedData, selectedIndex);

                gui.updateFileTab(selectedIndex, scrollPane);
                isContainsColumn = false;

           // Set the viewport to the rightmost columns
            JViewport viewport = scrollPane.getViewport();
            JTable table = (JTable) viewport.getView();
            Rectangle cellRect = table.getCellRect(0, 
                    table.getColumnCount() - 1, true);
            table.scrollRectToVisible(cellRect);

            OptionPane.showInfoDialog("Hatásterület rendezése éjjelre sikeresen"
                    + " elvégezve!");
        } else {
            OptionPane.showErrorDialog("Nincsenek rendezhetõ Hatásterület Éjjel"
                    + " eredmények. Végezz el számítást elõször!");
        }
    }
    
    /**
    * Sorts the data by the calculated differences between two files (Day).
    * @param projectPanel The project panel.
    * @param gui The GUI instance.
    */
    private void sortByLAeqDifferenceDay(
            ProjectPanel projectPanel,
            GUI gui) {
        
        // Access the controllers from the ControllerManager
        ModifierController modifierController = 
                controllerManager.getModifierController();           
        
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();
        List<String> columnNamesOfSelectedIndex = 
                gui.getColumnNamesForTabIndex(selectedIndex);

        for (String columnName : columnNamesOfSelectedIndex) {
            if (columnName.contains("LAeq Nappal különbsége")) {
                isContainsColumn = true;
                break;
            }
        }
        // Check if a file is selected
        if (selectedIndex >= 0 && isContainsColumn) {
                List<List<Object>> sortedData = modifierController.
                        invokeNoiseEmissionSortBy(NoiseEmissionSortAndDifferences.
                                SortBy.DAY_DECIBEL_DIFFERENCE_SORT, selectedIndex);

                // Refresh the display of the modified data in the GUI
                JScrollPane scrollPane = 
                        projectPanel.refreshTableData(sortedData, selectedIndex);

                gui.updateFileTab(selectedIndex, scrollPane);
                isContainsColumn = false;

           // Set the viewport to the rightmost columns
            JViewport viewport = scrollPane.getViewport();
            JTable table = (JTable) viewport.getView();
            Rectangle cellRect = table.getCellRect(0, 
                    table.getColumnCount() - 1, true);
            table.scrollRectToVisible(cellRect);

            OptionPane.showInfoDialog("Egyenértékû A-hangnyomásszint nappali"
                    + " különbségek rendezése sikeresen elvégezve!");
        } else {
            OptionPane.showErrorDialog("Nincsenek rendezhetõ egyenértékû"
                    + " A-hangnyomásszint különbség eredmények. Végezz el "
                    + "különbség számítást elõször!");
        }
    }
    
    /**
    * Sorts the data by the calculated differences between two files (Night).
    * @param projectPanel The project panel.
    * @param gui The GUI instance.
    */
    private void sortByLAeqDifferenceNight(
            ProjectPanel projectPanel,
            GUI gui) {
        
        // Access the controllers from the ControllerManager
        ModifierController modifierController = 
                controllerManager.getModifierController();          
        
            // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();
        List<String> columnNamesOfSelectedIndex = 
                gui.getColumnNamesForTabIndex(selectedIndex);

        for (String columnName : columnNamesOfSelectedIndex) {
            if (columnName.contains("LAeq Ejjel különbsége")) {
                isContainsColumn = true;
                break;
            }
        }
        // Check if a file is selected
        if (selectedIndex >= 0 && isContainsColumn) {
                List<List<Object>> sortedData = modifierController.
                        invokeNoiseEmissionSortBy(NoiseEmissionSortAndDifferences.
                                SortBy.NIGHT_DECIBEL_DIFFERENCE_SORT, selectedIndex);

                // Refresh the display of the modified data in the GUI
                JScrollPane scrollPane =
                        projectPanel.refreshTableData(sortedData, selectedIndex);

                gui.updateFileTab(selectedIndex, scrollPane);
                isContainsColumn = false;

           // Set the viewport to the rightmost columns
            JViewport viewport = scrollPane.getViewport();
            JTable table = (JTable) viewport.getView();
            Rectangle cellRect = table.getCellRect(0, 
                    table.getColumnCount() - 1, true);
            table.scrollRectToVisible(cellRect);

            OptionPane.showInfoDialog("Egyenértékû A-hangnyomásszint éjjeli"
                    + " különbségek rendezése sikeresen elvégezve!");
        } else {
            OptionPane.showErrorDialog("Nincsenek rendezhetõ egyenértékû"
                    + " A-hangnyomásszint különbség eredmények. Végezz el"
                    + " különbség számítást elõször!");
        }
    }
    
    /**
    * Restore the original order of a file.
    * @param projectPanel The project panel.
    * @param gui The GUI instance.
    */
    private void restoreOriginalOrder(
            ProjectPanel projectPanel,
            GUI gui) {
        
        // Access the controllers from the ControllerManager
        ModifierController modifierController = 
                controllerManager.getModifierController();            
        
        // Get the selected index from the GUI
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();
        // Check if a file is selected
        if (selectedIndex >= 0) {
             try {  
                List<List<Object>> sortedData = modifierController.
                        invokeNoiseEmissionSortBy(NoiseEmissionSortAndDifferences.
                                SortBy.ORIGINAL_ORDER, selectedIndex);

                // Refresh the display of the modified data in the GUI
                JScrollPane scrollPane = 
                        projectPanel.refreshTableData(sortedData, selectedIndex);

                gui.updateFileTab(selectedIndex, scrollPane);

                // Set the viewport to the rightmost columns
                JViewport viewport = scrollPane.getViewport();
                JTable table = (JTable) viewport.getView();
                Rectangle cellRect = 
                        table.getCellRect(0, table.getColumnCount() - 1, true);
                table.scrollRectToVisible(cellRect);

                OptionPane.showInfoDialog("Eredeti sorrend visszaállítása "
                        + "sikeresen elvégezve!");
                } catch (IllegalStateException ex) {
                    OptionPane.showErrorDialog("Nincsenek rendezhetõ eredmények."
                            + " Végezz el számítást elõször!");
                }
        }
    }
    
    /**
    * Calcualtes differences between two files and creating new columns to disaply it.
    * @param projectPanel The project panel.
    * @param gui The GUI instance.
    */
    private void calculateDifferences(
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
            // Get the list of opened files from the GUI
            List<File> openedFiles = gui.getOpenedFiles();

            // Call the file selection method in FileChooser class
            List<Object> selection = FileChooser.selectFiles(openedFiles);
            if (selection.size() == 2) {
                List<File> selectedFiles = (List<File>) selection.get(0);
                List<String> tmpDifferenceCase = (List<String>) selection.get(1);
                String selectedDifferenceCase = tmpDifferenceCase.get(0);

                // Extract the selected file indexes
                int fileIndex1 = openedFiles.indexOf(selectedFiles.get(0));
                int fileIndex2 = openedFiles.indexOf(selectedFiles.get(1));

                // Get the real names of the column based on the case
                String actualColumnName = 
                        selectedColumnForDifference(selectedDifferenceCase);

                // Get the column names of both files
                List<String> columnNamesOfSelectedIndex1 = 
                        gui.getColumnNamesForTabIndex(fileIndex1);    
                List<String> columnNamesOfSelectedIndex2 = 
                        gui.getColumnNamesForTabIndex(fileIndex2);

                if (columnNamesOfSelectedIndex1.contains(actualColumnName) 
                        && columnNamesOfSelectedIndex2.contains(actualColumnName)) {
                    isContainsColumn = true;
                }
                if (isContainsColumn) {
                    if (FileChooser.isAccepted()) {
                        // Extract the selected file indexes
                        int timeStateIndex1 = openedFiles.indexOf(selectedFiles.get(0));
                        int timeStateIndex2 = openedFiles.indexOf(selectedFiles.get(1));

                        // Create a map to map the selected string to the enum value
                        Map<String, NoiseEmissionSortAndDifferences.
                                DifferenceBy> differenceByMap = new HashMap<>();
                        differenceByMap.put("LAeq nappal különbsége",
                                NoiseEmissionSortAndDifferences.DifferenceBy.
                                DAY_DECIBEL_DIFFERENCE);
                        differenceByMap.put("LAeq éjjel különbsége",
                                NoiseEmissionSortAndDifferences.DifferenceBy.
                                NIGHT_DECIBEL_DIFFERENCE);
                        differenceByMap.put("Védõtávolság nappal különbsége",
                                NoiseEmissionSortAndDifferences.DifferenceBy.
                                PROTECTIVE_DISTANCE_DAY_DIFFERENCE);
                        differenceByMap.put("Védõtávolság éjjel különbsége",
                                NoiseEmissionSortAndDifferences.DifferenceBy.
                                PROTECTIVE_DISTANCE_NIGHT_DIFFERENCE);
                        differenceByMap.put("Hatásterület nappal különbsége",
                                NoiseEmissionSortAndDifferences.DifferenceBy.
                                    IMPACT_AREA_DISTANCE_DAY_DIFFERENCE);
                        differenceByMap.put("Hatásterület éjjel különbsége",
                                NoiseEmissionSortAndDifferences.DifferenceBy.
                                    IMPACT_AREA_DISTANCE_NIGHT_DIFFERENCE);

                        // Retrieve the enum value from the map based on the selected string
                        NoiseEmissionSortAndDifferences.DifferenceBy differenceBy = 
                                differenceByMap.get(selectedDifferenceCase);

                        // Perform the difference calculation using the indexes
                        List<Double> differenceData = modifierController.
                                invokeNoiseEmissionDifferenceBy(differenceBy,
                                        timeStateIndex1, timeStateIndex2);

                        // Get the name for the new column
                        String fileName0 = actualColumnName;
                        String fileName1 = selectedFiles.get(0).getName();
                        String fileName2 = selectedFiles.get(1).getName();
                        // Trim file extensions
                        fileName1 = fileName1.substring(0, fileName1.lastIndexOf('.'));
                        fileName2 = fileName2.substring(0, fileName2.lastIndexOf('.'));
                        String columnName = "<html>" + fileName0 + " különbsége"
                                + "<br>" + fileName1 + " és " + fileName2 + 
                                "<br>" + "között</html>";

                        modifierController.addCalculatedColumns(dbfController.
                                getFilteredDbfData(selectedIndex), columnName, 
                                differenceData);

                        // Refresh the display of the modified data in the GUI
                        JScrollPane scrollPane = 
                                projectPanel.updateTableData(controllerManager,
                                        selectedIndex);
                        gui.updateFileTab(selectedIndex, scrollPane);
                        isContainsColumn = false;

                        // Set the viewport to the rightmost columns
                        JViewport viewport = scrollPane.getViewport();
                        JTable table = (JTable) viewport.getView();
                        Rectangle cellRect = table.getCellRect(0, 
                                table.getColumnCount() - 1, true);
                        table.scrollRectToVisible(cellRect);

                        OptionPane.showInfoDialog(fileName0 + 
                                " különbségének számítása " + fileName1 + 
                                " és " + fileName2 +  " között sikeresen"
                                        + " elvégezve!");
                    }
                } else {
                    OptionPane.showErrorDialog("Nincsenek összehasonlítható "
                            + "eredmények. Végezz el számítást elõször!");
                }
            }
        } 
    }

    /**
    * Returns the selected column name based on the given column name for differences.
    *
    * @param columnName The column name for differences.
    * @return The selected column name.
    */
    private String selectedColumnForDifference(String columnName) {
        String selectedColumn = null;
        switch (columnName) {
            case "LAeq nappal különbsége":
                selectedColumn = "LAeq Nappal"; break;
            case "LAeq éjjel különbsége":
                selectedColumn = "LAeq Ejjel"; break;
            case "Védõtávolság nappal különbsége":
                selectedColumn = "Vedotavolsag Nappal"; break;
             case "Védõtávolság éjjel különbsége":
                selectedColumn = "Vedotavolsag Ejjel"; break;
            case "Hatásterület nappal különbsége":
                selectedColumn = "Hatasterulet Nappal"; break;
            case "Hatásterület éjjel különbsége":
                selectedColumn = "Hatasterulet Ejjel"; break;
        }
        return selectedColumn;
    }
}
