package businessPackage;

import java.time.LocalDate;
import java.util.List;

import exceptionPackage.DBConnectionException;
import exceptionPackage.HashException;
import modelPackage.Address;
import modelPackage.Country;
import modelPackage.Gender;

public interface IUserManager {
    
    void createNewUser(String firstName, String lastName, Gender gender, String email, LocalDate birthDate, String phoneNumber, Address address, String password) throws DBConnectionException;
    
    List<String> getGenders() throws DBConnectionException;

    List<Country> getCountries() throws DBConnectionException;

    String hashPassword(String passwd) throws HashException;

    boolean verifyPassword(String passwd1hash, String passwd2hash);

}