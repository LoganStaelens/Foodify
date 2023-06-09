package modelPackage;

import java.time.LocalDate;
import java.util.List;
import java.util.ListIterator;

public class Recipe {

    public static final int TITLE_MAX_LENGTH = 64;
    public static final int CREATOR_FIRST_NAME_MAX_LENGTH = 64;
    public static final int CREATOR_LAST_NAME_MAX_LENGTH = 64;

    private int recipeID;
    private String title;
    private String complexity;
    private boolean isVisible;
    private LocalDate lastUpdate;
    private String creatorFirstName;
    private String creatorLastName;

    private List<String> tags;

    public Recipe(int recipeID, String title, String complexity, boolean isVisible, LocalDate lastUpdate,
            String creatorFirstName, String creatorLastName) {
        this.recipeID = recipeID;
        this.title = title;
        this.complexity = complexity;
        this.isVisible = isVisible;
        this.lastUpdate = lastUpdate;
        this.creatorFirstName = creatorFirstName;
        this.creatorLastName = creatorLastName;
        this.tags = new TagList();
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public int getRecipeID() {
        return recipeID;
    }

    public String getTitle() {
        return title;
    }

    public String getComplexity() {
        return complexity;
    }

    public boolean getIsVisible() {
        return isVisible;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public String getCreatorFirstName() {
        return creatorFirstName;
    }

    public String getCreatorLastName() {
        return creatorLastName;
    }

    public String getTags() {
        return this.tags.toString();
    }

    public ListIterator<String> getTagsItterator() {
        return this.tags.listIterator();
    }

        

}
