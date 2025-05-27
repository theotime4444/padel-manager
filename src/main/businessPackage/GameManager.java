package main.businessPackage;

import main.dataAccessPackage.GameDataAccess;
import main.dataAccessPackage.GameDBAccess;
import main.exceptionPackage.GameSearchException;
import main.modelPackage.GameModel;
import java.util.List;

public class GameManager {
    private GameDataAccess gameDataAccess;

    public GameManager() {
        this.gameDataAccess = new GameDBAccess();
    }

    public List<GameModel> getGamesByTournamentId(int tournamentId) throws GameSearchException {
        if (tournamentId <= 0) {
            throw new GameSearchException("L'ID du tournoi doit être positif");
        }
        return gameDataAccess.getGamesByTournamentId(tournamentId);
    }

    public void createGame(GameModel game) throws GameCreationException {
        try {
            ValidationUtility.checkValidId(game.getCourtId(), "Le court");
            ValidationUtility.checkValidId(game.getTournamentId(), "Le tournoi");
            ValidationUtility.checkValidDateTime(game.getStartingDateHour(), "La date de début");
            ValidationUtility.checkValidDateTime(game.getEndingDateHour(), "La date de fin");

            if (game.getStartingDateHour().isAfter(game.getEndingDateHour())) {
                throw new GameCreationException("La date de début doit être antérieure à la date de fin");
            }

            gameDataAccess.createGame(game);
        } catch (GameCreationException e) {
            throw e;
        } catch (Exception e) {
            throw new GameCreationException("Erreur lors de la création du match : " + e.getMessage());
        }
    }

    public void updateGame(GameModel game) throws GameUpdateException {
        try {
            ValidationUtility.checkValidId(game.getGameId(), "Le match");
            ValidationUtility.checkValidId(game.getCourtId(), "Le court");
            ValidationUtility.checkValidId(game.getTournamentId(), "Le tournoi");
            ValidationUtility.checkValidDateTime(game.getStartingDateHour(), "La date de début");
            ValidationUtility.checkValidDateTime(game.getEndingDateHour(), "La date de fin");

            if (game.getStartingDateHour().isAfter(game.getEndingDateHour())) {
                throw new GameUpdateException("La date de début doit être antérieure à la date de fin");
            }

            gameDataAccess.updateGame(game);
        } catch (GameUpdateException e) {
            throw e;
        } catch (Exception e) {
            throw new GameUpdateException("Erreur lors de la mise à jour du match : " + e.getMessage());
        }
    }
} 