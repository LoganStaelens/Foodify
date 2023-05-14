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
import java.util.spi.LocaleNameProvider;

import dataAccessPackage.IMenuDBAccess;
import dataAccessPackage.MenuDBAccess;
import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;
import modelPackage.MenuView;
import modelPackage.Recipe;
import modelPackage.User;

public class MenuManager implements IMenuManager {

    private IRecipeManager recipeManager;
    private IMenuDBAccess menuDBAccess;

    public MenuManager() {
        this.recipeManager = new RecipeManager();
        this.menuDBAccess = new MenuDBAccess();
    }

    @Override
    public boolean createMenu(User user, Map<Integer, String> selectedTags) throws DBConnectionException {
        Map<Integer, Recipe> menuRecipes = new HashMap<>();

        for(Entry<Integer, String> pair : selectedTags.entrySet()) {
            Recipe recipe = this.recipeManager.findRecipeByTag(pair.getValue());
            menuRecipes.put(pair.getKey(), recipe);
        }

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

        return selectedTags.size() == menuRecipes.size();
    }

    @Override
    public boolean hasAMenuAlready(User user) throws DBConnectionException {
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
    public List<MenuView> getCurrentMenuFromUser(User user) throws DBConnectionException {
        ResultSet result = this.menuDBAccess.getCurrentMenuFromUser(user.getUniqueID().toString());

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
            throw new DBConnectionException(DBConnectionExceptionTypes.RESULT_SET_EXCEPTION);
        }
    
    }

    
    
}