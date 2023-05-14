package dataAccessPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;

public class MenuDBAccess {
    
    public int createNewMenu(String user, int year, int week) throws DBConnectionException {
        
        String sql = "INSERT INTO Menu (user, year, week) VALUES (?, ?, ?)";

        PreparedStatement insertStatement;

        try {
            insertStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            
            insertStatement.setString(1, user);
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
            e.printStackTrace();
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    public int createNewMenuItem(int menuID, int recipeID, int day) throws DBConnectionException {

        String sql = "INSERT INTO MenuItem (menuID, recipeID, day) VALUES (?, ?, ?)";

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
            e.printStackTrace();
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }
}
