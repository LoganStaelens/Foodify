package controllerPackage;

import java.io.IOException;

import businessPackage.IRecipeManager;
import businessPackage.RecipeManager;
import exceptionPackage.DBConnectionException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modelPackage.Recipe;

public class UserWindow extends Window {

    @FXML
    private TextField input_search_recipe;

    @FXML
    private Button button_search;

    IRecipeManager recipeManager;

    private PopupRecipeInfoWindow popupRecipeInfoWindow;

    public UserWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.recipeManager = new RecipeManager();

        this.fxmlWindow = new Scene(fxmlLoader.load());

        this.popupRecipeInfoWindow = new PopupRecipeInfoWindow(mainStage, popupStage, new FXMLLoader(getClass().getResource("../viewPackage/PopupRecipeInfo.fxml")));
    }

    @Override
    public void show() {
        mainStage.setTitle("Foodify");
        mainStage.getIcons().add(new Image("viewPackage/Foodify.png"));
        mainStage.setResizable(false);
        
        mainStage.setScene(this.fxmlWindow);
        mainStage.show();
    }

    public void searchAction(ActionEvent event) throws DBConnectionException {
        
        Recipe recipe = recipeManager.findRecipeByName(input_search_recipe.getText());
        
        if (recipe != null) {
            this.popupRecipeInfoWindow.setRecipe(recipe);
            this.popupRecipeInfoWindow.show();
        }
    }
    
}
