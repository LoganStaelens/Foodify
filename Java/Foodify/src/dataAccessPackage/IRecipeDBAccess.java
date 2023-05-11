package dataAccessPackage;

import java.sql.ResultSet;

import exceptionPackage.DBConnectionException;

public interface IRecipeDBAccess {
    ResultSet GetAllIngredients() throws DBConnectionException;

    ResultSet GetDifficulties() throws DBConnectionException;

    ResultSet GetTags() throws DBConnectionException;
}
