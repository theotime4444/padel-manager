package main.businessPackage;

import main.exceptionPackage.*;
import main.modelPackage.PlayerModel;
import main.modelPackage.ClubModel;
import main.modelPackage.LocalityModel;
import main.dataAccessPackage.PlayerDataAccess;
import main.dataAccessPackage.PlayerDBAccess;
import main.utilPackage.ValidationUtility;
import main.viewPackage.PlayerDisplayData;
import main.viewPackage.PlayerStatsDisplayData;
import main.dataAccessPackage.ConnectionDataAccess;

import java.sql.Date;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerManager {
    private final PlayerDataAccess playerDataAccess;

    public PlayerManager() {
        this.playerDataAccess = new PlayerDBAccess();
    }

    public Boolean createPlayer(PlayerModel player) throws PlayerCreationException, ValidationException {
        validatePlayer(player);
        return playerDataAccess.createPlayer(player);
    }

    public Boolean updatePlayer(PlayerModel player) throws PlayerUpdateException, ValidationException {
        validatePlayer(player);
        return playerDataAccess.updatePlayer(player);
    }

    public List<PlayerModel> getAllPlayers() throws PlayerSearchException {
        return playerDataAccess.getAllPlayers();
    }

    public PlayerModel getPlayerById(int id) throws PlayerSearchException {
        return playerDataAccess.getPlayerById(id);
    }

    public List<PlayerModel> getPlayersByFullName(String firstName, String lastName) throws PlayerSearchException {
        return playerDataAccess.getPlayersByFullName(firstName, lastName);
    }

    public Boolean deletePlayer(PlayerModel player) throws PlayerDeletionException {
        return playerDataAccess.deletePlayer(player);
    }

    public List<PlayerDisplayData> getPlayersWithDetailsByFullName(String firstName, String lastName) 
            throws PlayerSearchException, ClubSearchException, ValidationException, LocalitySearchException {
        List<PlayerModel> players = playerDataAccess.getPlayersByFullName(firstName, lastName);
        ClubManager clubManager = new ClubManager();
        LocalityManager localityManager = new LocalityManager();

        List<PlayerDisplayData> result = new ArrayList<>();

        for (PlayerModel player : players) {
            ClubModel lastClub = clubManager.getLastClubByPlayer(player);
            LocalityModel locality = localityManager.getLocalityById(player.getLocalityId());
            result.add(new PlayerDisplayData(player, lastClub, locality));
        }

        return result;
    }

    public List<PlayerDisplayData> getPlayersWithDetailsByRegion(String region) 
            throws PlayerSearchException, ClubSearchException, ValidationException, LocalitySearchException {
        List<PlayerModel> players = playerDataAccess.getPlayersByRegion(region);
        ClubManager clubManager = new ClubManager();
        LocalityManager localityManager = new LocalityManager();

        List<PlayerDisplayData> result = new ArrayList<>();

        for (PlayerModel player : players) {
            ClubModel lastClub = clubManager.getLastClubByPlayer(player);
            LocalityModel locality = localityManager.getLocalityById(player.getLocalityId());
            result.add(new PlayerDisplayData(player, lastClub, locality));
        }

        // Trier par points ELO décroissant
        result.sort((p1, p2) -> Integer.compare(p2.player.getEloPoints(), p1.player.getEloPoints()));

        return result;
    }

    //Validation
    public void validatePlayer(PlayerModel player) throws ValidationException {
        if (player == null) throw new ValidationException("null", "Le joueur est nul");

        ValidationUtility.checkRequiredString(player.getLastname(), "Le nom", 1, 20);
        ValidationUtility.checkRequiredString(player.getFirstname(), "Le prénom", 1, 20);
        ValidationUtility.checkValidEmail(player.getEmail(), "L'email");
        ValidationUtility.checkValidDate((Date) player.getBirthdayDate(), "La date de naissance");
        ValidationUtility.checkValidGender(player.getGender(), "Le genre");
        ValidationUtility.checkValidRange(player.getEloPoints(), 0, 2000000, "Les points ELO");
        ValidationUtility.checkValidId(player.getLocalityId(), "La localité");

        ValidationUtility.checkOptionalString(player.getPhoneNumber(), "Le numéro de téléphone", 1, 20);
        ValidationUtility.checkOptionalString(player.getInstagramProfile(), "Le profil Instagram", 1, 50);
    }

    public PlayerStatsDisplayData getPlayerStats(int playerId) throws PlayerSearchException {
        return playerDataAccess.getPlayerStats(playerId);
    }

    public List<PlayerModel> findPotentialPartners(int playerId, int maxEloDifference) 
            throws PlayerSearchException, LocalitySearchException {
        return playerDataAccess.findPotentialPartners(playerId, maxEloDifference);
    }

    public List<PlayerModel> findPotentialPartnersByEloAndCity(int eloPoints, String city, int maxEloDifference) 
            throws PlayerSearchException, LocalitySearchException {
        return playerDataAccess.findPotentialPartnersByEloAndCity(eloPoints, city, maxEloDifference);
    }

}
