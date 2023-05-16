package dataAccessPackage;

import exceptionPackage.DataFetchException;

public interface IAddressDataAccess {
    
    int createNewAddress(String street, int city, int number) throws DataFetchException;

    int checkAddress(int city, String street, int number) throws DataFetchException;
}