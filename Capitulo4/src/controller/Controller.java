/*
    Algoritmes Avançats - Capitulo 4
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package controller;

import java.awt.event.ActionEvent;
import java.io.File;

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
        view.addListeners((e) -> actionPerformed(e));
        view.setVisible(true);
    }

    /**
     * Define Listeners...
     */
    private void actionPerformed(ActionEvent evt) {
        File selectedFile = view.getSelectedFile();
        Thread thread = new Thread(() -> {
            switch (evt.getActionCommand()) {
                case "Compress" -> model.compress(selectedFile);
                case "Decompress" -> model.decompress(selectedFile);
            }
        });
        thread.start();
    }
}
