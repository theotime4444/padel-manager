package modelPackage;

import java.time.LocalDate;

public class ClubModel {

    private Integer id;
    private Integer localityID;
    private String name;
    private String streetAddress;
    private int phoneNumber;
    private LocalDate creationDate;
    private String website;
    private Boolean isBeginnersFriendly;
    private String instagramProfile;

    public ClubModel(int id, String name, String streetAddress, int localityID, int phoneNumber, LocalDate creationDate, String website, boolean isBeginnersFriendly, String instagramProfile) {

    }

    public Integer getId() { return id; }
    public void setId(Integer id) {this.id = id;}

    public Integer getLocalityID() {return localityID;}
    public void setLocalityID(Integer localityID) {this.localityID = localityID;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getStreetAddress() {return streetAddress;}
    public void setStreetAddress(String streetAddress) {this.streetAddress = streetAddress;}

    public int getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(int phoneNumber) {this.phoneNumber = phoneNumber;}

    public LocalDate getCreationDate() {return creationDate;}
    public void setCreationDate(LocalDate creationDate) {this.creationDate = creationDate;}

    public String getWebsite() {return website;}
    public void setWebsite(String website) {this.website = website;}

    public boolean isBeginnersFriendly() {return isBeginnersFriendly;}
    public void setBeginnersFriendly(boolean beginnersFriendly) {isBeginnersFriendly = beginnersFriendly;}

    public String getInstagramProfile() {return instagramProfile;}
    public void setInstagramProfile(String instagramProfile) {this.instagramProfile = instagramProfile;}


}
