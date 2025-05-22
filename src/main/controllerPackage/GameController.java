package main.controllerPackage;

import main.businessPackage.GameManager;
import main.exceptionPackage.GameSearchException;
import main.modelPackage.GameModel;
import java.util.List;

public class GameController {
    private GameManager gameManager;

    public GameController() {
        this.gameManager = new GameManager();
    }

    public List<GameModel> getGamesByTournamentId(int tournamentId) throws GameSearchException {
        return gameManager.getGamesByTournamentId(tournamentId);
    }
} 