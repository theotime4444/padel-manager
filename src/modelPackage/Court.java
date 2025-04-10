package modelPackage;

public class Court {
    private Integer id;
    private String state;
    private Boolean isOutdoor;
    private Club club;

    public Court(String state, Boolean isOutdoor, Club club) {

        this.state = state;
        this.isOutdoor = isOutdoor;
        this.club = club;
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

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
