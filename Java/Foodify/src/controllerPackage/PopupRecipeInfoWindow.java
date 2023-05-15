package controllerPackage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import businessPackage.IRecipeManager;
import businessPackage.RecipeManager;
import exceptionPackage.DBConnectionException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modelPackage.Ingredient;
import modelPackage.Recipe;
import modelPackage.RecipeStep;
import viewPackage.Foodify;

public class PopupRecipeInfoWindow extends Window implements Initializable{
    
    @FXML
    private TableView<Ingredient> tableviewIngredient;

    @FXML
    private TableColumn<Ingredient, Integer> columnIngredientAmount;

    @FXML
    private TableColumn<Ingredient, Integer> columnIngredientKcal;

    @FXML
    private TableColumn<Ingredient, String> columnIngredientName;

    @FXML
    private TableColumn<Ingredient, String> columnIngredientUnit;

    @FXML
    private TableColumn<RecipeStep, String> columnStepDescription;

    @FXML
    private TableColumn<RecipeStep, Integer> columnStepDuration;

    @FXML
    private TableColumn<RecipeStep, Integer> columnStepStepCount;

    @FXML
    private TableColumn<RecipeStep, String> columnStepTitle;

    @FXML
    private TableView<RecipeStep> tableviewStep;

    private IRecipeManager recipeManager;
    private Recipe recipe;

    public PopupRecipeInfoWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.recipeManager = new RecipeManager();
        this.fxmlWindow = new Scene(fxmlLoader.load());  
        this.fxmlWindow.getStylesheets().add(getClass().getResource("../viewPackage/style.css").toExternalForm());
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnIngredientName.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
        columnIngredientKcal.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("kcal"));
        columnIngredientUnit.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("unit"));
        columnIngredientAmount.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("quantity"));
        
        columnStepDescription.setCellValueFactory(new PropertyValueFactory<RecipeStep, String>("description"));
        columnStepDuration.setCellValueFactory(new PropertyValueFactory<RecipeStep, Integer>("duration"));
        columnStepStepCount.setCellValueFactory(new PropertyValueFactory<RecipeStep, Integer>("stepCount"));
        columnStepTitle.setCellValueFactory(new PropertyValueFactory<RecipeStep, String>("title"));
        reset();
    }

    private void reset() {
        this.recipe = null;
        tableviewIngredient.setItems(FXCollections.observableArrayList());
        tableviewStep.setItems(FXCollections.observableArrayList());

    }

    @Override
    public void show() { 
        if(this.recipe != null) {
            popupStage.setScene(this.fxmlWindow);
            popupStage.show();

            try {
                List<Ingredient> ingredients = this.recipeManager.getIngredientsForRecipe(recipe);

                tableviewIngredient.setItems(FXCollections.observableArrayList(ingredients));
            }
            catch (DBConnectionException e) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la récupération des ingrédients de la recette");
            }

            try {
                List<RecipeStep> recipeSteps = this.recipeManager.getRecipeStepsForRecipe(recipe);

                tableviewStep.setItems(FXCollections.observableArrayList(recipeSteps));
            }
            catch (DBConnectionException e) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la récupération des étapes de la recette");
            }
        }
    }

    @Override
    public void hide() {
        this.popupStage.hide();
    }

    @FXML
    void onButtonClose(ActionEvent event) {
        hide();
        reset();
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
