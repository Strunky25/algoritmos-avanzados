/*
    Algoritmes Avançats - Capitulo 1
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package capitulo1.controller;

import capitulo1.model.Model;
import capitulo1.model.Model.Task;
import capitulo1.view.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author elsho
 */
public class Controller {
    
    private final Model model;
    private final View view;
    
    private Task task;
    
    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }
    
    public void start(){
        this.view.addAnimateListener(new AnimateListener());
        this.view.addStopListener(new StopListener());
        view.setVisible(true);
    }
    
    public class AnimateListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setRunning(true);
            String complexity = view.getChosenComplexity();
            task = model.new Task(complexity);
            task.addPropertyChangeListener(new ProgressListener());
            task.execute();
            // view.setRunning(false); cuando acabe el worker
        } 
    }
    
    public class StopListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            task.cancel(true);
            view.setRunning(false);
            view.setProgress(0);
        } 
    } 
    
    public class ProgressListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("progress")) {
                int progress = (Integer) evt.getNewValue();
                view.setProgress(progress);
            } else if(evt.getPropertyName().equals("time")){
                int size = (int) evt.getOldValue();
                long time = (long) evt.getNewValue();
                view.animate(size, time);
            } else if(evt.getPropertyName().equals("state")){
                String state = String.valueOf(evt.getNewValue());
                if(state.equals("DONE")){
                    view.setRunning(false);
                }
            }
        }
        
    }
   
}
