package dataAccessPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;

public class RecipeDBAccess implements IRecipeDBAccess {
    

    @Override
    public ResultSet GetAllIngredients() throws DBConnectionException {

        String sql = "SELECT Ingredient.ingredient_id, Ingredient.name, Ingredient.calories, Unit.unit_id, Unit.name, Unit.abbreviation FROM Ingredient INNER Join Unit On Ingredient.unit = Unit.unit_id;";

        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }}

    @Override
    public ResultSet GetDifficulties() throws DBConnectionException {
        String sql = "SELECT * from complexity";

        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public ResultSet GetTags() throws DBConnectionException {
        String sql = "SELECT * from tag";

        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

}
