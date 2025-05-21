package main.viewPackage;

import main.modelPackage.PlayerModel;
import main.modelPackage.ClubModel;
import main.modelPackage.LocalityModel;

public class PlayerDisplayData {
    public PlayerModel player;
    public ClubModel lastClub;
    public LocalityModel locality;

    public PlayerDisplayData(PlayerModel player, ClubModel lastClub, LocalityModel locality) {
        this.player = player;
        this.lastClub = lastClub;
        this.locality = locality;
    }
} 