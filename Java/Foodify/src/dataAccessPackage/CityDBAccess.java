package dataAccessPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptionPackage.DataFetchException;
import exceptionPackage.DataFetchExceptionTypes;

public class CityDBAccess implements ICityDataAccess {

    public int checkCity(String country, String name, String postCode) throws DataFetchException {
        
        String sql = "SELECT City.city_id, City.country, City.name, City.postCode FROM City WHERE country = ? AND name = ? AND postCode = ?";

        PreparedStatement statement;

        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);

            statement.setString(1, country);
            statement.setString(2, name);
            statement.setString(3, postCode);
            
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("City.city_id");
            }
            else {
                return -1;
            }
        }
        catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.CONNECTION_EXCEPTION);
        }
    }
    
    public int createNewCity(String country, String name, String postCode) throws DataFetchException {

        String sql = "INSERT INTO City (country, name, postCode) VALUES (?, ?, ?)";

        PreparedStatement insertStatement;

        try {
            insertStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);

            insertStatement.setString(1, country);
            insertStatement.setString(2, name);
            insertStatement.setString(3, postCode);
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
            throw new DataFetchException(DataFetchExceptionTypes.CONNECTION_EXCEPTION);
        }
    }
}
