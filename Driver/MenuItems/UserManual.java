/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver.MenuItems;

import Driver.DialogWindows.FileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author szaboa
 */

public class UserManual {
    private JMenu menu;

    public UserManual() {
        // Create the menu for User Manual
        menu = new JMenu("Help");

        // Create the menu item for User Manual
        JMenuItem menuItem = new JMenuItem("Használati útmutató");
        menuItem.addActionListener(e -> openUserManual());

        // Add the menu item to the menu
        menu.add(menuItem);
    }

    public JMenu getMenu() {
        return menu;
    }

    private void openUserManual() {
        FileChooser.createUserManualDialog();
    }
}