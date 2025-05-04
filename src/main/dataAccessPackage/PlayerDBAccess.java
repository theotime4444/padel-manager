package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.PlayerModel;

import java.sql.*;
import java.util.*;

public class PlayerDBAccess implements PlayerDataAccess {
    public PlayerDBAccess() throws ConnectionDataAccessException {
    }


    public int playerInsertionOrUpdate(PlayerModel player, OperationType operationType) throws PlayerCreationException {

        String insertionQuery = "INSERT INTO player (lastName, firstName, birthdayDate, gender, eloPoints, phoneNumber, email, isPro, playerLocality, instagramProfile) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String updateQuery = "UPDATE player SET lastName = ?, firstName = ?, birthdayDate = ?, gender = ?, " +
                "eloPoints = ?, phoneNumber = ?, email = ?, isPro = ?,  " +
                "playerLocality = ?, instagramProfile = ? WHERE idPlayer = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement;

            if (operationType == OperationType.INSERT) {
                statement = connection.prepareStatement(insertionQuery, Statement.RETURN_GENERATED_KEYS);
            } else {
                statement = connection.prepareStatement(updateQuery);
            }

            statement.setString(1, player.getLastname());
            statement.setString(2, player.getFirstname());
            statement.setDate(3, new java.sql.Date(player.getBirthdayDate().getTime()));
            statement.setString(4, String.valueOf(player.getGender()));
            statement.setInt(5, player.getEloPoints());
            statement.setString(6, player.getPhoneNumber());
            statement.setString(7, player.getEmail());
            statement.setBoolean(8, player.getIsPro());
            statement.setInt(9, player.getLocality());

            String instagramProfile = player.getInstagramProfile();
            if (instagramProfile == null) {
                statement.setNull(10, Types.VARCHAR);
            } else {
                statement.setString(10, instagramProfile);
            }

            if (operationType == OperationType.UPDATE) {
                statement.setInt(11, player.getPlayerID());
            }

            int rowsAffected = statement.executeUpdate();

            if (operationType == OperationType.INSERT) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    player.setPlayerID(generatedId);
                }
            }

            return rowsAffected;

        } catch (SQLException e) {
            throw new PlayerCreationException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Create
    public Boolean createPlayer(PlayerModel player) throws PlayerCreationException {
        int lines = playerInsertionOrUpdate(player, OperationType.INSERT);
        if (lines == 0) throw new PlayerCreationException("Le joueur n'a pas pu être créé");
        return true;
    }

    // Update
    public Boolean updatePlayer(PlayerModel player) throws PlayerCreationException {
        int lines = playerInsertionOrUpdate(player, OperationType.UPDATE);
        if (lines == 0) throw new PlayerCreationException("Le joueur n'a pas pu être modifié");
        return true;
    }

    // Read
    public PlayerModel fillPlayer(ResultSet rs) throws SQLException {

        PlayerModel player = new PlayerModel();
        player.setPlayerID(rs.getInt("idPlayer"));
        player.setFirstname(rs.getString("firstName"));
        player.setLastname(rs.getString("lastName"));
        player.setBirthdayDate(rs.getDate("birthdayDate"));
        player.setGender(rs.getString("gender").charAt(0));
        player.setEloPoints(rs.getInt("eloPoints"));
        player.setEmail(rs.getString("email"));
        player.setLocality(rs.getInt("playerLocality"));
        player.setIsPro(rs.getBoolean("isPro"));

        String phoneNumber = rs.getString("phoneNumber");
        if (!rs.wasNull()) {
            player.setPhoneNumber(phoneNumber);
        }
        
        String instagramProfile = rs.getString("instagramProfile");
        if (!rs.wasNull()) {
            player.setInstagramProfile(instagramProfile);
        }

        return player;
    }

    public List<PlayerModel> getAllPlayers() throws PlayerSearchException {

        String query = "SELECT * FROM user";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            List<PlayerModel> players = new ArrayList<>();
            while (rs.next()) {
                players.add(fillPlayer(rs));
            }

            return players;

        } catch (SQLException e) {
            throw new PlayerSearchException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public PlayerModel getPlayerById(int id) throws PlayerSearchException {

        String query = "SELECT * FROM player WHERE player_id = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            List<PlayerModel> players = new ArrayList<>();
            while (rs.next()) {
                players.add(fillPlayer(rs));
            }

            return players.get(0);

        } catch (SQLException e) {
            throw new PlayerSearchException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PlayerModel> getPlayersByFullName (String firstName, String lastName) throws PlayerSearchException {

        String query = "SELECT * FROM player WHERE firstName ILIKE ? AND lastName ILIKE ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,firstName);
            statement.setString(2,lastName);
            ResultSet rs = statement.executeQuery();

            List<PlayerModel> players = new ArrayList<>();
            while (rs.next()) {
                players.add(fillPlayer(rs));
            }

            return players;

        } catch (SQLException e) {
            throw new PlayerSearchException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Delete
    public Boolean deletePlayer(PlayerModel player) throws PlayerDeletionException {
        if (player == null) throw new PlayerDeletionException("Le joueur n'existe pas");

        String query = "DELETE FROM player WHERE idPlayer = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, player.getPlayerID());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) throw new PlayerDeletionException("Le joueur n'a pas pu être supprimé");
            return true;

        }  catch (SQLException e) {
            throw new PlayerDeletionException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

     /*

    public PlayerModel getPlayerById(int playerId) throws SQLException {
        String query = "SELECT * FROM player WHERE player_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playerId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToPlayer(resultSet);
                }
            }
        }
        return null;
    }

    public List<PlayerModel> getAllPlayers() throws SQLException {
        List<PlayerModel> players = new ArrayList<>();
        String query = "SELECT * FROM player";
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                players.add(mapResultSetToPlayer(resultSet));
            }
        }
        return players;
    }



    // Helper method to map ResultSet to PlayerModel



    private PlayerModel mapResultSetToPlayer(ResultSet resultSet) throws SQLException {
        PlayerModel player = new PlayerModel(
            resultSet.getString("lastname"),
            resultSet.getString("firstname"),
            resultSet.getDate("birthday_date"),
            resultSet.getString("gender").charAt(0),
            resultSet.getInt("elo_points"),
            resultSet.getString("phone_number"),
            resultSet.getString("email"),
            resultSet.getBoolean("is_pro")
        );
        
        player.setPlayerID(resultSet.getInt("player_id"));
        player.setClub(resultSet.getObject("club_id", Integer.class));
        player.setLocality(resultSet.getObject("locality_id", Integer.class));
        player.setInstagramProfile(resultSet.getString("instagram_profile"));
        
        return player;
    }

     */
}
