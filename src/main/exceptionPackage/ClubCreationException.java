package main.exceptionPackage;

public class ClubCreationException extends Exception {

    private String error;

    public  ClubCreationException(String e) {
        setError(e);
    }

    public void setError(String error) {
        this.error = error;
    }

    public String toString() {
        return "Erreur lors de la création du club : " + error;
    }
}
