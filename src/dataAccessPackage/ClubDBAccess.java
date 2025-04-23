package dataAccessPackage;

import exceptionPackage.*;
import modelPackage.ClubModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClubDBAccess implements ClubDataAccess {

    private Connection connection;

    public ClubDBAccess() throws ConnectionDataAccessException {
    }

    public int clubInsertionOrUpdate(ClubModel club, boolean create) throws ClubCreationException {

        String sqlInsertion = "INSERT INTO club (clubName,streetAddress,locality,phoneNumber,creationDate,website,isBeginnersFriendly,instagramProfile) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String sqlUpdate = "UPDATE club SET clubName = ?, streetAddress = ?, locality = ?, phoneNumber = ?, " +
                "creationDate = ?, website = ?, isBeginnersFriendly = ?, instagramProfile = ?, WHERE idClub = ?";

        try {

            connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement;

            if (create) {
                // Ajoute RETURN_GENERATED_KEYS pour récupérer l'ID auto-généré
                statement = connection.prepareStatement(sqlInsertion, Statement.RETURN_GENERATED_KEYS);
            } else {
                statement = connection.prepareStatement(sqlUpdate);
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


            if (!create) {
                // Pour update, on ajoute le player_id en 9e paramètre
                statement.setInt(9, club.getId());
            }

            int rowsAffected = statement.executeUpdate();

            // Récupération de l'ID si on a fait un INSERT
            if (create) {
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

    public Boolean createPlayer(ClubModel club) throws ClubCreationException {

        int lines = clubInsertionOrUpdate(club, true);
        if (lines == 0) throw new ClubCreationException("Le club n'a pas pu être créé");
        return true;

    }

    public Boolean updatePlayer(ClubModel club) throws ClubCreationException {

        int lines = clubInsertionOrUpdate(club, false);
        if (lines == 0) throw new ClubCreationException("Le club n'a pas pu être modifié");
        return true;

    }

    public Boolean deletePlayer(ClubModel club) throws ClubDeletionException {

        String query = "DELETE FROM club WHERE idClub = ?";

        try {
            connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, club.getId());
            return statement.executeUpdate() != 0;

        }  catch (SQLException e) {
            throw new ClubDeletionException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
