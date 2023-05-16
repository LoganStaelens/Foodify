package controllerPackage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PopupYesNoWindow extends Window {
    
    private String message;
    private IYesNoPopupListener listener;

    @FXML
    private Label labelMessage;


    public PopupYesNoWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.fxmlWindow = new Scene(fxmlLoader.load());  
        this.fxmlWindow.getStylesheets().add(getClass().getResource("/viewPackage/style.css").toExternalForm());
        this.message = "Etes vous en forme ?";
    }

    @Override
    public void show() {
        popupStage.setScene(this.fxmlWindow);

        this.labelMessage.setText(this.message);

        popupStage.show();  
    }

    @FXML
    void onNoButton(ActionEvent event) {
        hide();
        this.listener.onPopupYesNoHandled(PopupYesNoResult.NO);
    }

    @FXML
    void onYesButton(ActionEvent event) {
        hide();
        this.listener.onPopupYesNoHandled(PopupYesNoResult.YES);
    }

    @Override
    public void hide() {
        popupStage.hide();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setListener(IYesNoPopupListener listener) {
        this.listener = listener;
    }

    

}
