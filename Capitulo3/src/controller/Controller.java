/*
    Algoritmes Avançats - Capitulo 3
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package controller;

import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import model.Model;
import view.CompareFrame;
import view.View;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * Class that manages interaction between the Model and View classes, including
 * user input.
 */
public class Controller implements Runnable {

    /* MVC Pattern */
    private final Model model;
    private final View view;

    private String method, num1, num2, res;
    private CompareFrame compare;
    private Thread compareThrd;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Method that configures Listeners for the view, and sets it to visible
     * waiting for user input.
     */
    public void start() {
        findMixedIntersection();
        /* Add Listeners */
        view.addButtonsListener((e) -> viewActionPerformed(e));
        //view.setVisible(true); //if algo??
    }

    /**
     * Define Listeners...
     */
    private void viewActionPerformed(ActionEvent e) {
        num1 = view.getFirstNumber();
        num2 = view.getSecondNumber();
        method = e.getActionCommand();
        if (!num1.equals("") && !num1.equals("") || method.equals("Comparison")) {
            Thread t = new Thread(this);
            t.start();
        } else {
            view.setResult("Please introduce two numbers");
        }
    }

    @Override
    public void run() {
        switch (method) {
            case "Traditional" -> res = model.multiply(num1, num2);
            case "Karatsuba" -> res = model.karatsuba(num1, num2, false);
            case "Mixed" -> {
                if (Model.tested)
                    res = model.karatsuba(num1, num2, true);
                else {
                    // model.calculateN();
                    res = model.karatsuba(num1, num2, true);
                }
            }
            case "Comparison" -> {
                SwingUtilities.invokeLater(() -> {
                    compare = new CompareFrame();
                    compare.addActionListener((e) -> compareActionPerformed(e));
                    compare.getInputSize();
                    compare.setVisible(true);
                });
                return;
            }
        }
        view.setResult(method + " solution found in " + model.getTime() + "s :\n" + res);
    }

    private void compareActionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "Start" -> {
                compareThrd = new Thread(() -> {
                    int size = compare.getTestSize();
                    long max = model.calculateN(size)[0];
                    for (int i = 1; i < size + 1; i++) {
                        try {
                            long[] times = model.calculateN(i);
                            compare.animate(i, times, max);
                            Thread.sleep(10);
                            compare.setProgress(i * 100 / size);
                        } catch (InterruptedException ignore) { return;}
                    }
                    compare.drawlastPointTexts();
                });
                compareThrd.start();
            }
            case "Stop" -> {
                compareThrd.interrupt();
            }
        }
    }

    private void findMixedIntersection() {
        compare = new CompareFrame();
        compare.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        compare.setTitle("Finding best intersection for mixed algorithm");
        compare.setVisible(true);
        Thread back = new Thread(() -> {
            long[] classic = new long[Model.N_TESTS];
            long[] karatsuba = new long[Model.N_TESTS];
            ArrayList<long[]> results = new ArrayList<>();
            for (int i = 1; i < Model.N_TESTS + 1; i++) {
                long[] times = model.calculateN(i);
                results.add(times);
                classic[i - 1] = times[0];
                karatsuba[i - 1] = times[1];
                if(i%4 == 0) compare.setProgress(i * 100 / Model.N_TESTS);
            }
            for(int i = 0; i < results.size(); i++){
                compare.animate(i, results.get(i), results.get(results.size()-1)[0]);
                try { Thread.sleep(10); } catch (InterruptedException ignore) {return;};
                if(i%4 == 0) compare.setProgress(i * 100 / Model.N_TESTS);
            }
    //        try {
    //            BufferedWriter bwc = new BufferedWriter(new FileWriter("classic.txt"));
    //            BufferedWriter bwk = new BufferedWriter(new FileWriter("karatsuba.txt"));
    //            for (int i = 0; i < Model.N_TESTS; i++) {
    //                bwc.write(String.valueOf(classic[i]) + ",");
    //                bwk.write(String.valueOf(karatsuba[i]) + ",");
    //            }
    //            bwc.close();
    //            bwk.close();
    //
    //        } catch (IOException e) {System.out.println(e.getMessage());}
            int N = 0;
            int counter = 0;
            for(int i = Model.N_TESTS - 1; i >= 0; i--) {
                if(classic[i] < karatsuba[i]) {
                    counter++;
                }
                if (counter==10){
                    N = i-10;
                    break;
                }
            }
            model.setNMix(N);
            compare.showIntersectionResult(N);
            compare.dispose();
            view.setVisible(true);
        });
        compare.addActionListener((e) -> {
            if("Stop".equals(e.getActionCommand())){
                back.interrupt();
                compare.dispose();
                view.setVisible(true);
            }
        });
        back.start();
    }
}
