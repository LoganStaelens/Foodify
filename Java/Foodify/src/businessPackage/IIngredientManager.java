package businessPackage;

import java.util.List;

import exceptionPackage.DBConnectionException;
import modelPackage.IngredientView;

public interface IIngredientManager {
    List<IngredientView> GetAllIngredients() throws DBConnectionException;
}
