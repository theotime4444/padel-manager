package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.LocalityModel;

import java.sql.*;
import java.util.*;

public class LocalityDBAccess {
    public LocalityDBAccess() throws ConnectionDataAccessException {
    }

    // Read
    public LocalityModel fillLocality(ResultSet rs) throws SQLException {
        LocalityModel locality = new LocalityModel();
        locality.setLocalityID(rs.getInt("idLocality"));
        locality.setCountry(rs.getString("country"));
        locality.setRegion(rs.getString("region"));
        locality.setCity(rs.getString("city"));
        return locality;
    }

    public LocalityModel getLocalityById(int id) throws LocalitySearchException {
        String query = "SELECT * FROM locality WHERE idLocality = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return fillLocality(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new LocalitySearchException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

}

    /*
    public int localityInsertionOrUpdate(LocalityModel locality, OperationType operationType) throws localityCreationException {
        String insertionQuery = "INSERT INTO Locality (country, region, city) VALUES (?, ?, ?)";
        String updateQuery = "UPDATE Locality SET country = ?, region = ?, city = ? WHERE idLocality = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement;

            if (operationType == OperationType.INSERT) {
                statement = connection.prepareStatement(insertionQuery);
            } else {
                statement = connection.prepareStatement(updateQuery);
            }

            statement.setString(1, locality.getCountry());
            statement.setString(2, locality.getRegion());
            statement.setString(3, locality.getCity());

            if (operationType == OperationType.UPDATE) {
                // Pour update, on ajoute le player_id en 9e paramètre
                statement.setInt(4, locality.getLocalityID());
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

        } catch (SQLException e) {
            throw new LocalityCreationException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Create
    public Boolean createMembership(MembershipModel membership) throws MembershipCreationException {
        int lines = membershipInsertionOrUpdate(membership, OperationType.INSERT);
        if (lines == 0) throw new MembershipCreationException("L'adhésion n'a pas pu être créée");
        return true;
    }
    */


