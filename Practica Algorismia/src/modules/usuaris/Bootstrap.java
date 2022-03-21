/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.usuaris;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modules.usuaris.model.Partida;
import modules.usuaris.model.*;
import modules.usuaris.model.User;
import modules.usuaris.repository.UsersRepository;
import modules.usuaris.repository.UsersRepositoryImpl;
import modules.usuaris.view.UsersWindow;

/**
 *
 * @author elsho
 */
public class Bootstrap implements Runnable {

    UsersRepository users;
    List<Partida> partides;

    public Bootstrap() {
        this.users = loadUsers();
        this.partides = loadGames();
    }

    @Override
    public void run() {
        java.awt.EventQueue.invokeLater(() -> {
            new UsersWindow(users).setVisible(true);
        });
    }

    private List<Partida> loadGames(){
        partides = new ArrayList<>();
        try {
            List<DataLoader.GameData> gamesData = DataLoader.gamesLoader();
            for (DataLoader.GameData gameData : gamesData) {
                // TODO: Codi per emplenar la llista de partides
                Partida partida = new Partida(gameData.idPartida, gameData.jugador1, gameData.jugador2, gameData.arbitre); //create new game with current gameData
                EstatTauler tablero = new EstatTauler(); //create new empty board
                tablero.transformar(gameData.tauler); //obtain data for board from gameData
                partida.setTauler(tablero); // add board to game
                users.findOne(gameData.jugador1).addPartida(partida); //add game to players and referee gamelist
                users.findOne(gameData.jugador2).addPartida(partida);
                users.findOne(gameData.arbitre).addPartida(partida);
                partides.add(partida); // add game to gamelist
            }          
        } catch (FileNotFoundException | UsersRepository.NotFoundException ex) {
            Logger.getLogger(Bootstrap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return partides; //return the game list
    }

    private static UsersRepository loadUsers(){
        List<User> users = new ArrayList<>();
        try {
            List<DataLoader.UserData> usersData = DataLoader.usersLoader();
            usersData.stream().map((DataLoader.UserData userData) -> {
                // TODO: Codi per emplenar la llista de usuaris
                User user = null; //creates empty user
                switch(userData.tipus){ //depending on userData create one type of user.
                    case "0" -> user = new Player(userData.nom, userData.llinatges, userData.correuElectronic, userData.id, userData.numAccessos);
                    case "1" -> user = new Member(userData.nom, userData.llinatges, userData.correuElectronic, userData.id, userData.numAccessos);
                    case "2" -> user = new Referee(userData.nom, userData.llinatges, userData.correuElectronic, userData.id, userData.numAccessos);
                }
                return user;
            }).forEachOrdered(user -> {
                users.add(user); //add user to userlist
            });
            // TODO: Contruir i retornar l'objecte amb el repository
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Bootstrap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new UsersRepositoryImpl(users); //create and return user repository with userlist.
    }
}
