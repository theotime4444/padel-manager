package main.modelPackage;


import java.util.Date;

public class PlayerModel {

    private Integer playerId;
    private String lastname;
    private String firstname;
    private Date birthdayDate;
    private char gender;
    private int eloPoints;
    private String phoneNumber;
    private String email;
    private boolean isPro;
    private Integer localityId;
    private String instagramProfile;

    public PlayerModel(Integer playerId, String lastname, String firstname, Date birthdayDate,
                       char gender, int eloPoints, String phoneNumber, String email, boolean isPro, Integer localityId, String instagramProfile) {
        setPlayerId(playerId);
        setLastname(lastname);
        setFirstname(firstname);
        setBirthdayDate(birthdayDate);
        setGender(gender);
        setEloPoints(eloPoints);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setIsPro(isPro);
        setLocalityId(localityId);
        setInstagramProfile(instagramProfile);
    }

    public PlayerModel() {}

    public Integer getPlayerId() {
        return playerId;
    }
    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }
    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public char getGender() {
        return gender;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getEloPoints() {
        return eloPoints;
    }
    public void setEloPoints(int eloPoints) {
        this.eloPoints = eloPoints;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getIsPro() {
        return isPro;
    }
    public void setIsPro(boolean isPro) {
        this.isPro = isPro;
    }

    public Integer getLocalityId() {
        return localityId;
    }
    public void setLocalityId(Integer localityId) {
        this.localityId = localityId;
    }

    public String getInstagramProfile() {
        return instagramProfile;
    }
    public void setInstagramProfile(String instagramProfile) {
        this.instagramProfile = instagramProfile;
    }

}

