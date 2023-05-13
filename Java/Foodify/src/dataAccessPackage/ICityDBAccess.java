package dataAccessPackage;

import exceptionPackage.DBConnectionException;

public interface ICityDBAccess {
    int createNewCity(String country, String name, String postCode) throws DBConnectionException;
}
