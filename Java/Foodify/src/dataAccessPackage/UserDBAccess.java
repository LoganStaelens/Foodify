package dataAccessPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;

public class UserDBAccess implements IUserDataAccess{

    @Override
    public ResultSet findUserByEmail(String userEmail) throws DBConnectionException  {

        String sql = "select User.unique_id, User.gender, User.isAdmin, User.firstName, User.lastName, User.email, User.password, User.birthDate, User.phoneNumber, Address.address_id, Address.street, Address.number, City.city_id, City.name, City.postCode, City.country from User INNER JOIN Address ON User.address = Address.address_id INNER JOIN City ON Address.city = City.city_id where email = ?;";

        
        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, userEmail);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }
    
}
