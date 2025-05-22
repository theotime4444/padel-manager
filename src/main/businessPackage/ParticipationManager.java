package main.businessPackage;

import main.dataAccessPackage.ParticipationDataAccess;
import main.dataAccessPackage.ParticipationDBAccess;
import main.exceptionPackage.ParticipationSearchException;
import main.modelPackage.ParticipationModel;
import java.util.List;

public class ParticipationManager {
    private ParticipationDataAccess participationDataAccess;

    public ParticipationManager() {
        this.participationDataAccess = new ParticipationDBAccess();
    }

    public List<ParticipationModel> getParticipationsByGameId(int gameId) throws ParticipationSearchException {
        if (gameId <= 0) {
            throw new ParticipationSearchException("L'ID du match doit être positif");
        }
        return participationDataAccess.getParticipationsByGameId(gameId);
    }
} 