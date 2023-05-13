package dataAccessPackage;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;

public class AddressDBAccess implements IAddressDBAccess {
    public int createNewAddress(String street, int city, int number) throws DBConnectionException {
        
        String sql = "INSERT INTO Address (city, street, number) VALUES (?, ?, ?)";

        PreparedStatement insertStatement;

        try {
            insertStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            
            insertStatement.setInt(1, city);
            insertStatement.setString(2, street);
            insertStatement.setInt(3, number);

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
