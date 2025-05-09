package main.exceptionPackage;

public class ClubSearchException extends RuntimeException {
    private String error;

    public ClubSearchException(String e) { setError(e); }

    public void setError(String error) {
      this.error = error;
    }

    public String toString() {
      return "Erreur lors de la recherche du club : " + error;
    }
}
