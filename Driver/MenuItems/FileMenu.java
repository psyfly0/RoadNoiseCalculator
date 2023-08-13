/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver.MenuItems;

import Driver.MainGUI.GUI;
import Calculators.Parameters.NoiseEmissionParameters;
import Driver.HelperClasses.ColumnSelectionDialog;
import Driver.ControllerManager.ControllerManager;
import Driver.DialogWindows.FileChooser;
import Driver.OptionPane.OptionPane;
import Driver.MainPanel.ProjectPanel;
import FileReadingAndDataStore.Controllers.DbfController;
import FileReadingAndDataStore.Controllers.ShapefileController;
import Modifiers.Controller.ModifierController;
import Saving.Controller.ShapeNoiseOutputController;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author szaboa
 */
/**
 * The FileMenu class represents a menu component for file-related operations in
 * a graphical user interface (GUI).
 * It provides functionality for opening, saving, and closing projects, as well
 * as exiting the application.
 * 
 * <p>The FileMenu class contains methods to handle user interactions with the
 * menu items, such as opening a file, saving a file, closing the current project,
 * closing all projects, and exiting the application. It also maintains a set of
 * opened file names and a file counter to manage the state of the projects.</p>
 */
public class FileMenu extends javax.swing.JMenu {
    private JMenu menu;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem closeCurrentMenuItem;
    private JMenuItem closeAllMenuItem;
    private JMenuItem exitMenuItem;
    private Map<String, String> previousSelection;
    private int fileCounter = 0;
    private Set<String> openedFileNames = new HashSet<>();
    private ControllerManager controllerManager;

    /**
    * Constructs a new FileMenu object.
    * Uses listeners to interact with the menu items
    *
    * @param controllerManager The ControllerManager instance.
    * @param projectPanel The ProjectPanel instance.
    * @param gui The GUI instance.
    */
    public FileMenu(
            ControllerManager controllerManager, 
            ProjectPanel projectPanel, GUI gui) {
        
        this.controllerManager = controllerManager;
        
        openMenuItem = new JMenuItem("Megnyitás");
        saveMenuItem = new JMenuItem("Mentés");
        closeCurrentMenuItem = new JMenuItem("Aktuális projekt bezárása");
        closeAllMenuItem = new JMenuItem("Minden projekt bezárása");
        exitMenuItem = new JMenuItem("Kilépés");
        
        openMenuItem.addActionListener(e -> openFile(projectPanel, gui));
        saveMenuItem.addActionListener(e -> saveFile(gui));
        closeCurrentMenuItem.addActionListener(e -> closeCurrentProject(projectPanel, gui));
        closeAllMenuItem.addActionListener(e -> closeAllProjects(projectPanel, gui));
        exitMenuItem.addActionListener(e -> exitApplication());       

        
        // Add menu items to the menu
        menu = new JMenu("Fájl");
        menu.add(openMenuItem);
        menu.add(Box.createVerticalStrut(1));
        menu.add(saveMenuItem);
        menu.add(Box.createVerticalStrut(1));
        menu.addSeparator();
        menu.add(Box.createVerticalStrut(2));
        menu.add(closeCurrentMenuItem);
        menu.add(Box.createVerticalStrut(1));
        menu.add(closeAllMenuItem);
        menu.add(Box.createVerticalStrut(1));
        menu.addSeparator();
        menu.add(Box.createVerticalStrut(2));
        menu.add(exitMenuItem); 
    }
        
    /**
    * Retrieves the JMenu instance.
    *
    * @return The JMenu instance.
    */
    public JMenu getMenu() {
        return menu;
    }
    
