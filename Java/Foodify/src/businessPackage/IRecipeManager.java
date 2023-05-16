package businessPackage;

import java.util.List;

import exceptionPackage.DataFetchException;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import modelPackage.Ingredient;
import modelPackage.Recipe;
import modelPackage.RecipeStep;

public interface IRecipeManager {
    List<Ingredient> getAllIngredients() throws DataFetchException;

    List<String> getDifficulties() throws DataFetchException;

    List<String> getTags() throws DataFetchException;

    void createNewRecipe(String title, String complexity, List<String> selectedTags, boolean isVisible, List<Ingredient> ingredients, List<RecipeStep> steps, String creatorFirstName, String creatorLastName) throws DataFetchException;

    List<Recipe> getAllRecipes() throws DataFetchException;

    FilteredList<Recipe> filterListByTitle(ObservableList<Recipe> recipes, String filter);

    void deleteRecipe(Recipe recipe) throws DataFetchException;

    void modifyRecipe(Recipe newRecipe) throws DataFetchException;
    
    List<Ingredient> getIngredientsForRecipe(Recipe recipe) throws DataFetchException;

    List<RecipeStep> getRecipeStepsForRecipe(Recipe recipe) throws DataFetchException;

    Recipe findRecipeByName(String name) throws DataFetchException;

    Recipe findRecipeByTag(String tag) throws DataFetchException;

    Recipe findRecipeById(int recipeID) throws DataFetchException;
}
