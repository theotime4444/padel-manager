package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.ClubModel;
import main.modelPackage.PlayerModel;

public interface ClubDataAccess {

    public Boolean createClub(ClubModel club) throws ClubCreationException;
    public Boolean updateClub(ClubModel club) throws ClubCreationException;
    public Boolean deleteClub(ClubModel club) throws ClubDeletionException;
    public ClubModel getLastClubByPlayer(PlayerModel player) throws ClubSearchException, ConnectionDataAccessException, PlayerCreationException;
}
