package modelPackage;

import java.time.LocalDate;
import java.util.UUID;

import exceptionPackage.StringTooLongException;

public class User {
    private static final int FIRST_NAME_MAX_LENGTH = 64;
    private static final int LAST_NAME_MAX_LENGTH = 64;
    private static final int EMAIL_MAX_LENGTH = 128;
    private static final int PHONE_NUMBER_MAX_LENGTH = 32;

    private UUID uniqueID;
    private Gender gender;
    private boolean isAdmin; 
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private String phoneNumber;
    private Address address;

    public User(UUID uniqueID, Gender gender, boolean isAdmin, String firstName, String lastName, String email,
            LocalDate birthDate, String phoneNumber, Address address) throws StringTooLongException {
        this.uniqueID = uniqueID;
        this.gender = gender;
        this.isAdmin = isAdmin;
        setFirstName(firstName);
        setLastName(lastName);;
        setEmail(email);
        setBirthDate(birthDate);
        setPhoneNumber(phoneNumber);
        this.address = address;
    }

    public void setFirstName(String firstName) throws StringTooLongException {
        if(firstName.length() > FIRST_NAME_MAX_LENGTH) 
            throw new StringTooLongException("Le prénom", firstName.length(), FIRST_NAME_MAX_LENGTH);  
        else
            this.firstName = firstName;    
    }

    public void setLastName(String lastName) throws StringTooLongException {
        if(lastName.length() > LAST_NAME_MAX_LENGTH) 
            throw new StringTooLongException("Le nom de famille", lastName.length(), LAST_NAME_MAX_LENGTH);  
        else
            this.lastName = lastName;  
    }

    public void setEmail(String email) throws StringTooLongException {
        if(email.length() > EMAIL_MAX_LENGTH) 
            throw new StringTooLongException("L'email'", email.length(), EMAIL_MAX_LENGTH);  
        else
            this.email = email;  
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhoneNumber(String phoneNumber) throws StringTooLongException {
        if(hasPhoneNumber() && phoneNumber.length() > PHONE_NUMBER_MAX_LENGTH) 
            throw new StringTooLongException("Le numéro de téléphone'", phoneNumber.length(), PHONE_NUMBER_MAX_LENGTH);  
        else
            this.phoneNumber = phoneNumber;
    }

    public boolean hasPhoneNumber() {
        return phoneNumber != null;
    }

    public UUID getUniqueID() {
        return uniqueID;
    }

    public String getUserID() {
        return uniqueID.toString();
    }

    public String getStreet() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.address.getStreet())
            .append(" ")
            .append(this.address.getNumber());

        return builder.toString();
    }

    public String getCity() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.address.getCity().getPostCode())
            .append(" ")
            .append(this.address.getCity().getName());

        return builder.toString();
    }

    public Gender getGender() {
        return gender;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public Address getAddress() {
        return address;
    }

    
}
