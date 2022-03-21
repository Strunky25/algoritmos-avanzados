/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package core.model.chesspieces;

/*
 * Child class of Chesspieces that represents the invented piece Ferz.
 */
public class Ferz extends Chesspiece{
    
    /**
     * Constructor that uses the parent one to
     * create a new Instance.
     * @param color of the new ChessPiece
     */
    public Ferz(Color color) {
        super(color);
    }
    
    /**
     * @return The String representation of this class
     */
    @Override
    public String getType() {
        return "Ferz";
    }

    @Override
    public boolean checkKill(int row, int col, Object[][] board) {
        if((row - 1) >= 0 && (col - 1) >= 0 && board[row - 1][col - 1] != null){
            return true;
        }
        if((row - 1) >= 0 && (col + 1) < board.length && board[row - 1][col + 1] != null){
            return true;
        }
        if((row + 1) < board.length && (col + 1) < board.length && board[row + 1][col + 1] != null){
            return true;
        }
        if((row + 1) < board.length && (col - 1) >= 0 && board[row + 1][col - 1] != null){
            return true;
        }
        return false;
    }
}
