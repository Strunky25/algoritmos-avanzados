/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package modules.saveboard.model;

import core.model.Problem;
import core.model.chesspieces.Chesspiece;
import transfer.Config;
import transfer.SaveBoardInterface;

/**
 * Class that implements the solution to the Saveboard Problem.
 */
public class SaveboardProblem extends Problem<Chesspiece> implements SaveBoardInterface{

    private Chesspiece[] pieces;

    public SaveboardProblem(int size) {
        this.board = new Chesspiece[size][size];
    }

    /**
     * Method that calculates the solution to the given problem.
     * @return true if it found a solution, false if no solution was found.
     */
    @Override
    public boolean solve() {
        return recursiveSolve(pieces.length);
    }
    
    /*
     * Recursive method to compute the solution with backtracking
     */
    private boolean recursiveSolve(int piecesRemaining) {
        if(piecesRemaining == 0){
            return true;
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] == null){
                    board[i][j] = pieces[pieces.length - piecesRemaining]; //poner pieza
                    if (!pieces[pieces.length - piecesRemaining].checkKill(i, j, board) && !checkOtherKills()) {
                        if(recursiveSolve(piecesRemaining - 1)){
                            return true;
                        }
                    }
                    board[i][j] = null; //poner pieza
                }
            }
        }
        return false;
    }

    /**
     * Method that sets the pieces to be used in the problem solution.
     * @param selectedPieces Array of chesspieces.
     */
    public void setPieces(Chesspiece[] selectedPieces) {
        this.pieces = selectedPieces;
    }

    /**
     * Method that checks if any piece can kill another.
     * @return 
     */
    private boolean checkOtherKills() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].checkKill(i, j, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method from the interface for tests purposes.
     * @param figuresCodes figures array to be used in the problem.
     * @param boardSize size of the chessboard.
     * @return solution board with all pieces set accordingly.
     */  
    @Override
    public int[][] solve(int[] figuresCodes, int boardSize) {
        this.board = new Chesspiece[boardSize][boardSize];
        this.pieces = new Chesspiece[figuresCodes.length];
        for (int i = 0; i < figuresCodes.length; i++) {            
            pieces[i] = Config.getPiece(figuresCodes[i]);
        }
        if (!solve()){
            return null;
        } 
        return Config.getBoardCodes(board);
    }
}
