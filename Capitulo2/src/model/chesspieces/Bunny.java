/*
    Algoritmes Avançats - Capitulo 2
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package model.chesspieces;

public class Bunny extends Chesspiece{
    public static final int[] dx = {0,1,2,-1,-2,2,-2,2,-2,-2, 2, 2,-2, 0, 1,-1};
    public static final int[] dy = {2,2,2, 2, 2,1, 1,0, 0,-1,-1,-2,-2,-2,-2,-2};

    @Override
    public Type getType() {
        return Type.Bunny;
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
