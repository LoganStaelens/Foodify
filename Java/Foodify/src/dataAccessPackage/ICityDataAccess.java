package dataAccessPackage;

import exceptionPackage.DataFetchException;

public interface ICityDataAccess {

    int createNewCity(String country, String name, String postCode) throws DataFetchException;

    int checkCity(String country, String name, String postCode) throws DataFetchException;

}
