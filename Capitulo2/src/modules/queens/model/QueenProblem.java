/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package modules.queens.model;

import core.model.Problem;
import core.model.chesspieces.Chesspiece;
import core.model.chesspieces.Queen;

/**
 * Class that implements the solution to the N Queens problem.
 */
public class QueenProblem extends Problem <Boolean> {

    private int queenColumn;
    private final Queen queen;

    public QueenProblem(int size) {
        this.board = new Boolean[size][size];
        this.queen = new Queen(Chesspiece.Color.white);
    }
    
    /**
     * this method is used to place the queen on the board before calculating a solution.
     * @param queenRow row position of the queen.
     * @param queenColumn column position of the queen.
     */
    public void setInitialPosition(int queenRow, int queenColumn){
        this.queenColumn = queenColumn;
        this.board[queenRow][queenColumn] = true;
    }

    /**
     * Method that calculates the solution to the given problem.
     * @return true if it found a solution, false if no solution was found.
     */
    @Override
    public boolean solve() {
        return recursiveSolve(0);
    }

    /*
     * Recursive method to compute the solution with backtracking
     */
    private boolean recursiveSolve(int column) {
        if (column == board.length) {
            return true;
        }
        for (int i = 0; i < board.length; i++) {
            if(column == queenColumn && column < board.length){
                if (recursiveSolve(column + 1)) {
                    return true;
                }
            } else if (!queen.checkKill(i, column, board)) {
                board[i][column] = true;
                if (recursiveSolve(column + 1)) {
                    return true;
                }
                board[i][column] = null;
            }
        }
        return false;
    }
}
