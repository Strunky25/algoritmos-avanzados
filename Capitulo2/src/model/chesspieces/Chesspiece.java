/*
    Algoritmes Avançats - Capitulo 2
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package model.chesspieces;

import java.awt.Image;
import java.awt.Toolkit;

/**
 * Abstract class that represents a Chess Piece.
 */
public abstract class Chesspiece {
    
    public abstract Type getType();
    
    public abstract int[] getDx();
    public abstract int[] getDy();
    
    public Image getImage(){  
        return Toolkit.getDefaultToolkit().getImage("resources/chesspieces/" + getType() + ".png");
    }
    
    public static enum Type {
        Bishop,
        King,
        Knight,
        Pawn,
        Queen,
        Rook,
        Bunny;
        
        public Chesspiece getInstance(){
            switch(this){
                case Bishop ->  {return new Bishop();}
                case King ->    {return new King();}
                case Knight ->  {return new Knight();}
                case Pawn ->    {return new Pawn();}
                case Queen ->   {return new Queen();}
                case Rook ->    {return new Rook();}
                case Bunny ->   {return new Bunny();}
                default ->      {return null;}
            }
        }
    }
}
