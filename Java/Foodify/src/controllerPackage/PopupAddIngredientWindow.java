package controllerPackage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import businessPackage.IRecipeManager;
import businessPackage.RecipeManager;
import dataAccessPackage.IRecipeDBAccess;
import dataAccessPackage.RecipeDBAccess;
import exceptionPackage.DBConnectionException;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import modelPackage.Ingredient;
import viewPackage.Foodify;

public class PopupAddIngredientWindow extends Window implements Initializable {

    @FXML
    private TableColumn<Ingredient, String> column_kcal;

    @FXML
    private TableColumn<Ingredient, String> column_name;

    @FXML
    private TextField input_quantity;

    @FXML
    private TextField input_search_bar;

    @FXML
    private Label label_unit;

    @FXML
    private TableView<Ingredient> table;

    private IAddIngredientPopupListener popupListener;

    private IRecipeManager recipeManager;

    private ObservableList<Ingredient> ingredientList;

    public PopupAddIngredientWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader, IAddIngredientPopupListener popupListener) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.popupListener = popupListener;
        this.recipeManager = new RecipeManager();
        this.fxmlWindow = new Scene(fxmlLoader.load());  
        this.fxmlWindow.getStylesheets().add(getClass().getResource("../viewPackage/style.css").toExternalForm());
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        column_name.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
        column_kcal.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("kcal"));
    
        try {
            ingredientList = FXCollections.observableArrayList(this.recipeManager.getAllIngredients());
        } catch (DBConnectionException e) {
            e.printStackTrace();
        }
        table.setItems(ingredientList);
        input_search_bar.textProperty().addListener((obs, oldValue, newValue) -> {onInputTextFieldChanged();});
    
        table.getSelectionModel().selectedItemProperty().addListener(this::onSelectedItem);
    
        
    }

    private void onSelectedItem(ObservableValue<? extends Ingredient> obs, Ingredient oldValue, Ingredient newValue) {
        
        if(newValue != null) label_unit.setText(newValue.getUnitAbbrevition());
        else label_unit.setText("");
    }

    
    void onInputTextFieldChanged() {
        String searchText = input_search_bar.getText();
        FilteredList<Ingredient> filteredList = new FilteredList<>(ingredientList);
        filteredList.setPredicate(p -> p.getName().toLowerCase().contains(searchText.toLowerCase().trim()));
        table.setItems(filteredList);
        
    }

    @FXML
    void onAddIngredient(ActionEvent event) {
        Ingredient ingredient = table.getSelectionModel().getSelectedItem();
        
        try {
            int quantity = Integer.parseInt(input_quantity.getText());
            ingredient.setQuantity(quantity);
            this.hide();
            popupListener.onAddIngredient(ingredient);
        }
        catch (Exception e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le champ spécifié dans quantité n'est pas valide");
        } 
    }

    @Override
    public void hide() {
        popupStage.hide();
    }

    @Override
    public void show() {
        popupStage.setTitle("Foodify");
        popupStage.getIcons().add(new Image("viewPackage/Foodify.png"));
        popupStage.setResizable(false);
        
        popupStage.setScene(this.fxmlWindow);
        popupStage.show();  
    }
}
