package main.controllerPackage;

import main.exceptionPackage.*;
import main.modelPackage.ClubModel;
import main.businessPackage.ClubManager;
import main.modelPackage.PlayerModel;

import java.util.*;

public class ClubController {
    private ClubManager clubManager;

    public ClubController() throws ConnectionDataAccessException {
        clubManager = new ClubManager();
    }

    public Boolean createClub(ClubModel club) throws ClubCreationException {
        return clubManager.createClub(club);
    }

    public Boolean updateClub(ClubModel club) throws ClubCreationException {
        return clubManager.updateClub(club);
    }

    public ClubModel getLastClubByPlayer(PlayerModel player) throws ClubSearchException, ConnectionDataAccessException, PlayerCreationException {
        return clubManager.getLastClubByPlayer(player);
    }

    public Boolean deleteClub(ClubModel club) throws ClubDeletionException {
        return clubManager.deleteClub(club);
    }

}
