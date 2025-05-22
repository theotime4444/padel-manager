package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.ParticipationModel;

import java.sql.*;
import java.util.*;

public class ParticipationDBAccess implements ParticipationDataAccess {
    public ParticipationDBAccess() {}

    public ParticipationModel fillParticipation(ResultSet rs) throws SQLException {
        ParticipationModel participation = new ParticipationModel();
        participation.setGameId(rs.getInt("gameId"));
        participation.setPlayerId(rs.getInt("playerId"));
        participation.setScore(rs.getInt("score"));
        participation.setTeamNbr(rs.getInt("teamNbr"));
        return participation;
    }

    @Override
    public List<ParticipationModel> getParticipationsByGameId(int gameId) throws ParticipationSearchException {
        String query = "SELECT * FROM Participation WHERE gameId = ?";
        List<ParticipationModel> participations = new ArrayList<>();

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, gameId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                participations.add(fillParticipation(rs));
            }
            return participations;

        } catch (SQLException e) {
            throw new ParticipationSearchException("Erreur lors de la recherche des participations: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new ParticipationSearchException("Erreur de connexion lors de la recherche des participations: " + e.getMessage());
        }
    }
} 