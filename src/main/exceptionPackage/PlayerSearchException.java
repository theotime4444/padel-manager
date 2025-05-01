package main.exceptionPackage;

public class PlayerSearchException extends Exception {
    private String error;

    public PlayerSearchException(String e) {
        setError(e);
    }

    public void setError(String error) {
        this.error = error;
    }

    public String toString() {
        return "Erreur lors de la recherche du joueur : " + error;
    }
}
