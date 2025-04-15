package exceptionPackage;

public class PlayerCreationException extends Exception {

    private String error;

    public  PlayerCreationException(String e) {
        setError(e);
    }

    public void setError(String error) {
        this.error = error;
    }

    public String toString() {
        return "Erreur lors de la création du joueur : " + error;
    }
}
