package dataAccessPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;

public class UserDBAccess implements IUserDataAccess{

    @Override
    public ResultSet FindUserByEmail(String userEmail) throws DBConnectionException  {

        String sql = "select unique_id, isAdmin, email, password from user where email = ?";

        
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
