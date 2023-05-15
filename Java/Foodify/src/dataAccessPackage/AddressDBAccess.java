package dataAccessPackage;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;

public class AddressDBAccess implements IAddressDataAccess {

    public int checkAddress(int city, String street, int number) throws DBConnectionException {
        
        String sql = "SELECT Address.address_id, Address.city, Address.street, Address.number FROM Address WHERE city = ? AND street = ? AND number = ?";

        PreparedStatement statement;

        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);

            statement.setInt(1, city);
            statement.setString(2, street);
            statement.setInt(3, number);
            
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("Address.address_id");
            }
            else {
                return -1;
            }
        }
        catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    public int createNewAddress(String street, int city, int number) throws DBConnectionException {
        
        String sql = "INSERT INTO Address (city, street, number) VALUES (?, ?, ?)";

        PreparedStatement insertStatement;

        try {
            insertStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            
            insertStatement.setInt(1, city);
            insertStatement.setString(2, street);
            insertStatement.setInt(3, number);
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
}
