/*
    Algoritmes Avançats - Capitulo 2
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
 */
package model.chesspieces;

/**
 * Class that represents the Pawn Chess Piece.
 */
public class Pawn extends Chesspiece{

    public static final int[] dx = {2, 1, -1, -2, -2, -1, 1, 2};
    public static final int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};
    
    @Override
    public Type getType() {
        return Type.Pawn;
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
