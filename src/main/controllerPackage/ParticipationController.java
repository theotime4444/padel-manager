package main.controllerPackage;

import main.businessPackage.ParticipationManager;
import main.exceptionPackage.ParticipationSearchException;
import main.modelPackage.ParticipationModel;
import java.util.List;

public class ParticipationController {
    private ParticipationManager participationManager;

    public ParticipationController() {
        this.participationManager = new ParticipationManager();
    }

    public List<ParticipationModel> getParticipationsByGameId(int gameId) throws ParticipationSearchException {
        return participationManager.getParticipationsByGameId(gameId);
    }
} 