/*
    Algoritmes Avançats - Capitulo 6
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
        Julián Wallis Medina
 */
package controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collections;

import model.Model;
import model.PerformanceTests;
import model.Model.Node;
import view.View;

/**
 * Class that manages interaction between the Model and View classes, including
 * user input.
 */
public class Controller implements Runnable {

    /* Constants */
    private static final int SLEEP_TIME = 600;

    /* MVC Pattern */
    private final Model model;
    private final View view;

    /* Variables */
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
    private void modelPropertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "Calculating" -> {
                boolean value = (boolean) evt.getNewValue();
                view.setIndeterminate(value);
            }
            case "Update" -> {
                int[][] mat = (int[][]) evt.getNewValue();
                view.showResults(mat);
            }
        }
    }

    private void viewActionPerformed(ActionEvent evt) {
        switch (evt.getActionCommand()) {
            case "Solve" -> {
                if (thread != null && thread.isAlive()) return;
                thread = new Thread(this);
                thread.start();
            }
        }
    }

    @Override
    public void run() {
        Model.setN(view.getN());
        int[][] order = view.getOrder();
        int[] pos = view.getPos();
        model.solve(order, pos[1], pos[0], view.getHeuristic());
        ArrayList<Node> moves = model.getSolution();
        animateSolution(moves);

        // PerformanceTests tests = new PerformanceTests(model, view);
        // tests.start();

    }

    private void animateSolution(ArrayList<Node> moves) {
        Collections.reverse(moves);
        int cnt = 0;
        for (Node move : moves) {
            view.showResults(move.getMatrix());
            view.setProgress((int) ((int) cnt / (double) moves.size() * 100));
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            cnt++;
        }
        view.setProgress(100);
    }
}
