/*
    Algoritmes Avançats - Capitulo 2
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package controller;

import model.Model;
import model.chesspieces.Chesspiece;
import view.View;
import view.View.Animator;

/**
 * Class that manages interaction between the Model and View classes, including
 * user input.
 */
public class Controller implements Runnable{
    
    /* MVC Pattern */
    private final Model model;
    private final View view;
    
    /* Variables */
    private Thread thread;
    private Animator animator;
    
    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }
    
    /**
     * Method that configures  Listeners for the view, and sets it to visible
     * waiting for user input.
     */
    public void start(){
        this.view.addComputeListener((e) -> computeActionPerformed());
        this.view.addAnimateListener((e) -> animateActionPerformed());
        this.view.addStopListener((e) -> stopActionPerformed());
        this.view.printMessagge(Message.DEFAULT, Message.Type.INFO);
        this.view.setVisible(true);
    }
    
    public void computeActionPerformed() { // mencionar en docu
        model.start();
        thread = new Thread(this);
        thread.start();
    }
    
    public void animateActionPerformed(){
        this.animator = view.new Animator(model.getBoard());
        this.animator.execute();
    }
    
    public void stopActionPerformed() { // mencionar en docu
        model.stop();
    }

    @Override
    public void run() {
        Integer[] coordinates = view.getPiecePosition();
        Chesspiece selectedPiece = view.getSelectedPiece();
        if(coordinates != null && selectedPiece != null){
            view.setRunning(true);
            view.printMessagge(Message.COMPUTING, Message.Type.INFO);
            model.setBoardSize(view.getBoardSize());
            if(model.solve(selectedPiece, coordinates[0], coordinates[1])){
                view.paintBoardNumbers(model.getBoard());
                view.printMessagge(Message.solutionWithTime(model.getTime()), Message.Type.SUCCESS);
            } else if(model.isStopped()){
                this.view.printMessagge(Message.STOPPED, Message.Type.ERROR);
            } else {
                this.view.printMessagge(Message.NO_SOLUTION_FOUND, Message.Type.ERROR);
            }
            view.setRunning(false);
            view.setProgress(100);
        } else {
            this.view.printMessagge(Message.DEFAULT, Message.Type.ERROR);
        }
    }
}
