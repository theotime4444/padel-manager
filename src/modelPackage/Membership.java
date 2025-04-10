package modelPackage;

import java.time.LocalDate;

public class Membership {
    private Integer clubId;
    private Integer playerId;
    private LocalDate registrationDate;

    public Membership( LocalDate registrationDate) {

        this.registrationDate = registrationDate;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer club) {
        this.clubId = club;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer player) {
        this.playerId = player;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
