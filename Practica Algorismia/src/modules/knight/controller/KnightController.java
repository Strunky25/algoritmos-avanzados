/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package modules.knight.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modules.knight.model.KnightProblem;
import modules.knight.view.KnightViewer;
import modules.queens.view.QueenViewer;

/**
 * Class that manages interaction between model and View
 * and acts as ActionListener.
 */
public class KnightController implements ActionListener{

    private KnightProblem model;
    private final KnightViewer view;

    public KnightController(KnightProblem model, KnightViewer view) {
        this.model = model;
        this.view = view;
        this.view.attachActionListener((e) -> computeActionPerformed());
        this.view.attachAnimateActionListener((e) -> animateActionPerformed());
    }
    
    /**
     * Switch of the different actions it can perform.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "compute" -> computeActionPerformed();
            case "animate" -> animateActionPerformed();
        }
    }
    
    /**
     * this method is performed when the compute button is clicked.
     * Solves the knight problem using the model class and displays the
     * solution on view.
     */
    public void computeActionPerformed() {
        if(view.getKnightRow()!= -1){
            model = new KnightProblem(view.getN());
            model.setInitialPosition(view.getKnightRow(), view.getKnightColumn());
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
    /**
     * this method is performed when the action button is clicked.
     */
    public void animateActionPerformed(){
        view.animateBoard(model.getBoard());
    }
}
