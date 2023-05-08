package controllerPackage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import businessPackage.IIngredientManager;
import businessPackage.IngredientManager;
import dataAccessPackage.IIngredientDataAccess;
import dataAccessPackage.IngredientDBAccess;
import exceptionPackage.DBConnectionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import modelPackage.IngredientView;

public class PopupAddIngredientWindow extends Window implements Initializable {

    @FXML
    private TableColumn<IngredientView, String> column_kcal;

    @FXML
    private TableColumn<IngredientView, String> column_name;

    @FXML
    private TableColumn<IngredientView, String> column_unit;

    @FXML
    private TextField input_quantity;

    @FXML
    private TableView<IngredientView> table;

    private IAddPopupListener popupListener;

    private IIngredientManager ingredientManager;

    private ObservableList<IngredientView> ingredientList;

    public PopupAddIngredientWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader, IAddPopupListener popupListener) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.fxmlWindow = new Scene(fxmlLoader.load());  
        this.fxmlWindow.getStylesheets().add(getClass().getResource("../viewPackage/style.css").toExternalForm());
        this.popupListener = popupListener;
        this.ingredientManager = new IngredientManager();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        column_name.setCellValueFactory(new PropertyValueFactory<IngredientView, String>("name"));
        column_kcal.setCellValueFactory(new PropertyValueFactory<IngredientView, String>("kcal"));
        column_unit.setCellValueFactory(new PropertyValueFactory<IngredientView, String>("unit"));
    
        try {
            ingredientList = FXCollections.observableArrayList(this.ingredientManager.GetAllIngredients());
        } catch (DBConnectionException e) {
            e.printStackTrace();
        }
        table.setItems(ingredientList);
    }

    @FXML
    void onInputTextFieldChanged(InputMethodEvent event) {
        
    }

    @FXML
    void onAddIngredient(ActionEvent event) {


        popupListener.onPopupClosed(null);
    }

    @Override
    public void show() {
        mainStage.setTitle("Foodify");
        mainStage.getIcons().add(new Image("viewPackage/Foodify.png"));
        mainStage.setResizable(false);
        
        mainStage.setScene(this.fxmlWindow);
        mainStage.show();  
    }
}
