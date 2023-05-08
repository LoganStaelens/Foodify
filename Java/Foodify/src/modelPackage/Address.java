package modelPackage;

import exceptionPackage.StringTooLongException;

public class Address {
    private static final int STREET_MAX_LENGTH = 128;

    private City city;
    private int addressID;
    private String street;

    public Address(int addressID, String street, City city) throws StringTooLongException {
        this.addressID = addressID;
        setStreet(street);
        this.city = city;
    }

    public void setStreet(String street) throws StringTooLongException {
        if(street.length() > STREET_MAX_LENGTH) 
            throw new StringTooLongException("La rue", street.length(), STREET_MAX_LENGTH);  
        else
            this.street = street;
    }

    public int getAddressID() {
        return this.addressID;
    }

    public City getCity() {
        return this.city;
    }

    public String getStreet() {
        return this.street;
    }
}
