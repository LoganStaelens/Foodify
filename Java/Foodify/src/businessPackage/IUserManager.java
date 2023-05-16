package businessPackage;

import java.time.LocalDate;
import java.util.List;

import exceptionPackage.DataFetchException;
import exceptionPackage.HashException;
import exceptionPackage.StringTooLongException;
import modelPackage.Address;
import modelPackage.Country;
import modelPackage.Gender;
import modelPackage.User;

public interface IUserManager {
    
    void createNewUser(String firstName, String lastName, Gender gender, String email, LocalDate birthDate, String phoneNumber, Address address, String password) throws DataFetchException;
    
    LoginResult login(String email, String passwd) throws HashException, DataFetchException, StringTooLongException;

    List<String> getGenders() throws DataFetchException;

    List<Country> getCountries() throws DataFetchException;

    String hashPassword(String passwd) throws HashException;

    boolean verifyPassword(String passwd1hash, String passwd2hash);

    boolean findUserByEmail(String userEmail) throws DataFetchException;

    List<User>findUsersByCountry(String country) throws DataFetchException;
}