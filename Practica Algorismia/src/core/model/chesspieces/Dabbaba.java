/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package core.model.chesspieces;

/*
 * Child class of Chesspieces that represents the invented piece Dabbaba.
 */
public class Dabbaba extends Chesspiece{

    /**
     * Constructor that uses the parent one to
     * create a new Instance.
     * @param color of the new ChessPiece
     */
    public Dabbaba(Color color) {
        super(color);
    }
    
    /**
     * @return The String representation of this class
     */
    @Override
    public String getType() {
        return "Dabbaba";
    }

    @Override
    public boolean checkKill(int row, int col, Object[][] board) {
        if((row - 2) >= 0 && board[row - 2][col] != null)
            return true;
        if((row + 2) < board.length && board[row + 2][col] != null)
            return true; 
        if((col - 2) >= 0 && board[row][col - 2] != null)
            return true;
        if((col + 2) < board.length && board[row][col + 2] != null)
            return true; 
        return false;
    }
}
