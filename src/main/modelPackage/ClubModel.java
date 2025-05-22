package main.modelPackage;


import java.util.Date;

public class ClubModel {

    private Integer clubId;
    private Integer localityId;
    private String name;
    private String streetAddress;
    private String phoneNumber;
    private Date creationDate;
    private String website;
    private Boolean isBeginnersFriendly;
    private String instagramProfile;

    public ClubModel(Integer clubId, String name, String streetAddress, int localityId, String phoneNumber, Date creationDate, String website, boolean isBeginnersFriendly, String instagramProfile) {
        setClubId(clubId);
        setName(name);
        setStreetAddress(streetAddress);
        setLocalityId(localityId);
        setPhoneNumber(phoneNumber);
        setCreationDate(creationDate);
        setWebsite(website);
        setIsBeginnersFriendly(isBeginnersFriendly);
        setInstagramProfile(instagramProfile);
    }

    public ClubModel() {}

    public Integer getClubId() { return clubId; }
    public void setClubId(Integer clubId) {this.clubId = clubId;}

    public Integer getLocalityId() {return localityId;}
    public void setLocalityId(Integer localityId) {this.localityId = localityId;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getStreetAddress() {return streetAddress;}
    public void setStreetAddress(String streetAddress) {this.streetAddress = streetAddress;}

    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    public Date getCreationDate() {return creationDate;}
    public void setCreationDate(Date creationDate) {this.creationDate = creationDate;}

    public String getWebsite() {return website;}
    public void setWebsite(String website) {this.website = website;}

    public boolean getIsBeginnersFriendly() {return isBeginnersFriendly;}
    public void setIsBeginnersFriendly(boolean beginnersFriendly) {isBeginnersFriendly = beginnersFriendly;}

    public String getInstagramProfile() {return instagramProfile;}
    public void setInstagramProfile(String instagramProfile) {this.instagramProfile = instagramProfile;}

}
