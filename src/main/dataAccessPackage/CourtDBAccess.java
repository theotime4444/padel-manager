package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.CourtModel;

import java.sql.*;
import java.util.*;

public class CourtDBAccess implements CourtDataAccess {
    public CourtDBAccess() {}

    public CourtModel fillCourt(ResultSet rs) throws SQLException {
        CourtModel court = new CourtModel();
        court.setCourtId(rs.getInt("CourtID"));
        court.setState(rs.getString("State"));
        court.setOutdoor(rs.getBoolean("Outdoor"));
        court.setClub(rs.getInt("Club"));

        return court;
    }

    public CourtModel getCourtById(int id) throws CourtSearchException {
        String query = "SELECT * FROM Court WHERE idCourt = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                return fillCourt(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new CourtSearchException("Erreur lors de la recherche du terrain: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new CourtSearchException("Erreur de connexion lors de la recherche du terrain: " + e.getMessage());
        }
    }

    public List<CourtModel> getAllCourts() throws CourtSearchException {
        String query = "SELECT * FROM Court";
        List<CourtModel> courts = new ArrayList<>();

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                courts.add(fillCourt(rs));
            }

            return courts;

        } catch (SQLException e) {
            throw new CourtSearchException("Erreur lors de la récupération des terrains: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new CourtSearchException("Erreur de connexion lors de la recherche des terrains: " + e.getMessage());
        }
    }


}
