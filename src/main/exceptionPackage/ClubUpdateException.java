package main.exceptionPackage;

public class ClubUpdateException extends RuntimeException {

    private String error;

    public ClubUpdateException(String e) { setError(e); }

    public void setError(String error) { this.error = error; }

    public String toString() { return "Erreur lors de la mise à jour du club : " + error; }
}
