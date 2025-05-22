package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.GameModel;

import java.util.List;

public interface GameDataAccess {
    public Boolean createGame(GameModel game) throws GameCreationException;
    public Boolean updateGame(GameModel game) throws GameUpdateException;
    public GameModel getGameById(int id) throws GameSearchException;
    public Boolean deleteGame(GameModel game) throws GameDeletionException;
    List<GameModel> getGamesByTournamentId(int tournamentId) throws GameSearchException;
}
