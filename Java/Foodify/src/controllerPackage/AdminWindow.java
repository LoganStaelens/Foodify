package controllerPackage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import businessPackage.IRecipeManager;
import businessPackage.RecipeManager;
import exceptionPackage.DBConnectionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modelPackage.Ingredient;
import modelPackage.Recipe;
import modelPackage.RecipeStep;
import viewPackage.Foodify;


public class AdminWindow extends Window implements Initializable, IAddIngredientPopupListener, IAddStepPopupListener {

    //Create Recipe Section
    @FXML
    private TextField cr_textfield_recipe_name;
    @FXML
    private TextField cr_textfield_recipe_creator_first_name;
    @FXML
    private TextField cr_textfield_recipe_creator_last_name;
    @FXML
    private ChoiceBox<String> cr_choice_box_complexity;
    @FXML
    private CheckBox cr_checkbox_visible;
    @FXML
    private MenuButton cr_menu_button_tags;

    @FXML
    private TableView<Ingredient> cr_tableview_ingredients;
    @FXML
    private TableColumn<Ingredient, Integer> cr_ing_kcal_column;
    @FXML
    private TableColumn<Ingredient, String> cr_ing_name_column;
    @FXML
    private TableColumn<Ingredient, Integer> cr_ing_quantity_column;
    @FXML
    private TableColumn<Ingredient, String> cr_ing_unit_column;

    @FXML
    private TableView<RecipeStep> cr_tableview_steps;
    @FXML
    private TableColumn<RecipeStep, String> cr_recipe_step_desc_column;
    @FXML
    private TableColumn<RecipeStep, Integer> cr_recipe_step_duration_column;
    @FXML
    private TableColumn<RecipeStep, Integer> cr_recipe_step_stepcount_column;
    @FXML
    private TableColumn<RecipeStep, String> cr_recipe_step_title_column;

    //List recipes section
    @FXML
    private TextField lr_input_search_bar;

    @FXML
    private TableView<Recipe> lr_tableview;

    @FXML
    private TableColumn<Recipe, String> lr_column_complexity;

    @FXML
    private TableColumn<Recipe, String> lr_column_first_name;

    @FXML
    private TableColumn<Recipe, Integer> lr_column_id;

    @FXML
    private TableColumn<Recipe, Boolean> lr_column_is_visible;

    @FXML
    private TableColumn<Recipe, String> lr_column_last_name;

    @FXML
    private TableColumn<Recipe, LocalDate> lr_column_last_update;

    @FXML
    private TableColumn<Recipe, String> lr_column_tags;

    @FXML
    private TableColumn<Recipe, String> lr_column_title;

    private PopupAddIngredientWindow popupAddIngredientWindow;
    private PopupAddStepWindow popupAddStepWindow;

    private IRecipeManager recipeManager;

    private static final int RECIPE_TITLE_MAX_LENGTH = 64;

    public AdminWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.recipeManager = new RecipeManager();

        this.fxmlWindow = new Scene(fxmlLoader.load());  
        this.fxmlWindow.getStylesheets().add(getClass().getResource("../viewPackage/style.css").toExternalForm());
        
