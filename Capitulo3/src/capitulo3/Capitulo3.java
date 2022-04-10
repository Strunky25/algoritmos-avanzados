/*
    Algoritmes Avançats - Capitulo 3
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package capitulo3;

import controller.Controller;
import view.View;
import model.Model;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;

/**
 * Main Class that starts the program
 */
public class Capitulo3 implements Runnable{

    /* MVC Pattern */
    private Model model;
    private Controller controller;
    private View view;
   
    @Override
    public void run() {
        model = new Model();
        view = new View();
        controller = new Controller(model, view);
        controller.start();
    }
   
    /**
     * Main method that configures the Look and Feel, and creates and runs
     * an instance of the program.
     * @param args not used
     */
    public static void main(String[] args) {
        FlatLightLaf.setup();
        Capitulo3 prog = new Capitulo3();
        SwingUtilities.invokeLater(prog); 
    }
}