    /**
    * Opens a file.
    *
    * @param projectPanel The ProjectPanel instance.
    * @param gui The GUI instance.
    */
    private void openFile(
            ProjectPanel projectPanel,
            GUI gui) {
        
        // Access the controllers from the ControllerManager
        ShapefileController shapefileController = 
                controllerManager.getShapefileController();
        DbfController dbfController = controllerManager.getDbfController();
        ModifierController modifierController = 
                controllerManager.getModifierController();

        // Create a set to store the opened file names
        List<File> openedFiles = gui.getOpenedFiles();

        for (File file : openedFiles) {
            openedFileNames.add(file.getName());
        }
        File shpFile = FileChooser.selectFile(openedFileNames);
        if (shpFile != null) {
            String fileNameCheck = shpFile.getName();
            String fileExtension = fileNameCheck.substring(fileNameCheck.lastIndexOf(".") + 1);
            fileExtension = fileExtension.toLowerCase();
            
            if (fileExtension.equals("shp")) {
            
            try {
                // load all the necessary files with the same name but different extensions
                String filePath = shpFile.getAbsolutePath().substring(0, 
                        shpFile.getAbsolutePath().lastIndexOf("."));

                // Check if the required .dbf file exists
                File dbfFile = new File(filePath + ".dbf");
                if (!dbfFile.exists()) {
                    OptionPane.showErrorDialog("Nem található a \"" + 
                            dbfFile.getName() + "\" fájl.");
                    return;
                }                
                File shxFile = new File(filePath + ".shx");
                if (!shxFile.exists()) {
                    OptionPane.showErrorDialog("Nem található a \"" + 
                            shxFile.getName() + "\" fájl.");
                    return;
                }

                // Check if the optional files exist
                File cpgFile = new File(filePath + ".cpg");
                File prjFile = new File(filePath + ".prj");
                File qmdFile = new File(filePath + ".qmd");
                File idxFile = new File(filePath + ".idx");

                shapefileController.onShapefileSelected(shpFile);
                dbfController.onDbfSelected(dbfFile);
                int totalRows = dbfController.getDbfRowCount();

                // Call the Column Selection Dialog   
                ColumnSelectionDialog dialog = new ColumnSelectionDialog((Frame) 
                        SwingUtilities.getWindowAncestor(menu), shpFile, 
                        controllerManager, previousSelection);

                // Check if the dialog was accepted
                if (dialog.isAccepted()) {
                    // Store the previous selection
                    previousSelection = dialog.getPreviousSelection();


                    // Get the current parameters for the selected row
                    Map<NoiseEmissionParameters, Double> currentParameters =
                            new HashMap<>();
                    for (NoiseEmissionParameters parameter : 
                            NoiseEmissionParameters.getModifiableParameters()) {
                        double value = modifierController
                                .getParameterValue(fileCounter, 0, parameter);
                        currentParameters.put(parameter, value);
                    }


                    // Call the parameter selection dialog
                    Map<NoiseEmissionParameters, Double> modifiedParameters = 
                            FileChooser.selectParametersForFile(currentParameters);
                    // Set the parameters for all rows
                    modifierController.setParameterValueForAllRows(fileCounter,
                            modifiedParameters, totalRows);   

                    JScrollPane scrollPane = projectPanel.
                            displayDbfData(controllerManager, fileCounter);

                    fileCounter++;

                    String fileName = shpFile.getName();
                    gui.addFileTab(shpFile, scrollPane);
                    gui.updateFileTab(gui.getTabbedPane().
                            getTabCount() - 1, scrollPane);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (shpFile != null) {
            OptionPane.showErrorDialog("Nem megfelelő fájlformátum. "
                    + "Válassz .shp kiterjesztést!");
        }
        }
    }
    
    /**
    * Saves a file.
    *
    * @param gui The GUI instance.
    */
    private void saveFile(GUI gui) {
        // Access the controllers from the ControllerManager
        ShapeNoiseOutputController shapeNoiseOutputController = 
                controllerManager.getShapeNoiseOutputController();
        
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();
        if (selectedIndex >= 0) {
            File outputShapeFile = FileChooser.selectSaveFile();
            if (outputShapeFile != null) {
                if (outputShapeFile.exists()) {
                    // File already exists, show a confirmation dialog
                    boolean confirmed = OptionPane.
                            showConfirmDialog("A fájl már létezik! Felülírod?");
                    if (confirmed) {
                        try {
                            shapeNoiseOutputController.
                                    saveShapefile(outputShapeFile, selectedIndex);
                            OptionPane.showInfoDialog("Fájl sikeresen mentve: " 
                                    + outputShapeFile.getPath());
                        } catch (IOException ex) {
                            OptionPane.showErrorDialog("Sikertelen mentés!");
                            ex.printStackTrace();
                        }
                    } else {
                        OptionPane.showInfoDialog("Mentés megszakítva!");
                    }
                } else {
                    try {
                        shapeNoiseOutputController.
                                saveShapefile(outputShapeFile, selectedIndex);
                        OptionPane.showInfoDialog("Fájl sikeresen mentve: " + 
                                outputShapeFile.getPath());
                    } catch (IOException ex) {
                        OptionPane.showErrorDialog("Sikertelen mentés!");
                        ex.printStackTrace();
                    }
                }
            } else {
                OptionPane.showErrorDialog("Sikertelen mentés!");
            }
        }
    }

    /**
    * Closes the current project.
    *
    * @param projectPanel The ProjectPanel instance.
    * @param gui The GUI instance.
    */
    private void closeCurrentProject(
            ProjectPanel projectPanel,
            GUI gui) {
        
         // Access the controllers from the ControllerManager
        DbfController dbfController = controllerManager.getDbfController();
        ModifierController modifierController = controllerManager.
                getModifierController();       
        
        int selectedIndex = gui.getTabbedPane().getSelectedIndex();
        if (selectedIndex >= 0) {
            // Get the file being closed
            File fileToClose = gui.getOpenedFiles().get(selectedIndex);

            // Remove the file from the lists
            gui.getOpenedFiles().remove(selectedIndex);
            openedFileNames.remove(fileToClose.getName());

            gui.getTabbedPane().removeTabAt(selectedIndex);
            dbfController.deleteDbfData(selectedIndex);
            modifierController.removeResult(selectedIndex);
            modifierController.removeFileParameters(selectedIndex);
            projectPanel.removeTableModel(selectedIndex);
            fileCounter--;
        }
    }

    /**
    * Closes all projects.
    *
    * @param projectPanel The ProjectPanel instance.
    * @param gui The GUI instance.
    */
    private void closeAllProjects(
            ProjectPanel projectPanel,
            GUI gui) {
        // Access the controllers from the ControllerManager
        DbfController dbfController = controllerManager.getDbfController();
        ModifierController modifierController = controllerManager.
                getModifierController();
        
        int tabCount = gui.getTabbedPane().getTabCount();
        for (int i = tabCount - 1; i >= 0; i--) {
            // Get the file being closed
            File fileToClose = gui.getOpenedFiles().get(i);

            // Remove the file from the list
            gui.getOpenedFiles().remove(i);
            gui.getTabbedPane().removeTabAt(i);               
            gui.getTabbedPane().removeTabAt(i);
        }
        dbfController.deleteAllDbfData();
        modifierController.removeAllResult();
        modifierController.removeAllParameters();
        projectPanel.removeAllTableModels();
        // Clear the HashSet of opened file names
        openedFileNames.clear();

        fileCounter = -1;
    }

    /**
    * Exits the application.
    */
    private void exitApplication() {
        boolean confirmExit = OptionPane.
                showConfirmDialog("Biztos ki akarsz lépni?");
        if (confirmExit) {
            System.exit(0);
        }
    }
    
    
    
}   
