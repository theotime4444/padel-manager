package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.ClubModel;
import main.modelPackage.PlayerModel;

import java.sql.*;
import java.util.*;

public class ClubDBAccess implements ClubDataAccess {
    public ClubDBAccess() {}

    public int clubInsertionOrUpdate(ClubModel club, OperationType operationType) throws SQLException, ConnectionDataAccessException {

        String insertionQuery = "INSERT INTO Club (name, streetAddress, clubLocality, phoneNumber, creationDate, website, isBeginnersFriendly, instagramProfile) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String updateQuery = "UPDATE Club SET name = ?, streetAddress = ?, clubLocality = ?, phoneNumber = ?, " +
                "creationDate = ?, website = ?, isBeginnersFriendly = ?, instagramProfile = ? WHERE idClub = ?";

        Connection connection = ConnectionDataAccess.getInstance();
        PreparedStatement statement;

        if (operationType == OperationType.INSERT) {
            statement = connection.prepareStatement(insertionQuery, Statement.RETURN_GENERATED_KEYS);
        } else {
            statement = connection.prepareStatement(updateQuery);
        }

        statement.setString(1, club.getName());
        statement.setString(2, club.getStreetAddress());
        statement.setInt(3, club.getLocalityID());
        statement.setString(4, club.getPhoneNumber());
        statement.setDate(5, new java.sql.Date(club.getCreationDate().getTime()));

        String website = club.getWebsite();
        if (website == null) statement.setNull(6,Types.VARCHAR);
        statement.setString(6, club.getWebsite());

        statement.setBoolean(7, club.getIsBeginnersFriendly());

        String insta = club.getInstagramProfile();
        if (insta == null) statement.setNull(8, Types.VARCHAR);
        statement.setString(8, club.getInstagramProfile());

        if (operationType == OperationType.UPDATE) {
            // Pour update, on ajoute le player_id en 9e paramètre
            statement.setInt(9, club.getId());
        }

        int rowsAffected = statement.executeUpdate();

        // Récupération de l'ID si on a fait un INSERT
        if (operationType == OperationType.INSERT) {
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                club.setId(generatedId); // Mets à jour ton ClubModel avec l'ID
            }
        }

        return rowsAffected;
    }

    //Create
    public Boolean createClub(ClubModel club) throws ClubCreationException {
        try {
            int rowsAffected = clubInsertionOrUpdate(club, OperationType.INSERT);
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new ClubCreationException("Erreur lors de la création du club: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new ClubCreationException("Erreur de connexion lors de la création du club: " + e.getMessage());
        }
    }

    //Update
    public Boolean updateClub(ClubModel club) throws ClubUpdateException {
        try {
            int rowsAffected = clubInsertionOrUpdate(club, OperationType.UPDATE);
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new ClubUpdateException("Erreur lors de la modification du club: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new ClubUpdateException("Erreur de connexion lors de la modification du club: " + e.getMessage());
        }
    }

    //Read
    public ClubModel fillClub(ResultSet rs) throws SQLException {

        ClubModel club = new ClubModel();
        club.setId(rs.getInt("idClub"));
        club.setName(rs.getString("name"));
        club.setStreetAddress(rs.getString("streetAddress"));
        club.setLocalityID(rs.getInt("clubLocality"));
        club.setPhoneNumber(rs.getString("phoneNumber"));
        club.setCreationDate(rs.getDate("creationDate"));
        club.setIsBeginnersFriendly(rs.getBoolean("isBeginnersFriendly"));
        club.setInstagramProfile(rs.getString("instagramProfile"));

        String website = rs.getString("website");
        if (!rs.wasNull()) {
            club.setWebsite(website);
        }

        String instagramProfile = rs.getString("instagramProfile");
        if (!rs.wasNull()) {
            club.setInstagramProfile(instagramProfile);
        }

        return club;
    }

    public ClubModel getClubById(int id) throws ClubSearchException {
        String query = "SELECT * FROM Club WHERE idClub = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return fillClub(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new ClubSearchException("Erreur lors de la recherche du club: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new ClubSearchException("Erreur de connexion lors de la recherche du club: " + e.getMessage());
        }
    }

    public List<ClubModel> getAllClubs() throws ClubSearchException {
        String query = "SELECT * FROM Club";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            List<ClubModel> clubs = new ArrayList<>();
            while (rs.next()) {
                clubs.add(fillClub(rs));
            }

            return clubs;

        } catch (SQLException e) {
            throw new ClubSearchException("Erreur lors de la recherche des clubs: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new ClubSearchException("Erreur de connexion lors de la recherche des clubs: " + e.getMessage());
        }
    }

    public ClubModel getLastClubByPlayer(PlayerModel player) throws ClubSearchException {
        String query = """
            SELECT c.*
            FROM Club c
            JOIN Membership m ON m.Club = c.idClub
            WHERE m.Player = ?
            ORDER BY m.registrationDate DESC
            LIMIT 1
        """;

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, player.getPlayerID());
            ResultSet rs = statement.executeQuery();

            ClubModel club = new ClubModel();
            if(rs.next()) {
                club = fillClub(rs);
            }

            return club;

        }  catch (SQLException e) {
            throw new ClubSearchException("Erreur lors de la recherche du club: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new ClubSearchException("Erreur de connexion lors de la recherche du club: " + e.getMessage());
        }
    }

    //Delete
    public Boolean deleteClub(ClubModel club) throws ClubDeletionException {
        String query = "DELETE FROM Club WHERE idClub = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, club.getId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new ClubDeletionException("Erreur lors de la suppression du club: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new ClubDeletionException("Erreur de connexion lors de la suppression du club: " + e.getMessage());
        }
    }

}
