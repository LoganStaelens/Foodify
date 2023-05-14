package businessPackage;

import java.util.List;

import exceptionPackage.DBConnectionException;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import modelPackage.Ingredient;
import modelPackage.Recipe;
import modelPackage.RecipeStep;

public interface IRecipeManager {
    List<Ingredient> getAllIngredients() throws DBConnectionException;

    List<String> getDifficulties() throws DBConnectionException;

    List<String> getTags() throws DBConnectionException;

    void createNewRecipe(String title, String complexity, List<String> selectedTags, boolean isVisible, List<Ingredient> ingredients, List<RecipeStep> steps, String creatorFirstName, String creatorLastName) throws DBConnectionException;

    List<Recipe> getAllRecipes() throws DBConnectionException;

    FilteredList<Recipe> filterListByTitle(ObservableList<Recipe> recipes, String filter);

    void deleteRecipe(Recipe recipe) throws DBConnectionException;

    void modifyRecipe(Recipe newRecipe) throws DBConnectionException;
    
    List<Ingredient> getIngredientsForRecipe(Recipe recipe) throws DBConnectionException;

    List<RecipeStep> getRecipeStepsForRecipe(Recipe recipe) throws DBConnectionException;

    Recipe findRecipeByName(String name) throws DBConnectionException;

    Recipe findRecipeByTag(String tag) throws DBConnectionException;
}
