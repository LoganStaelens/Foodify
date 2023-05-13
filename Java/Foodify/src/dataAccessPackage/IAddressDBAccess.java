package dataAccessPackage;

import exceptionPackage.DBConnectionException;

public interface IAddressDBAccess {
    
    int createNewAddress(String street, int city, int number) throws DBConnectionException;

    boolean checkAddress(int city, String street, int number) throws DBConnectionException;
}