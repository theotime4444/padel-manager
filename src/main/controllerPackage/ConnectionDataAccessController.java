package main.controllerPackage;

import main.exceptionPackage.ConnectionDataAccessException;
import main.businessPackage.ConnectionDataAccessManager;

import java.sql.Connection;

public class ConnectionDataAccessController {

    private static ConnectionDataAccessManager connectionDataAccessManager;

    public ConnectionDataAccessController() {
        connectionDataAccessManager = new ConnectionDataAccessManager();
    }

    public void closeConnection() throws ConnectionDataAccessException {
        connectionDataAccessManager.closeConnection();
    }

    public  Connection getInstance() throws ConnectionDataAccessException {
        return connectionDataAccessManager.getInstance();
    }
}
