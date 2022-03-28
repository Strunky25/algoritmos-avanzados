/*
    Algoritmes Avançats - Capitulo 2
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
 */
package model.chesspieces;

/**
 * Class that represents the Queen Chess Piece.
 */
public class Queen extends Chesspiece{ 
    // 0, 0, 0, 0, 0, 0, 0,
    // 1, 2, 3, 4, 5, 6, 7,
    // -1, -2, -3, -4, -5, -6, -7,
    

    public static int[] dx = {1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 6, 7,
    -1, -2, -3, -4, -5, -6, -7, -1, -2, -3, -4, -5, -6, -7,
    -1, -2, -3, -4, -5, -6, -7, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7,};
    public static int[] dy = {0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 0, 0, 0, 0, 0, 0, 0,
    1, 2, 3, 4, 5, 6, 7, 0, 0, 0, 0, 0, 0, 0,
    -1, -2, -3, -4, -5, -6, -7, -1, -2, -3, -4, -5, -6, -7, -1, -2, -3, -4, -5, -6, -7,};
    
    @Override
    public Type getType() {
        return Type.Queen;
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
