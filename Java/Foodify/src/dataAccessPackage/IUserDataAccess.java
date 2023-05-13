package dataAccessPackage;

import java.sql.ResultSet;

import exceptionPackage.DBConnectionException;

public interface IUserDataAccess {
    ResultSet findUserByEmail(String userEmail) throws DBConnectionException;
}
