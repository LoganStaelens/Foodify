package businessPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import dataAccessPackage.IMenuDataAccess;
import dataAccessPackage.MenuDBAccess;
import exceptionPackage.DataFetchException;
import exceptionPackage.DataFetchExceptionTypes;
import modelPackage.MenuView;
import modelPackage.Recipe;
import modelPackage.User;

public class MenuManager implements IMenuManager {

    private IRecipeManager recipeManager;
    private IMenuDataAccess menuDBAccess;

    public MenuManager() {
        this.recipeManager = new RecipeManager();
        this.menuDBAccess = new MenuDBAccess();
    }

    @Override
    public boolean createMenu(User user, Map<Integer, String> selectedTags) throws DataFetchException {
        Map<Integer, Recipe> menuRecipes = new HashMap<>();

        for(Entry<Integer, String> pair : selectedTags.entrySet()) {
            Recipe recipe = this.recipeManager.findRecipeByTag(pair.getValue());

            if(recipe != null)
                menuRecipes.put(pair.getKey(), recipe);
        }

        if(menuRecipes.size() > 0) {
            //Create Menu
            Calendar calendar = Calendar.getInstance(Locale.FRANCE);
            calendar.setTime(Date.from(Instant.now()));
            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR) + 1;
            int year = LocalDate.now().getYear();

            if(weekOfYear > 52) {
                weekOfYear -= 52;
                year++;
            }

            int menuID = this.menuDBAccess.createNewMenu(user.getUniqueID().toString(), year, weekOfYear);

            for(Entry<Integer, Recipe> menuItems : menuRecipes.entrySet()) {
                this.menuDBAccess.createNewMenuItem(menuID, menuItems.getValue().getRecipeID(), menuItems.getKey());
            }
        }

        return menuRecipes.size() > 0;
    }

    @Override
    public boolean hasAMenuAlready(User user) throws DataFetchException {
        Calendar calendar = Calendar.getInstance(Locale.FRANCE);
        calendar.setTime(Date.from(Instant.now()));
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR) + 1;
        int year = LocalDate.now().getYear();
        
        if(weekOfYear > 52) {
            weekOfYear -= 52;
            year++;
        }

        return this.menuDBAccess.hasMenuForUser(user.getUniqueID().toString(), year, weekOfYear);
    }

    @Override
    public List<MenuView> getCurrentMenuFromUser(User user) throws DataFetchException {
        Calendar calendar = Calendar.getInstance(Locale.FRANCE);
        calendar.setTime(Date.from(Instant.now()));
        int weekSearch = calendar.get(Calendar.WEEK_OF_YEAR) + 1;
        int yearSearch = LocalDate.now().getYear();
        
        if(weekSearch > 52) {
            weekSearch -= 52;
            yearSearch++;
        }
        
        ResultSet result = this.menuDBAccess.getCurrentMenuFromUser(user.getUniqueID().toString(), weekSearch, yearSearch);

        List<MenuView> menuItemsView = new ArrayList<>();

        try {
            while(result.next()) {
                int year = result.getInt("year");
                int week = result.getInt("week");
                int day = result.getInt("day");
                int recipeID = result.getInt("recipe_id");
                String recipeTitle = result.getString("title");

                menuItemsView.add(new MenuView(year, week, day, recipeTitle, recipeID));
            }

            return menuItemsView;
        } catch (SQLException e) {
            throw new DataFetchException(DataFetchExceptionTypes.RESULT_SET_EXCEPTION);
        }
    
    }    
}
