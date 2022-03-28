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
        King,
        Knight,
        Queen,
        Rook,
        Bunny,
        Elephant;
        
        public Chesspiece getInstance(){
            switch(this){
                case King ->    {return new King();}
                case Knight ->  {return new Knight();}
                case Queen ->   {return new Queen();}
                case Rook ->    {return new Rook();}
                case Bunny ->   {return new Bunny();}
                case Elephant ->   {return new Elephant();}
                default ->      {return null;}
            }
        }
    }
}
