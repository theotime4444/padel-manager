package main.businessPackage;

import main.exceptionPackage.ConnectionDataAccessException;
import main.dataAccessPackage.ConnectionDataAccess;

import java.sql.Connection;

public class ConnectionDataAccessManager {

    public void closeConnection() throws ConnectionDataAccessException {
        ConnectionDataAccess.closeConnection();
    }
    public Connection getInstance() throws ConnectionDataAccessException {
        return ConnectionDataAccess.getInstance();
    }
}
