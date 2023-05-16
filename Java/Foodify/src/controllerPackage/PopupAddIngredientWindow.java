package controllerPackage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import businessPackage.IRecipeManager;
import businessPackage.RecipeManager;
import exceptionPackage.DataFetchException;
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
import javafx.stage.Stage;
import modelPackage.Ingredient;
import viewPackage.Foodify;

public class PopupAddIngredientWindow extends Window implements Initializable {

    @FXML
    private TableColumn<Ingredient, String> columnKcal;

    @FXML
    private TableColumn<Ingredient, String> columnName;

    @FXML
    private TextField inputQuantity;

    @FXML
    private TextField inputSearchBar;

    @FXML
    private Label labelUnit;

    @FXML
    private TableView<Ingredient> tableview;

    private IAddIngredientPopupListener popupListener;

    private IRecipeManager recipeManager;

    private ObservableList<Ingredient> ingredientList;

    public PopupAddIngredientWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader, IAddIngredientPopupListener popupListener) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.popupListener = popupListener;
        this.recipeManager = new RecipeManager();
        this.fxmlWindow = new Scene(fxmlLoader.load());  
        this.fxmlWindow.getStylesheets().add(getClass().getResource("/viewPackage/style.css").toExternalForm());
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnName.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
        columnKcal.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("kcal"));
    
        try {
            ingredientList = FXCollections.observableArrayList(this.recipeManager.getAllIngredients());
        } catch (DataFetchException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors le récupération des ingrédients");
        }
        tableview.setItems(ingredientList);
        inputSearchBar.textProperty().addListener((obs, oldValue, newValue) -> {onInputTextFieldChanged();});
    
        tableview.getSelectionModel().selectedItemProperty().addListener(this::onSelectedItem);
    
        
    }

    private void onSelectedItem(ObservableValue<? extends Ingredient> obs, Ingredient oldValue, Ingredient newValue) {
        
        if(newValue != null) labelUnit.setText(newValue.getUnitAbbrevition());
        else labelUnit.setText("");
    }

    
    void onInputTextFieldChanged() {
        String searchText = inputSearchBar.getText();
        FilteredList<Ingredient> filteredList = new FilteredList<>(ingredientList);
        filteredList.setPredicate(p -> p.getName().toLowerCase().contains(searchText.toLowerCase().trim()));
        tableview.setItems(filteredList);
        
    }

    @FXML
    void onAddIngredient(ActionEvent event) {
        Ingredient ingredient = tableview.getSelectionModel().getSelectedItem();
        if(ingredient != null) {
            try {
                int quantity = Integer.parseInt(inputQuantity.getText());
                ingredient.setQuantity(quantity);
                this.hide();
                popupListener.onAddIngredient(ingredient);
            }
            catch (Exception e) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le champ spécifié dans quantitée n'est pas valide");
            }
        }
        else 
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Vous n'avez rien sélectionné");
    }

    @Override
    public void hide() {
        popupStage.hide();
    }

    @Override
    public void show() {
        popupStage.setScene(this.fxmlWindow);
        popupStage.show();  
    }
}
