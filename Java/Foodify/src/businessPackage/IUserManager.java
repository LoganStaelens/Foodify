package businessPackage;

import java.time.LocalDate;
import java.util.List;

import exceptionPackage.DBConnectionException;
import exceptionPackage.HashException;
import exceptionPackage.StringTooLongException;
import modelPackage.Address;
import modelPackage.Country;
import modelPackage.Gender;
import modelPackage.User;

public interface IUserManager {
    
    void createNewUser(String firstName, String lastName, Gender gender, String email, LocalDate birthDate, String phoneNumber, Address address, String password) throws DBConnectionException;
    
    LoginResult login(String email, String passwd) throws HashException, DBConnectionException, StringTooLongException;

    List<String> getGenders() throws DBConnectionException;

    List<Country> getCountries() throws DBConnectionException;

    String hashPassword(String passwd) throws HashException;

    boolean verifyPassword(String passwd1hash, String passwd2hash);

    List<User>findUsersByCountry(String country) throws DBConnectionException;
}