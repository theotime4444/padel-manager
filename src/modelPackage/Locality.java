package modelPackage;

public class Locality {
    private Integer localityID;
    private String country;
    private String region;
    private String city;

    public Locality(String country, String region, String city) {
        setCountry(country);
        setRegion(region);
        setCity(city);
    }

    public Integer getLocalityID() {
        return localityID;
    }

    public void setLocalityID(Integer localityID) {
        this.localityID = localityID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
