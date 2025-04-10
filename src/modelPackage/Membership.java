package modelPackage;

import java.time.LocalDate;

public class Membership {
    private Club club;
    private Player player;
    private LocalDate registrationDate;

    public Membership(Club club, Player player, LocalDate registrationDate) {
        this.club = club;
        this.player = player;
        this.registrationDate = registrationDate;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
