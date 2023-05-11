package businessPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import dataAccessPackage.IRecipeDBAccess;
import dataAccessPackage.RecipeDBAccess;
import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;
import modelPackage.Ingredient;
import modelPackage.Recipe;
import modelPackage.RecipeStep;

public class RecipeManager implements IRecipeManager {

    private IRecipeDBAccess recipeDataAccess;

    public RecipeManager() {
        this.recipeDataAccess = new RecipeDBAccess();
    }

    @Override
    public List<Ingredient> getAllIngredients() throws DBConnectionException {
        ResultSet result = recipeDataAccess.getAllIngredients();

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
            throw new DBConnectionException(DBConnectionExceptionTypes.RESULT_SET_EXCEPTION);
        }

        return data;
    }

    @Override
    public List<String> getDifficulties() throws DBConnectionException {
        ResultSet result = recipeDataAccess.getDifficulties();

        List<String> data = new ArrayList<String>();

        try {
            while(result.next()) {
                String complexity = result.getString("complexity");
                
                data.add(complexity);
            }

            result.close();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.RESULT_SET_EXCEPTION);
        }

        return data;
    }

    @Override
    public List<String> getTags() throws DBConnectionException {
        ResultSet result = recipeDataAccess.getTags();

        List<String> data = new ArrayList<String>();

        try {
            while(result.next()) {
                String tag = result.getString("name");
                
                data.add(tag);
            }

            result.close();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.RESULT_SET_EXCEPTION);
        }

        return data;
    }

    @Override
    public void createNewRecipe(String title, String complexity, List<String> selectedTags, boolean isVisible,
            List<Ingredient> ingredients, List<RecipeStep> steps, String creatorFirstName, String creatorLastName)
            throws DBConnectionException {
        
        int recipeID = recipeDataAccess.createNewRecipe(title, complexity, isVisible, creatorFirstName, creatorLastName);
        
        for(String tag : selectedTags) {
            recipeDataAccess.addTagToRecipe(recipeID, tag);
        }

        for(RecipeStep recipeStep : steps) {
            recipeDataAccess.addRecipeStep(recipeID, recipeStep.getStepCount(), recipeStep.getTitle(), recipeStep.getDescription(), recipeStep.getDuration());
        }

        for(Ingredient ingredient : ingredients) {
            recipeDataAccess.addIngredient(recipeID, ingredient.getIngredientID(), ingredient.getQuantity());
        }
    }

    @Override
    public List<Recipe> GetAllRecipes() throws DBConnectionException {
        ResultSet result = recipeDataAccess.getAllIngredients();
        List<Recipe> data = new ArrayList<>();

        try {
            while(result.next()) {
                int recipe_id = result.getInt("recipe_id");
                String complexity = result.getString("complexity");
                boolean isVisible = result.getBoolean("isVisible");
                String title = result.getString("title");
                LocalDate lastUpdate = result.getDate("lastUpdate").toLocalDate();
                String creatorFirstName = result.getString("creatorFirstName");
                String creatorLastName = result.getString("creatorLastName");
                data.add(new Recipe(recipe_id, title, complexity, isVisible, lastUpdate, creatorFirstName, creatorLastName));
            }

            result.close();

            for(Recipe recipe : data) {
                result = recipeDataAccess.getTagsForRecipe(recipe.getRecipeID());

                while(result.next()) {
                    recipe.addTag(result.getString("TagLink.tag"));
                }
                result.close();
            }
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.RESULT_SET_EXCEPTION);
        }
        

        return data;
    }
    
}
