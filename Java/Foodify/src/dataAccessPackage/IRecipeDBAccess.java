package dataAccessPackage;

import java.sql.ResultSet;

import exceptionPackage.DBConnectionException;

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
}
