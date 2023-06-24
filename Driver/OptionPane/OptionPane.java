/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver.OptionPane;

import javax.swing.JOptionPane;

/**
 *
 * @author szaboa
 */
public class OptionPane { 
/**
 * Displays an error message dialog.
 *
 * @param message the message to display
 */
    public static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", 
                JOptionPane.ERROR_MESSAGE);
    }
    
    /**
    * Displays a confirmation dialog with "Yes" and "No" options.
    *
    * @param message the message to display
    * @return true if "Yes" was selected, false if "No" was selected or the dialog was closed
    */
    public static boolean showConfirmDialog(String message) {
        int option = JOptionPane.showConfirmDialog(null, message, "Confirm", 
                JOptionPane.YES_NO_OPTION);
        return option == JOptionPane.YES_OPTION;
    }
    
    /**
    * Displays an input dialog with a text field.
    *
    * @param message the message to display
    * @return the text entered by the user, or null if the dialog was closed or cancelled
    */
    public static String showInputDialog(String message) {
        return JOptionPane.showInputDialog(null, message);
    }
    
    /**
    * Displays an informative dialog with an OK button.
    *
    * @param message the message to display
    */
    public static void showInfoDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", 
                JOptionPane.INFORMATION_MESSAGE);
    }
}
