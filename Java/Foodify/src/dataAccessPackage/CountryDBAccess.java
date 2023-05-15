package dataAccessPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;

public class CountryDBAccess implements ICountryDataAccess {

    @Override
    public ResultSet getCountries() throws DBConnectionException {
        String sql = "SELECT * FROM Country";

        PreparedStatement statement;

        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            
            return statement.executeQuery();
        }
        catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }
    
}
