package dataAccessPackage;

import modelPackage.PlayerModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDataAcces {
    private Connection connection;

    public PlayerDataAcces() {
        this.connection = ConnectionDataAccess.getInstance().getConnection();
    }

    // Create
    public void createPlayer(PlayerModel player) throws SQLException {
        String query = "INSERT INTO player (lastname, firstname, birthday_date, gender, elo_points, phone_number, email, is_pro, club_id, locality_id, instagram_profile) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, player.getLastname());
            statement.setString(2, player.getFirstname());
            statement.setDate(3, new java.sql.Date(player.getBirthdayDate().getTime()));
            statement.setString(4, String.valueOf(player.getGender()));
            statement.setInt(5, player.getEloPoints());
            statement.setString(6, player.getPhoneNumber());
            statement.setString(7, player.getEmail());
            statement.setBoolean(8, player.isPro());
            statement.setObject(9, player.getClub());
            statement.setObject(10, player.getLocality());
            statement.setString(11, player.getInstagramProfile());
            
            statement.executeUpdate();
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    player.setPlayerID(generatedKeys.getInt(1));
                }
            }
        }
    }

    // Read
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

    // Update
    public void updatePlayer(PlayerModel player) throws SQLException {
        String query = "UPDATE player SET lastname = ?, firstname = ?, birthday_date = ?, gender = ?, " +
                      "elo_points = ?, phone_number = ?, email = ?, is_pro = ?, club_id = ?, " +
                      "locality_id = ?, instagram_profile = ? WHERE player_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, player.getLastname());
            statement.setString(2, player.getFirstname());
            statement.setDate(3, new java.sql.Date(player.getBirthdayDate().getTime()));
            statement.setString(4, String.valueOf(player.getGender()));
            statement.setInt(5, player.getEloPoints());
            statement.setString(6, player.getPhoneNumber());
            statement.setString(7, player.getEmail());
            statement.setBoolean(8, player.isPro());
            statement.setObject(9, player.getClub());
            statement.setObject(10, player.getLocality());
            statement.setString(11, player.getInstagramProfile());
            statement.setInt(12, player.getPlayerID());
            
            statement.executeUpdate();
        }
    }

    // Delete
    public void deletePlayer(int playerId) throws SQLException {
        String query = "DELETE FROM player WHERE player_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playerId);
            statement.executeUpdate();
        }
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
}
