package modelPackage;

import exceptionPackage.StringTooLongException;

public class Address {
    private static final int STREET_MAX_LENGTH = 128;

    private City city;
    private int addressID;
    private String street;
    private int number;

    public Address(int addressID, String street, City city, int number) throws StringTooLongException {
        this.addressID = addressID;
        setStreet(street);
        this.city = city;
        this.number = number;
    }

    public Address(String street, int number, City city) throws StringTooLongException {
        this.addressID = 0;
        setStreet(street);
        this.city = city;
        this.number = number;
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

    public int getNumber() {
        return this.number;
    }
}
