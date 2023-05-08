package businessPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dataAccessPackage.IIngredientDataAccess;
import dataAccessPackage.IngredientDBAccess;
import exceptionPackage.DBConnectionException;
import exceptionPackage.DBConnectionExceptionTypes;
import modelPackage.IngredientView;

public class IngredientManager implements IIngredientManager{

    private IIngredientDataAccess ingredientDataAccess;

    public IngredientManager() {
        this.ingredientDataAccess = new IngredientDBAccess();
    }

    @Override
    public List<IngredientView> GetAllIngredients() throws DBConnectionException {
        ResultSet result = ingredientDataAccess.GetAllIngredients();

        List<IngredientView> data = new ArrayList<IngredientView>();

        try {
            while(result.next()) {
                String ingredientName = result.getString("Ingredient.name");
                String kcal = result.getString("Ingredient.calories");
                String unit = result.getString("Unit.name");

                data.add(new IngredientView(ingredientName, kcal, unit, "0"));
            }

            result.close();
        } catch (SQLException e) {
            throw new DBConnectionException(DBConnectionExceptionTypes.PREPARED_STATEMENT_EXCEPTION);
        }

        return data;
    }
    
}
