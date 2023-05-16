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
import javafx.stage.Stage;
import modelPackage.MenuView;
import modelPackage.Recipe;
import viewPackage.Foodify;

public class UserWindow extends Window implements Initializable {

    @FXML
    private ChoiceBox<String> cmInputChoiceboxFriday;

    @FXML
    private ChoiceBox<String> cmInputChoiceboxMonday;

    @FXML
    private ChoiceBox<String> cmInputChoiceboxSaturday;

    @FXML
    private ChoiceBox<String> cmInputChoiceboxSunday;

    @FXML
    private ChoiceBox<String> cmInputChoiceboxThursday;

    @FXML
    private ChoiceBox<String> cmInputChoiceboxTuesday;

    @FXML
    private ChoiceBox<String> cmInputChoiceboxWednesday;

    @FXML
    private Tab cmTab;

    @FXML
    private Tab mmTab;

    @FXML
    private TableColumn<MenuView, Integer> mmColumnYear;

    @FXML
    private TableColumn<MenuView, Integer> mmColumnWeek;

    @FXML
    private TableColumn<MenuView, String> mmColumnDay;

    @FXML
    private TableColumn<MenuView, String> mmColumnRecipe;

    @FXML
    private TableView<MenuView> mmTableview;

    @FXML
    private Button mmButtonRecipeInfo;

    private List<ChoiceBox<String>> cmInputChoiceBoxes;

    IRecipeManager recipeManager;
    IMenuManager menuManager;
    
    private PopupRecipeInfoWindow popupRecipeInfoWindow;

    public UserWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.recipeManager = new RecipeManager();  
        this.menuManager = new MenuManager();
        this.fxmlWindow = new Scene(fxmlLoader.load());
        this.popupRecipeInfoWindow = new PopupRecipeInfoWindow(mainStage, popupStage, new FXMLLoader(getClass().getResource("/viewPackage/PopupRecipeInfo.fxml")));
        this.fxmlWindow.getStylesheets().add(getClass().getResource("/viewPackage/style.css").toExternalForm());
    }

    @Override
    public void show() {
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
                for (ChoiceBox<String> choiceBox : cmInputChoiceBoxes) {
                    
                    if(choiceBox.getValue() != null && !choiceBox.getValue().isEmpty()) {
                        selectedTags.put(day, choiceBox.getValue());
                    }
                    
                    day++;
                }
        
                boolean result;
                
                result = menuManager.createMenu(Foodify.getInstance().getUser(), selectedTags);
                if(result)
                    Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.SUCCESS, "Le menu a bien été créé");
                else
                    Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "le menu n'a pas pu être créé");

            }
            else {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Attention vous avez déjà un menu de créé");
            }
        } catch (DBConnectionException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la création du menu");
        }   
    }

    @FXML
    void mm_onButtonRecipeInfo(ActionEvent event) throws DBConnectionException {
        MenuView itemSelected = mmTableview.selectionModelProperty().getValue().getSelectedItem();

        if(itemSelected != null) {
            int menuID = itemSelected.getRecipeID();
            Recipe recipe = recipeManager.findRecipeById(menuID);
    
            if (recipe != null) {
                this.popupRecipeInfoWindow.setRecipe(recipe);
                this.popupRecipeInfoWindow.show();
            }
        }
        else
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Vous n'avez rien sélectionné");

        
    }
    
    private void loadCmTab() {
        
        try {
            List<String> tags = this.recipeManager.getTags();
        
            for (ChoiceBox<String> choiceBox : cmInputChoiceBoxes) {
                choiceBox.getItems().clear();
                choiceBox.setItems(FXCollections.observableArrayList(tags));
            }

        }
        catch (DBConnectionException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la récupération des tags");
        }
    }

    private void loadMmTab() {
        mmColumnYear.setCellValueFactory(new PropertyValueFactory<MenuView, Integer>("year"));
        mmColumnWeek.setCellValueFactory(new PropertyValueFactory<MenuView, Integer>("week"));
        mmColumnDay.setCellValueFactory(new PropertyValueFactory<MenuView, String>("dayName"));
        mmColumnRecipe.setCellValueFactory(new PropertyValueFactory<MenuView, String>("recipeTitle"));
        mmTableview.getItems().clear();

        
        try {
            boolean visible = this.menuManager.hasAMenuAlready(Foodify.getInstance().getUser());
            mmTableview.setVisible(visible);
            mmButtonRecipeInfo.setVisible(visible);

            if(visible) {
                List<MenuView> menuItemViews = this.menuManager.getCurrentMenuFromUser(Foodify.getInstance().getUser());

                mmTableview.setItems(FXCollections.observableArrayList(menuItemViews));
            }
            


        } catch (DBConnectionException e) {
            mmTableview.setVisible(false);
            mmButtonRecipeInfo.setVisible(false);

            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de l'initialisation de la fenêtre mon menu");
        }
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmInputChoiceBoxes = Arrays.asList (
            cmInputChoiceboxMonday,
            cmInputChoiceboxTuesday,
            cmInputChoiceboxWednesday,
            cmInputChoiceboxThursday,
            cmInputChoiceboxFriday,
            cmInputChoiceboxSaturday,
            cmInputChoiceboxSunday);

        
        loadCmTab();

        mmTab.selectedProperty().addListener((observable, oldTab, newTab) -> {
            loadMmTab();
        });
    }
}
