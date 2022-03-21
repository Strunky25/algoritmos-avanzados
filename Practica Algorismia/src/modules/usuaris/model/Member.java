/*
 * Class that represents a member of a Chess club.
 */
package modules.usuaris.model;

/**
 * @author Jonathan Salisbury
 * @author Joan Vilella
 */
public class Member extends User{
    
    public Member(String name, String surname, String email, long id, long nAccess) {
        super(name, surname, email, id, nAccess);
    }
    
    @Override
    public String getType(){
        return "Soci";
    }

    /**
     * Empty method
     * Members don't play games
     * @param partida 
     */
    @Override
    public void addPartida(Partida partida) {}
}
