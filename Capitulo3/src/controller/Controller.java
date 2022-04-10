/*
    Algoritmes Avançats - Capitulo 3
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package controller;

import java.math.BigInteger;
import model.Model;
import view.View;

/**
 * Class that manages interaction between the Model and View classes, including
 * user input.
 */
public class Controller {

    /* MVC Pattern */
    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Method that configures Listeners for the view, and sets it to visible
     * waiting for user input.
     */
    public void start() {
        /* Add Listeners */
        view.addTraditionalListener((e) -> traditionalActionPerformed());
        view.setVisible(true);
    }

    /**
     * Define Listeners...
     */
    private void traditionalActionPerformed() {
        String first = view.getFirstNumber();
        String second = view.getSecondNumber();
        if (first != null && second != null) {
            view.setResult(model.multiply(first, second));
        } else {
            System.out.println("Error");
        }
    }

}
