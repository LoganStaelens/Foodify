package dataAccessPackage;

import java.sql.ResultSet;

import exceptionPackage.DataFetchException;
import modelPackage.Recipe;

public interface IRecipeDataAccess {
    ResultSet getAllIngredients() throws DataFetchException;

    ResultSet getDifficulties() throws DataFetchException;

    ResultSet getTags() throws DataFetchException;

    int createNewRecipe(String recipeTitle, String complexity, boolean isVisible, String creatorFirstname, String creatorLastName) throws DataFetchException;

    void addTagToRecipe(int recipeID, String tag) throws DataFetchException;

    void addRecipeStep(int recipeID, int stepCount, String title, String description, int duration) throws DataFetchException;

    void addIngredient(int recipeID, int ingredientID, int amount) throws DataFetchException;

    ResultSet getAllRecipes() throws DataFetchException;

    ResultSet getTagsForRecipe(int recipeID) throws DataFetchException;

    void deleteTagByRecipeID(int recipeID) throws DataFetchException;

    void deleteIngredientsByRecipeID(int recipeID) throws DataFetchException;

    void deleteRecipeStepsByRecipeID(int recipeID) throws DataFetchException;
    
    void deleteRecipeByRecipeID(int recipeID) throws DataFetchException;

    void modifyRecipe(Recipe newRecipe) throws DataFetchException;

    ResultSet getIngredientsForRecipe(int recipeID) throws DataFetchException;

    ResultSet getRecipeStepsForRecipe(int recipeID) throws DataFetchException;

    ResultSet findRecipeByName(String name) throws DataFetchException;

    ResultSet findRecipesByTag(String tag) throws DataFetchException;

    ResultSet findRecipeById(int recipeID) throws DataFetchException;
}
