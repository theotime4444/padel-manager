package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.ClubModel;
import main.modelPackage.PlayerModel;

import java.sql.*;
import java.util.*;

public class ClubDBAccess implements ClubDataAccess {
    public ClubDBAccess() throws ConnectionDataAccessException {
    }

    public int clubInsertionOrUpdate(ClubModel club, OperationType operationType) throws ClubCreationException {

        String insertionQuery = "INSERT INTO club (clubName,streetAddress,locality,phoneNumber,creationDate,website,isBeginnersFriendly,instagramProfile) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String updateQuery = "UPDATE club SET clubName = ?, streetAddress = ?, locality = ?, phoneNumber = ?, " +
                "creationDate = ?, website = ?, isBeginnersFriendly = ?, instagramProfile = ?, WHERE idClub = ?";

        try {
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
                    club.setId(generatedId); // Mets à jour ton ClubModel avec l’ID
                }
            }

            return rowsAffected;

        } catch (SQLException e){
            throw new ClubCreationException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }

    }

    //Create
    public Boolean createClub(ClubModel club) throws ClubCreationException {
        int lines = clubInsertionOrUpdate(club, OperationType.INSERT);
        if (lines == 0) throw new ClubCreationException("Le club n'a pas pu être créé");
        return true;

    }

    //Update
    public Boolean updateClub(ClubModel club) throws ClubCreationException {

        int lines = clubInsertionOrUpdate(club, OperationType.UPDATE);
        if (lines == 0) throw new ClubCreationException("Le club n'a pas pu être modifié");
        return true;

    }

    //Read
    public ClubModel fillClub(ResultSet rs) throws SQLException {

        ClubModel club = new ClubModel();
        club.setId(rs.getInt("idClub"));
        club.setName(rs.getString("name"));
        club.setStreetAddress(rs.getString("streetAddress"));
        club.setLocalityID(rs.getInt("locality"));
        club.setPhoneNumber(rs.getString("phoneNumber"));
        club.setCreationDate(rs.getDate("creationDate"));
        club.setWebsite(rs.getString("website"));
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

    public ClubModel getLastClubByPlayer(PlayerModel player) throws ClubSearchException {
        if (player == null) throw new ClubSearchException("Le club n'existe pas");

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
            throw new ClubSearchException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //Delete
    public Boolean deleteClub(ClubModel club) throws ClubDeletionException {
        if (club == null) throw new ClubDeletionException("le club n'existe pas");

        String query = "DELETE FROM club WHERE idClub = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, club.getId());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) throw new ClubDeletionException("Le club n'a pas pu être supprimé");
            return true;

        }  catch (SQLException e) {
            throw new ClubDeletionException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
