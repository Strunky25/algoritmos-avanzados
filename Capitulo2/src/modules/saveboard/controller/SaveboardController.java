/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package modules.saveboard.controller;

import core.view.ProblemViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modules.saveboard.model.SaveboardProblem;
import modules.saveboard.view.SaveboardViewer;

/**
 * Class that manages interaction between model and View
 * and acts as ActionListener.
 */
public class SaveboardController implements ActionListener{

    private SaveboardProblem model;
    private final SaveboardViewer view;  
    
    public SaveboardController(SaveboardProblem model, SaveboardViewer view){
        this.model = model;
        this.view = view;
    }
    
    /**
     * Switch of the different actions it can perform.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "compute" -> computeActionPerformed();
        }
    }
    
   /**
     * this method is performed when the compute button is clicked.
     * Solves the queens problem using the model class and displays the
     * solution on view.
     */
    public void computeActionPerformed(){
        if(view.getSelectedPieces() != null){
            model = new SaveboardProblem(view.getN());
            model.setPieces(view.getSelectedPieces());
            if(model.solve()){
                view.reset();
                view.paintBoard(model.getBoard());
                view.printMessage("Solution Found!\n\nTry with a different set of pieces and compute.", ProblemViewer.MESSAGE);
            } else {
                view.printMessage("Sorry, no solution found.\n\nTry with other pieces.", ProblemViewer.ERROR);
            }
        } else {
            view.printMessage("Add pieces and then compute", ProblemViewer.ERROR);
        }
    }
}
