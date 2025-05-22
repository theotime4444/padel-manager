package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.LocalityModel;

import java.sql.*;
import java.util.*;

public class LocalityDBAccess implements LocalityDataAccess {
    public LocalityDBAccess() {}

    // Read
    public LocalityModel fillLocality(ResultSet rs) throws SQLException {
        LocalityModel locality = new LocalityModel();
        locality.setLocalityId(rs.getInt("localityId"));
        locality.setCountry(rs.getString("country"));
        locality.setRegion(rs.getString("region"));
        locality.setCity(rs.getString("city"));
        return locality;
    }

    @Override
    public LocalityModel getLocalityById(int id) throws LocalitySearchException {
        String query = "SELECT * FROM Locality WHERE localityId = ?";

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
            throw new LocalitySearchException("Erreur lors de la recherche de la localité: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new LocalitySearchException("Erreur de connexion lors de la recherche de la localité: " + e.getMessage());
        }
    }

    @Override
    public List<LocalityModel> getAllLocalities() throws LocalitySearchException {
        String query = "SELECT * FROM Locality ORDER BY city";
        List<LocalityModel> localities = new ArrayList<>();

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                localities.add(fillLocality(rs));
            }

            return localities;

        } catch (SQLException e) {
            throw new LocalitySearchException("Erreur lors de la récupération des localités: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new LocalitySearchException("Erreur de connexion lors de la recherche des localités: " + e.getMessage());
        }
    }

    @Override
    public List<String> getAllRegions() throws LocalitySearchException {
        String query = "SELECT DISTINCT region FROM Locality ORDER BY region";

        List<String> regions = new ArrayList<>();

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                regions.add(rs.getString("region"));
            }

            return regions;

        } catch (SQLException e) {
            throw new LocalitySearchException("Erreur lors de la recherche des régions : " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new LocalitySearchException("Erreur de connexion lors de la recherche des régions : " + e.getMessage());
        }
    }
}


