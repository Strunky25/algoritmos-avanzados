/*
    Algoritmes Avançats - Capitulo 3
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package controller;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import model.Model;
import view.CompareFrame;
import view.View;

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
        /* Add Listeners */
        view.addButtonsListener((e) -> viewActionPerformed(e));
        view.setVisible(true);
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
                if(Model.tested)
                    res = model.karatsuba(num1,num2, true);
                else {
                    //model.calculateN();
                    res = model.karatsuba(num1,num2, true);
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
    
    private void compareActionPerformed(ActionEvent event){
        System.out.println(event.getActionCommand());
        switch(event.getActionCommand()){
            case "Start" -> {
                compareThrd = new Thread(() -> {
                    int size = compare.getTestSize();
                    for (int i = 1; i < size; i++) {
                        try {
                            long[] times = model.calculateN(i);
                            compare.animate(i, times);
                            Thread.sleep(10);
                            compare.setProgress(i*100/size);
                        } catch (InterruptedException ignore) { return; }
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
}
