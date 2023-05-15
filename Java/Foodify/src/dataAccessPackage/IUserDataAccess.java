package dataAccessPackage;

import java.sql.ResultSet;
import java.time.LocalDate;

import exceptionPackage.DBConnectionException;
import modelPackage.Gender;

public interface IUserDataAccess {
    
    ResultSet findUserByEmail(String userEmail) throws DBConnectionException;

    void createNewUser(String uniqueID, boolean isAdmin, String firstName, String lastName, Gender gender, String email, LocalDate birthDate, String phoneNumber, int address, String password) throws DBConnectionException;

    ResultSet getGenders() throws DBConnectionException;

    ResultSet getUsersByCountry(String country) throws DBConnectionException;
}
