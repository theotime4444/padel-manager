package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.MembershipModel;

import java.sql.*;
import java.util.*;

public class MembershipDBAccess implements MembershipDataAccess {
    public MembershipDBAccess() throws ConnectionDataAccessException {
    }

    public int membershipInsertionOrUpdate(MembershipModel membership, OperationType operationType) throws MembershipCreationException {
        String insertionQuery = "INSERT INTO Membership (registrationDate, Club, Player) VALUES (?, ?, ?)";
        String updateQuery = "UPDATE Membership SET registrationDate = ?, Club = ?, Player = ? WHERE registrationDate = ? AND Club = ? AND Player = ?";

        try {
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

        } catch (SQLException e) {
            throw new MembershipCreationException(e.getMessage());
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

    // Read
    public MembershipModel fillMembership(ResultSet rs) throws SQLException {
        MembershipModel membership = new MembershipModel();
        membership.setRegistrationDate(rs.getTimestamp("registrationDate").toLocalDateTime());
        membership.setClubId(rs.getInt("Club"));
        membership.setPlayerId(rs.getInt("Player"));
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
            throw new MembershipSearchException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MembershipModel> getMembershipsByPlayerId(int playerId) throws MembershipSearchException {
        String query = "SELECT * FROM Membership WHERE Player = ?";

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
            throw new MembershipSearchException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MembershipModel> getMembershipsByClubId(int clubId) throws MembershipSearchException {
        String query = "SELECT * FROM Membership WHERE Club = ?";

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
            throw new MembershipSearchException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
