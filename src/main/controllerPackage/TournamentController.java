package main.controllerPackage;

import main.businessPackage.TournamentManager;
import main.exceptionPackage.*;
import main.modelPackage.TournamentModel;
import main.viewPackage.TournamentDisplayData;
import java.util.List;

public class TournamentController {
    private TournamentManager tournamentManager;

    public TournamentController() {
        this.tournamentManager = new TournamentManager();
    }

    public TournamentModel getTournamentByName(String name) throws TournamentSearchException {
        return tournamentManager.getTournamentByName(name);
    }

    public List<TournamentDisplayData> getTournamentMatchesWithDetails(String tournamentName) 
            throws TournamentSearchException, GameSearchException, ParticipationSearchException {
        return tournamentManager.getTournamentMatchesWithDetails(tournamentName);
    }
} 