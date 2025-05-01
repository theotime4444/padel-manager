package controllerPackage;

import exceptionPackage.*;
import modelPackage.PlayerModel;
import businessPackage.PlayerManager;
import exceptionPackage.ConnectionDataAccessException;

import java.util.*;

public class PlayerController {
    private PlayerManager playerManager;

    public PlayerController() throws ConnectionDataAccessException {
        playerManager = new PlayerManager();
    }

    public Boolean createPlayer(PlayerModel player) throws PlayerCreationException {
        return playerManager.createPlayer(player);
    }

    public Boolean updatePlayer(PlayerModel player) throws PlayerCreationException, PlayerUpdateException {
        return playerManager.updatePlayer(player);
    }

    public List<PlayerModel> getAllPlayers() throws PlayerSearchException {
        return playerManager.getAllPlayers();
    }

    public PlayerModel getPlayerById(int playerId) throws PlayerSearchException {
        return playerManager.getPlayerById(playerId);
    }

    public List<PlayerModel> getPlayersByFullName(String firstName, String lastName) throws PlayerSearchException {
        return playerManager.getPlayersByFullName(firstName, lastName);
    }

    public Boolean deletePlayer(PlayerModel player) throws PlayerDeletionException {
        return playerManager.deletePlayer(player);
    }
}
