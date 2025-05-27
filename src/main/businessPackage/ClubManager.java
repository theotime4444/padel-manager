package main.businessPackage;

import main.exceptionPackage.*;
import main.modelPackage.ClubModel;
import main.dataAccessPackage.ClubDBAccess;
import main.dataAccessPackage.ClubDataAccess;
import main.modelPackage.PlayerModel;
import main.utilPackage.ValidationUtility;

import java.sql.Date;
import java.util.*;

public class ClubManager {
    private final ClubDataAccess clubDataAccess;

    public ClubManager() {
        this.clubDataAccess = new ClubDBAccess();
    }

    public List<ClubModel> getAllClubs() throws ClubSearchException {
        return clubDataAccess.getAllClubs();
    }

    public Boolean createClub(ClubModel club) throws ClubCreationException, ValidationException {
        validateClub(club);
        return clubDataAccess.createClub(club);
    }

    public Boolean updateClub(ClubModel club) throws ClubUpdateException, ValidationException {
        validateClub(club);
        return clubDataAccess.updateClub(club);
    }

    public ClubModel getLastClubByPlayer(PlayerModel player) throws ClubSearchException, ValidationException {
        PlayerManager playerManager = new PlayerManager();
        playerManager.validatePlayer(player);
        return clubDataAccess.getLastClubByPlayer(player);
    }

    public Boolean deleteClub(ClubModel club) throws ClubDeletionException {
        return clubDataAccess.deleteClub(club);
    }

    public ClubModel getClubById(int id) throws ClubSearchException {
        return clubDataAccess.getClubById(id);
    }

    //Validation
    private void validateClub(ClubModel club) throws ValidationException {
        if (club == null) throw new ValidationException("null", "Le club est nul");

        ValidationUtility.checkRequiredString(club.getName(), "Le nom", 1, 45);
        ValidationUtility.checkRequiredString(club.getStreetAddress(), "La rue", 1, 45);
        ValidationUtility.checkValidId(club.getLocalityId(), "La localité");
        ValidationUtility.checkRequiredString(club.getPhoneNumber(), "Le numéro de téléphone", 1, 20);
        ValidationUtility.checkValidDate((Date) club.getCreationDate(), "La date de création");

        ValidationUtility.checkOptionalString(club.getWebsite(), "Le site internet", 1, 45);
        ValidationUtility.checkOptionalString(club.getInstagramProfile(), "Le profil Instagram", 1, 50);
    }
}
