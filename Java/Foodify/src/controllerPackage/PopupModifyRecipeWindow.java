package controllerPackage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

import businessPackage.IRecipeManager;
import businessPackage.RecipeManager;
import exceptionPackage.DBConnectionException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modelPackage.Recipe;
import viewPackage.Foodify;

public class PopupModifyRecipeWindow extends Window implements Initializable{
    
    @FXML
    private CheckBox check_box_is_visible;

    @FXML
    private ChoiceBox<String> choice_box_complexity;

    @FXML
    private TextField input_recipe_creator_first_name;

    @FXML
    private TextField input_recipe_creator_last_name;

    @FXML
    private TextField input_recipe_title;

    @FXML
    private MenuButton menu_button_tags;

    private IModifyRecipePopupListener popupListener;
    private IRecipeManager recipeManager;

    private Recipe recipe;
    
    public PopupModifyRecipeWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader, IModifyRecipePopupListener popupListener) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.popupListener = popupListener;
        this.recipeManager = new RecipeManager();
        this.fxmlWindow = new Scene(fxmlLoader.load());  
        this.fxmlWindow.getStylesheets().add(getClass().getResource("../viewPackage/style.css").toExternalForm());
        
    }

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<String> complexities = this.recipeManager.getDifficulties();
            choice_box_complexity.getItems().clear();
            for (String complexity : complexities) {
                choice_box_complexity.getItems().add(complexity);
            }
            choice_box_complexity.setValue(complexities.get(0));

            List<String> tags = this.recipeManager.getTags();
            menu_button_tags.getItems().clear();
            for (String tag : tags) {
                menu_button_tags.getItems().add(new CheckMenuItem(tag));
            }

        } catch (DBConnectionException e) {
            e.printStackTrace();
        } 
    }

    @Override
    public void show() {
        popupStage.setTitle("Foodify");
        popupStage.getIcons().add(new Image("viewPackage/Foodify.png"));
        popupStage.setResizable(false);
        
        popupStage.setScene(this.fxmlWindow);
        popupStage.show();  
    }

    @Override
    public void hide() {
        this.recipe = null;
        reset();
        this.popupStage.hide();
    }

    private void fillFormFromRecipe(Recipe recipe) {
        check_box_is_visible.setSelected(recipe.getIsVisible());
        choice_box_complexity.setValue(recipe.getComplexity());
        ListIterator<String> tagListIterrator = recipe.getTagsItterator();

        while(tagListIterrator.hasNext()) {
            String tags = tagListIterrator.next();

            for(MenuItem menuItem : menu_button_tags.getItems()) {
                CheckMenuItem checkMenuItem = (CheckMenuItem)menuItem;

                if(checkMenuItem.getText().equalsIgnoreCase(tags)) 
                    checkMenuItem.setSelected(true);
                
            }
        }

        input_recipe_creator_first_name.setText(recipe.getCreatorFirstName());
        input_recipe_creator_last_name.setText(recipe.getCreatorLastName());
        input_recipe_title.setText(recipe.getTitle());
    }

    private void reset() {
        //Need to reset tags for the next modify popup to work
        for(MenuItem menuItem : menu_button_tags.getItems()) {
            CheckMenuItem checkMenuItem = (CheckMenuItem)menuItem;
            checkMenuItem.setSelected(false);          
        }
    }

    @FXML
    void onButtonCancel(ActionEvent event) {v
        hide();
    }

    @FXML
    void onButtonModify(ActionEvent event) {
        int recipeID = this.recipe.getRecipeID();

        

        if(input_recipe_title.getLength() >= Recipe.TITLE_MAX_LENGTH) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le titre de la recette est trop long, la longueur maximale est de " + Recipe.TITLE_MAX_LENGTH + " caractères.");
            return;
        }

        if(input_recipe_creator_first_name.getLength() >= Recipe.CREATOR_FIRST_NAME_MAX_LENGTH) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le prenom de l'auteur de la recette est trop long, la longueur maximale est de " + Recipe.CREATOR_FIRST_NAME_MAX_LENGTH + " caractères.");
            return;
        }

        if(input_recipe_creator_last_name.getLength() >= Recipe.CREATOR_LAST_NAME_MAX_LENGTH) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le nom de l'auteur de la recette est trop long, la longueur maximale est de " + Recipe.CREATOR_LAST_NAME_MAX_LENGTH + " caractères.");
            return;
        }

        Recipe newRecipe = new Recipe (
            recipeID,
            input_recipe_title.getText(),
            choice_box_complexity.getValue(),
            check_box_is_visible.isSelected(),
            LocalDate.now(),
            input_recipe_creator_first_name.getText(), 
            input_recipe_creator_last_name.getText()
            );

        boolean hasTags = false;
        for(MenuItem item : menu_button_tags.getItems()) {
            CheckMenuItem menuItem = (CheckMenuItem)item;
            if(menuItem.isSelected()) {
                newRecipe.addTag(menuItem.getText());
                hasTags = true;
            }
                
        }

        if(!hasTags) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Aucun tag n'a été choisi.");
            return;
        }

        

        Recipe oldRecipe = this.recipe;

        hide();

        this.popupListener.onRecipeModified(oldRecipe, newRecipe);
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        fillFormFromRecipe(recipe);
    }

    


}
