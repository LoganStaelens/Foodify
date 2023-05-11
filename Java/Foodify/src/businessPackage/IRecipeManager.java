package businessPackage;

import java.util.List;

import exceptionPackage.DBConnectionException;
import modelPackage.Ingredient;
import modelPackage.Recipe;
import modelPackage.RecipeStep;

public interface IRecipeManager {
    List<Ingredient> getAllIngredients() throws DBConnectionException;

    List<String> getDifficulties() throws DBConnectionException;

    List<String> getTags() throws DBConnectionException;

    void createNewRecipe(String title, String complexity, List<String> selectedTags, boolean isVisible, List<Ingredient> ingredients, List<RecipeStep> steps, String creatorFirstName, String creatorLastName) throws DBConnectionException;

    List<Recipe> GetAllRecipes() throws DBConnectionException;
}
