package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.MembershipModel;

import java.sql.*;
import java.util.*;

public class MembershipDBAccess implements MembershipDataAccess {
    public MembershipDBAccess() {}

    public int membershipInsertionOrUpdate(MembershipModel membership, OperationType operationType) throws SQLException, ConnectionDataAccessException {
        String insertionQuery = "INSERT INTO Membership (registrationDate, clubId, playerId) VALUES (?, ?, ?)";
        String updateQuery = "UPDATE Membership SET registrationDate = ?, clubId = ?, playerId = ? WHERE registrationDate = ? AND clubId = ? AND playerId = ?";

        Connection connection = ConnectionDataAccess.getInstance();
        PreparedStatement statement;

        if (operationType == OperationType.INSERT) {
            statement = connection.prepareStatement(insertionQuery);
        } else {
            statement = connection.prepareStatement(updateQuery);
        }

        statement.setTimestamp(1, Timestamp.valueOf(membership.getRegistrationDate()));
        statement.setInt(2, membership.getClubId());
        statement.setInt(3, membership.getPlayerId());

        return statement.executeUpdate();
    }

    public Boolean createMembership(MembershipModel membership) throws MembershipCreationException {
        try {
            int rowsAffected = membershipInsertionOrUpdate(membership, OperationType.INSERT);
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new MembershipCreationException("Erreur lors de la création de l'adhésion: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new MembershipCreationException("Erreur de connexion lors de la création de l'adhésion: " + e.getMessage());
        }
    }

    public MembershipModel fillMembership(ResultSet rs) throws SQLException {
        MembershipModel membership = new MembershipModel();
        membership.setRegistrationDate(rs.getTimestamp("registrationDate").toLocalDateTime());
        membership.setClubId(rs.getInt("clubId"));
        membership.setPlayerId(rs.getInt("playerId"));
        return membership;
    }

    public List<MembershipModel> getAllMemberships() throws MembershipSearchException {
        String query = "SELECT * FROM Membership";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            List<MembershipModel> memberships = new ArrayList<>();
            while (rs.next()) {
                memberships.add(fillMembership(rs));
            }

            return memberships;

        } catch (SQLException e) {
            throw new MembershipSearchException("Erreur lors de la recherche des adhésions: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new MembershipSearchException("Erreur de connexion lors de la recherche des adhésions: " + e.getMessage());
        }
    }

    public List<MembershipModel> getMembershipsByPlayerId(int playerId) throws MembershipSearchException {
        String query = "SELECT * FROM Membership WHERE playerId = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, playerId);
            ResultSet rs = statement.executeQuery();

            List<MembershipModel> memberships = new ArrayList<>();
            while (rs.next()) {
                memberships.add(fillMembership(rs));
            }

            return memberships;

        } catch (SQLException e) {
            throw new MembershipSearchException("Erreur lors de la recherche des adhésions du joueur: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new MembershipSearchException("Erreur de connexion lors de la recherche des adhésions du joueur: " + e.getMessage());
        }
    }

    public List<MembershipModel> getMembershipsByClubId(int clubId) throws MembershipSearchException {
        String query = "SELECT * FROM Membership WHERE clubId = ?";

        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, clubId);
            ResultSet rs = statement.executeQuery();

            List<MembershipModel> memberships = new ArrayList<>();
            while (rs.next()) {
                memberships.add(fillMembership(rs));
            }

            return memberships;

        } catch (SQLException e) {
            throw new MembershipSearchException("Erreur lors de la recherche des adhésions du club: " + e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new MembershipSearchException("Erreur de connexion lors de la recherche des adhésions du club: " + e.getMessage());
        }
    }
}
