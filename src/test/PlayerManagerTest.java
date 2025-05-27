package test;

import main.businessPackage.PlayerManager;
import main.exceptionPackage.PlayerSearchException;
import main.exceptionPackage.LocalitySearchException;
import main.modelPackage.PlayerModel;
import main.viewPackage.PlayerStatsDisplayData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerManagerTest {
    private PlayerManager playerManager;

    @BeforeEach
    void setUp() {
        playerManager = new PlayerManager();
    }

    @Test
    void getPlayerStats() throws PlayerSearchException {
        PlayerStatsDisplayData stats = playerManager.getPlayerStats(1);
        assertNotNull(stats);
    }

    @Test
    void findPotentialPartnersByEloAndCity() throws PlayerSearchException, LocalitySearchException {
        int eloPoints = 1500;
        String city = "Bruxelles";
        int maxEloDifference = 200;

        List<PlayerModel> potentialPartners = playerManager.findPotentialPartnersByEloAndCity(
            eloPoints, city, maxEloDifference
        );

        assertNotNull(potentialPartners);

        assertFalse(potentialPartners.isEmpty());

        for (PlayerModel partner : potentialPartners) {
            assertNotNull(partner);
            assertTrue(Math.abs(partner.getEloPoints() - eloPoints) <= maxEloDifference);
        }
    }
} 