package modelPackage;

import java.time.LocalDateTime;

public class Tournement {
    private int id;
    private String nom;
    private double prix;
    private LocalDateTime dateHeureDebut;
    private LocalDateTime dateHeureFin;
    private Club club;

    public Tournement(int id, String nom, double prix, LocalDateTime dateHeureDebut, LocalDateTime dateHeureFin, Club club) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.dateHeureDebut = dateHeureDebut;
        this.dateHeureFin = dateHeureFin;
        this.club = club;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public LocalDateTime getDateHeureDebut() {
        return dateHeureDebut;
    }

    public void setDateHeureDebut(LocalDateTime dateHeureDebut) {
        this.dateHeureDebut = dateHeureDebut;
    }

    public LocalDateTime getDateHeureFin() {
        return dateHeureFin;
    }

    public void setDateHeureFin(LocalDateTime dateHeureFin) {
        this.dateHeureFin = dateHeureFin;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
