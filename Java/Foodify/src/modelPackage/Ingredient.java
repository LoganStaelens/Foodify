package modelPackage;

public class Ingredient {
    private int ingredientID;
    private String name;
    private int kcal;
    private int unitID;
    private String unit;
    private String unitAbbreviation;
    private float quantity;

    public Ingredient(String name, int kcal, String unit, float quantity) {
        this.name = name;
        this.kcal = kcal;
        this.unit = unit;
        this.quantity = quantity;
        this.ingredientID = 0;
        this.unitID = 0;
        this.unitAbbreviation = "";
    }
    
    public Ingredient(String name, int kcal, String unit, float quantity, String unitAbbreviation, int ingredientID, int unitID) {
        this.name = name;
        this.kcal = kcal;
        this.unit = unit;
        this.quantity = quantity;
        this.ingredientID = ingredientID;
        this.unitID = unitID;
        this.unitAbbreviation = unitAbbreviation;
    }

    public void setQuantity(float value) {
        this.quantity = value;
    }

    public String getName() {
        return name;
    }

    public int getKcal() {
        return kcal;
    }

    public String getUnit() {
        return unit;
    }

    public String getUnitAbbrevition() {
        return this.unitAbbreviation;
    }

    public float getQuantity() {
        return quantity;
    } 
    
    public int getIngredientID() {
        return this.ingredientID;
    }

    public int getUnitID() {
        return this.unitID;
    }
}
