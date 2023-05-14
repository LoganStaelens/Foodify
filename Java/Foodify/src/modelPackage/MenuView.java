package modelPackage;

import java.util.Arrays;
import java.util.List;

public class MenuView {

    private static final List<String> daysName = Arrays.asList(
        "Lundi",
        "Mardi",
        "Mercredi",
        "Jeudi",
        "Vendredi",
        "Samedi",
        "Dimanche"
    );

    private int year;
    private int week;
    private int day;
    private String recipeTitle;
    private int recipeID;

    
    public MenuView(int year, int week, int day, String recipeTitle, int recipeID) {
        this.year = year;
        this.week = week;
        this.day = day;
        this.recipeTitle = recipeTitle;
        this.recipeID = recipeID;
    }


    public int getYear() {
        return year;
    }


    public int getWeek() {
        return week;
    }


    public String getDayName() {
        return daysName.get(day-1);
    }


    public String getRecipeTitle() {
        return recipeTitle;
    }


    public int getRecipeID() {
        return recipeID;
    }

    
}
