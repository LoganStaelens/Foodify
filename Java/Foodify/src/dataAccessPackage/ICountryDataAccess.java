package dataAccessPackage;

import java.sql.ResultSet;

import exceptionPackage.DataFetchException;

public interface ICountryDataAccess {
    ResultSet getCountries() throws DataFetchException;
}
