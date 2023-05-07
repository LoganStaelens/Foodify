package dataAccessPackage;

import java.sql.ResultSet;

import exceptionPackage.DBConnectionException;

public interface IUserDataAccess {
    ResultSet FindUserByEmail(String userEmail) throws DBConnectionException;
}
