package dataAccessPackage;

import java.sql.ResultSet;

import exceptionPackage.DBConnectionException;

public interface ICountryDBAccess {
    ResultSet getCountries() throws DBConnectionException;
}
