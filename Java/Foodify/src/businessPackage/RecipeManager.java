package businessPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import dataAccessPackage.IRecipeDataAccess;
import dataAccessPackage.RecipeDBAccess;
import exceptionPackage.DataFetchException;
import exceptionPackage.DataFetchExceptionTypes;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import modelPackage.Ingredient;
import modelPackage.Recipe;
import modelPackage.RecipeStep;

public class RecipeManager implements IRecipeManager {

    private IRecipeDataAccess recipeDataAccess;

    public RecipeManager() {
        this.recipeDataAccess = new RecipeDBAccess();
    }

    @Override
    public List<Ingredient> getAllIngredients() throws DataFetchException {
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
            throw new DataFetchException(DataFetchExceptionTypes.RESULT_SET_EXCEPTION);
        }

        return data;
    }

    @Override
    public List<String> getDifficulties() throws DataFetchException {
        ResultSet result = recipeDataAccess.getDifficulties();

        List<String> data = new ArrayList<String>();

        try {
            while(result.next()) {
                String complexity = result.getString("complexity");
                
                data.add(complexity);
            }

            result.close();
        } catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.RESULT_SET_EXCEPTION);
        }

        return data;
    }

    @Override
    public List<String> getTags() throws DataFetchException {
        ResultSet result = recipeDataAccess.getTags();

        List<String> data = new ArrayList<String>();

        try {
            while(result.next()) {
                String tag = result.getString("name");
                
                data.add(tag);
            }

            result.close();
        } catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.RESULT_SET_EXCEPTION);
        }

        return data;
    }

    @Override
    public void createNewRecipe(String title, String complexity, List<String> selectedTags, boolean isVisible,
            List<Ingredient> ingredients, List<RecipeStep> steps, String creatorFirstName, String creatorLastName)
            throws DataFetchException {
        
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
    public List<Recipe> getAllRecipes() throws DataFetchException {
        ResultSet result = recipeDataAccess.getAllRecipes();
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
            throw new DataFetchException(DataFetchExceptionTypes.RESULT_SET_EXCEPTION);
        }
    
        return data;
    }

    @Override
    public FilteredList<Recipe> filterListByTitle(ObservableList<Recipe> recipes, String filter) {
        FilteredList<Recipe> filteredList = new FilteredList<>(recipes);
        filteredList.setPredicate(p -> p.getTitle().toLowerCase().contains(filter.toLowerCase().trim()));
        return filteredList;
    }

    @Override
    public void deleteRecipe(Recipe recipe) throws DataFetchException {
        this.recipeDataAccess.deleteTagByRecipeID(recipe.getRecipeID());
        this.recipeDataAccess.deleteIngredientsByRecipeID(recipe.getRecipeID());
        this.recipeDataAccess.deleteRecipeStepsByRecipeID(recipe.getRecipeID());
        this.recipeDataAccess.deleteRecipeByRecipeID(recipe.getRecipeID());
    }

    @Override
    public void modifyRecipe(Recipe newRecipe) throws DataFetchException {
        this.recipeDataAccess.modifyRecipe(newRecipe);

        this.recipeDataAccess.deleteTagByRecipeID(newRecipe.getRecipeID());
        
        ListIterator<String> tagsIterator = newRecipe.getTagsItterator(); 
        while(tagsIterator.hasNext()) {
            recipeDataAccess.addTagToRecipe(newRecipe.getRecipeID(), tagsIterator.next());
        }
    }

    @Override
    public List<Ingredient> getIngredientsForRecipe(Recipe recipe) throws DataFetchException {
     
        ResultSet result = recipeDataAccess.getIngredientsForRecipe(recipe.getRecipeID());
        List<Ingredient> data = new ArrayList<>();

        try {
            while(result.next()) {
                String ingredientName = result.getString("ingredient");
                int kcal = result.getInt("kcal");
                int amount = result.getInt("amount");
                String unit = result.getString("unit");
                data.add(new Ingredient(ingredientName, kcal, unit, amount));
            }

            result.close();
          
        } catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.RESULT_SET_EXCEPTION);
        }
        
        return data;
    }

    @Override
    public List<RecipeStep> getRecipeStepsForRecipe(Recipe recipe) throws DataFetchException {
        ResultSet result = recipeDataAccess.getRecipeStepsForRecipe(recipe.getRecipeID());
        List<RecipeStep> data = new ArrayList<>();

        try {
            while(result.next()) {
                String title = result.getString("title");
                int duration = result.getInt("duration");
                int stepCount = result.getInt("stepCount");
                String description = result.getString("description");
                RecipeStep step = new RecipeStep(title, description, duration);
                step.setStepCount(stepCount);
                data.add(step);
            }

            result.close();
          
        } catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.RESULT_SET_EXCEPTION);
        }
        
        return data;
    }
    
    public Recipe findRecipeByName(String name) throws DataFetchException {

        ResultSet result = recipeDataAccess.findRecipeByName(name);

        try {
            if (result.next()) {
                Recipe recipe = new Recipe(result.getInt("recipe_id"), 
                                result.getString("title"), 
                                result.getString("complexity"), 
                                result.getBoolean("isVisible"), 
                                result.getDate("lastUpdate").toLocalDate(), 
                                result.getString("creatorFirstName"), 
                                result.getString("creatorLastName"));
            
            result.close();
            return recipe;
            }


        }
        catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.RESULT_SET_EXCEPTION);
        }
        return null;
    }

    public Recipe findRecipeByTag(String tag) throws DataFetchException {
        
        ResultSet result = recipeDataAccess.findRecipesByTag(tag);
        List<Integer> data = new ArrayList<>();

        try {
            
            while(result.next()) {
                data.add(result.getInt("recipe"));
            }
            
            result.close();

            if(data.size() > 0) {
                int index = (int)(Math.random() * data.size());

                result = recipeDataAccess.findRecipeById(data.get(index));
    
                if (result.next()) {
                    Recipe recipe = new Recipe(result.getInt("recipe_id"), 
                                    result.getString("title"), 
                                    result.getString("complexity"), 
                                    result.getBoolean("isVisible"), 
                                    result.getDate("lastUpdate").toLocalDate(), 
                                    result.getString("creatorFirstName"), 
                                    result.getString("creatorLastName"));
                    result.close();
                    return recipe;
                }
            }

        }
        catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.RESULT_SET_EXCEPTION);
        }
        return null;
    }

    public Recipe findRecipeById(int recipeID) throws DataFetchException {
        
        ResultSet result = recipeDataAccess.findRecipeById(recipeID);
        
        try {
            if (result.next()) {
                Recipe recipe = new Recipe(result.getInt("recipe_id"), 
                                result.getString("title"), 
                                result.getString("complexity"), 
                                result.getBoolean("isVisible"), 
                                result.getDate("lastUpdate").toLocalDate(), 
                                result.getString("creatorFirstName"), 
                                result.getString("creatorLastName"));
                result.close();
                return recipe;
            }
        }
        catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.RESULT_SET_EXCEPTION);
        }
        return null;
    }
}
