package dataAccessPackage;

import exceptionPackage.*;



import modelPackage.PlayerModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDBAccess implements PlayerDataAccess {
    private Connection connection;

    public PlayerDBAccess() throws ConnectionDataAccessException {
    }


    public int playerInsertionOrUpdate(PlayerModel player, boolean create) throws PlayerCreationException {

        String sqlInsertion = "INSERT INTO player (lastName, firstName, birthdayDate, gender, eloPoints, phoneNumber, email, isPro, playerLocality, instagramProfile) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String sqlUpdate = "UPDATE player SET lastName = ?, firstName = ?, birthdayDate = ?, gender = ?, " +
                "eloPoints = ?, phoneNumber = ?, email = ?, isPro = ?,  " +
                "playerLocality = ?, instagramProfile = ? WHERE idPlayer = ?";

        try {
            connection = ConnectionDataAccess.getInstance();

            PreparedStatement statement;

            if (create) {
                // Ajoute RETURN_GENERATED_KEYS pour récupérer l'ID auto-généré
                statement = connection.prepareStatement(sqlInsertion, Statement.RETURN_GENERATED_KEYS);
            } else {
                statement = connection.prepareStatement(sqlUpdate);
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

            String insta = player.getInstagramProfile();
            if (insta == null) {
                statement.setNull(10, Types.VARCHAR);
            } else {
                statement.setString(10, insta);
            }

            if (!create) {
                // Pour update, on ajoute le player_id en 11e paramètre
                statement.setInt(11, player.getPlayerID());
            }

            int rowsAffected = statement.executeUpdate();

            // Récupération de l'ID si on a fait un INSERT
            if (create) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    player.setPlayerID(generatedId); // Mets à jour ton PlayerModel avec l’ID
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
        
        try {
            if (player == null) throw new PlayerDeletionException("le joueur n'existe pas");
            connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM player WHERE idPlayer = ?");
            statement.setInt(1, player.getPlayerID());
            
            return statement.executeUpdate() != 0;

        }  catch (SQLException e) {
            throw new PlayerDeletionException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PlayerModel> getAllPlayers() throws PlayerSearchException {

        try {
            String sql = "SELECT * FROM user";
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
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

        if (rs.getString("phoneNumber") != null)
            player.setPhoneNumber(rs.getString("phoneNumber"));

        if (rs.getString("instagramProfile") != null)
            player.setPhoneNumber(rs.getString("instagramProfile"));

        return player;
    }

    public List<PlayerModel> getPlayersByFullName (String firstName, String lastName) throws PlayerSearchException {

        List<PlayerModel> players = new ArrayList<>();

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            String sql = "SELECT * FROM player WHERE firstName = ? AND lastName = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,firstName);
            statement.setString(2,lastName);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                players.add(fillPlayer(rs));
            }
        } catch (SQLException e) {
            throw new PlayerSearchException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }
        return players;
    }




    // Read

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
