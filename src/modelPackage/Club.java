package modelPackage;

import java.time.LocalDate;

public class Club {

    private int clubID;
    private String name;
    private String streetAddress;
    private int localityID;
    private int phoneNumber;
    private LocalDate creationDate;
    private String website;
    private boolean isBeginnersFriendly;
    private String instagramProfile;

    public Club(int clubID, String name, String streetAddress, int localityID, int phoneNumber, LocalDate creationDate, String website, boolean isBeginnersFriendly, String instagramProfile) {
        this.clubID = clubID;
        this.name = name;
        this.streetAddress = streetAddress;
        this.localityID = localityID;
        this.phoneNumber = phoneNumber;
        this.creationDate = creationDate;
        this.website = website;
        this.isBeginnersFriendly = isBeginnersFriendly;
        this.instagramProfile = instagramProfile;
    }


}
