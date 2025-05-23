package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.TournamentModel;

import java.sql.*;
import java.util.*;

public class TournamentDBAccess implements TournamentDataAccess {
    public TournamentDBAccess() {}

    public TournamentModel fillTournament(ResultSet rs) throws SQLException {
        TournamentModel tournament = new TournamentModel();
        tournament.setTournamentId(rs.getInt("tournamentId"));
        tournament.setName(rs.getString("name"));
        tournament.setPrize(rs.getString("prize"));
        tournament.setStartingDateHour(rs.getTimestamp("startingDateHour").toLocalDateTime());
        tournament.setEndingDateHour(rs.getTimestamp("endingDateHour").toLocalDateTime());
        tournament.setClubId(rs.getInt("clubId"));
        return tournament;
    }

    @Override
    public List<TournamentModel> getTournamentsByName(String name) throws TournamentSearchException {
        String query = "SELECT * FROM Tournament WHERE LOWER(name) LIKE LOWER(?)";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + name + "%");
            ResultSet rs = statement.executeQuery();

            List<TournamentModel> tournaments = new ArrayList<>();
            while (rs.next()) {
                tournaments.add(fillTournament(rs));
            }

            return tournaments;

        } catch (SQLException e) {
            throw new TournamentSearchException("Erreur lors de la recherche des tournois: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new TournamentSearchException("Erreur de connexion lors de la recherche des tournois: " + e.getMessage());
        }
    }
} 