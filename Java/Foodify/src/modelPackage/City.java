package modelPackage;

import exceptionPackage.StringTooLongException;

public class City {
    private static final int NAME_MAX_LENGTH = 128;
    private static final int POSTCODE_MAX_LENGTH = 128;

    private Country country;
    private int cityID;
    private String name;
    private String postCode;

    public City(int cityID, String name, String postCode, Country country) throws StringTooLongException {
        this.country = country;
        this.cityID = cityID;
        setName(name);
        setPostCode(postCode);
    }

    public City(String name, String postCode, Country country) throws StringTooLongException {
        this.country = country;
        this.cityID = 0;
        setName(name);
        setPostCode(postCode);
    }

    public City(String name, String postCode, String country) throws StringTooLongException {
        this.country = new Country(country);
        this.cityID = 0;
        setName(name);
        setPostCode(postCode);
    }

    private void setName(String name) throws StringTooLongException {
        if(name.length() > NAME_MAX_LENGTH) 
            throw new StringTooLongException("Le nom de la ville", name.length(), NAME_MAX_LENGTH);  
        else
            this.name = name;
    }

    private void setPostCode(String postCode) throws StringTooLongException {
        if(postCode.length() > POSTCODE_MAX_LENGTH) 
            throw new StringTooLongException("Le code postal", postCode.length(), POSTCODE_MAX_LENGTH);  
        else
            this.postCode = postCode;
    }

    public Country getCountry() {
        return this.country;
    }

    public String getName() {
        return this.name;
    }

    public String getPostCode() {
        return this.postCode;
    }

    public int getCityID() {
        return this.cityID;
    }
}
