package dataAccessPackage;

import java.sql.ResultSet;
import java.time.LocalDate;

import exceptionPackage.DataFetchException;
import modelPackage.Gender;

public interface IUserDataAccess {
    
    ResultSet findUserByEmail(String userEmail) throws DataFetchException;

    void createNewUser(String uniqueID, boolean isAdmin, String firstName, String lastName, Gender gender, String email, LocalDate birthDate, String phoneNumber, int address, String password) throws DataFetchException;

    ResultSet getGenders() throws DataFetchException;

    ResultSet getUsersByCountry(String country) throws DataFetchException;
}
