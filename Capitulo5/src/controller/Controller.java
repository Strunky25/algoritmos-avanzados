/*
    Algoritmes Avançats - Capitulo 5
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
 */
package controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
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
    private Thread thread;

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
        model.addPropertyChangeListener((e) -> modelPropertyChange(e));
        view.addListeners((e) -> viewActionPerformed(e));
        view.setVisible(true);
    }

    /**
     * Define Listeners...
     */
    private void viewActionPerformed(ActionEvent event) {
        if ("Check".equals(event.getActionCommand())) {
            if(thread != null && thread.isAlive()){
                System.out.println("already running");
                return;
            }
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        String[] words = view.getText();
        HashMap<String, ArrayList<String>> results = model.correct(words);
        view.showResults(results);
    }

    private void modelPropertyChange(PropertyChangeEvent evt) {
        switch(evt.getPropertyName()){
            case "progress" ->  {
                int progress = (int) (double) evt.getNewValue();
                //System.out.println(progress);
                view.setProgress(progress);
            }
        }
    }
}
