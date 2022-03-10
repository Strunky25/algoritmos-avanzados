/*
 * Child class of Chesspieces that represents the King.
 */
package core.model.chesspieces;

/**
 * @author Jonathan Salisbury
 * @author Joan Vilella
 */
public class King extends Chesspiece{

    /**
     * Constructor that uses the parent one to
     * create a new Instance.
     * @param color of the new ChessPiece
     */
    public King(Color color) {
        super(color);
    }
    
    /**
     * @return The String representation of this class
     */
    @Override
    public String getType() {
        return "King";
    }

    @Override
    public boolean checkKill(int row, int col, Object[][] board) {
        if(row - 1 >= 0 && board[row - 1][col] != null) // UP
            return true;
        if(row + 1 < board.length && board[row + 1][col] != null) // DOWN 
            return true;
        if(col - 1 >= 0 && board[row][col - 1] != null) // RIGHT
            return true;
        if(col + 1 < board.length && board[row][col + 1] != null) // LEFT
            return true;
        if((row + 1) < board.length && (col + 1) < board.length && board[row + 1][col + 1] != null) // RIGHT LOWER DIAGONAL
            return true;
        if((row + 1) < board.length && (col - 1) >= 0 && board[row + 1][col - 1] != null) // LEFT LOWER DIAGONAL
            return true;
        if((row - 1) >= 0 && (col - 1) >= 0 && board[row - 1][col - 1] != null) // LEFT UPPER DIAGONAL
            return true;
        if((row - 1) >= 0 && (col + 1) < board.length && board[row - 1][col + 1] != null) // RIGHT UPPER DIAGONAL
            return true;
        return false;
    } 
}
