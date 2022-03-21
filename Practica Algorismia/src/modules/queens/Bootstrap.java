/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package modules.queens;

import modules.queens.controller.QueenController;
import modules.queens.model.QueenProblem;
import modules.queens.view.QueenViewer;

/**
 * Class to boot the N Queens Problem
 */
public class Bootstrap implements Runnable{

    private final QueenProblem model;
    private final QueenViewer view;
    private final QueenController controller;
    
    public Bootstrap(){
        model = new QueenProblem(QueenViewer.INIT_SIZE);
        view = new QueenViewer();
        controller = new QueenController(model, view);
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
