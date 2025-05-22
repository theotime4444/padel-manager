package main.modelPackage;
import java.time.LocalDateTime;

public class GameModel {

    private Integer gameId;
    private LocalDateTime startingDateHour;
    private LocalDateTime endingDateHour;
    private Integer courtId;
    private Integer tournementId;

    public GameModel(LocalDateTime startingDateHour, LocalDateTime endingDateHour) {
        setStartingDateHour(startingDateHour);
        setEndingDateHour(endingDateHour);
    }

    public GameModel() {}

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
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

    public Integer getCourtId() {
        return courtId;
    }

    public void setCourtId(Integer courtId) {
        this.courtId = courtId;
    }

    public Integer getTournamentId() {
        return tournementId;
    }

    public void setTournamentId(Integer tournamentId) {
        this.tournementId = tournamentId;
    }
}
