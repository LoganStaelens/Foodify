package dataAccessPackage;

import java.sql.ResultSet;

import exceptionPackage.DBConnectionException;

public interface IIngredientDataAccess {
    ResultSet GetAllIngredients() throws DBConnectionException;
}
