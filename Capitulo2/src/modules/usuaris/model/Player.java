/**
 * Class that represents a chess Player of the application.
 */
package modules.usuaris.model;

/**
 * @author Jonathan Salisbury
 * @author Joan Vilella
 */
public class Player extends User{
    
    private int playedGames, wonGames, unfinishedGames;
    
    public Player(String name, String surname, String email, long id, long nAccess) {
        super(name, surname, email, id, nAccess);
        playedGames = 0;
        wonGames = 0;
        unfinishedGames = 0;
    }

    /**
     * @return the number of games played by this player.
     */
    public int getPlayedGames() {
        return playedGames;
    }

    /**
     * @return the number of games won by this player.
     */
    public int getWonGames() {
        return wonGames;
    }

    /**
     * 
     * @return the number of unfinished games from this player.
     */
    public int getUnfinishedGames() {
        return unfinishedGames;
    }
    
    @Override
    public String getType(){
        return "Jugador";
    }
    
    /**
     * Method that adds a game to the player game list
     * updating the game variables.
     * @param partida game to be added.
     */
    @Override
    public void addPartida(Partida partida){
        games.add(partida);
        playedGames++;
        if(partida.finished()){
            if(partida.winner() == this.id){
                wonGames++;
            }
        } else {
            unfinishedGames++;
        }
    }
}
