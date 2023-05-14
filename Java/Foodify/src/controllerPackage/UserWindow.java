package controllerPackage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import businessPackage.IRecipeManager;
import businessPackage.RecipeManager;
import exceptionPackage.DBConnectionException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modelPackage.Recipe;
import viewPackage.Foodify;

public class UserWindow extends Window implements Initializable {

    @FXML
    private TextField input_search_recipe;

    @FXML
    private Button button_search;

    @FXML
    private Button button_create_menu;

    @FXML
    private MenuButton menu_button_monday_tag;

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
    
    public void createAction(ActionEvent event) throws DBConnectionException {

        List<String> selectedTags = new ArrayList<>();

        for (MenuItem item : menu_button_monday_tag.getItems()) {
            
            CheckMenuItem menuItem = (CheckMenuItem)item;
            
            if (menuItem.isSelected())
                selectedTags.add(menuItem.getText());
        }

        System.out.println(selectedTags.get(0));

        Recipe recipe = recipeManager.findRecipeByTag(selectedTags.get(0));
        
        if (recipe != null) {
            this.popupRecipeInfoWindow.setRecipe(recipe);
            this.popupRecipeInfoWindow.show();
        }
    }

    void loadWindow() {
        
        try {
            List<String> tags = this.recipeManager.getTags();
        
            menu_button_monday_tag.getItems().clear();
            
            for (String tag : tags) {
                menu_button_monday_tag.getItems().add(new CheckMenuItem(tag));
            }
        }
        catch (DBConnectionException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la recuperation des tags");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadWindow();
    }
}
