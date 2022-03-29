/*
    Algoritmes Avançats - Capitulo 2
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package controller;

import javax.swing.SwingWorker;
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
    private Animator animator;
    private boolean computed, animating;
    
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
        if(!animating){
            model.start();
            thread = new Thread(this);
            thread.start();
        } else {
            this.view.printMessagge(Message.ANIMATING, Message.Type.ERROR);
        }
    }
    
    public void animateActionPerformed(){
        if(computed){
            this.view.printMessagge(Message.ANIMATING, Message.Type.INFO);
            this.animator = new Animator(model.getBoard());
            this.animator.execute();
        } else {
            this.view.printMessagge(Message.COMPUTE_FIRST, Message.Type.ERROR);
        }
    }
    
    public void stopActionPerformed() { // mencionar en docu
        model.stop();
        if(animating) {
            view.printMessagge(Message.ANIMATION_STOPPED, Message.Type.ERROR);
            animator.cancel(true);
            animating = false;
        }
    }

    @Override
    public void run() {
        Integer[] coordinates = view.getPiecePosition();
        Chesspiece selectedPiece = view.getSelectedPiece();
        if(coordinates != null && selectedPiece != null){
            view.printMessagge(Message.COMPUTING, Message.Type.INFO);
            model.setBoardSize(view.getBoardSize());
            model.solve(selectedPiece, coordinates[1], coordinates[0]);
            showResults(model.hasSolution());
        } else {
            this.view.printMessagge(Message.DEFAULT, Message.Type.ERROR);
        }
    }
    
    private void showResults(boolean solved){
        if(solved){
            view.paintBoardNumbers(model.getBoard());
            view.printMessagge(Message.solutionWithTime(model.getTime()), Message.Type.SUCCESS);
            computed = true;
        } else if(model.isStopped()){
            this.view.printMessagge(Message.STOPPED, Message.Type.ERROR);
        } else {
            this.view.printMessagge(Message.NO_SOLUTION_FOUND, Message.Type.ERROR);
        }
        view.setProgress(100);
    }
    
        public class Animator extends SwingWorker<Void, Void> {
        
        private final int[][] board;
        
        public Animator(int[][] board){
            this.board = board;
        }

        @Override
        protected Void doInBackground() throws Exception {
            animating = true;
            int num = 0, max = board.length * board.length;
            Integer[] thisPoint = view.getPiecePosition();
            Integer[] nextPoint = findNumber(++num);
            while(nextPoint != null && !isCancelled()){
                view.paintLine(thisPoint[0], thisPoint[1], nextPoint[0], nextPoint[1]);
                thisPoint = nextPoint;
                nextPoint = findNumber(++num);
                view.setProgress(num*100/max);
                Thread.sleep(200);
            }
            animating = false;
            return null;
        }
        
        private Integer[] findNumber(int num){
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if(board[i][j] == num) return new Integer[]{j, i};
                }
            }
            return null;
        }
    }
}
