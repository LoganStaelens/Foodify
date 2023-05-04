package dataAccessPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDBAccess implements IUserDataAccess{

    @Override
    public ResultSet FindUserByEmail(String userEmail) {

        String sql = "select unique_id, isAdmin, email, password from user where email = ?";

        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        
            statement.setString(1, userEmail);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO: Throw new error
        }
        return null;
    }
    
}
