/*
    Algoritmes Avançats - Capitulo 2
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
 */
package model.chesspieces;

/**
 * Class that represents the Rook Chess Piece.
 */
public class Rook extends Chesspiece{

    public static final int[] dx = {1, 2, 3, 4, 5, 6, 7, 0, 0, 0, 0, 0, 0, 0,
        -1, -2, -3, -4, -5, -6, -7, 0, 0, 0, 0, 0, 0, 0};
    public static final int[] dy = {0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7,
        0, 0, 0, 0, 0, 0, 0,- 1, -2, -3, -4, -5, -6, -7};
    
    @Override
    public Type getType() {
        return Type.Rook;
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
