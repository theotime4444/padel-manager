package main.dataAccessPackage;

import main.exceptionPackage.ParticipationSearchException;
import main.modelPackage.ParticipationModel;
import java.util.List;

public interface ParticipationDataAccess {
    List<ParticipationModel> getParticipationsByGameId(int gameId) throws ParticipationSearchException;
} 