/*
 * Child class of Chesspieces that represents the Knight.
 */
package core.model.chesspieces;

/**
 * @author Jonathan Salisbury
 * @author Joan Vilella
 */
public class Knight extends Chesspiece{

    /* Static Variables */
    public static final int rowMove[] = {1, 1, 2, 2, -1, -1, -2, -2}; 
    public static final int columnMove[] = {2, -2, 1, -1, 2, -2, 1, -1};  
    
    /**
     * Constructor that uses the parent one to
     * create a new Instance.
     * @param color of the new ChessPiece
     */
    public Knight(Color color) {
        super(color);
    }

    /**
     * @return The String representation of this class
     */
    @Override
    public String getType() {
        return "Knight";
    }

    @Override
    public boolean checkKill(int row, int col, Object[][] board) {
         for (int i = 0; i < rowMove.length; i++) {
            if(!possibleMove(row + rowMove[i], col + columnMove[i], board)){
                return true;
            }
        }
         return false;
    }   
    
    /*
     * Method used to check if a square is a posible move.
     */
    public static boolean possibleMove(int x, int y, Object[][] board) {
        return (x >= 0 && x < board.length && y >= 0 && y < board.length && board[x][y] == null);
    } 
}
