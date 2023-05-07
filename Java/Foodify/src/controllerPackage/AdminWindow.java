package controllerPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class AdminWindow extends Window {

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
    private TableView<StepView> cr_tableview_steps;

    private List<IngredientView> cr_ingredientsList;
    private ObservableList<IngredientView> cr_ingredientsObservableList;

    //Find Recipe Section

    //Delete Recipe Section

    public AdminWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.fxmlWindow = new Scene(fxmlLoader.load());  
        this.fxmlWindow.getStylesheets().add(getClass().getResource("../viewPackage/theme_dark.gls").toExternalForm());
        cr_ingredientsList = new ArrayList<>();
        cr_ingredientsObservableList = FXCollections.observableArrayList(cr_ingredientsList);
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
        cr_ingredientsObservableList.add(new IngredientView("Concombre", 89, "gramme", 250));
        cr_tableview_ingredients.setItems(cr_ingredientsObservableList);
    }

    @FXML
    private void onButtonAddStep(ActionEvent event) { 
        
    }


    class IngredientView {
        private String name;
        private int kcal;
        private String unit;
        private float quatity;

        
        public IngredientView(String name, int kcal, String unit, float quatity) {
            this.name = name;
            this.kcal = kcal;
            this.unit = unit;
            this.quatity = quatity;
        }

        public String getName() {
            return name;
        }

        public int getKcal() {
            return kcal;
        }

        public String getUnit() {
            return unit;
        }

        public float getQuatity() {
            return quatity;
        }    
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
