package modelPackage;

import exceptionPackage.StringTooLongException;

public class Address {
    private static final int STREET_MAX_LENGTH = 128;

    private City city;
    private String street;

    public Address(City city, String street) throws StringTooLongException {
        setStreet(street);
    }

    public void setStreet(String street) throws StringTooLongException {
        if(street.length() > STREET_MAX_LENGTH) 
            throw new StringTooLongException("La rue", street.length(), STREET_MAX_LENGTH);  
        else
            this.street = street;
    }

    public City getCity() {
        return this.city;
    }

    public String getStreet() {
        return this.street;
    }
}
