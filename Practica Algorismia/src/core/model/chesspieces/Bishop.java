/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package core.model.chesspieces;


/*
 * Child class of Chesspieces that represents the Bishop.
 */
public class Bishop extends Chesspiece{

    /**
     * Constructor that uses the parent one to
     * create a new Instance.
     * @param color of the new ChessPiece
     */
    public Bishop(Color color) {
        super(color);
    }

    /**
     * @return The String representation of this class
     */
    @Override
    public String getType() {
        return "Bishop";
    }

    /**
     * Method that calculates if this piece can kill another in a given
     * position on a given board.
     * @param row int of the row position
     * @param col int of the col position
     * @param board object[][] board.
     * @return true if it can kill or false if not.
     */
    @Override
    public boolean checkKill(int row, int col, Object[][] board) {
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--)//left superior diagonal
            if (board[i][j] != null) 
                return true; 
        for (int i = row + 1, j = col - 1; j >= 0 && i < board.length; i++, j--)//left inferior diagonal
            if (board[i][j] != null) 
                return true; 
        for (int i = row - 1, j = col + 1; i >= 0 && j < board.length; i--, j++)//right superior diagonal
            if (board[i][j] != null) 
                return true; 
        for (int i = row + 1, j = col + 1; j < board.length && i < board.length; i++, j++)//right inferior diagonal
            if (board[i][j] != null) 
                return true; 
        return false;
    } 
}
