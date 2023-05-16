package controllerPackage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

import businessPackage.IRecipeManager;
import businessPackage.RecipeManager;
import exceptionPackage.DataFetchException;
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
import javafx.stage.Stage;
import modelPackage.Recipe;
import viewPackage.Foodify;

public class PopupModifyRecipeWindow extends Window implements Initializable {
    
    @FXML
    private CheckBox checkBoxIsVisible;

    @FXML
    private ChoiceBox<String> choiceBoxComplexity;

    @FXML
    private TextField inputRecipeCreatorFirstName;

    @FXML
    private TextField inputRecipeCreatorLastName;

    @FXML
    private TextField inputRecipeTitle;

    @FXML
    private MenuButton menuButtonTags;

    private IModifyRecipePopupListener popupListener;
    private IRecipeManager recipeManager;

    private Recipe recipe;
    
    public PopupModifyRecipeWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader, IModifyRecipePopupListener popupListener) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.popupListener = popupListener;
        this.recipeManager = new RecipeManager();
        this.fxmlWindow = new Scene(fxmlLoader.load());  
        this.fxmlWindow.getStylesheets().add(getClass().getResource("/viewPackage/style.css").toExternalForm());
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<String> complexities = this.recipeManager.getDifficulties();
            choiceBoxComplexity.getItems().clear();
            for (String complexity : complexities) {
                choiceBoxComplexity.getItems().add(complexity);
            }
            choiceBoxComplexity.setValue(complexities.get(0));

            List<String> tags = this.recipeManager.getTags();
            menuButtonTags.getItems().clear();
            for (String tag : tags) {
                menuButtonTags.getItems().add(new CheckMenuItem(tag));
            }

        } catch (DataFetchException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de l'initialisation du popup de modification de recettes");
        } 
    }

    @Override
    public void show() {
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
        checkBoxIsVisible.setSelected(recipe.getIsVisible());
        choiceBoxComplexity.setValue(recipe.getComplexity());
        ListIterator<String> tagListIterrator = recipe.getTagsItterator();

        while(tagListIterrator.hasNext()) {
            String tags = tagListIterrator.next();

            for(MenuItem menuItem : menuButtonTags.getItems()) {
                CheckMenuItem checkMenuItem = (CheckMenuItem)menuItem;

                if(checkMenuItem.getText().equalsIgnoreCase(tags)) 
                    checkMenuItem.setSelected(true);          
            }
        }

        inputRecipeCreatorFirstName.setText(recipe.getCreatorFirstName());
        inputRecipeCreatorLastName.setText(recipe.getCreatorLastName());
        inputRecipeTitle.setText(recipe.getTitle());
    }

    private void reset() {
        //Need to reset tags for the next modify popup to work
        for(MenuItem menuItem : menuButtonTags.getItems()) {
            CheckMenuItem checkMenuItem = (CheckMenuItem)menuItem;
            checkMenuItem.setSelected(false);          
        }
    }

    @FXML
    void onButtonCancel(ActionEvent event) {
        hide();
    }

    @FXML
    void onButtonModify(ActionEvent event) {
        int recipeID = this.recipe.getRecipeID();

        if(inputRecipeTitle.getText().isBlank()) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le nom de la recette est vide.");
            return;
        }

        if(inputRecipeTitle.getLength() >= Recipe.TITLE_MAX_LENGTH) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le titre de la recette est trop long, la longueur maximale est de " + Recipe.TITLE_MAX_LENGTH + " caractères.");
            return;
        }

        if(inputRecipeCreatorFirstName.getLength() >= Recipe.CREATOR_FIRST_NAME_MAX_LENGTH) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le prenom de l'auteur de la recette est trop long, la longueur maximale est de " + Recipe.CREATOR_FIRST_NAME_MAX_LENGTH + " caractères.");
            return;
        }

        if(inputRecipeCreatorLastName.getLength() >= Recipe.CREATOR_LAST_NAME_MAX_LENGTH) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le nom de l'auteur de la recette est trop long, la longueur maximale est de " + Recipe.CREATOR_LAST_NAME_MAX_LENGTH + " caractères.");
            return;
        }

        Recipe newRecipe = new Recipe (
            recipeID,
            inputRecipeTitle.getText(),
            choiceBoxComplexity.getValue(),
            checkBoxIsVisible.isSelected(),
            LocalDate.now(),
            inputRecipeCreatorFirstName.getText(), 
            inputRecipeCreatorLastName.getText()
            );

        boolean hasTags = false;
        for(MenuItem item : menuButtonTags.getItems()) {
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
