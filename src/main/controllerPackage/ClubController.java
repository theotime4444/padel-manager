package main.controllerPackage;

import main.exceptionPackage.*;
import main.modelPackage.ClubModel;
import main.businessPackage.ClubManager;
import main.modelPackage.PlayerModel;

import java.util.*;

public class ClubController {
    private ClubManager clubManager;

    public ClubController() {
        clubManager = new ClubManager();
    }

    public List<ClubModel> getAllClubs() throws ClubSearchException {
        return clubManager.getAllClubs();
    }

    public Boolean createClub(ClubModel club) throws ClubCreationException, ValidationException {
        return clubManager.createClub(club);
    }

    public Boolean updateClub(ClubModel club) throws ClubUpdateException, ValidationException {
        return clubManager.updateClub(club);
    }

    public ClubModel getLastClubByPlayer(PlayerModel player) throws ClubSearchException, ValidationException {
        return clubManager.getLastClubByPlayer(player);
    }

    public Boolean deleteClub(ClubModel club) throws ClubDeletionException {
        return clubManager.deleteClub(club);
    }

    public ClubModel getClubById(int id) throws ClubSearchException {
        return clubManager.getClubById(id);
    }

}
