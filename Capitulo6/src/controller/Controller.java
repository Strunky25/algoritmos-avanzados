/*
    Algoritmes Avançats - Capitulo 6
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Arrays;

import model.Model;
import model.Model.Node;
import view.View;

/**
 * Class that manages interaction between the Model and View classes, including
 * user input.
 */
public class Controller implements Runnable {
    /* Constants */
    private static final int[][] GOAL = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    private static final int[][] INITIAL = {{1, 8, 2}, {0, 4, 3}, {7, 6, 5}};
    
    /* MVC Pattern */
    private final Model model;
    private final View view;
    
    /* Variables */
    private Thread thread;
    
    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }
    
    /**
     * Method that configures  Listeners for the view, and sets it to visible
     * waiting for user input.
     */
    public void start(){
        /* Add Listeners */
        model.addPropertyChangeListener((e) -> modelPropertyChange(e));
        view.addListeners((e) -> viewActionPerformed(e));
        view.setVisible(true);
    }
    
    /**
     * Define Listeners...
     */
    private void modelPropertyChange(PropertyChangeEvent evt) {
        if("Update".equals(evt.getPropertyName())){
            int[][] mat = (int[][]) evt.getNewValue();
            view.showResults(mat);
        }
    }
    
    private void viewActionPerformed(ActionEvent evt){
        if("Solve".equals(evt.getActionCommand())){
            if(thread != null && thread.isAlive()) return;
            thread = new Thread(this);
            thread.start();
        }
    }
    

    @Override
    public void run() {
        int[][] order = view.getOrder();
        int[] pos = view.getPos();
        model.solve(order, GOAL, pos[1], pos[0]);
        Node sol = model.getSolution();
        ArrayList<Node> animacion = new ArrayList<>();
        while(sol!=null){
            animacion.add(sol);
            sol = sol.getParent();
        }
        for(int i = animacion.size()-1; i>=0; i--){
            view.showResults(animacion.get(i).getMatrix());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
