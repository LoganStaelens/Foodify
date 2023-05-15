package dataAccessPackage;

import exceptionPackage.DBConnectionException;

public interface ICityDataAccess {

    int createNewCity(String country, String name, String postCode) throws DBConnectionException;

    int checkCity(String country, String name, String postCode) throws DBConnectionException;

}
