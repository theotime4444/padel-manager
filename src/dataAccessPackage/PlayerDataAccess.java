package dataAccessPackage;

import exceptionPackage.*;



import modelPackage.PlayerModel;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDataAccess implements PlayerDateAccess {
    private Connection connection;

    public PlayerDataAccess() throws ConnectionDataAccessException {
    }


    public int playerInsertionOrUpdate(PlayerModel player, boolean create) throws PlayerCreationException {

        String sqlInsertion = "INSERT INTO player (lastname, firstname, birthday_date, gender, elo_points, phone_number, email, is_pro, club_id, locality_id, instagram_profile) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String sqlUpdate = "UPDATE player SET lastname = ?, firstname = ?, birthday_date = ?, gender = ?, " +
                "elo_points = ?, phone_number = ?, email = ?, is_pro = ?, club_id = ?, " +
                "locality_id = ?, instagram_profile = ? WHERE player_id = ?";

        try {

            connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(create ? sqlInsertion : sqlUpdate);

            statement.setString(1, player.getLastname());
            statement.setString(2, player.getFirstname());
            statement.setDate(3, new java.sql.Date(player.getBirthdayDate().getTime()));
            statement.setString(4, String.valueOf(player.getGender()));
            statement.setInt(5, player.getEloPoints());
            statement.setString(6, player.getPhoneNumber());
            statement.setString(7, player.getEmail());
            statement.setBoolean(8, player.isPro());
            statement.setInt(9, player.getClub());
            statement.setInt(10, player.getLocality());

            String insta = player.getInstagramProfile();
            if (insta == null) statement.setNull(11, Types.VARCHAR);
            statement.setString(11, insta);

            return statement.executeUpdate();



        } catch (SQLException e){
            throw new PlayerCreationException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }

    }
    // Create
    public Boolean createPlayer (PlayerModel player) throws PlayerCreationException {
        int lines = playerInsertionOrUpdate(player, true);
        if (lines == 0) throw new PlayerCreationException("Le joueur n'a pas pu être créé");
        return true;
    }

    // Update
    public Boolean updatePlayer(PlayerModel player)  throws PlayerCreationException {
        int lines = playerInsertionOrUpdate(player, false);
        if (lines == 0) throw new PlayerCreationException("Le joueur n'a pas pu être modifié");
        return true;
    }

    // Delete
    public Boolean deletePlayer(PlayerModel player) throws PlayerDeletionException {


        String query = "DELETE FROM player WHERE player_id = ?";

        try {
            connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, player.getPlayerID());
            return statement.executeUpdate() != 0;

        }  catch (SQLException e) {
            throw new PlayerDeletionException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
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


    // Delete


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
