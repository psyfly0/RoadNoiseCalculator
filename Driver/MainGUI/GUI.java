/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver.MainGUI;

import Calculators.Repository.CalculatorRepository;
import Calculators.Repository.CalculatorRepositoryImpl;
import Driver.HelperClasses.ApplyUserModifications;
import Driver.ControllerManager.ControllerManager;
import Driver.MenuItems.FileMenu;
import Driver.MenuItems.OperationsMenu;
import Driver.MainPanel.ProjectPanel;
import Driver.MenuItems.SortDifferencesMenu;
import FileReadingAndDataStore.Controllers.DbfController;
import FileReadingAndDataStore.Repositories.DbfRepository;
import FileReadingAndDataStore.Repositories.DbfRepositoryImpl;
import FileReadingAndDataStore.Services.DbfService;
import FileReadingAndDataStore.Repositories.ShapeFileRepository;
import FileReadingAndDataStore.Repositories.ShapeFileRepositoryImpl;
import FileReadingAndDataStore.Controllers.ShapefileController;
import FileReadingAndDataStore.Services.ShapefileService;
import Modifiers.Controller.ModifierController;
import Modifiers.Repository.ModifierRepository;
import Modifiers.Repository.ModifierRepositoryImpl;
import Modifiers.Service.ModifierService;
import Saving.Controller.ShapeNoiseOutputController;
import Saving.Repository.ShapeNoiseOutputRepository;
import Saving.Repository.ShapeNoiseOutputRepositoryImpl;
import Saving.Service.ShapeNoiseOutputService;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

/**
 *
 * @author szaboa
 */
/**
 * The GUI class represents the graphical user interface for the Közúti Zajszámító Program.
 * It provides a visual interface for managing files, performing operations, and displaying tabbed content.
 */
public class GUI extends javax.swing.JFrame {
    private JPanel mainPanel;
    private List<File> openedFiles = new ArrayList<>();
    private JMenuBar menuBar;
    private JTabbedPane tabbedPane;
    private int currentTabIndex = -1;
    private ApplyUserModifications applyUserModifications;

    /**
     * Creates a new instance of the GUI class.
     * Initializes the repositories, services, controllers, and menus required for the program.
     * Sets up the menu bar, main panel, and tabbed pane.
     */
    public GUI() {
        // Create repositories, services, controllers
        ShapeFileRepository shapeFileRepository = new ShapeFileRepositoryImpl();
        DbfRepository dbfRepository = new DbfRepositoryImpl();

        ShapefileService shapefileService = 
                new ShapefileService(shapeFileRepository);
        DbfService dbfService = new DbfService(dbfRepository);
        ShapefileController shapefileController = 
                new ShapefileController(shapefileService);
        DbfController dbfController = new DbfController(dbfService);
        
        CalculatorRepository calculatorRepository = 
                new CalculatorRepositoryImpl(dbfRepository);
        ModifierRepository modifierRepository = 
                new ModifierRepositoryImpl(shapeFileRepository, dbfRepository, calculatorRepository);
        ModifierService modifierService = 
                new ModifierService(modifierRepository);
        ModifierController modifierController = 
                new ModifierController(modifierService);
        
       // ShapeFileWriter shapeFileWriter = new ShapeFileWriter(dbfRepository, shapeFileRepository);
        ShapeNoiseOutputRepository shapeNoiseOutputRepository = 
                new ShapeNoiseOutputRepositoryImpl(dbfRepository, shapeFileRepository);
        ShapeNoiseOutputService shapeNoiseOutputService =
                new ShapeNoiseOutputService(shapeNoiseOutputRepository);
        ShapeNoiseOutputController shapeNoiseOutputController = 
                new ShapeNoiseOutputController(shapeNoiseOutputService);
        
        // Create FileMenu, OperationsMenu
        ControllerManager controllerManager = 
                new ControllerManager(shapefileController, dbfController,
                        modifierController, shapeNoiseOutputController);
        ProjectPanel projectPanel = new ProjectPanel(controllerManager, this);
        FileMenu fileMenu = new FileMenu(controllerManager, projectPanel, this);
        OperationsMenu operationsMenu = 
                new OperationsMenu(controllerManager, projectPanel, this);
        SortDifferencesMenu sortDifferencesMenu = 
                new SortDifferencesMenu(controllerManager, projectPanel, this);
        
        applyUserModifications = 
                new ApplyUserModifications(modifierController, dbfController,
                        projectPanel, this);


        // Add them to the menuBar
        menuBar = new JMenuBar();
        // Set the preferred size of the menu bar
        Dimension menuBarSize = new Dimension(menuBar.getPreferredSize().width, 30);
        menuBar.setPreferredSize(menuBarSize);
        menuBar.add(fileMenu.getMenu());

        menuBar.add(Box.createHorizontalStrut(20));

        menuBar.add(operationsMenu.getMenu());

        menuBar.add(Box.createHorizontalStrut(20)); 

        menuBar.add(sortDifferencesMenu.getMenu());

        menuBar.add(Box.createHorizontalStrut(20));

        // set the menu bar
        setJMenuBar(menuBar);
        
        initComponents();
        setTitle("Közúti Zajszámító Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainPanel = new JPanel(new BorderLayout());
        // Create the tabbed pane and add it to the main panel
        tabbedPane = new JTabbedPane(); 
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
              
        // Listen for tab selection events
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex != -1) {
                currentTabIndex = selectedIndex;
                // Start the cell change listener
                applyUserModifications.startCellChangeListener(selectedIndex);
            }
        });
        
        pack();
        setVisible(true);      
    }
    
    /**
     * Returns the tabbed pane component.
     *
     * @return the JTabbedPane component used in the GUI
     */
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
    
    /**
     * Adds a new file tab with the given file and content component.
     *
     * @param file         the file associated with the tab
     * @param fileContent  the component to be displayed in the tab
     */
    public void addFileTab(File file, Component fileContent) {
        String fileName = file.getName();
        tabbedPane.addTab(fileName, fileContent);
        int newIndex = tabbedPane.getTabCount() - 1; // Index of the newly added tab
        tabbedPane.setSelectedIndex(newIndex); // Set the selected index to the newly added tab

        // Add the corresponding file to the openedFiles list
        openedFiles.add(file);      
    }

    /**
     * Updates the file tab at the specified index with the given scroll pane component.
     *
     * @param index       the index of the file tab to update
     * @param scrollPane  the new scroll pane component for the tab
     */
    public void updateFileTab(int index, JScrollPane scrollPane) {
        tabbedPane.setComponentAt(index, scrollPane);
    }
    
    /**
     * Returns the list of opened files.
     *
     * @return the list of files that are currently opened in the GUI
     */
    public List<File> getOpenedFiles() {
        return openedFiles;
    }
    
    /**
     * Retrieves the column names for the tab at the specified index.
     *
     * @param index  the index of the tab
     * @return a list of column names for the specified tab, or an empty list if the index is invalid
     */
    public List<String> getColumnNamesForTabIndex(int index) {
    List<String> columnNames = new ArrayList<>();

    if (index >= 0 && index < tabbedPane.getTabCount()) {
        Component fileContent = tabbedPane.getComponentAt(index);
        if (fileContent instanceof JScrollPane) {
            JTable table = (JTable) ((JScrollPane) fileContent).
                    getViewport().getView();
            for (int i = 0; i < table.getColumnCount(); i++) {
                columnNames.add(table.getColumnName(i));
            }
        }
    }
    return columnNames;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 559, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * The main method that launches the GUI application.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GUI gui = new GUI();
                gui.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
