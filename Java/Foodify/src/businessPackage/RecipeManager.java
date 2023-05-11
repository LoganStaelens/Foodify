package businessPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dataAccessPackage.IRecipeDBAccess;
import dataAccessPackage.RecipeDBAccess;
import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;
import modelPackage.Ingredient;

public class RecipeManager implements IRecipeManager {

    private IRecipeDBAccess recipeDataAccess;

    public RecipeManager() {
        this.recipeDataAccess = new RecipeDBAccess();
    }

    @Override
    public List<Ingredient> GetAllIngredients() throws DBConnectionException {
        ResultSet result = recipeDataAccess.GetAllIngredients();

        List<Ingredient> data = new ArrayList<Ingredient>();

        try {
            while(result.next()) {
                String ingredientName = result.getString("Ingredient.name");
                int kcal = Integer.parseInt(result.getString("Ingredient.calories"));
                String unit = result.getString("Unit.name");
                String unitAbbreviation = result.getString("Unit.abbreviation");
                int ingredientID = result.getInt("Ingredient.ingredient_id");
                int unitID = result.getInt("Unit.unit_id");
                data.add(new Ingredient(ingredientName, kcal, unit, 0, unitAbbreviation, ingredientID, unitID));
            }

            result.close();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.PREPARED_STATEMENT_EXCEPTION);
        }

        return data;
    }

    @Override
    public List<String> GetDifficulties() throws DBConnectionException {
        ResultSet result = recipeDataAccess.GetDifficulties();

        List<String> data = new ArrayList<String>();

        try {
            while(result.next()) {
                String complexity = result.getString("complexity");
                
                data.add(complexity);
            }

            result.close();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.PREPARED_STATEMENT_EXCEPTION);
        }

        return data;
    }

    @Override
    public List<String> GetTags() throws DBConnectionException {
        ResultSet result = recipeDataAccess.GetTags();

        List<String> data = new ArrayList<String>();

        try {
            while(result.next()) {
                String tag = result.getString("name");
                
                data.add(tag);
            }

            result.close();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.PREPARED_STATEMENT_EXCEPTION);
        }

        return data;
    }
    
}
