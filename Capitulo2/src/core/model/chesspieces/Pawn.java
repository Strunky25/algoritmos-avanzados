/*
 * Child class of Chesspieces that represents the Pawn.
 */
package core.model.chesspieces;

/**
 * @author Jonathan Salisbury
 * @author Joan Vilella
 */
public class Pawn extends Chesspiece {

    /**
     * Constructor that uses the parent one to
     * create a new Instance.
     * @param color of the new ChessPiece
     */
    public Pawn(Color color) {
        super(color);
    }
    
    /**
     * @return The String representation of this class
     */
    @Override
    public String getType() {
        return "Pawn";
    }

    @Override
    public boolean checkKill(int row, int col, Object[][] board) {
        if(this.color == Color.white){
            if((row - 1) >= 0 && (col - 1) >= 0 && board[row - 1][col - 1] != null){
                return true;
            }
            if((row - 1) >= 0 && (col + 1) < board.length && board[row - 1][col + 1] != null){
                return true;
            }
        } else {
            if((row + 1) < board.length && (col + 1) < board.length && board[row + 1][col + 1] != null){
                return true;
            }
            if((row + 1) < board.length && (col - 1) >= 0 && board[row + 1][col - 1] != null){
                return true;
            }
        }
        return false;
    }
}
