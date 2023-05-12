package controllerPackage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modelPackage.RecipeStep;
import viewPackage.Foodify;

public class PopupAddStepWindow extends Window {

    @FXML
    private TextArea input_description;

    @FXML
    private TextField input_duration;

    @FXML
    private TextField input_title;

    
    
    private IAddStepPopupListener popupListener;

    public PopupAddStepWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader, IAddStepPopupListener popupListener) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.popupListener = popupListener;
        this.fxmlWindow = new Scene(fxmlLoader.load());  
        this.fxmlWindow.getStylesheets().add(getClass().getResource("../viewPackage/style.css").toExternalForm());
        
    }

    @FXML
    void onAddStep(ActionEvent event) {

        String title = input_title.getText();
        String description = input_description.getText();
        
        if(title.length() >= RecipeStep.STEP_TITLE_MAX_LENGTH) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le titre de l'étape de la recette est trop long, la longueur maximale est de " + RecipeStep.STEP_TITLE_MAX_LENGTH + " caractères");
            return;
        }

        if(description.length() >= RecipeStep.STEP_DESCRIPTION_MAX_LENGTH) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le titre de la description de la recette est trop long, la longueur maximale est de " + RecipeStep.STEP_DESCRIPTION_MAX_LENGTH + " caractères.");
            return;
        }


        try {
            int duration = Integer.parseInt(input_duration.getText());

            this.hide();
            this.popupListener.onAddStep(new RecipeStep(title, description, duration));
        }
        catch (NumberFormatException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le champ spécifié dans durée n'est pas valide");
        }
       
    }

    @Override
    public void show() {
        popupStage.setTitle("Foodify");
        popupStage.getIcons().add(new Image("viewPackage/Foodify.png"));
        popupStage.setResizable(false);
        
        popupStage.setScene(this.fxmlWindow);
        popupStage.show();  
    }

    @Override
    public void hide() {
        popupStage.hide();
    }
}
