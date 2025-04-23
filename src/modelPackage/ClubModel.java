package modelPackage;


import java.util.Date;

public class ClubModel {

    private Integer id;
    private Integer localityID;
    private String name;
    private String streetAddress;
    private String phoneNumber;
    private Date creationDate;
    private String website;
    private Boolean isBeginnersFriendly;
    private String instagramProfile;

    public ClubModel(int id, String name, String streetAddress, int localityID, String phoneNumber, Date creationDate, String website, boolean isBeginnersFriendly, String instagramProfile) {
        setName(name);
        setStreetAddress(streetAddress);
        setLocalityID(localityID);
        setPhoneNumber(phoneNumber);
        setCreationDate(creationDate);
        setWebsite(website);
        setBeginnersFriendly(isBeginnersFriendly);
        setInstagramProfile(instagramProfile);
    }



    public Integer getId() { return id; }
    public void setId(Integer id) {this.id = id;}

    public Integer getLocalityID() {return localityID;}
    public void setLocalityID(Integer localityID) {this.localityID = localityID;}

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
    public void setBeginnersFriendly(boolean beginnersFriendly) {isBeginnersFriendly = beginnersFriendly;}

    public String getInstagramProfile() {return instagramProfile;}
    public void setInstagramProfile(String instagramProfile) {this.instagramProfile = instagramProfile;}


}
