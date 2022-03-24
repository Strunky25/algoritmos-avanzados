/*
    Algoritmes Avançats - Capitulo 2
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
 */
package model.chesspieces;

/**
 * Class that represents the King Chess Piece.
 */
public class King extends Chesspiece{

    public static final int[] dx = {1, 0, -1, -1, -1, 0, 1, 1};
    public static final int[] dy = {1, 1, 1, 0, -1, -1, -1, 0};
    
    @Override
    public Type getType() {
        return Type.King;
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
