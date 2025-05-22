package main.viewPackage;

import main.modelPackage.TournamentModel;
import main.modelPackage.GameModel;
import main.modelPackage.ParticipationModel;

public class TournamentDisplayData {
    public TournamentModel tournament;
    public GameModel game;
    public ParticipationModel participation;

    public TournamentDisplayData(TournamentModel tournament, GameModel game, ParticipationModel participation) {
        this.tournament = tournament;
        this.game = game;
        this.participation = participation;
    }
} 