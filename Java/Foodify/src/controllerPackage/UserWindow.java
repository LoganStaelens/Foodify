package controllerPackage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import businessPackage.IMenuManager;
import businessPackage.IRecipeManager;
import businessPackage.MenuManager;
import businessPackage.RecipeManager;
import exceptionPackage.DBConnectionException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modelPackage.MenuView;
import viewPackage.Foodify;

public class UserWindow extends Window implements Initializable {

    @FXML
    private ChoiceBox<String> cm_input_choicebox_friday;

    @FXML
    private ChoiceBox<String> cm_input_choicebox_monday;

    @FXML
    private ChoiceBox<String> cm_input_choicebox_saturday;

    @FXML
    private ChoiceBox<String> cm_input_choicebox_sunday;

    @FXML
    private ChoiceBox<String> cm_input_choicebox_thursday;

    @FXML
    private ChoiceBox<String> cm_input_choicebox_tuesday;

    @FXML
    private ChoiceBox<String> cm_input_choicebox_wednesday;

    @FXML
    private Tab cm_tab;

    @FXML
    private Tab mm_tab;

    @FXML
    private TableColumn<MenuView, Integer> mm_column_year;

    @FXML
    private TableColumn<MenuView, Integer> mm_column_week;

    @FXML
    private TableColumn<MenuView, String> mm_column_day;

    @FXML
    private TableColumn<MenuView, String> mm_column_recipe;

    @FXML
    private TableView<MenuView> mm_tableview;

    @FXML
    private Button mm_buttonRecipeInfo;

    private List<ChoiceBox<String>> cm_input_choice_boxes;

    IRecipeManager recipeManager;
    IMenuManager menuManager;
    
    private PopupRecipeInfoWindow popupRecipeInfoWindow;

    public UserWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.recipeManager = new RecipeManager();  
        this.menuManager = new MenuManager();
        this.fxmlWindow = new Scene(fxmlLoader.load());
        this.popupRecipeInfoWindow = new PopupRecipeInfoWindow(mainStage, popupStage, new FXMLLoader(getClass().getResource("../viewPackage/PopupRecipeInfo.fxml")));
        this.fxmlWindow.getStylesheets().add(getClass().getResource("../viewPackage/style.css").toExternalForm());
    }

    @Override
    public void show() {
        mainStage.setTitle("Foodify");
        mainStage.getIcons().add(new Image("viewPackage/Foodify.png"));
        mainStage.setResizable(false);
        
        mainStage.setScene(this.fxmlWindow);
        mainStage.show();
    }

    /*public void searchAction(ActionEvent event) throws DBConnectionException {
        
        Recipe recipe = recipeManager.findRecipeByName(input_search_recipe.getText());
        
        if (recipe != null) {
            this.popupRecipeInfoWindow.setRecipe(recipe);
            this.popupRecipeInfoWindow.show();
        }
    }*/

    @FXML
    private void cm_onButtonCreate(ActionEvent event) {
        try {
            if(!this.menuManager.hasAMenuAlready(Foodify.getInstance().getUser())) {
                Map<Integer, String> selectedTags = new HashMap<>();
                int day = 1;
                for (ChoiceBox<String> choiceBox : cm_input_choice_boxes) {
                    
                    if(choiceBox.getValue() != null && !choiceBox.getValue().isEmpty()) {
                        selectedTags.put(day, choiceBox.getValue());
                    }
                    
                    day++;
                }
        
                boolean result;
                
                result = menuManager.createMenu(Foodify.getInstance().getUser(), selectedTags);
                if(result)
                    Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.SUCCESS, "Le menu a bien ete cree");
                else
                    Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la creation du menu");

            }
            else {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Attention vous avez deja un menu de cree");
            }
        } catch (DBConnectionException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la creation du menu");
        }   
    }

    @FXML
    void mm_onButtonRecipeInfo(ActionEvent event) {
        MenuView itemSelected = mm_tableview.selectionModelProperty().getValue().getSelectedItem();
        
    }
    
    private void loadCmTab() {
        
        try {
            List<String> tags = this.recipeManager.getTags();
        
            for (ChoiceBox<String> choiceBox : cm_input_choice_boxes) {
                choiceBox.getItems().clear();
                choiceBox.setItems(FXCollections.observableArrayList(tags));
            }

        }
        catch (DBConnectionException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la recuperation des tags");
        }
    }

    private void loadMmTab() {
        mm_column_year.setCellValueFactory(new PropertyValueFactory<MenuView, Integer>("year"));
        mm_column_week.setCellValueFactory(new PropertyValueFactory<MenuView, Integer>("week"));
        mm_column_day.setCellValueFactory(new PropertyValueFactory<MenuView, String>("dayName"));
        mm_column_recipe.setCellValueFactory(new PropertyValueFactory<MenuView, String>("recipeTitle"));
        mm_tableview.getItems().clear();

        
        try {
            boolean visible = this.menuManager.hasAMenuAlready(Foodify.getInstance().getUser());
            mm_tableview.setVisible(visible);
            mm_buttonRecipeInfo.setVisible(visible);

            if(visible) {
                List<MenuView> menuItemViews = this.menuManager.getCurrentMenuFromUser(Foodify.getInstance().getUser());

                mm_tableview.setItems(FXCollections.observableArrayList(menuItemViews));
            }
            


        } catch (DBConnectionException e) {
            mm_tableview.setVisible(false);
            mm_buttonRecipeInfo.setVisible(false);

            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de l'initialisation de la fenetre mon menu");
        }
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cm_input_choice_boxes = Arrays.asList (
            cm_input_choicebox_monday,
            cm_input_choicebox_tuesday,
            cm_input_choicebox_wednesday,
            cm_input_choicebox_thursday,
            cm_input_choicebox_friday,
            cm_input_choicebox_saturday,
            cm_input_choicebox_sunday);

        
        loadCmTab();

        mm_tab.selectedProperty().addListener((observable, oldTab, newTab) -> {
            loadMmTab();
        });
    }
}
