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
    private TableView<Ingredient> table_view_ingredient;

    @FXML
    private TableColumn<Ingredient, Integer> column_ingredient_amount;

    @FXML
    private TableColumn<Ingredient, Integer> column_ingredient_kcal;

    @FXML
    private TableColumn<Ingredient, String> column_ingredient_name;

    @FXML
    private TableColumn<Ingredient, String> column_ingredient_unit;

    @FXML
    private TableColumn<RecipeStep, String> column_step_description;

    @FXML
    private TableColumn<RecipeStep, Integer> column_step_duration;

    @FXML
    private TableColumn<RecipeStep, Integer> column_step_step_count;

    @FXML
    private TableColumn<RecipeStep, String> column_step_title;

    @FXML
    private TableView<RecipeStep> table_view_step;

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
        column_ingredient_name.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
        column_ingredient_kcal.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("kcal"));
        column_ingredient_unit.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("unit"));
        column_ingredient_amount.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("quantity"));
        
        column_step_description.setCellValueFactory(new PropertyValueFactory<RecipeStep, String>("description"));
        column_step_duration.setCellValueFactory(new PropertyValueFactory<RecipeStep, Integer>("duration"));
        column_step_step_count.setCellValueFactory(new PropertyValueFactory<RecipeStep, Integer>("stepCount"));
        column_step_title.setCellValueFactory(new PropertyValueFactory<RecipeStep, String>("title"));
        reset();
    }

    private void reset() {
        this.recipe = null;
        table_view_ingredient.setItems(FXCollections.observableArrayList());
        table_view_step.setItems(FXCollections.observableArrayList());

    }

    @Override
    public void show() { 
        if(this.recipe != null) {
            popupStage.setTitle("Foodify");
            popupStage.getIcons().add(new Image("viewPackage/Foodify.png"));
            popupStage.setResizable(false);
            
            popupStage.setScene(this.fxmlWindow);
            popupStage.show();

            try {
                List<Ingredient> ingredients = this.recipeManager.getIngredientsForRecipe(recipe);

                table_view_ingredient.setItems(FXCollections.observableArrayList(ingredients));
            }
            catch (DBConnectionException e) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la recuperation des ingredients de la recette");
            }

            try {
                List<RecipeStep> recipeSteps = this.recipeManager.getRecipeStepsForRecipe(recipe);

                table_view_step.setItems(FXCollections.observableArrayList(recipeSteps));
            }
            catch (DBConnectionException e) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la recuperation des etapes de la recette");
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
