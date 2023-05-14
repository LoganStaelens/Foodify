package dataAccessPackage;

import java.sql.ResultSet;
import java.time.LocalDate;

import exceptionPackage.DBConnectionException;
import modelPackage.Gender;

public interface IUserDBAccess {
    
    ResultSet findUserByEmail(String userEmail) throws DBConnectionException;

    void createNewUser(String uniqueID, boolean isAdmin, String firstName, String lastName, Gender gender, String email, LocalDate birthDate, String phoneNumber, int address, String password) throws DBConnectionException;

    public ResultSet getGenders() throws DBConnectionException;
}