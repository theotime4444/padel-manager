package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.PlayerModel;
import main.viewPackage.PlayerStatsDisplayData;

import java.util.*;

public interface PlayerDataAccess {

    public Boolean createPlayer(PlayerModel player) throws PlayerCreationException;
    public Boolean updatePlayer(PlayerModel player) throws PlayerUpdateException;
    public List<PlayerModel> getAllPlayers() throws PlayerSearchException;
    public PlayerModel getPlayerById(int id) throws PlayerSearchException;
    public List<PlayerModel> getPlayersByFullName(String firstName, String lastName) throws PlayerSearchException;
    public Boolean deletePlayer(PlayerModel player) throws PlayerDeletionException;
    public List<PlayerModel> getPlayersByRegion(String region) throws PlayerSearchException;
    public PlayerStatsDisplayData getPlayerStats(int playerId) throws PlayerSearchException;
    public List<PlayerModel> findPotentialPartners(int playerId, int maxEloDifference) throws PlayerSearchException;
    public List<PlayerModel> findPotentialPartnersByEloAndCity(int eloPoints, String city, int maxEloDifference) 
            throws PlayerSearchException;

}
