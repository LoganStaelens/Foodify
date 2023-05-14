package dataAccessPackage;

import java.sql.ResultSet;

import exceptionPackage.DBConnectionException;

public interface IMenuDBAccess {
    
    int createNewMenu(String userID, int year, int week) throws DBConnectionException;

    int createNewMenuItem(int menuID, int recipeID, int day) throws DBConnectionException;

    boolean hasMenuForUser(String userID, int year, int week) throws DBConnectionException;

    ResultSet getCurrentMenuFromUser(String userID) throws DBConnectionException;
}
