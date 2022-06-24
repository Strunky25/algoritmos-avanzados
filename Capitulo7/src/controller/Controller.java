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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.sound.sampled.SourceDataLine;

import model.Model;
import view.View;

/**
 * Class that manages interaction between the Model and View classes, including
 * user input.
 */
public class Controller {

    /* Constants */
    private static final File DIR = new File("resources/flags");
    private static int N_PIXELS = 30000;

    /* MVC Pattern */
    private final Model model;
    private final View view;

    private boolean TESTING_MODE = false;

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
        model.addPropertyChangeListener((e) -> modelPropertyChanged(e));
        view.addListeners((e) -> viewActionPerformed(e));
        view.setVisible(true);

        // model.loadDatabase(DIR, N_TESTS);
        // File[] files = DIR.listFiles();
        // Random rand = new Random();
        // File flagFile = files[rand.nextInt(files.length)];
        //
        // model.loadFlag(flagFile);
        // double[] perc = model.getColorPercentages(N_TESTS);
        // String guess = model.findCountry(perc);
        //
        // Locale loc = new Locale("", flagFile.getName().replace(".png", ""));
        // String country = loc.getDisplayCountry();
        // System.out.println("Real Country: " + country);
        // System.out.println("Guess: " + guess);
    }

    /**
     * Define Listeners...
     */
    private void modelPropertyChanged(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "creating DB" -> {
                boolean val = (boolean) evt.getNewValue();
                if (val == true) {
                    // pop up creating database
                }
            }
        }
    }

    private void viewActionPerformed(ActionEvent evt) {
        switch (evt.getActionCommand()) {
            case "Random Flag" -> {
                File Flag = model.getRandomFlag(DIR);
                model.loadFlag(Flag);
                view.setFlagImage(model.getFlagImage(), model.getCountryName());
            }
            case "Guess Country" -> {
                if (!TESTING_MODE) {
                    model.loadDatabase(DIR, N_PIXELS);
                    double[] percentages = model.getColorPercentages(N_PIXELS);
                    String country = model.findCountry(percentages);
                    view.setGuessImage(model.getFlagImage(country), country, percentages);
                } else {
                    // TESTS
                    long time = System.currentTimeMillis();
                    int N_TESTS = 1000;
                    int[] Ns = new int[] { 50, 100, 150, 200, 250, 500, 750, 1000, 2000, 3000, 5000, 8000, 10000, 15000,
                            20000, 25000, 30000, 35000, 40000, 45000, 50000 };

                    try (FileWriter fw = new FileWriter("results.csv")) {
                        BufferedWriter bw = new BufferedWriter(fw);

                        for (int N : Ns) {
                            N_PIXELS = N;
                            System.out.println("N: " + N);
                            double score = 0;
                            int errors = 0;
                            for (int i = 0; i < N_TESTS; i++) {
                                File Flag = model.getRandomFlag(DIR);
                                model.loadFlag(Flag);
                                view.setFlagImage(model.getFlagImage(), model.getCountryName());

                                model.loadDatabase(DIR, N_PIXELS);
                                double[] percentages = model.getColorPercentages(N_PIXELS);
                                String country = model.findCountry(percentages);
                                try {
                                    score += view.setGuessImage(model.getFlagImage(country), country, percentages);
                                } catch (NullPointerException e) {
                                    errors++;
                                }
                            }
                            score = score / (double) (N_TESTS - errors);
                            System.out.println(N + "," + score + "\n");
                            bw.write(N + "," + score + "\n");
                        }
                        bw.flush();
                        bw.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println("Time (s): " + (System.currentTimeMillis() - time) / 1000);
                }
            }
        }
    }
}
