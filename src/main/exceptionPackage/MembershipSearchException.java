package main.exceptionPackage;

public class MembershipSearchException extends Exception {
    private String error;

    public MembershipSearchException(String e) {
        setError(e);
    }

    public void setError(String error) {
        this.error = error;
    }

    public String toString() {
        return "Erreur lors de la recherche de l'adhésion : " + error;
    }
} 