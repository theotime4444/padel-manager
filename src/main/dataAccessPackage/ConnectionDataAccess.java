package main.dataAccessPackage;

import main.exceptionPackage.ConnectionDataAccessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataAccess {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/padelManager";
    private static final String USER = "root";
    private static final String PASSWORD = "paddle";
    private static Connection connection;

    public static Connection getInstance() throws ConnectionDataAccessException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new ConnectionDataAccessException(e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() throws ConnectionDataAccessException {
        if (connection != null) {
            try {
                connection.close();
                // si jamais on décide de fermer la connexion sans quitter le programme
                connection = null;
            } catch (SQLException e) {
                throw new ConnectionDataAccessException(e.getMessage());
            }
        }
    }




}
