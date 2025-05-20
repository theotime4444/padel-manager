package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.ClubModel;
import main.modelPackage.PlayerModel;
import java.util.List;

public interface ClubDataAccess {

    public Boolean createClub(ClubModel club) throws ClubCreationException;
    public Boolean updateClub(ClubModel club) throws ClubUpdateException;
    public List<ClubModel> getAllClubs() throws ClubSearchException;
    public ClubModel getClubById(int id) throws ClubSearchException;
    public ClubModel getLastClubByPlayer(PlayerModel player) throws ClubSearchException;
    public Boolean deleteClub(ClubModel club) throws ClubDeletionException;
}
