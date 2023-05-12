package dataAccessPackage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;
import modelPackage.Recipe;

public class RecipeDBAccess implements IRecipeDBAccess {
    

    @Override
    public ResultSet getAllIngredients() throws DBConnectionException {

        String sql = "SELECT Ingredient.ingredient_id, Ingredient.name, Ingredient.calories, Unit.unit_id, Unit.name, Unit.abbreviation FROM Ingredient INNER Join Unit On Ingredient.unit = Unit.unit_id;";

        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }}

    @Override
    public ResultSet getDifficulties() throws DBConnectionException {
        String sql = "SELECT * from complexity;";

        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public ResultSet getTags() throws DBConnectionException {
        String sql = "SELECT * from tag;";

        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public int createNewRecipe(String recipeTitle, String complexity, boolean isVisible, String creatorFirstname,
            String creatorLastName) throws DBConnectionException {
        String sql = "INSERT INTO Recipe (title, complexity, isVisible, lastUpdate, creatorFirstName, creatorLastName) VALUES (?, ?, ?, NOW(), ?, ?);";

        PreparedStatement insertStatement;
        try {
            insertStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            insertStatement.setString(1, recipeTitle);
            insertStatement.setString(2, complexity);
            insertStatement.setBoolean(3, isVisible);

            if(creatorFirstname == null || creatorFirstname.isEmpty()) 
                insertStatement.setString(4, null);
            else 
                insertStatement.setString(4, creatorFirstname);
         

            if(creatorLastName == null || creatorLastName.isEmpty()) 
                insertStatement.setString(5, null);       
            else 
                insertStatement.setString(5, creatorLastName);
            
            insertStatement.executeUpdate();



            sql = "SELECT LAST_INSERT_ID() as 'last_id'";
            
            PreparedStatement lastIDStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);

            ResultSet result = lastIDStatement.executeQuery();


            result.next();

            int last_id = result.getInt("last_id");

            result.close();

            return last_id;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public void addTagToRecipe(int recipeID, String tag) throws DBConnectionException {
        String sql = "INSERT INTO TagLink (recipe, tag) VALUES (?, ?);";
        
        PreparedStatement insertStatement;
        try {
            insertStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            insertStatement.setInt(1, recipeID);
            insertStatement.setString(2, tag);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public void addRecipeStep(int recipeID, int stepCount, String title, String description, int duration)
            throws DBConnectionException {
        String sql = "INSERT INTO RecipeStep (recipe, stepCount, title, description, duration) VALUES (?, ?, ?, ?, ?);";
        
        PreparedStatement insertStatement;
        try {
            insertStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            insertStatement.setInt(1, recipeID);
            insertStatement.setInt(2, stepCount);
            insertStatement.setString(3, title);
            insertStatement.setString(4, description);
            insertStatement.setInt(5, duration);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public void addIngredient(int recipeID, int ingredientID, int amount) throws DBConnectionException {
        String sql = "INSERT INTO IngredientStack (recipe, ingredient, amount) VALUES (?, ?, ?);";
        
        PreparedStatement insertStatement;
        try {
            insertStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            insertStatement.setInt(1, recipeID);
            insertStatement.setInt(2, ingredientID);
            insertStatement.setInt(3, amount);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public ResultSet getAllRecipes() throws DBConnectionException {
        String sql = "SELECT * from Recipe;";

        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public ResultSet getTagsForRecipe(int recipeID) throws DBConnectionException {
        String sql = "SELECT Recipe.recipe_id, TagLink.tag from Recipe INNER JOIN TagLink On Recipe.recipe_id = TagLink.recipe Where Recipe.recipe_id = ?;";

        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, recipeID);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public void deleteTagByRecipeID(int recipeID) throws DBConnectionException {
        String sql = "DELETE FROM TagLink Where recipe = ?";

        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, recipeID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public void deleteIngredientsByRecipeID(int recipeID) throws DBConnectionException {
        String sql = "DELETE FROM IngredientStack Where recipe = ?";

        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, recipeID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public void deleteRecipeStepsByRecipeID(int recipeID) throws DBConnectionException {
        String sql = "DELETE FROM RecipeStep Where recipe = ?";

        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, recipeID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public void deleteRecipeByRecipeID(int recipeID) throws DBConnectionException {
        String sql = "DELETE FROM Recipe Where recipe_id = ?";

        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, recipeID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

    @Override
    public void modifyRecipe(Recipe newRecipe) throws DBConnectionException {
        String sql = "UPDATE Recipe SET complexity = ?, isVisible = ?, title = ?, lastUpdate = ?, creatorFirstName = ?, creatorLastName = ? Where recipe_id = ?";

        PreparedStatement statement;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, newRecipe.getComplexity());
            statement.setBoolean(2, newRecipe.getIsVisible());
            statement.setString(3, newRecipe.getTitle());
            statement.setDate(4, Date.valueOf(newRecipe.getLastUpdate()));
            
            if(newRecipe.getCreatorFirstName() == null  || newRecipe.getCreatorFirstName().isEmpty()) 
                statement.setString(5, null);
            else 
                statement.setString(5, newRecipe.getCreatorFirstName());
    
            if(newRecipe.getCreatorLastName() == null || newRecipe.getCreatorLastName().isEmpty()) 
                statement.setString(6, null);       
            else 
                statement.setString(6, newRecipe.getCreatorLastName());
            statement.setInt(7, newRecipe.getRecipeID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.CONNECTION_EXCEPTION);
        }
    }

}
