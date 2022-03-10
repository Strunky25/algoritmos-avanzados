package transfer;

/**
 * Interface which should implement a save board problem solution.
 * The implementation of this interface is mandatory to integrate it in 
 * the professor software.
 * 
 * <b>It must provide an empty constructor </b>
 * 
 * @author Bernat Galmés Rubert
 */
public interface SaveBoardInterface {
    /**
     * Configure the problem which you want to solve. 
     * Specify the pieces you want to place on the board and the board size.
     * 
     * @param figuresCodes 
     *      Array with the figures codes you want to place on the board.
     *      <b>Those codes should fit with the constants defined in Config class.</b>
     * 
     * @param boardSize 
     *      Integer with the number of columns or rows of the board.
     *      Note that a board always have the same number of columns or rows.
     */
//    public void configure(int[] figuresCodes, int boardSize);
    
    /**
     * Compute the save board problem solution with the configuration given by the method params.
     * 
     * @param figuresCodes
     *      Array with the figures codes you want to place on the board.
     *      <b>Those codes should fit with the constants defined in Config class.</b>
     * 
     * @param boardSize 
     *      Integer with the number of columns or rows of the board.
     *      Note that a board always have the same number of columns or rows.
     
     * @return 
     *      A bidimensional array with the solution of the problem.
     *      Its dimensions should be boardSize x boardSize.
     *      Each cell represents a board cell, where empty cells should be filled 
     *      by 0s and cells with a figure with its respective code (defined in Config class).
     *      If the implementation is not able to find a solution, the method should return a null value.
     */
    public int[][] solve(int[] figuresCodes, int boardSize);
    
}
