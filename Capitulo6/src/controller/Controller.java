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
import java.util.Arrays;

import model.Heuristica;
import model.Model;
import model.Model.Node;
import view.View;

/**
 * Class that manages interaction between the Model and View classes, including
 * user input.
 */
public class Controller implements Runnable {
    /* Constants */
    private Heuristica heuristica;
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
        if ("Update".equals(evt.getPropertyName())) {
            int[][] mat = (int[][]) evt.getNewValue();
            view.showResults(mat);
        }
    }

    private void viewActionPerformed(ActionEvent evt) {
        switch (evt.getActionCommand()) {
            case "Solve":
                if (thread != null && thread.isAlive())
                    return;
                thread = new Thread(this);
                thread.start();
            case "ChangeHeuristic":
                this.heuristica = view.getHeuristica();
            case "ChangeSize":
                // setN(view.getSelectedSize());
        }
    }

    @Override
    public void run() {
        Model.setN(view.getN());
        int[][] order = view.getOrder();
        int[] pos = view.getPos();
        model.solve(order, pos[1], pos[0], heuristica);
        ArrayList<Node> moves = model.getSolution();
        for (int i = moves.size() - 1; i >= 0; i--) {
            view.showResults(moves.get(i).getMatrix());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
