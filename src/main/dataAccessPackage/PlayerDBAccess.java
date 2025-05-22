package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.PlayerModel;
import main.viewPackage.PlayerStatsDisplayData;

import java.sql.*;
import java.util.*;

public class PlayerDBAccess implements PlayerDataAccess {
    public PlayerDBAccess() {}

    public int playerInsertionOrUpdate(PlayerModel player, OperationType operationType) throws SQLException, ConnectionDataAccessException {
        String insertionQuery = "INSERT INTO Player (lastName, firstName, birthdayDate, gender, eloPoints, phoneNumber, email, isPro, localityId, instagramProfile) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String updateQuery = "UPDATE Player SET lastName = ?, firstName = ?, birthdayDate = ?, gender = ?, " +
                "eloPoints = ?, phoneNumber = ?, email = ?, isPro = ?,  " +
                "localityId = ?, instagramProfile = ? WHERE playerId = ?";

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
        statement.setInt(9, player.getLocalityId());

        String instagramProfile = player.getInstagramProfile();
        if (instagramProfile == null || instagramProfile.trim().isEmpty()) {
            statement.setNull(10, Types.VARCHAR);
        } else {
            statement.setString(10, instagramProfile);
        }

        if (operationType == OperationType.UPDATE) {
            statement.setInt(11, player.getPlayerId());
        }

        int rowsAffected = statement.executeUpdate();

        if (operationType == OperationType.INSERT) {
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                player.setPlayerId(generatedId);
            }
        }

