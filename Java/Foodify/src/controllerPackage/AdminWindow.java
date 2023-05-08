package controllerPackage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modelPackage.IngredientView;


public class AdminWindow extends Window implements Initializable {

    //Create Recipe Section
    @FXML
    private TextField cr_textfield_recipe_name;

    @FXML
    private ChoiceBox<?> cr_choice_box_complexity;

    @FXML
    private CheckBox cr_checkbox_visible;

    @FXML
    private MenuButton cr_menu_button_tags;

    @FXML
    private TableView<IngredientView> cr_tableview_ingredients;

    @FXML
    private TableColumn<IngredientView, String> cr_ing_kcal_column;

    @FXML
    private TableColumn<IngredientView, String> cr_ing_name_column;

    @FXML
    private TableColumn<IngredientView, String> cr_ing_quantity_column;

    @FXML
    private TableColumn<IngredientView, String> cr_ing_unit_column;

    @FXML
    private TableView<StepView> cr_tableview_steps;

    private ObservableList<IngredientView> list = FXCollections.observableArrayList(
        new IngredientView("Concombre", "100", "gramme", "250")  
    );

    public AdminWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.fxmlWindow = new Scene(fxmlLoader.load());  
        this.fxmlWindow.getStylesheets().add(getClass().getResource("../viewPackage/style.css").toExternalForm());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cr_ing_name_column.setCellValueFactory(new PropertyValueFactory<IngredientView, String>("name"));
        cr_ing_kcal_column.setCellValueFactory(new PropertyValueFactory<IngredientView, String>("kcal"));
        cr_ing_unit_column.setCellValueFactory(new PropertyValueFactory<IngredientView, String>("unit"));
        cr_ing_quantity_column.setCellValueFactory(new PropertyValueFactory<IngredientView, String>("quantity"));
        
        cr_tableview_ingredients.setItems(list);
        System.out.println("Here");
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
    private void onButtonSave(ActionEvent event) {

    }

    @FXML
    private void onButtonAddIngredient(ActionEvent event) {
        IngredientView ingredient = new IngredientView("Concombre", "89", "gramme", "250");
        cr_tableview_ingredients.getItems().add(ingredient);
        System.out.println(cr_tableview_ingredients.getItems().get(0).getName());
    }

    @FXML
    private void onButtonAddStep(ActionEvent event) { 
        
    }


    class StepView {
        private int stepCount;
        private String title;
        private String description;
        private int duration;

        
        public StepView(int stepCount, String title, String description, int duration) {
            this.stepCount = stepCount;
            this.title = title;
            this.description = description;
            this.duration = duration;
        }

        public int getStepCount() {
            return stepCount;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public int getDuration() {
            return duration;
        }      
    }


    
    
}
