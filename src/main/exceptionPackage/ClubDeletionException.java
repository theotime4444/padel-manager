package main.exceptionPackage;

public class ClubDeletionException extends Exception {

    private String error;

    public ClubDeletionException(String e) {
        setError(e);
    }
    public void setError(String error) {
        this.error = error;
    }

    public String toString() {
        return "Erreur lors de la suppresion du club : " + error;
    }

}
