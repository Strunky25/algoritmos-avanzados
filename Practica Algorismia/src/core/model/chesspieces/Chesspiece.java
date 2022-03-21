/*
 * Authors:
 * Jonathan Salisbury
 * Joan Vilella
 * #SomUIB - Algoritmia
 */
package core.model.chesspieces;

import java.awt.Image;
import java.awt.Toolkit;

/*
 * Abstract Class that represents a Chesspiece in a Chess Game.
 */

/**
 *
 * @author elsho
 */

public abstract class Chesspiece {

    protected Color color;

    /**
     * Constructor that uses the parent one to
     * create a new Instance.
     * @param color of the new ChessPiece
     */
    public Chesspiece(Color color) {
        this.color = color;
    }

    /**
     * Method that returns the color of this Chesspiece.
     * @return Color of the piece.
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * @return String name of the child class.
     */
    public abstract String getType();
    
    /**
     * Abstract method that checks if this piece can kill another one.
     * @param row
     * @param col
     * @param board
     * @return true if it can kill, false otherwise.
     */
    public abstract boolean checkKill(int row, int col, Object[][] board);

    /**
     * @param color new color of this Chesspiece
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * Method that returns the image representation of this piece.
     * @return
     */
    public Image getImage(){
        if(color == Color.white)
            return Toolkit.getDefaultToolkit().getImage("resources/images/white/" + getType() + ".png");
        else
            return Toolkit.getDefaultToolkit().getImage("resources/images/black/" + getType() + ".png");
    }
    
    /**
     *
     * @return String representation of the chesspiece.
     */
    @Override
    public String toString(){
        return getType();
    }

    /**
     * Enum of the two possible colors of a Chesspiece.
     */
    public enum Color {
        white,
        black
    }
}
