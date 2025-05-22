package main.modelPackage;

public class CourtModel {
    private Integer courtId;
    private String state;
    private Boolean isOutdoor;
    private Integer clubId;

    public CourtModel(String state, Boolean isOutdoor, Integer clubId) {
        setState(state);
        setOutdoor(isOutdoor);
        setClubId(clubId);
    }

    public CourtModel() {}

    public Integer getCourtId() {
        return courtId;
    }

    public void setCourtId(Integer courtId) {
        this.courtId = courtId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isOutdoor() {
        return isOutdoor;
    }

    public void setOutdoor(boolean outdoor) {
        isOutdoor = outdoor;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }
}
