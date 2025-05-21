package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.GameModel;

import java.sql.*;
import java.util.*;

public class GameDBAccess implements GameDataAccess {

    public GameDBAccess() {}

    private int gameInsertionOrUpdate(GameModel game, OperationType operationType) throws SQLException, ConnectionDataAccessException {
        String insertionQuery = "INSERT INTO Game (startTime, endTime, courtId, tournamentId)" +
                " VALUES (?, ?, ?, ?)";

        String updateQuery = "UPDATE Game SET startTime = ?, endTime = ?, courtId = ?, tournamentId = ? WHERE gameId = ?";

        Connection connection = ConnectionDataAccess.getInstance();
        PreparedStatement statement;

        if (operationType == OperationType.INSERT) {
            statement = connection.prepareStatement(insertionQuery, Statement.RETURN_GENERATED_KEYS);
        } else {
            statement = connection.prepareStatement(updateQuery);
        }

        statement.setTimestamp(1, Timestamp.valueOf(game.getStartTime()));
        statement.setTimestamp(2, Timestamp.valueOf(game.getEndTime()));
        statement.setInt(3, game.getCourtId());

        if (game.getTournement() != null) {
            statement.setInt(4, game.getTournement());
        } else {
            statement.setNull(4, Types.INTEGER);
        }

        if (operationType == OperationType.UPDATE) {
            statement.setInt(5, game.getGameID());
        }

        int rowsAffected = statement.executeUpdate();

        if (operationType == OperationType.INSERT) {
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                game.setgameId(generatedId);
            }
        }

        return rowsAffected;
    }

    public Boolean createGame(GameModel game) throws GameCreationException {
        try {
            int rowsAffected = gameInsertionOrUpdate(game, OperationType.INSERT);
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new GameCreationException("Erreur lors de la création du match : " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new GameCreationException("Erreur de connexion lors de la création du match : " + e.getMessage());
        }
    }

    public Boolean updateGame(GameModel game) throws GameUpdateException {
        try {
            int rowsAffected = gameInsertionOrUpdate(game, OperationType.UPDATE);
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new GameUpdateException("Erreur lors de la modification du match : " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new GameUpdateException("Erreur de connexion lors de la modification du match : " + e.getMessage());
        }
    }

    public GameModel fillGame(ResultSet rs) throws SQLException {

        GameModel game = new GameModel();
        game.setgameId(rs.getInt("gameId"));
        game.setStartTime(rs.getTimestamp("startingDateHour").toLocalDateTime());
        game.setEndTime(rs.getTimestamp("endingDateHour").toLocalDateTime());
        game.setCourtId(rs.getInt("courtId"));

        int tournamentId = rs.getInt("tournamentId");
        if(!rs.wasNull()) {
            game.settournementId(tournamentId);
        }

        return game;

    }

    public GameModel getGameById(int id) throws GameSearchException {
        String query = "SELECT * FROM Game WHERE gameId = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return fillGame(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new GameSearchException("Erreur lors de la recherche du match : " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new GameSearchException("Erreur de connexion lors de la recherche du match : " + e.getMessage());
        }
    }

    public Boolean deleteGame(GameModel game) throws GameDeletionException {
        String query = "DELETE FROM Game WHERE gameId = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, game.getGameID());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new GameDeletionException("Erreur lors de la suppression du match : " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new GameDeletionException("Erreur de connexion lors de la suppression du match : " + e.getMessage());
        }
    }
}

