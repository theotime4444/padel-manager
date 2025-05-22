package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.TournamentModel;

import java.sql.*;
import java.util.*;

public class TournamentDBAccess implements TournamentDataAccess {
    public TournamentDBAccess() {}

    public TournamentModel fillTournament(ResultSet rs) throws SQLException {
        TournamentModel tournament = new TournamentModel();
        tournament.setTournamentId(rs.getInt("tournementId"));
        tournament.setName(rs.getString("name"));
        tournament.setPrize(rs.getInt("prize"));
        tournament.setStartingDateHour(rs.getTimestamp("startingDateHour").toLocalDateTime());
        tournament.setEndingDateHour(rs.getTimestamp("endingDateHour").toLocalDateTime());
        tournament.setClubId(rs.getInt("clubId"));
        return tournament;
    }

    @Override
    public TournamentModel getTournamentByName(String name) throws TournamentSearchException {
        String query = "SELECT * FROM Tournament WHERE name LIKE ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + name + "%");
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return fillTournament(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new TournamentSearchException("Erreur lors de la recherche du tournoi: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new TournamentSearchException("Erreur de connexion lors de la recherche du tournoi: " + e.getMessage());
        }
    }
} 