/**
 * Class that represents a chess Game.
 * This class contains the players ID and the state of the board.
 */
package modules.usuaris.model;

import core.model.chesspieces.Chesspiece;


/**
 * @author Jonathan Salisbury
 * @author Joan Vilella
 */
public class Partida {
    public long idPartida;
    public long jugador1;
    public long jugador2;
    public long arbitre;
    public EstatTauler tauler;

    public Partida(long idPartida, long jugador1, long jugador2, long arbitre) {
        this.idPartida = idPartida;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.arbitre = arbitre;
    }

    /**
     * Method that checks if a game is finished by counting the number
     * of kings left in the board.
     * @return true if the game is finished, otherwise return false.
     */
    public boolean finished(){
        int nKings = tauler.getNKings();
        return nKings < 2;
    }
    
    /**
     * Method that checks if the game is finished and gets the player who has won.
     * @return the id of the winner
     */
    public long winner(){
        if(finished()){
            Chesspiece.Color color = tauler.getColorWKing();
            if(color == Chesspiece.Color.white){
                return jugador1;
            } else if(color == Chesspiece.Color.black){
                return jugador2;
            }
        }
        return -1;
    }
    
    /**
     * Method that checks if there is a winner and gets the player who loosed.
     * @return the id of the loser.
     */
    public long looser(){
        long winner = winner();
        if(winner == jugador1){
            return jugador2;
        } else if(winner == jugador2){
            return jugador1;
        }
        return -1;
    }

    /**
     * @param tauler set Board
     */
    public void setTauler(EstatTauler tauler) {
        this.tauler = tauler;
    }

    /**
     * @return the current board.
     */
    public EstatTauler getTauler() {
        return tauler;
    }  
}
