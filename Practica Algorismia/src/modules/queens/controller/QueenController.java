/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package modules.queens.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modules.queens.model.QueenProblem;
import modules.queens.view.QueenViewer;

/**
 * Class that manages interaction between model and View
 * and acts as ActionListener.
 */
public class QueenController implements ActionListener{
    
    private QueenProblem model;
    private final QueenViewer view;  
    
    public QueenController(QueenProblem model, QueenViewer view){
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
        if(view.getQueenRow() != -1){
            model = new QueenProblem(view.getN());
            model.setInitialPosition(view.getQueenRow(), view.getQueenColumn());
            if(model.solve()){
                view.reset();
                view.paintBoard(model.getBoard());
                view.printMessage("Solution Found!\n\nClick another square and Compute to see a diferent solution.", QueenViewer.MESSAGE);
            } else {
                view.printMessage("Sorry, no solution found.\n\nClick another square and Compute to see a diferent solution.", QueenViewer.ERROR);
            }
        } else {
            view.printMessage(QueenViewer.DESCRIPTION_TEXT, QueenViewer.ERROR);
        }
    }
}
