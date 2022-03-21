/*
 * Class that represents the state of a chessboard in a game.
 * Contains the position of the chesspieces.
 */
package modules.usuaris.model;

import core.model.chesspieces.Chesspiece;
import core.model.chesspieces.*;
import core.utils.PositionCode;

/**
 * @author Jonathan Salisbury
 * @author Joan Vilella
 */
public class EstatTauler {
    
    public static final int SIZE = 8;
    
    private final Chesspiece[][] tiles;//Array that represent the tiles of a chessboard.
            
    public EstatTauler(){
        tiles = new Chesspiece[SIZE][SIZE];
    }
    
    /**
     * Method that transforms a given String that contains the information
     * of a the board into the tiles array with the indexes given by
     * the utils.PositionCode class.
     * @param estado the String array that represents the board.
     */
    public void transformar(String estado){
        String[] casillas = estado.split(" ");
        for (String casilla : casillas) {
            String[] tile = casilla.split("-");
            PositionCode pc = new PositionCode(tile[0]);
            int i = pc.getRow() - 1;
            int j = pc.getColumn() - 1;
            int color = Integer.parseInt(tile[2]);
            switch(tile[1]){
                case "rook" -> tiles[i][j] =  new Rook(Chesspiece.Color.values()[color]);
                case "kight" -> tiles[i][j] =  new Knight(Chesspiece.Color.values()[color]);
                case "bishop" -> tiles[i][j] =  new Bishop(Chesspiece.Color.values()[color]);
                case "queen" -> tiles[i][j] =  new Queen(Chesspiece.Color.values()[color]);
                case "king" -> tiles[i][j] =  new King(Chesspiece.Color.values()[color]);
                case "pawnn" -> tiles[i][j] =  new Pawn(Chesspiece.Color.values()[color]);
            }
        }
    }
    
    /**
     * Method that loops through the tiles array and counts the number of
     * Kings left in the board.
     * @return the number of Kings left.
     */
    public int getNKings(){
        int nKings = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(tiles[i][j] instanceof King){
                    nKings++;
                }
            }
        }
        return nKings;
    }
    
    /**
     * Method that loops through the tiles array and returns the color
     * of the first King found, if no King is found returns null.
     * This method is suposed to be used after Partida.finished() to ensure there is only one king left
     * after that you can know what player has won the game with its color.
     * @return the color of the first king found in the tiles array.
     */
    public Chesspiece.Color getColorWKing(){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(tiles[i][j] instanceof King){
                    return tiles[i][j].getColor();
                }
            }
        }
        return null;
    }
    
    /**
     * @return the tiles array.
     */
    public Chesspiece[][] getTiles() {
        return tiles;
    }
}
