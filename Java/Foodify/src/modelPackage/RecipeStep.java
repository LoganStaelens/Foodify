package modelPackage;

public class RecipeStep {
    private String title;
    private String description;
    private int duration;
    private int stepCount;

    public RecipeStep(String title, String description, int duration) {
        this.title = title;
        this.description = description;
        this.duration = duration;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public int getStepCount() {
        return stepCount;
    }

    
}
