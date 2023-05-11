package businessPackage;

import java.util.List;

import exceptionPackage.DBConnectionException;
import modelPackage.Ingredient;
import modelPackage.RecipeStep;

public interface IRecipeManager {
    List<Ingredient> GetAllIngredients() throws DBConnectionException;

    List<String> GetDifficulties() throws DBConnectionException;

    List<String> GetTags() throws DBConnectionException;

    void CreateNewRecipe(String title, String complexity, List<String> selectedTags, boolean isVisible, List<Ingredient> ingredients, List<RecipeStep> steps) throws DBConnectionException;
}
