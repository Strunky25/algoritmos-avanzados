/*
 * Child class of Chesspieces that represents the Queen.
 */
package core.model.chesspieces;

/**
 * @author Jonathan Salisbury
 * @author Joan Vilella
 */
public class Rook extends Chesspiece{

    /**
     * Constructor that uses the parent one to
     * create a new Instance.
     * @param color of the new ChessPiece
     */
    public Rook(Color color) {
        super(color);
    }

    /**
     * @return The String representation of this class
     */
    @Override
    public String getType() {
        return "Rook";
    }

    @Override
    public boolean checkKill(int row, int col, Object[][] board) {
        for (int i = 0; i < board.length; i++) {//check row
            if(i == col)
                continue;
            else if (board[row][i] != null)
                return true;
        }
        for (int i = 0; i < board.length; i++) {//check column
            if(i == row)
                continue;
            else if (board[i][col] != null)
                return true;
        }
        return false;  
    }
            
}
