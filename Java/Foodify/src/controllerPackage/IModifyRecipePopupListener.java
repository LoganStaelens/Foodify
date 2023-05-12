package controllerPackage;

import modelPackage.Recipe;

public interface IModifyRecipePopupListener {
    void onRecipeModified(Recipe oldversion, Recipe newVersion);
}
