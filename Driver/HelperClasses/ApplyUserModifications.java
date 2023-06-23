/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver.HelperClasses;

import Driver.MainPanel.ProjectPanel;
import Driver.MainGUI.GUI;
import FileReadingAndDataStore.Controllers.DbfController;
import Modifiers.Controller.ModifierController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;


/**
 *
 * @author szaboa
 */
/**
 * A class for applying user modifications (modifying cell values) to a project.
 */
public class ApplyUserModifications {
    private ModifierController modifierController;
    private DbfController dbfController;
    private ProjectPanel projectPanel;
    private GUI gui;
    private List<Integer> activeTabs = new ArrayList<>();
    private Map<Integer, List<Object>> oldValuesMap = new HashMap<>();
    
    /**
     * Constructs an instance of ApplyUserModifications.
     *
     * @param modifierController the modifier controller for applying modifications
     * @param dbfController      the DBF controller for accessing DBF data
     * @param projectPanel       the project panel containing the table
     * @param gui                the GUI instance
     */
    public ApplyUserModifications(
            ModifierController modifierController,
            DbfController dbfController,
            ProjectPanel projectPanel,
            GUI gui) {
        
        this.modifierController = modifierController;
        this.dbfController = dbfController;
        this.projectPanel = projectPanel;
        this.gui = gui;       
    }

    /**
     * Starts the cell change listener for the selected index.
     *
     * @param selectedIndex the index of the selected table
     */
    public void startCellChangeListener(int selectedIndex) {
        JTable table = projectPanel.getTable(selectedIndex);

        if (table != null) {
            List<Object> oldValues = oldValuesMap.get(selectedIndex);
            if (oldValues == null) {
                oldValues = new ArrayList<>();
                oldValuesMap.put(selectedIndex, oldValues);
            }

            table.getDefaultEditor(Object.class).
                    addCellEditorListener(new CellEditorListener() {
                @Override
                public void editingStopped(ChangeEvent e) {
                    int row = table.getSelectedRow();
                    int column = table.getSelectedColumn();
                    String columnName = table.getColumnName(column);

                    Object oldValue = dbfController.
                            getFilteredDbfData(selectedIndex).
                            getValue(columnName, row);
                    Object tmpNewValue = table.getValueAt(row, column);
                    Object newValue = (!tmpNewValue.equals("") ? tmpNewValue : 0);
                    if (row != -1 && column != -1 && !newValue.equals(oldValue)) {

                        // Update the original dbf file with the new value
                        modifierController.updateCellValue(dbfController.
                                getFilteredDbfData(selectedIndex), column, row, newValue);

                        // Refresh the table
                        JScrollPane scrollPane = projectPanel.
                                refreshTableCellData(row, column, newValue, selectedIndex);
                        gui.updateFileTab(selectedIndex, scrollPane);
                    }
                }

                @Override
                public void editingCanceled(ChangeEvent e) {
                    // Do nothing
                }
            });
        }
    }
      
}
