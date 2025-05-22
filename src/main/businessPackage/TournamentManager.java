package main.businessPackage;

import main.dataAccessPackage.TournamentDataAccess;
import main.dataAccessPackage.TournamentDBAccess;
import main.exceptionPackage.*;
import main.modelPackage.*;
import main.viewPackage.TournamentDisplayData;
import java.util.*;

public class TournamentManager {
    private TournamentDataAccess tournamentDataAccess;
    private GameManager gameManager;
    private ClubManager clubManager;
    private ParticipationManager participationManager;
    private PlayerManager playerManager;

    public TournamentManager() {
        this.tournamentDataAccess = new TournamentDBAccess();
        this.gameManager = new GameManager();
        this.clubManager = new ClubManager();
        this.participationManager = new ParticipationManager();
        this.playerManager = new PlayerManager();
    }

    public TournamentModel getTournamentByName(String name) throws TournamentSearchException {
        if (name == null || name.trim().isEmpty()) {
            throw new TournamentSearchException("Le nom du tournoi ne peut pas être vide");
        }
        return tournamentDataAccess.getTournamentByName(name);
    }

    public List<TournamentDisplayData> getTournamentMatchesWithDetails(String tournamentName) 
            throws TournamentSearchException, GameSearchException, ParticipationSearchException {
        // Recherche du tournoi
        TournamentModel tournament = getTournamentByName(tournamentName);
        if (tournament == null) {
            return new ArrayList<>();
        }

        // Récupération des matchs du tournoi
        List<GameModel> games = gameManager.getGamesByTournamentId(tournament.getTournamentId());
        if (games.isEmpty()) {
            return new ArrayList<>();
        }

        List<TournamentDisplayData> result = new ArrayList<>();

        // Pour chaque match
        for (GameModel game : games) {
            // Récupération des participations
            List<ParticipationModel> participations = participationManager.getParticipationsByGameId(game.getGameId());
            
            // Pour chaque participation
            for (ParticipationModel participation : participations) {
                result.add(new TournamentDisplayData(tournament, game, participation));
            }
        }

        return result;
    }
} 