/*
    Algoritmes Avan�ats - Capitulo 2
    Autors:
        Jonathan Salisbury Vega
        Joan Sans� Peric�s
        Joan Vilella Candia
 */
package model.chesspieces;

/**
 *
 * @author elsho
 */
public class Elephant extends Chesspiece{
    
    public static final int[] dx = {1,0,0,-1,-1,-2,-1,0,0,1,1,2};
    public static final int[] dy = {1,1,2,1,0,0,-1,-1,-2,-1,0,0};

    @Override
    public Type getType() {
        return Type.Elephant;
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
