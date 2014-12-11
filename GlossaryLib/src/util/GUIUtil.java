/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.swing.JOptionPane;

/**
 * Utility for the GUI.
 * @author fazo
 */
public class GUIUtil {

    /**
     * Ask confirmation to user.
     *
     * @param msg the question to ask
     * @return true if the user said Yes
     */
    public static boolean ask(String msg) {
        return JOptionPane.showConfirmDialog(null, msg) == JOptionPane.OK_OPTION;
    }
    
    public static void tell(String msg){
        JOptionPane.showMessageDialog(null, msg);
    }
    
    public static void tellError(String msg){
        JOptionPane.showMessageDialog(null, msg,"Error",JOptionPane.ERROR_MESSAGE);
    }
}
