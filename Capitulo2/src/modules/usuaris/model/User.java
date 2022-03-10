/**
 * Class that represents a user of the application.
 */
package modules.usuaris.model;

import java.util.ArrayList;

/**
 * @author Jonathan Salisbury
 * @author Joan Vilella
 */
public abstract class User {

    protected String name;
    protected String surname;
    protected String email;
    protected long id;
    protected long nAccess;
    protected ArrayList<Partida> games;

    public User(String name, String surname, String email, long id, long nAccess) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.id = id;
        this.nAccess = nAccess;
        games = new ArrayList();
    }

    /**
     * @return String name of the child class.
     */
    public abstract String getType();

    /**
     * Method that adds a game to the user game list
     * members don't have game list.
     * @param partida game to be added.
     */
    public abstract void addPartida(Partida partida);

    /**
     * @return the name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the ID of the user.
     */
    public long getId() {
        return id;
    }
    
    /**
     * @return the email address of the user.
     */
    public String getEmail(){
        return email;
    }

    /**
     * @return the number of access to the program by the user. 
     */
    public long getnAccess() {
        return nAccess;
    }

    /**
     * This method returns the N game of this user,
     * if there is no N game returns NULL.
     * @param n the index of the game.
     * @return the game.
     */
    public Partida getNPartida(int n) {
        if (!games.isEmpty() && n <= games.size()) {
            return games.get(n);
        }
        return null;
    }
}
