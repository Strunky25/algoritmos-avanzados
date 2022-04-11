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
public class Controller implements Runnable {

    /* MVC Pattern */
    private final Model model;
    private final View view;

    private String method, num1, num2, res;

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
        num1 = view.getFirstNumber();
        num2 = view.getSecondNumber();
        if (num1 != null && num1 != null) {
            method = e.getActionCommand();
            Thread t = new Thread(this);
            t.start();
        } else {
            System.out.println("Error");
        }
    }

    @Override
    public void run() {
        switch (method) {
            case "Traditional":
                res = model.multiply(num1, num2);
                break;
            case "Karatsuba":
                res = model.karatsuba(num1, num2);
                break;
            case "Mixed":
                break;
        }
        view.setResult(res);
    }
}
