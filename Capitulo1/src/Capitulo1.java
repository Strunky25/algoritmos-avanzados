/*
    Algoritmes Avançats - Capitulo 1
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package capitulo1;

import capitulo1.controller.Controller;
import capitulo1.view.View;
import model.Model;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;

/**
 * Main Class that starts the program
 */
public class Capitulo1 implements Runnable{

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
        Capitulo1 prog = new Capitulo1();
        SwingUtilities.invokeLater(prog); 
    }
}
