package controllerPackage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import businessPackage.IRecipeManager;
import businessPackage.IUserManager;
import businessPackage.RecipeManager;
import businessPackage.UserManager;
import exceptionPackage.DBConnectionException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelPackage.Country;
import modelPackage.Ingredient;
import modelPackage.Recipe;
import modelPackage.RecipeStep;
import modelPackage.User;
import viewPackage.Foodify;


public class AdminWindow extends Window implements Initializable, IAddIngredientPopupListener, IAddStepPopupListener, IModifyRecipePopupListener {

    //Create Recipe Section
    @FXML
    private TextField crTextfieldRecipeName;
    @FXML
    private TextField crTextfieldRecipeCreatorFirstName;
    @FXML
    private TextField crTextfieldRecipeCreatorLastName;
    @FXML
    private ChoiceBox<String> crChoiceBoxComplexity;
    @FXML
    private CheckBox crCheckboxVisible;
    @FXML
    private MenuButton crMenuButtonTags;

    @FXML
    private TableView<Ingredient> crTableviewIngredients;
    @FXML
    private TableColumn<Ingredient, Integer> crIngKcalColumn;
    @FXML
    private TableColumn<Ingredient, String> crIngNameColumn;
    @FXML
    private TableColumn<Ingredient, Integer> crIngQuantityColumn;
    @FXML
    private TableColumn<Ingredient, String> crIngUnitColumn;

    @FXML
    private TableView<RecipeStep> crTableviewSteps;
    @FXML
    private TableColumn<RecipeStep, String> crRecipeStepDescColumn;
    @FXML
    private TableColumn<RecipeStep, Integer> crRecipeStepDurationColumn;
    @FXML
    private TableColumn<RecipeStep, Integer> crRecipeStepStepcountColumn;
    @FXML
    private TableColumn<RecipeStep, String> crRecipeStepTitleColumn;

    //List recipes section
    @FXML
    private TextField lrInputSearchBar;

    @FXML
    private TableView<Recipe> lrTableview;

    @FXML
    private TableColumn<Recipe, Integer> lrColumnId;

    @FXML
    private TableColumn<Recipe, String> lrColumnTitle;

    @FXML
    private TableColumn<Recipe, String> lrColumnComplexity;

    @FXML
    private TableColumn<Recipe, String> lrColumnTags;

    @FXML
    private TableColumn<Recipe, LocalDate> lrColumnLastUpdate;
    
    @FXML
    private TableColumn<Recipe, Boolean> lrColumnIsVisible;

    @FXML
    private TableColumn<Recipe, String> lrColumnFirstName;

    @FXML
    private TableColumn<Recipe, String> lrColumnLastName;

    private ObservableList<Recipe> lrRecipes;

    @FXML
    private Tab crTab;

    @FXML
    private Tab lrTab;

    @FXML
    private Tab fuTab;

    @FXML
    private ChoiceBox<String> fuInputChoiceBox;

    @FXML
    private TableView<User> fuTableView;

    @FXML
    private TableColumn<User, LocalDate> fuColumnBirthDate;

    @FXML
    private TableColumn<User, String> fuColumnCity;

    @FXML
    private TableColumn<User, String> fuColumnEmail;

    @FXML
    private TableColumn<User, String> fuColumnFirstName;

    @FXML
    private TableColumn<User, String> fuColumnLastName;

    @FXML
    private TableColumn<User, String> fuColumnStreet;

    @FXML
    private TableColumn<User, String> fuColumnUserID;

    @FXML
    private ImageView fuImageViewLoadingKettle;

    @FXML
    private ImageView fuImageViewLoadingLid;

    @FXML
    private ImageView fuImageViewLoadingSteam;

    private PopupAddIngredientWindow popupAddIngredientWindow;
    private PopupAddStepWindow popupAddStepWindow;
    private PopupModifyRecipeWindow popupModifyRecipeWindow;
    private PopupRecipeInfoWindow popupRecipeInfoWindow;

