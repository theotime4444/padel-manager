package exceptionPackage;

public class MembershipDeletionException extends Exception {

    private String error;

    public MembershipDeletionException(String e) {
        setError(e);
    }
    public void setError(String error) {
        this.error = error;
    }

    public String toString() {
        return "Erreur lors de la suppresion de l'affiliation : " + error;
    }
}
