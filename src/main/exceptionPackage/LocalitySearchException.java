package main.exceptionPackage;

public class LocalitySearchException extends Exception {

    private String error;

    public LocalitySearchException(String e) {
        setError(e);
    }

    public void setError(String error) {
        this.error = error;
    }

    public String toString() {
        return "Erreur lors de la recherche de la localite : " + error;
    }
}
