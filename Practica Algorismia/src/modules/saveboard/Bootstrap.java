/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package modules.saveboard;

import core.view.ProblemViewer;
import modules.saveboard.controller.SaveboardController;
import modules.saveboard.model.SaveboardProblem;
import modules.saveboard.view.SaveboardViewer;

/**
 * Class to boot the Saveboard Problem
 */
public class Bootstrap implements Runnable{

    private final SaveboardProblem model;
    private final SaveboardViewer view;
    private final SaveboardController controller;

    public Bootstrap() {
        model = new SaveboardProblem(ProblemViewer.INIT_SIZE);
        view = new SaveboardViewer();
        controller = new SaveboardController(model, view);
    }
    
    /**
     * Method used to start the problem.
     */
    @Override
    public void run() {
        view.attachActionListener(controller);
        view.setVisible(true);
    }  
}