package test;

import businessPackage.IRecipeManager;
import businessPackage.RecipeManager;
import exceptionPackage.DataFetchException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeManagerTest {

    private IRecipeManager recipeManager;

    @BeforeEach
    public void setup() {
        this.recipeManager = new RecipeManager();
    }

    @Test
    void getAllIngredients() throws DataFetchException {
        assertNotEquals(0, this.recipeManager.getAllIngredients().size());
    }

    @Test
    void findRecipeByName() throws DataFetchException {
        assertNotNull(this.recipeManager.findRecipeByName("Spaghetti bolognaise"));
        assertNull(this.recipeManager.findRecipeByName("dohwiouhqweuidhqwuiheiquhe"));
        assertNull(this.recipeManager.findRecipeByName("spaghetti"));
    }

    @Test
    void findRecipeByTag() throws DataFetchException {
        assertNotNull(this.recipeManager.findRecipeByTag("ITALIEN"));
        assertNotNull(this.recipeManager.findRecipeByTag("italien"));
        assertNull(this.recipeManager.findRecipeByTag("GEGGG"));
    }

    @Test
    void findRecipeById() throws DataFetchException {
        assertNotNull(this.recipeManager.findRecipeById(1));
        assertNull(this.recipeManager.findRecipeById(0));
        assertNull(this.recipeManager.findRecipeById(-1));
    }
}