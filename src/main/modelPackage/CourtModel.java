package main.modelPackage;

public class CourtModel {
    private Integer id;
    private String state;
    private Boolean isOutdoor;
    private ClubModel clubModel;

    public CourtModel(String state, Boolean isOutdoor, ClubModel clubModel) {

        this.state = state;
        this.isOutdoor = isOutdoor;
        this.clubModel = clubModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ClubModel getClub() {
        return clubModel;
    }

    public void setClub(ClubModel clubModel) {
        this.clubModel = clubModel;
    }
}
