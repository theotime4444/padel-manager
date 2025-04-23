package dataAccessPackage;

import exceptionPackage.*;
import modelPackage.PlayerModel;

public interface PlayerDataAccess {

    public Boolean createPlayer(PlayerModel player) throws PlayerCreationException;
    public Boolean updatePlayer(PlayerModel player) throws PlayerCreationException;
    public Boolean deletePlayer(PlayerModel player) throws PlayerDeletionException;

}
