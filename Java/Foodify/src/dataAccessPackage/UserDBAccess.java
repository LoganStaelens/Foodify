package dataAccessPackage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;
import modelPackage.Gender;

public class UserDBAccess implements IUserDataAccess {

    @Override
    public ResultSet findUserByEmail(String userEmail) throws DBConnectionException  {

        String sql = "select User.unique_id, User.gender, User.isAdmin, User.firstName, User.lastName, User.email, User.password, User.birthDate, User.phoneNumber, Address.address_id, Address.street, Address.number, City.city_id, City.name, City.postCode, City.country from User INNER JOIN Address ON User.address = Address.address_id INNER JOIN City ON Address.city = City.city_id where email = ?";
        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, userEmail);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    public void createNewUser(String uniqueID, boolean isAdmin, String firstName, String lastName, Gender gender, String email, LocalDate birthDate, String phoneNumber, int address, String password) throws DBConnectionException {
        
        String sql = "INSERT INTO User (unique_id, address, gender, isAdmin, firstName, lastName, email, password, birthDate, phoneNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement insertStatement;

        try {
            insertStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);

            insertStatement.setString(1, uniqueID);
            insertStatement.setInt(2, address);
            insertStatement.setString(3, gender.toString().toLowerCase());
            insertStatement.setBoolean(4, isAdmin);
            insertStatement.setString(5, firstName);
            insertStatement.setString(6, lastName);
            
            insertStatement.setString(7, email);
            insertStatement.setString(8, password);
            insertStatement.setDate(9, Date.valueOf(birthDate));
            
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                insertStatement.setString(10, null);
            }
            else {
                insertStatement.setString(10, phoneNumber);
            }

            insertStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    public ResultSet getGenders() throws DBConnectionException {
        
        String sql = "SELECT Gender.gender from Gender;";

        PreparedStatement statement;
        
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            return statement.executeQuery();
        } 
        catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public ResultSet getUsersByCountry(String country) throws DBConnectionException {
        String sql = "SELECT User.unique_id, User.gender, User.isAdmin, User.firstName, User.lastName, User.email, User.birthDate, User.phoneNumber, Address.address_id, Address.street, Address.number, City.city_id, City.name, City.postCode, City.country FROM User INNER JOIN Address ON User.address = Address.address_id INNER JOIN City ON Address.city = City.city_id INNER JOIN Country ON City.country = Country.name where Country.name = ?;";

        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, country);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }
    
}
