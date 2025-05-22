package main.viewPackage;

import main.modelPackage.PlayerModel;

public class PlayerStatsDisplayData {
    public PlayerModel player;
    public int totalGames;
    public int wonGames;
    public int lostGames;
    public double averageScore;

    public PlayerStatsDisplayData(PlayerModel player, int totalGames, int wonGames, int lostGames, double averageScore) {
        this.player = player;
        this.totalGames = totalGames;
        this.wonGames = wonGames;
        this.lostGames = lostGames;
        this.averageScore = averageScore;
    }
} 