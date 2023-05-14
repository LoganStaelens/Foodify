package dataAccessPackage;

import java.sql.ResultSet;

import exceptionPackage.DBConnectionException;
import modelPackage.Recipe;

public interface IRecipeDBAccess {
    ResultSet getAllIngredients() throws DBConnectionException;

    ResultSet getDifficulties() throws DBConnectionException;

    ResultSet getTags() throws DBConnectionException;

    int createNewRecipe(String recipeTitle, String complexity, boolean isVisible, String creatorFirstname, String creatorLastName) throws DBConnectionException;

    void addTagToRecipe(int recipeID, String tag) throws DBConnectionException;

    void addRecipeStep(int recipeID, int stepCount, String title, String description, int duration) throws DBConnectionException;

    void addIngredient(int recipeID, int ingredientID, int amount) throws DBConnectionException;

    ResultSet getAllRecipes() throws DBConnectionException;

    ResultSet getTagsForRecipe(int recipeID) throws DBConnectionException;

    void deleteTagByRecipeID(int recipeID) throws DBConnectionException;

    void deleteIngredientsByRecipeID(int recipeID) throws DBConnectionException;

    void deleteRecipeStepsByRecipeID(int recipeID) throws DBConnectionException;
    
    void deleteRecipeByRecipeID(int recipeID) throws DBConnectionException;

    void modifyRecipe(Recipe newRecipe) throws DBConnectionException;

    ResultSet getIngredientsForRecipe(int recipeID) throws DBConnectionException;

    ResultSet getRecipeStepsForRecipe(int recipeID) throws DBConnectionException;

    ResultSet findRecipeByName(String name) throws DBConnectionException;
}
