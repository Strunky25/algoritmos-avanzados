/*
    Algoritmes Avançats - Capitulo 2
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
 */
package model.chesspieces;

/**
 * Class that represents the Bishop Chess Piece.
 */
public class Bishop extends Chesspiece {

    public static final int[] dx = {1, 2, 3, 4, 5, 6, 7, 8, -1, -2, -3, -4, -5, -6, -7, -8, -1, -2, -3, -4, -5, -6, -7, -8, 1, 2, 3, 4, 5, 6, 7, 8};
    public static final int[] dy = {1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8, -1, -2, -3, -4, -5, -6, -7, -8, -1, -2, -3, -4, -5, -6, -7, -8};
    
    @Override
    public Type getType() {
        return Type.Bishop;
    }
    
    @Override
    public int[] getDx() {
        return dx;
    }

    @Override
    public int[] getDy() {
        return dy;
    }
}
