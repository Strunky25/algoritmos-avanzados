/**
 * Class that represents a chess Referee of the application.
 */
package modules.usuaris.model;

/**
 * @author Jonathan Salisbury
 * @author Joan Vilella
 */
public class Referee extends User{
    
    private int refereedGames;
    
    public Referee(String name, String surname, String email, long id, long nAccess) {
        super(name, surname, email, id, nAccess);
        refereedGames = 0;
    }

    /**
     * @return the number of refereed games by this referee.
     */
    public int getRefereedGames() {
        return refereedGames;
    }

    @Override
    public String getType() {
        return "Arbitre";
    }
    
    /**
     * Method that adds a game to the referee game list
     * updating the game variables.
     * @param partida game to be added.
     */
    @Override
    public void addPartida(Partida partida) {
        games.add(partida);
        refereedGames++;
    }
}
