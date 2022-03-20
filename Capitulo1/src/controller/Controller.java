/*
    Algoritmes Avançats - Capitulo 1
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package controller;

import model.Model;
import model.Model.Task;
import view.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Class that manages interaction between the Model and View classes, including
 * user input.
 */
public class Controller {
    
    /* MVC Pattern */
    private final Model model;
    private final View view;
    
    /* Variables */
    private Task task;
    private String complexity;
    
    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }
    
    /**
     * Method that configures  Listeners for the view, and sets it to visible
     * waiting for user input.
     */
    public void start(){
        this.view.addAnimateListener(new AnimateListener());
        this.view.addStopListener(new StopListener());
        this.view.addClearListener(new ClearListener());
        view.setVisible(true);
    }
    
    /**
     * Nested Class to manage user interaction with the Animate Button.
     */
    public class AnimateListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setRunning(true);
            complexity = view.getChosenComplexity();
            
            /* Create SwingWorker to simulate complexity */
            task = model.new Task(complexity);
            task.addPropertyChangeListener(new ProgressListener());
            task.execute();
        } 
    }
    
    /**
     * Nested Class to manage user interaction with the Stop Button.
     */
    public class StopListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            task.cancel(true); // Sends Cancel signal to SwingWorker
            view.setRunning(false);
            view.setProgress(0);
        } 
    } 
    
    /**
     * Nested Class to manage user interaction with the Clear Button.
     */
    public class ClearListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearAnimationPanel();
            view.setProgress(0);
        } 
    }
    
    /**
     * Nested Class to manage progress from the SwingWorker.
     */
    public class ProgressListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            switch (evt.getPropertyName()) {
                /**
                 * If Progress is updated, update the View.
                 */
                case "progress" -> {
                    int progress = (Integer) evt.getNewValue();
                    view.setProgress(progress);
                }
                /**
                 * If a new Coordinate is sent, update View.
                 */
                case "point" -> {
                    if(!task.isCancelled()){
                        double x = (double) evt.getOldValue();
                        double y = (double) evt.getNewValue();
                        view.animate(x, y);
                    }
                }
                /**
                 * If the worker has ended
                 */
                case "state" -> {
                    String state = String.valueOf(evt.getNewValue());
                    if(state.equals("DONE")){
                        view.drawlastPointText(complexity);
                        view.setRunning(false);
                    }
                }
            }
        }
    }
   
}