    private IRecipeManager recipeManager;
    private IUserManager userManager;

    public AdminWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.recipeManager = new RecipeManager();
        this.userManager = new UserManager();

        this.fxmlWindow = new Scene(fxmlLoader.load());  
        this.fxmlWindow.getStylesheets().add(getClass().getResource("../viewPackage/style.css").toExternalForm());
        
        this.popupAddIngredientWindow = new PopupAddIngredientWindow(mainStage, popupStage, new FXMLLoader(getClass().getResource("../viewPackage/PopupAddIngredient.fxml")), this);
        this.popupAddStepWindow = new PopupAddStepWindow(mainStage, popupStage, new FXMLLoader(getClass().getResource("../viewPackage/PopupAddStep.fxml")), this);
        this.popupModifyRecipeWindow = new PopupModifyRecipeWindow(mainStage, popupStage, new FXMLLoader(getClass().getResource("../viewPackage/PopupModifyRecipe.fxml")), this);
        this.popupRecipeInfoWindow = new PopupRecipeInfoWindow(mainStage, popupStage, new FXMLLoader(getClass().getResource("../viewPackage/PopupRecipeInfo.fxml")));
    }


    void onTabCreateRecipe() {
        crIngNameColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
        crIngKcalColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("kcal"));
        crIngUnitColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("unit"));
        crIngQuantityColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("quantity"));
        crTableviewIngredients.setItems(FXCollections.observableArrayList());

        crRecipeStepDescColumn.setCellValueFactory(new PropertyValueFactory<RecipeStep, String>("description"));
        crRecipeStepDurationColumn.setCellValueFactory(new PropertyValueFactory<RecipeStep, Integer>("duration"));
        crRecipeStepStepcountColumn.setCellValueFactory(new PropertyValueFactory<RecipeStep, Integer>("stepCount"));
        crRecipeStepTitleColumn.setCellValueFactory(new PropertyValueFactory<RecipeStep, String>("title"));
        crTableviewSteps.setItems(FXCollections.observableArrayList());

        try {
            List<String> complexities = this.recipeManager.getDifficulties();
            crChoiceBoxComplexity.getItems().clear();
            for (String complexity : complexities) {
                crChoiceBoxComplexity.getItems().add(complexity);
            }
            crChoiceBoxComplexity.setValue(complexities.get(0));

            List<String> tags = this.recipeManager.getTags();
            crMenuButtonTags.getItems().clear();
            for (String tag : tags) {
                crMenuButtonTags.getItems().add(new CheckMenuItem(tag));
            }

        } catch (DBConnectionException e) {
            e.printStackTrace();
        }       
    }

    void onTabListRecipe() {
        lrColumnId.setCellValueFactory(new PropertyValueFactory<Recipe, Integer>("recipeID"));
        lrColumnTitle.setCellValueFactory(new PropertyValueFactory<Recipe, String>("title"));
        lrColumnComplexity.setCellValueFactory(new PropertyValueFactory<Recipe, String>("complexity"));
        lrColumnTags.setCellValueFactory(new PropertyValueFactory<Recipe, String>("tags"));
        lrColumnLastUpdate.setCellValueFactory(new PropertyValueFactory<Recipe, LocalDate>("lastUpdate"));
        lrColumnIsVisible.setCellValueFactory(new PropertyValueFactory<Recipe, Boolean>("isVisible"));
        lrColumnFirstName.setCellValueFactory(new PropertyValueFactory<Recipe, String>("creatorFirstName"));
        lrColumnLastName.setCellValueFactory(new PropertyValueFactory<Recipe, String>("creatorLastName"));

        lrRecipes = FXCollections.observableArrayList();

        lrInputSearchBar.setText("");

        try {
            List<Recipe> recipes = this.recipeManager.getAllRecipes();
            for (Recipe recipe : recipes) {
                lrRecipes.add(recipe);
            }

            lrTableview.setItems(lrRecipes);

        } catch (DBConnectionException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la récupération des recettes");
        }     
        
        lrInputSearchBar.textProperty().addListener((obs, oldValue, newValue) -> {lrOnInputTextFieldChanged();});
    }

    void onTabFindUser() {
        fuColumnUserID.setCellValueFactory(new PropertyValueFactory<User, String>("userID"));
        fuColumnEmail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        fuColumnFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        fuColumnLastName.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        fuColumnBirthDate.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("birthDate"));
        fuColumnStreet.setCellValueFactory(new PropertyValueFactory<User, String>("street"));
        fuColumnCity.setCellValueFactory(new PropertyValueFactory<User, String>("city"));
        fuTableView.setVisible(false);

        try {
            List<Country> countries = this.userManager.getCountries();
            for (Country country : countries) {
                fuInputChoiceBox.getItems().add(country.GetCountryName());
            }

        } catch (DBConnectionException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la récupération des pays");
        }    
    }

    @FXML
    void fuOnButtonSearch(ActionEvent event) {
        fuTableView.setVisible(false);
        String country = fuInputChoiceBox.getValue();

        if(country == null || country.isBlank())
            return;

        LoadingCommonZone commonZone = new LoadingCommonZone(fuImageViewLoadingSteam, fuImageViewLoadingLid, fuImageViewLoadingKettle);

        ThreadLoading loadingThread = new ThreadLoading(commonZone);
        loadingThread.start();

        //Find users by country
        try {
            List<User> usersFound = this.userManager.findUsersByCountry(country);
        
            //Simuler un delai
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    commonZone.loadingComplete();

                    fuTableView.setItems(FXCollections.observableArrayList(usersFound));
            
                    fuTableView.setVisible(true);
                }
            }));  
            timeline.setCycleCount(1);
            timeline.play();

            
        } catch (DBConnectionException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la recherche des utilisateurs");
            commonZone.loadingComplete();
        }

        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onTabCreateRecipe();
        crTab.setOnSelectionChanged(e -> onTabCreateRecipe());
        lrTab.setOnSelectionChanged(e -> onTabListRecipe());
        onTabFindUser();
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
    private void crOnButtonClear(ActionEvent event) {
        crTableviewIngredients.getItems().clear();
        crTableviewSteps.getItems().clear();
        crCheckboxVisible.setSelected(false);
        crTextfieldRecipeName.setText("");
        for(MenuItem item : crMenuButtonTags.getItems()) {
            CheckMenuItem menuItem = (CheckMenuItem)item;
            menuItem.setSelected(false);
        }
        crChoiceBoxComplexity.setValue(crChoiceBoxComplexity.getItems().get(0));
    }

    @FXML
    private void crOnButtonSave(ActionEvent event) {
        
        List<String> selectedTags = new ArrayList<>();

        for(MenuItem item : crMenuButtonTags.getItems()) {
            CheckMenuItem menuItem = (CheckMenuItem)item;
            if(menuItem.isSelected())
                selectedTags.add(menuItem.getText());
        }

        if(selectedTags.isEmpty()) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Aucun tag n'a été choisi.");
            return;
        }

        if(crTextfieldRecipeName.getText().isBlank()) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le nom de la recette est vide.");
            return;
        }

        if(crTextfieldRecipeName.getLength() >= Recipe.TITLE_MAX_LENGTH) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le titre de la recette est trop long, la longueur maximale est de " + Recipe.TITLE_MAX_LENGTH + " caractères.");
            return;
        }

        if(crTextfieldRecipeCreatorFirstName.getLength() >= Recipe.CREATOR_FIRST_NAME_MAX_LENGTH) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le prénom de l'auteur de la recette est trop long, la longueur maximale est de " + Recipe.CREATOR_FIRST_NAME_MAX_LENGTH + " caractères.");
            return;
        }

        if(crTextfieldRecipeCreatorLastName.getLength() >= Recipe.CREATOR_LAST_NAME_MAX_LENGTH) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le nom de l'auteur de la recette est trop long, la longueur maximale est de " + Recipe.CREATOR_LAST_NAME_MAX_LENGTH + " caractères.");
            return;
        }

        if(crTableviewIngredients.getItems().isEmpty()) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Aucun ingrédient n'a été ajouté à la recette.");
            return;
        }

        if(crTableviewSteps.getItems().isEmpty()) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Aucune étape n'a été ajouté à la recette.");
            return;
        }

        try {
            this.recipeManager.createNewRecipe(
            crTextfieldRecipeName.getText(),
            crChoiceBoxComplexity.getValue(),
            selectedTags,
            crCheckboxVisible.isSelected(),
            crTableviewIngredients.getItems(),
            crTableviewSteps.getItems(),
            crTextfieldRecipeCreatorFirstName.getText(),
            crTextfieldRecipeCreatorLastName.getText()
            );

            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.SUCCESS, "La recette a bien été ajoutée");
        }
        catch (DBConnectionException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Une erreur est survenue!");
        }
        
    }

    @FXML
    private void crOnButtonAddIngredient(ActionEvent event) {
        this.popupAddIngredientWindow.show();
    }

    @Override
    public void onAddIngredient(Ingredient ingredient) {
        crTableviewIngredients.getItems().add(ingredient);
    }

    @FXML
    private void crOnButtonAddStep(ActionEvent event) { 
        this.popupAddStepWindow.show();
    }

    @Override
    public void onAddStep(RecipeStep step) {
        step.setStepCount(crTableviewSteps.getItems().size() + 1);
        crTableviewSteps.getItems().add(step);
    }

    private void lrOnInputTextFieldChanged() {
        lrTableview.setItems(this.recipeManager.filterListByTitle(lrRecipes, lrInputSearchBar.getText()));
    }

    @FXML
    void lrOnButtonDelete(ActionEvent event) {
        if(lrTableview.getSelectionModel().getSelectedItem() != null) {
            Foodify.getInstance().setPopupYesNoWindow("Etes vous sûre de vouloir supprimer cette recette ?", new IYesNoPopupListener() {

                @Override
                public void onPopupYesNoHandled(PopupYesNoResult result) {
                    if(result == PopupYesNoResult.YES)
                        lrDeleteRecipe();
                }
                
            });
        }
        else 
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Vous n'avez rien sélectionné");
        
    }

    private void lrDeleteRecipe() {
        Recipe recipeToDelete = lrTableview.getSelectionModel().getSelectedItem();
        
        try {
            this.recipeManager.deleteRecipe(recipeToDelete);
            onTabListRecipe();
              
        } catch (DBConnectionException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la suppression de la recette");
        }
       
    }

    @FXML
    void lrOnButtonModify(ActionEvent event) {
        Recipe recipeToModify = lrTableview.getSelectionModel().getSelectedItem();
        if(recipeToModify != null) {
            this.popupModifyRecipeWindow.setRecipe(recipeToModify);
            this.popupModifyRecipeWindow.show();
        }     
        else 
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Vous n'avez rien sélectionné");
    }

    @FXML
    void lrOnButtonInfo(ActionEvent event) {
        Recipe recipeToModify = lrTableview.getSelectionModel().getSelectedItem();
        
        if (recipeToModify != null) {
            this.popupRecipeInfoWindow.setRecipe(recipeToModify);
            this.popupRecipeInfoWindow.show();
        }  
        else 
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Vous n'avez rien sélectionné");
    }

    @Override
    public void onRecipeModified(Recipe oldversion, Recipe newVersion) {
        try {
            this.recipeManager.modifyRecipe(newVersion);
            onTabListRecipe();
        } catch (DBConnectionException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la modification de la recette");
        }
    }
  
}
