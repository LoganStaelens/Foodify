package modelPackage;

public class MenuItem {
    
    public static final int MAX_DAY = 7;

    private int menuItemID;
    private int menuID;
    private int recipeID;
    private Recipe recipe;
    private int day;

    public MenuItem(int menuItemID, int menuID, int recipeID, int day) {
        this.menuItemID = menuItemID;
        this.menuID = menuID;
        this.recipeID = recipeID;
        this.day = day;
    }

    public MenuItem(int menuItemID, int menuID, int day, Recipe recipe) {
        this.menuItemID = menuItemID;
        this.menuID = menuID;
        this.recipeID = recipe.getRecipeID();
        this.recipe = recipe;
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public int getMenuID() {
        return menuID;
    }

    public int getMenuItemID() {
        return menuItemID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public String getRecipeTitle() {
        return recipe.getTitle();
    }
}
