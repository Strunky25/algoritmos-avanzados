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
import model.Model;
import model.Word;
import view.View;

/**
 * Class that manages interaction between the Model and View classes, including
 * user input.
 */
public class Controller {
    
    /* MVC Pattern */
    private final Model model;
    private final View view;
    
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
        view.addListeners((e) -> actionPerformed(e));
        view.setVisible(true);
    }
    
    /**
     * Define Listeners...
     */
    private void actionPerformed(ActionEvent event){
        switch(event.getActionCommand()){
            case "Check" -> {
                String[] words = view.getText();
                ArrayList<Word> results = model.correct(words);
                view.showResults(results);
            }
        }
    }
}
