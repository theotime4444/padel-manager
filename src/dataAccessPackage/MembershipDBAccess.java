package dataAccessPackage;

import exceptionPackage.*;
import modelPackage.ClubModel;
import modelPackage.MembershipModel;

import java.sql.*;


public class MembershipDBAccess implements MembershipDataAccess {


    private Connection connection;

    public MembershipDBAccess() throws ConnectionDataAccessException {
    }

    public Boolean createMembership(MembershipModel membership) throws MembershipCreationException {
        
        try {
            Connection connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO membership (registrationDate, Club, Player) VALUES (?,?,?)");

            statement.setDate(1,new java.sql.Date(membership.getRegistrationDate().getTime()));
            statement.setInt(2,membership.getClubId());
            statement.setInt(3,membership.getPlayerId());

            int lines = statement.executeUpdate();
            if (lines == 0) throw new MembershipCreationException("L'affiliation n'a pas pu être créée");
            return true;

        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new MembershipCreationException(e.getMessage());
        }
    }

    public Boolean deleteMembership(MembershipModel membership) throws MembershipDeletionException {

        try {
            if (membership == null) throw new MembershipDeletionException("L'affiliation n'existe pas");
            connection = ConnectionDataAccess.getInstance();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM membership WHERE Club = ? AND Player = ?");
            statement.setInt(1, membership.getClubId());
            statement.setInt(2, membership.getPlayerId());

            return statement.executeUpdate() != 0;

        }  catch (SQLException e) {
            throw new MembershipDeletionException(e.getMessage());
        } catch (ConnectionDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

}



    