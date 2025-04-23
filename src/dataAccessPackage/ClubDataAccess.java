package dataAccessPackage;

import exceptionPackage.*;
import modelPackage.ClubModel;

public interface ClubDataAccess {

    public Boolean createPlayer(ClubModel club) throws ClubCreationException;
    public Boolean updatePlayer(ClubModel club) throws ClubCreationException;
    public Boolean deletePlayer(ClubModel club) throws ClubDeletionException;
}
