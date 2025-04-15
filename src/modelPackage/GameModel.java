package modelPackage;
import java.time.LocalDateTime;

public class GameModel {

    private Integer gameId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer courtId;
    private Integer tournementId;

    public GameModel(LocalDateTime startTime, LocalDateTime endTime) {
        setStartTime(startTime);
        setEndTime(endTime);
    }

    public Integer getGameID() {
        return gameId;
    }

    public void setgameId(Integer gameId) {
        this.gameId = gameId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getCourtId() {
        return courtId;
    }

    public void setCourtId(Integer courtId) {
        this.courtId = courtId;
    }

    public Integer getTournement() {
        return tournementId;
    }

    public void settournementId(Integer tournementId) {
        this.tournementId = tournementId;
    }
}
