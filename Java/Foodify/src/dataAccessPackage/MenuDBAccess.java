package dataAccessPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;

public class MenuDBAccess implements IMenuDataAccess {
    
    public int createNewMenu(String userID, int year, int week) throws DBConnectionException {
        
        String sql = "INSERT INTO Menu (user, year, week) VALUES (?, ?, ?)";

        PreparedStatement insertStatement;

        try {
            insertStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            
            insertStatement.setString(1, userID);
            insertStatement.setInt(2, year);
            insertStatement.setInt(3, week);
            insertStatement.executeUpdate();

            sql = "SELECT LAST_INSERT_ID() as 'last_id'";
            
            PreparedStatement lastIDStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);

            ResultSet result = lastIDStatement.executeQuery();

            result.next();
            int lastID = result.getInt("last_id");
            result.close();

            return lastID;
        }
        catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    public int createNewMenuItem(int menuID, int recipeID, int day) throws DBConnectionException {

        String sql = "INSERT INTO MenuItem (menu, recipe, day) VALUES (?, ?, ?)";

        PreparedStatement insertStatement;

        try {
            insertStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            
            insertStatement.setInt(1, menuID);
            insertStatement.setInt(2, recipeID);
            insertStatement.setInt(3, day);
            insertStatement.executeUpdate();

            sql = "SELECT LAST_INSERT_ID() as 'last_id'";
            
            PreparedStatement lastIDStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);

            ResultSet result = lastIDStatement.executeQuery();

            result.next();
            int lastID = result.getInt("last_id");
            result.close();

            return lastID;
        }
        catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public boolean hasMenuForUser(String userID, int year, int week) throws DBConnectionException {
        String sql = "SELECT * FROM Menu WHERE user = ? AND year = ? AND week = ?";

        PreparedStatement insertStatement;

        try {
            insertStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            
            insertStatement.setString(1, userID);
            insertStatement.setInt(2, year);
            insertStatement.setInt(3, week);
            ResultSet result = insertStatement.executeQuery();

            return result.next();
        }
        catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public ResultSet getCurrentMenuFromUser(String userID, int week, int year) throws DBConnectionException {
        String sql = "SELECT Menu.year, Menu.week, MenuItem.day, Recipe.title, Recipe.recipe_id FROM Menu INNER JOIN MenuItem On Menu.menu_id = MenuItem.menu INNER JOIN Recipe ON Recipe.recipe_id = MenuItem.recipe WHERE Menu.user = ? AND Menu.week = ? AND Menu.year = ?;";

        PreparedStatement insertStatement;

        try {
            insertStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            
            insertStatement.setString(1, userID);
            insertStatement.setInt(2, week);
            insertStatement.setInt(3, year);

            return insertStatement.executeQuery();
        }
        catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }
}
