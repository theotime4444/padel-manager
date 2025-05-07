package main.modelPackage;

import java.time.LocalDateTime;

public class MembershipModel {
    private Integer clubId;
    private Integer playerId;
    private LocalDateTime registrationDate;

    public MembershipModel(Integer clubId, Integer playerId, LocalDateTime registrationDate) {
        setClubId(clubId);
        setPlayerId(playerId);
        setRegistrationDate(registrationDate);
    }

    public MembershipModel() {}

    public Integer getClubId() { return clubId; }
    public void setClubId(Integer clubId) { this.clubId = clubId; }

    public Integer getPlayerId() { return playerId; }
    public void setPlayerId(Integer playerId) { this.playerId = playerId; }

    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }
}
