/*
    Algoritmes Avançats - Capitulo 2
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package controller;

import java.util.Arrays;
import model.Model;
import model.chesspieces.Chesspiece;
import view.View;

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
    
    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }
    
    /**
     * Method that configures  Listeners for the view, and sets it to visible
     * waiting for user input.
     */
    public void start(){
        /* Add Listeners */
        //this.view.addComputeListener(new ComputeListener());
        this.view.addComputeListener((e) -> ComputeActionPerformed());
        this.view.addStopListener((e) -> stopActionPerformed());
        view.setVisible(true);
    }
    
    /**
     * Define Listeners...
     */
    public void ComputeActionPerformed() { // mencionar en docu
        view.setRunning(true);
        thread = new Thread(this);
        thread.start();
    }
    
    public void stopActionPerformed() { // mencionar en docu
        model.stop();
    }

    @Override
    public void run() {
        Integer[] coordinates = view.getPiecePosition();
        Chesspiece selectedPiece = view.getSelectedPiece();
        if(coordinates != null || selectedPiece != null){
            //System.out.println(selectedPiece.getType() + " at " + Arrays.toString(coordinates));
            model.setBoardSize(view.getBoardSize());
            if(model.solve(selectedPiece, coordinates[0], coordinates[1])){
                view.paintBoardNumbers(model.getBoard());
                view.printMessagge("Solution Found :)");
            } else {
                view.printMessagge("No Solution Found :(");
            }   
        }
        view.setRunning(false);
        view.setProgress(100);
    }
}
