/*
    Algoritmes Avançats - Capitulo X
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Class that contains the Swing GUI for the project.
 */
public class View extends JFrame{
    
    /* Constants */
    
    /* Variables */
    
    /* Swing Components */

    
    public View(){
        initComponents();
    }
    
    /**
     * Method that initializes swing components, and sets JFrame properties.
     */
    private void initComponents(){
        setTitle("Capitulo 1");
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        /* Init Components */
        
        addComponents();
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    /**
     * Method that creates the frame layout with all its components.
     */
    private void addComponents(){
        /* Add Layout */
        pack();
    }

    /* METHODS */
    
    // Add Listeners
}
