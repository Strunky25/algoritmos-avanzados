/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package modules.knight.model;

import core.model.Problem;
import core.model.chesspieces.Knight;

/**
 * Class that implements the solution to the knight problem.
 */
public class KnightProblem extends Problem <Integer> { 
    
    private int knightRow, knightColumn;

    public KnightProblem(int size) {
        board = new Integer[size][size];
    }

    /**
     * this method is used to place the knight on the board before calculating a solution.
     * @param knightRow row position of the knight
     * @param knightColumn column position of the knight
     */
    public void setInitialPosition(int knightRow, int knightColumn) {
        this.knightRow = knightRow;
        this.knightColumn = knightColumn;
        board[knightRow][knightColumn] = 0;
    }

    /**
     * Method that calculates the solution to the given problem.
     * @return true if it found a solution, false if no solution was found.
     */
    @Override
    public boolean solve() {
        return recursiveSolve(knightRow, knightColumn, 1);
    }

    /*
     * Recursive method to compute the solution with backtracking
     */
    private boolean recursiveSolve(int x, int y, int movei) {
        int next_x, next_y;
        if (movei == board.length * board.length) {
            return true;
        }
        for (int k = 0; k < Knight.rowMove.length; k++) {
            next_x = x + Knight.rowMove[k];
            next_y = y + Knight.columnMove[k];
            if (Knight.possibleMove(next_x, next_y, board)){
                board[next_x][next_y] = movei;
                if (recursiveSolve(next_x, next_y, movei + 1)) {
                    return true;
                } else {
                    board[next_x][next_y] = null; // backtracking
                }
            }
        }
        return false;
    }
}