        this.popupAddIngredientWindow = new PopupAddIngredientWindow(mainStage, popupStage, new FXMLLoader(getClass().getResource("../viewPackage/PopupAddIngredient.fxml")), this);
        this.popupAddStepWindow = new PopupAddStepWindow(mainStage, popupStage, new FXMLLoader(getClass().getResource("../viewPackage/PopupAddStep.fxml")), this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cr_ing_name_column.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
        cr_ing_kcal_column.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("kcal"));
        cr_ing_unit_column.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("unit"));
        cr_ing_quantity_column.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("quantity"));
        cr_tableview_ingredients.setItems(FXCollections.observableArrayList());

        cr_recipe_step_desc_column.setCellValueFactory(new PropertyValueFactory<RecipeStep, String>("description"));
        cr_recipe_step_duration_column.setCellValueFactory(new PropertyValueFactory<RecipeStep, Integer>("duration"));
        cr_recipe_step_stepcount_column.setCellValueFactory(new PropertyValueFactory<RecipeStep, Integer>("stepCount"));
        cr_recipe_step_title_column.setCellValueFactory(new PropertyValueFactory<RecipeStep, String>("title"));
        cr_tableview_steps.setItems(FXCollections.observableArrayList());

       
        try {
            List<String> complexities = this.recipeManager.getDifficulties();
            for (String complexity : complexities) {
                cr_choice_box_complexity.getItems().add(complexity);
            }
            cr_choice_box_complexity.setValue(complexities.get(0));

            List<String> tags = this.recipeManager.getTags();
            for (String tag : tags) {
                cr_menu_button_tags.getItems().add(new CheckMenuItem(tag));
            }

        } catch (DBConnectionException e) {
            e.printStackTrace();
        }        
    }

    @Override
    public void show() {
        mainStage.setTitle("Foodify");
        mainStage.getIcons().add(new Image("viewPackage/Foodify.png"));
        mainStage.setResizable(false);
        
        mainStage.setScene(this.fxmlWindow);
        mainStage.show();
    }

    @FXML
    private void cr_onButtonClear(ActionEvent event) {
        cr_tableview_ingredients.getItems().clear();
        cr_tableview_steps.getItems().clear();
        cr_checkbox_visible.setSelected(false);
        cr_textfield_recipe_name.setText("");
        for(MenuItem item : cr_menu_button_tags.getItems()) {
            CheckMenuItem menuItem = (CheckMenuItem)item;
            menuItem.setSelected(false);
        }
        cr_choice_box_complexity.setValue(cr_choice_box_complexity.getItems().get(0));
    }

    @FXML
    private void cr_onButtonSave(ActionEvent event) {
        
        List<String> selectedTags = new ArrayList<>();

        for(MenuItem item : cr_menu_button_tags.getItems()) {
            CheckMenuItem menuItem = (CheckMenuItem)item;
            if(menuItem.isSelected())
                selectedTags.add(menuItem.getText());
        }

        if(selectedTags.isEmpty()) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Aucun tag n'a été choisi.");
            return;
        }

        if(cr_textfield_recipe_name.getLength() >= RECIPE_TITLE_MAX_LENGTH) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le titre de la recette est trop long, la longueur maximale est de " + RECIPE_TITLE_MAX_LENGTH + " caractères.");
            return;
        }

        if(cr_tableview_ingredients.getItems().isEmpty()) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Aucun ingrédient n'a été ajouté à la recette.");
            return;
        }

        if(cr_tableview_steps.getItems().isEmpty()) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Aucune étape n'a été ajouté à la recette.");
            return;
        }

        try {
            this.recipeManager.createNewRecipe(
            cr_textfield_recipe_name.getText(),
            cr_choice_box_complexity.getValue(),
            selectedTags,
            cr_checkbox_visible.isSelected(),
            cr_tableview_ingredients.getItems(),
            cr_tableview_steps.getItems(),
            cr_textfield_recipe_creator_first_name.getText(),
            cr_textfield_recipe_creator_last_name.getText()
            );

            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.SUCCESS, "La recette a bien été ajoutée");
        }
        catch (DBConnectionException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Une erreur est survenue!");
        }
        
    }

    @FXML
    private void cr_onButtonAddIngredient(ActionEvent event) {
        this.popupAddIngredientWindow.show();
    }

    @Override
    public void onAddIngredient(Ingredient ingredient) {
        cr_tableview_ingredients.getItems().add(ingredient);
    }

    @FXML
    private void cr_onButtonAddStep(ActionEvent event) { 
        this.popupAddStepWindow.show();
    }

    @Override
    public void onAddStep(RecipeStep step) {
        step.setStepCount(cr_tableview_steps.getItems().size() + 1);
        cr_tableview_steps.getItems().add(step);
    }

    @FXML
    void lr_onButtonDelete(ActionEvent event) {

    }

    @FXML
    void lr_onButtonInfo(ActionEvent event) {

    }
  
}
