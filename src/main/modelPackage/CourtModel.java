package main.modelPackage;

public class CourtModel {
    private Integer courtId;
    private String state;
    private Boolean isOutdoor;
    private Integer clubId;

    public CourtModel(String state, Boolean isOutdoor, Integer clubId) {
        setState(state);
        setOutdoor(isOutdoor);
        setClub(clubId);
    }

    public CourtModel() {}

    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
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

    public Integer getClub() {
        return clubId;
    }

    public void setClub(Integer clubId) {
        this.clubId = clubId;
    }
}
