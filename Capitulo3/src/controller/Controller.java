/*
    Algoritmes Avançats - Capitulo 3
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package controller;

import java.awt.event.ActionEvent;
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
        view.addButtonsListener((e) -> buttonActionPerformed(e));
        view.setVisible(true);
    }

    /**
     * Define Listeners...
     */
    private void buttonActionPerformed(ActionEvent e) {
        String first = view.getFirstNumber();
        String second = view.getSecondNumber();
        if (first != null && second != null) {
            switch (e.getActionCommand()) {
                case "Traditional":
                    view.setResult(model.multiply(first, second));
                    break;
                case "Karatsuba":
                    view.setResult(model.karatsuba(first, second));
                    break;
                case "Mixed":
                    break;
            }
            view.setResult(model.multiply(first, second));
        } else {
            System.out.println("Error");
        }
    }
}
