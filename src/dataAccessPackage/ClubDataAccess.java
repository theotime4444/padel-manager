package dataAccessPackage;

import exceptionPackage.*;
import modelPackage.ClubModel;

public interface ClubDataAccess {

    public Boolean createClub(ClubModel club) throws ClubCreationException;
    public Boolean updateClub(ClubModel club) throws ClubCreationException;
    public Boolean deleteClub(ClubModel club) throws ClubDeletionException;
}
