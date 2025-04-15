package dataAccessPackage;

import modelPackage.ClubModel;

public interface ClubDataAccess {

    public Boolean createPlayer(ClubModel club);
    public Boolean updatePlayer(ClubModel club);
    public Boolean deletePlayer(ClubModel club);
}
