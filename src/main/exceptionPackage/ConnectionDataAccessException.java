package main.exceptionPackage;

public class ConnectionDataAccessException extends Exception{

    private String error;

    public ConnectionDataAccessException(String e) {
        setError(e);
    }

    public void setError(String error) {
        this.error = error;
    }

    public String toString() {
        return "Erreur lors de la connexion : " + error;
    }
}
