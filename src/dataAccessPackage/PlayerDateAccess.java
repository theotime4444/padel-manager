package dataAccessPackage;

import exceptionPackage.PlayerCreationException;
import exceptionPackage.PlayerDeletionException;
import modelPackage.ClubModel;
import modelPackage.PlayerModel;

import java.sql.SQLException;

public interface PlayerDateAccess {

    public Boolean createPlayer(PlayerModel player) throws PlayerCreationException;
    public Boolean updatePlayer(PlayerModel player) throws PlayerCreationException;
    public Boolean deletePlayer(PlayerModel player) throws PlayerDeletionException;

}
