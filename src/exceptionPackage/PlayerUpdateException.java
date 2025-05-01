package exceptionPackage;

public class PlayerUpdateException extends RuntimeException {

    private String error;

    public  PlayerUpdateException(String e) {
        setError(e);
    }

    public void setError(String error) {
        this.error = error;
    }

    public String toString() {
        return "Erreur lors de la mise à jour du joueur : " + error;
    }
}
