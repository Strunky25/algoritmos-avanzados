/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package core.model;

/**
 * Asbtract class that represents a chess problem
 * @param <T> Object type that will represent the chesspieces on the board.
 */
public abstract class Problem <T> {
    
    /**
     *  Generic array that represents a chessboard.
     */
    protected T[][] board;
    
    /**
     * Method that calculates the solution to the problem.
     * @return true if there is a solution, false if no solution was found.
     */
    public abstract boolean solve();
    
    /**
     * Method to obtain the board with the solution.
     * @return board.
     */
    public T[][] getBoard(){
        return board;
    }
}
