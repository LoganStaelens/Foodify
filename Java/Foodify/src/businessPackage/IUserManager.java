package businessPackage;

import java.time.LocalDate;
import java.util.List;

import exceptionPackage.DBConnectionException;
import modelPackage.Address;
import modelPackage.Gender;

public interface IUserManager {
    
    void createNewUser(String firstName, String lastName, Gender gender, String email, LocalDate birthDate, String phoneNumber, Address address, String password) throws DBConnectionException;
    
    public List<String> getGenders() throws DBConnectionException;
}