/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package bootstrap;

import com.formdev.flatlaf.FlatLightLaf;
import core.view.Menu;

/**
 * Main class that boots the whole project
 */
public class Bootstrap {
    
    /**
     * Main method, creates a Menu object and sets it to visible.
     */
    public static void main(String args[]) {
        FlatLightLaf.install();
        java.awt.EventQueue.invokeLater(() -> {
            new Menu().setVisible(true);
        });
    }
}
