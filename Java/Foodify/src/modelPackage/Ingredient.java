package modelPackage;

public class Ingredient {
    private int ingredientID;
    private String name;
    private int kcal;
    private int unitID;
    private String unit;
    private String unitAbbreviation;
    private int quantity;

    public Ingredient(String name, int kcal, String unit, int quantity) {
        this.name = name;
        this.kcal = kcal;
        this.unit = unit;
        this.quantity = quantity;
        this.ingredientID = 0;
        this.unitID = 0;
        this.unitAbbreviation = "";
    }
    
    public Ingredient(String name, int kcal, String unit, int quantity, String unitAbbreviation, int ingredientID, int unitID) {
        this.name = name;
        this.kcal = kcal;
        this.unit = unit;
        this.quantity = quantity;
        this.ingredientID = ingredientID;
        this.unitID = unitID;
        this.unitAbbreviation = unitAbbreviation;
    }

    public void setQuantity(int value) {
        this.quantity = value;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
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

    public int getQuantity() {
        return quantity;
    } 
    
    public int getIngredientID() {
        return this.ingredientID;
    }

    public int getUnitID() {
        return this.unitID;
    }
}
