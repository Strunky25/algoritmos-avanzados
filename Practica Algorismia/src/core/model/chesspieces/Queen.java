/*
 * Child class of Chesspieces that represents the Queen.
 */
package core.model.chesspieces;

/**
 * @author Jonathan Salisbury
 * @author Joan Vilella
 */
public class Queen extends Chesspiece{

    /**
     * Constructor that uses the parent one to
     * create a new Instance.
     * @param color of the new Chesspiece
     */
    public Queen(Color color) {
        super(color);
    }

    /**
     * @return The String representation of this class
     */
    @Override
    public String getType() {
        return "Queen";
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
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {//left superior diagonal
            if (board[i][j] != null)
                return true;
        }
        for (int i = row + 1, j = col - 1; j >= 0 && i < board.length; i++, j--) {//left inferior diagonal
            if (board[i][j] != null)
                return true;
        }
        for (int i = row - 1, j = col + 1; i >= 0 && j < board.length; i--, j++) {//right superior diagonal
            if (board[i][j] != null)
                return true;
        }
        for (int i = row + 1, j = col + 1; j < board.length && i < board.length; i++, j++) {//right inferior diagonal
            if (board[i][j] != null)
                return true;
        }
        return false;
    }
    
}
