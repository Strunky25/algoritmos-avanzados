/*
    Algoritmes Avançats - Capitulo X
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package controller;

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
        view.setVisible(true);
    }
    
    /**
     * Define Listeners...
     */
   
}
