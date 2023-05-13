package businessPackage;

import java.time.LocalDate;

import dataAccessPackage.IUserDBAccess;
import dataAccessPackage.UserDBAccess;
import exceptionPackage.DBConnectionException;
import modelPackage.Address;
import modelPackage.Gender;

public class UserManager implements IUserManager {

    private IUserDBAccess userDataAccess;

    public UserManager() {
        userDataAccess = new UserDBAccess();
    }
    
    public void createNewUser(String firstName, String lastName, Gender gender, String email, LocalDate birthDate, String phoneNumber, Address address, String password) throws DBConnectionException {

    }
}
