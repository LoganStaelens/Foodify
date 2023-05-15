package dataAccessPackage;

import java.sql.ResultSet;

import exceptionPackage.DBConnectionException;

public interface ICountryDataAccess {
    ResultSet getCountries() throws DBConnectionException;
}
