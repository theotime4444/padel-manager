package main.controllerPackage;

import main.exceptionPackage.*;
import main.modelPackage.PlayerModel;
import main.businessPackage.PlayerManager;
import main.viewPackage.PlayerDisplayData;

import java.util.*;

public class PlayerController {
    private PlayerManager playerManager;

    public PlayerController() {
        playerManager = new PlayerManager();
    }

    public Boolean createPlayer(PlayerModel player) throws PlayerCreationException, ValidationException {
        return playerManager.createPlayer(player);
    }

    public Boolean updatePlayer(PlayerModel player) throws PlayerUpdateException, ValidationException {
        return playerManager.updatePlayer(player);
    }

    public List<PlayerModel> getAllPlayers() throws PlayerSearchException {
        return playerManager.getAllPlayers();
    }

    public PlayerModel getPlayerById(int id) throws PlayerSearchException {
        return playerManager.getPlayerById(id);
    }

    public List<PlayerModel> getPlayersByFullName(String firstName, String lastName) throws PlayerSearchException {
        return playerManager.getPlayersByFullName(firstName, lastName);
    }

    public Boolean deletePlayer(PlayerModel player) throws PlayerDeletionException {
        return playerManager.deletePlayer(player);
    }

    public List<PlayerDisplayData> getPlayersWithDetailsByFullName(String firstName, String lastName) 
            throws PlayerSearchException, ClubSearchException, ValidationException, LocalitySearchException {
        return playerManager.getPlayersWithDetailsByFullName(firstName, lastName);
    }
}
