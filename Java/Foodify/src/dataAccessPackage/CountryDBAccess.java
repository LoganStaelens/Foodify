package dataAccessPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptionPackage.DataFetchException;
import exceptionPackage.DataFetchExceptionTypes;

public class CountryDBAccess implements ICountryDataAccess {

    @Override
    public ResultSet getCountries() throws DataFetchException {
        String sql = "SELECT * FROM Country";

        PreparedStatement statement;

        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            
            return statement.executeQuery();
        }
        catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.CONNECTION_EXCEPTION);
        }
    }
    
}
