 /*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package modules.knight;

import modules.knight.controller.KnightController;
import modules.knight.model.KnightProblem;
import modules.knight.view.KnightViewer;

/**
 * Class to boot the Knight Problem
 */
public class Bootstrap implements Runnable{

    private final KnightProblem model;
    private final KnightViewer view;
    private final KnightController controller;

    public Bootstrap() {
        model = new KnightProblem(KnightViewer.INIT_SIZE);
        view = new KnightViewer();
        controller = new KnightController(model, view);
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
