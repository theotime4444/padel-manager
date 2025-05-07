package main.exceptionPackage;

public class MembershipCreationException extends Exception {

    private String error;

    public MembershipCreationException(String e) {
        setError(e);
    }

    public void setError(String error) {
        this.error = error;
    }

    public String toString() {
        return "Erreur lors de la création de l'adhésion : " + error;
    }
} 