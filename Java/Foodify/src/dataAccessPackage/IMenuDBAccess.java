package dataAccessPackage;

import exceptionPackage.DBConnectionException;

public interface IMenuDBAccess {
    
    public int createNewMenu(String user, int year, int week) throws DBConnectionException;

    public int createNewMenuItem(int menuID, int recipeID, int day) throws DBConnectionException;
}
