package main.modelPackage;

import java.time.LocalDateTime;

public class TournamentModel {
    private Integer tournamentId;
    private String name;
    private String prize;
    private LocalDateTime startingDateHour;
    private LocalDateTime endingDateHour;
    private Integer clubId;

    public TournamentModel(String name, String prize, LocalDateTime startingDateHour, LocalDateTime endingDateHour) {
        this.name = name;
        this.prize = prize;
        this.startingDateHour = startingDateHour;
        this.endingDateHour = endingDateHour;
    }

    public TournamentModel() {}

    public Integer getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public LocalDateTime getStartingDateHour() {
        return startingDateHour;
    }

    public void setStartingDateHour(LocalDateTime startingDateHour) {
        this.startingDateHour = startingDateHour;
    }

    public LocalDateTime getEndingDateHour() {
        return endingDateHour;
    }

    public void setEndingDateHour(LocalDateTime endingDateHour) {
        this.endingDateHour = endingDateHour;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }
}
