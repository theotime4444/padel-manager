package main.modelPackage;

public class LocalityModel {
    private Integer localityId;
    private String country;
    private String region;
    private String city;

    public LocalityModel(String country, String region, String city) {
        setCountry(country);
        setRegion(region);
        setCity(city);
    }

    public LocalityModel() {}

    public Integer getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Integer localityId) {
        this.localityId = localityId;
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
