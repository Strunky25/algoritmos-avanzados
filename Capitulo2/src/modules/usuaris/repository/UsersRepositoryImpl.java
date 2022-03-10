/*
 * Class that implements UserRepository and contains all the data from 
 * the app users.
 */
package modules.usuaris.repository;

import java.util.ArrayList;
import java.util.List;
import modules.usuaris.model.*;

/**
 * @author Jonathan Salisbury
 * @author Joan Vilella
 */
public class UsersRepositoryImpl implements UsersRepository {

    private final List<User> users; //list that contains all users

    public UsersRepositoryImpl(List<User> users) {
        this.users = users;
    }

    @Override
    public User findOne(long id) throws NotFoundException {
        for(User user: users){
            if(user.getId() == id){
                return user;
            }
        }
        throw new NotFoundException();
    }   

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public float[] meanAccessByClass() {
        float[] means = new float[3];
        int nReferee = 0, nMember = 0, nPlayer = 0;
        for (User user: users) {
            if (user instanceof Referee) {
                means[0] += user.getnAccess();
                nReferee++;
            } else if (user instanceof Member) {
                means[1] += user.getnAccess();
                nMember++;
            } else if (user instanceof Player) {
                means[2] += user.getnAccess();
                nPlayer++;
            }
        }
        if(nReferee != 0 && nMember != 0 && nPlayer != 0){
            means[0] /= nReferee;
            means[1] /= nMember;
            means[2] /= nPlayer;
            return means;
        }
        return null;
    }

    @Override
    public String findUserWithMaxAccess() {
        User maxAcces = users.get(0);
        for (User user: users) {
            if (maxAcces.getnAccess() < user.getnAccess()) {
                maxAcces = user;
            }
        }
        return maxAcces.getName();
    }

    @Override
    public List<String> findUsersWithNoAccess() {
        List<String> usersNoAcces = new ArrayList();
        users.stream().filter(user -> (user.getnAccess() == 0)).forEachOrdered(user -> {
            usersNoAcces.add(user.getName());
        });
        return usersNoAcces;
    }

    @Override
    public String findUserWithMaxWins() {
        boolean first = true;
        Player maxWins = null;
        for (User user: users){
            if (user instanceof Player && first) {
                maxWins = (Player) user;
                first = false;
            } else if (user instanceof Player) {
                Player player = (Player) user;
                if (player.getWonGames() < user.getnAccess()) {
                    maxWins = (Player) user;
                }
            }

        }
        return maxWins.getName(); 
    }
}
