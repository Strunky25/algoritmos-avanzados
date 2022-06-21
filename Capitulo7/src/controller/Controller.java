/*
    Algoritmes Avançats - Capitulo 7
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import model.Model;
import view.View;

/**
 * Class that manages interaction between the Model and View classes, including
 * user input.
 */
public class Controller {
    
    /* Constants */
    private static final File DIR = new File("resources/flags");
    private static final int N_TESTS = 30000;
    
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
        model.addPropertyChangeListener((e) -> modelPropertyChanged(e));
        view.addListeners((e) -> viewActionPerformed(e));
        view.setVisible(true);
        
//        model.loadDatabase(DIR, N_TESTS);
//        File[] files = DIR.listFiles();
//        Random rand = new Random();
//        File flagFile = files[rand.nextInt(files.length)];
//
//        model.loadFlag(flagFile);
//        double[] perc = model.getColorPercentages(N_TESTS);
//        String guess = model.findCountry(perc);
//        
//        Locale loc = new Locale("", flagFile.getName().replace(".png", ""));
//        String country = loc.getDisplayCountry();
//        System.out.println("Real Country: " + country);
//        System.out.println("Guess: " + guess);
    }
    
    /**
     * Define Listeners...
     */
    private void modelPropertyChanged(PropertyChangeEvent evt){
        switch(evt.getPropertyName()){
            case "creating DB" -> {
                boolean val = (boolean) evt.getNewValue();
                if(val == true){
                    // pop up creating database
                }
            }
        }
    }
    
    private void viewActionPerformed(ActionEvent evt) {
        switch(evt.getActionCommand()){
            case "Random Flag" -> {
                File Flag = model.getRandomFlag(DIR);
                model.loadFlag(Flag);
                view.setFlagImage(model.getFlagImage(), model.getCountryName());
            }
            case "Guess Country" -> {
                model.loadDatabase(DIR, N_TESTS);
                double[] percentages = model.getColorPercentages(N_TESTS);
                String country = model.findCountry(percentages);
                view.setGuessImage(model.getFlagImage(country), country, percentages);
            }
        }
    }
}
