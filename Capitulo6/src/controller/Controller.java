/*
    Algoritmes Avançats - Capitulo 6
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package controller;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import model.Model;
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
        view.addListeners((e) -> viewActionPerformed(e));
        view.setVisible(true);
    }
    
    /**
     * Define Listeners...
     */
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
        //if(model.isSolvable(order)){
            System.out.println(Arrays.toString(pos));
            //model.solve(INITIAL, GOAL, 1, 0);
            model.solve(order, GOAL, pos[0], pos[1]);
            int[][] sol = model.getSolution();
            view.showResults(sol);
//        } else {
//            System.out.println("The given initial is impossible to solve");
//        }
    }
}