        return rowsAffected;
    }

    public Boolean createPlayer(PlayerModel player) throws PlayerCreationException {
        try {
            int rowsAffected = playerInsertionOrUpdate(player, OperationType.INSERT);
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new PlayerCreationException("Erreur lors de la création du joueur: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new PlayerCreationException("Erreur de connexion lors de la création du joueur: " + e.getMessage());
        }
    }

    public Boolean updatePlayer(PlayerModel player) throws PlayerUpdateException {
        try {
            int rowsAffected = playerInsertionOrUpdate(player, OperationType.UPDATE);
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new PlayerUpdateException("Erreur lors de la modification du joueur: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new PlayerUpdateException("Erreur de connexion lors de la modification du joueur: " + e.getMessage());
        }
    }

    public PlayerModel fillPlayer(ResultSet rs) throws SQLException {
        PlayerModel player = new PlayerModel();
        player.setPlayerId(rs.getInt("playerId"));
        player.setFirstname(rs.getString("firstName"));
        player.setLastname(rs.getString("lastName"));
        player.setBirthdayDate(rs.getDate("birthdayDate"));
        player.setGender(rs.getString("gender").charAt(0));
        player.setEloPoints(rs.getInt("eloPoints"));
        player.setEmail(rs.getString("email"));
        
        int localityId = rs.getInt("localityId");
        if (!rs.wasNull()) {
            player.setLocalityId(localityId);
        }
        
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
        String query = "SELECT * FROM Player";

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
            throw new PlayerSearchException("Erreur lors de la recherche des joueurs: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new PlayerSearchException("Erreur de connexion lors de la recherche des joueurs: " + e.getMessage());
        }
    }

    public PlayerModel getPlayerById(int id) throws PlayerSearchException {
        String query = "SELECT * FROM Player WHERE playerId = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return fillPlayer(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new PlayerSearchException("Erreur lors de la recherche du joueur: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new PlayerSearchException("Erreur de connexion lors de la recherche du joueur: " + e.getMessage());
        }
    }

    public List<PlayerModel> getPlayersByFullName(String firstName, String lastName) throws PlayerSearchException {
        String query = "SELECT * FROM Player WHERE LOWER(firstName) LIKE LOWER(?) AND LOWER(lastName) LIKE LOWER(?)";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + firstName + "%");
            statement.setString(2, "%" + lastName + "%");
            ResultSet rs = statement.executeQuery();

            List<PlayerModel> players = new ArrayList<>();
            while (rs.next()) {
                players.add(fillPlayer(rs));
            }

            return players;

        } catch (SQLException e) {
            throw new PlayerSearchException("Erreur lors de la recherche des joueurs: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new PlayerSearchException("Erreur de connexion lors de la recherche des joueurs: " + e.getMessage());
        }
    }

    public Boolean deletePlayer(PlayerModel player) throws PlayerDeletionException {
        String query = "DELETE FROM Player WHERE playerId = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, player.getPlayerId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new PlayerDeletionException("Erreur lors de la suppression du joueur: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new PlayerDeletionException("Erreur de connexion lors de la suppression du joueur: " + e.getMessage());
        }
    }

    @Override
    public List<PlayerModel> getPlayersByRegion(String region) throws PlayerSearchException {
        String query = "SELECT p.* FROM Player p " +
                      "JOIN Locality l ON p.localityId = l.localityId " +
                      "WHERE l.region = ? " +
                      "ORDER BY p.eloPoints DESC";

        List<PlayerModel> players = new ArrayList<>();

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, region);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                players.add(fillPlayer(rs));
            }

            return players;

        } catch (SQLException e) {
            throw new PlayerSearchException("Erreur lors de la recherche des joueurs par région : " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new PlayerSearchException("Erreur de connexion lors de la recherche des joueurs par région : " + e.getMessage());
        }
    }

    @Override
    public PlayerStatsDisplayData getPlayerStats(int playerId) throws PlayerSearchException {
        String query = "SELECT " +
                      "COUNT(DISTINCT g.gameId) as totalGames, " +
                      "SUM(CASE WHEN p1.score > p2.score THEN 1 ELSE 0 END) as wonGames, " +
                      "SUM(CASE WHEN p1.score < p2.score THEN 1 ELSE 0 END) as lostGames, " +
                      "AVG(p1.score) as averageScore " +
                      "FROM Game g " +
                      "JOIN Participation p1 ON g.gameId = p1.gameId " +
                      "JOIN Participation p2 ON g.gameId = p2.gameId AND p1.teamNbr != p2.teamNbr " +
                      "WHERE p1.playerId = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, playerId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                PlayerModel player = getPlayerById(playerId);
                return new PlayerStatsDisplayData(
                    player,
                    rs.getInt("totalGames"),
                    rs.getInt("wonGames"),
                    rs.getInt("lostGames"),
                    rs.getDouble("averageScore")
                );
            }
            return new PlayerStatsDisplayData(getPlayerById(playerId), 0, 0, 0, 0);

        } catch (SQLException e) {
            throw new PlayerSearchException("Erreur lors de la récupération des statistiques : " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new PlayerSearchException("Erreur de connexion lors de la récupération des statistiques : " + e.getMessage());
        }
    }

    @Override
    public List<PlayerModel> findPotentialPartners(int playerId, int maxEloDifference) throws PlayerSearchException {
        PlayerModel player = getPlayerById(playerId);
        if (player == null) {
            throw new PlayerSearchException("Joueur non trouvé");
        }

        String query = "SELECT p.* FROM Player p " +
                      "JOIN Locality l1 ON p.localityId = l1.localityId " +
                      "JOIN Locality l2 ON l2.localityId = ? " +
                      "WHERE p.playerId != ? " +
                      "AND l1.city = l2.city " +
                      "AND ABS(p.eloPoints - ?) <= ? " +
                      "ORDER BY ABS(p.eloPoints - ?)";

        List<PlayerModel> partners = new ArrayList<>();

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, player.getLocalityId());
            statement.setInt(2, playerId);
            statement.setInt(3, player.getEloPoints());
            statement.setInt(4, maxEloDifference);
            statement.setInt(5, player.getEloPoints());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                partners.add(fillPlayer(rs));
            }

            return partners;

        } catch (SQLException e) {
            throw new PlayerSearchException("Erreur lors de la recherche des partenaires : " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new PlayerSearchException("Erreur de connexion lors de la recherche des partenaires : " + e.getMessage());
        }
    }

    @Override
    public List<PlayerModel> findPotentialPartnersByEloAndCity(int eloPoints, String city, int maxEloDifference) 
            throws PlayerSearchException {
        String query = "SELECT p.* FROM Player p " +
                      "JOIN Locality l ON p.localityId = l.localityId " +
                      "WHERE l.city = ? " +
                      "AND ABS(p.eloPoints - ?) <= ? " +
                      "ORDER BY ABS(p.eloPoints - ?)";

        List<PlayerModel> partners = new ArrayList<>();

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, city);
            statement.setInt(2, eloPoints);
            statement.setInt(3, maxEloDifference);
            statement.setInt(4, eloPoints);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                partners.add(fillPlayer(rs));
            }

            return partners;

        } catch (SQLException e) {
            throw new PlayerSearchException("Erreur lors de la recherche des partenaires : " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new PlayerSearchException("Erreur de connexion lors de la recherche des partenaires : " + e.getMessage());
        }
    }
}
