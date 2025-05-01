package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.PlayerModel;

import java.util.*;

public interface PlayerDataAccess {

    public Boolean createPlayer(PlayerModel player) throws PlayerCreationException;
    public Boolean updatePlayer(PlayerModel player) throws PlayerCreationException;
    public List<PlayerModel> getAllPlayers() throws PlayerSearchException;
    public PlayerModel getPlayerById(int playerId) throws PlayerSearchException;
    public List<PlayerModel> getPlayersByFullName(String firstName, String lastName) throws PlayerSearchException;
    public Boolean deletePlayer(PlayerModel player) throws PlayerDeletionException;

}
