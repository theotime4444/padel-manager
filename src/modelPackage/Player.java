package modelPackage;



    import java.util.Date;
    public class Player {
        private Integer playerID;
        private String lastname;
        private String firstname;
        private Date birthdayDate;
        private char gender;
        private int eloPoints;
        private String phoneNumber;
        private String email;
        private boolean isPro;
        private Integer club;
        private Integer locality;
        private String instagramProfile;

        public Player(String lastname, String firstname, Date birthdayDate,
                      char gender, int eloPoints, String phoneNumber, String email, boolean isPro) {
            setLastname(lastname);
            setFirstname(firstname);
            setBirthdayDate(birthdayDate);
            setGender(gender);
            setEloPoints(eloPoints);
            setPhoneNumber(phoneNumber);
            setEmail(email);
            setPro(isPro);
        }

        public Integer getPlayerID() {
            return playerID;
        }

        public void setPlayerID(Integer playerID) {
            this.playerID = playerID;
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

        public boolean isPro() {
            return isPro;
        }

        public void setPro(boolean isPro) {
            this.isPro = isPro;
        }

        public Integer getClub() {
            return club;
        }

        public void setClub(Integer club) {
            this.club = club;
        }

        public Integer getLocality() {
            return locality;
        }

        public void setLocality(Integer locality) {
            this.locality = locality;
        }

        public String getInstagramProfile() {
            return instagramProfile;
        }

        public void setInstagramProfile(String instagramProfile) {
            this.instagramProfile = instagramProfile;
        }

    }

