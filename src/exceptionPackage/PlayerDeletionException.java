package exceptionPackage;

public class PlayerDeletionException extends Exception {
    private String error;

    public PlayerDeletionException(String e) {
        setError(e);
    }

    public void setError(String error) {
        this.error = error;
    }

    public String toString() {
        return "Erreur lors de la suppresion du joueur : " + error;
    }
}
