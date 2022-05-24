/*
    Algoritmes Avançats - Capitulo 5
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
 */
package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
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
        view.addListeners((e) -> actionPerformed(e));
        view.setVisible(true);
    }

    /**
     * Define Listeners...
     */
    private void actionPerformed(ActionEvent event) {
        if ("Check".equals(event.getActionCommand())) {
            Thread thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        String[] words = view.getText();
        HashMap<String, ArrayList<String>> results = model.correct(words);
        view.showResults(results);
    }
}
