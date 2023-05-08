package modelPackage;

public class IngredientView {
    private String name;
    private String kcal;
    private String unit;
    private String quantity;

    
    public IngredientView(String name, String kcal, String unit, String quantity) {
        this.name = name;
        this.kcal = kcal;
        this.unit = unit;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getKcal() {
        return kcal;
    }

    public String getUnit() {
        return unit;
    }

    public String getQuantity() {
        return quantity;
    }    
}
