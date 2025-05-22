package main.modelPackage;

public class ParticipationModel {

    private Integer playerId;
    private Integer gameId;
    private int score;
    private int teamNbr;

    public ParticipationModel() {}

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTeamNbr() {
        return teamNbr;
    }

    public void setTeamNbr(int teamNbr) {
        this.teamNbr = teamNbr;
    }
}
