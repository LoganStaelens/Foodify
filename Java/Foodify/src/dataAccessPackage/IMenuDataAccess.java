package dataAccessPackage;

import java.sql.ResultSet;

import exceptionPackage.DataFetchException;

public interface IMenuDataAccess {
    
    int createNewMenu(String userID, int year, int week) throws DataFetchException;

    int createNewMenuItem(int menuID, int recipeID, int day) throws DataFetchException;

    boolean hasMenuForUser(String userID, int year, int week) throws DataFetchException;

    ResultSet getCurrentMenuFromUser(String userID, int week, int year) throws DataFetchException;
}
