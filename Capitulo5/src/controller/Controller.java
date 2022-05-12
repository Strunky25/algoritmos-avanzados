/*
    Algoritmes Avançats - Capitulo 5
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package controller;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;
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
        //view.setVisible(true);
        Scanner sc = new Scanner(System.in);
        System.out.println("Insert some text:");
        String txt = sc.nextLine();
        String[] words = txt.split("[\\p{Punct}\\s]+");
        System.out.println(Arrays.toString(words));
        model.correct(words);
        System.exit(0);
    }
    
    /**
     * Define Listeners...
     */
   
}
