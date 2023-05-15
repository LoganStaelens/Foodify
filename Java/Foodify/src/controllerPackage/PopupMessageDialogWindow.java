package controllerPackage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class PopupMessageDialogWindow extends Window {


    private PopupMessageTypes messageType;
    private String message;

    @FXML
    private Label labelMessage;

    @FXML
    private Label labelMessageType;

    public PopupMessageDialogWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.fxmlWindow = new Scene(fxmlLoader.load());  
        this.fxmlWindow.getStylesheets().add(getClass().getResource("../viewPackage/style.css").toExternalForm());
        this.message = "";
        this.messageType = PopupMessageTypes.INFO;
    }

    @Override
    public void show() {
        popupStage.setTitle("Foodify");
        popupStage.getIcons().add(new Image("viewPackage/Foodify.png"));
        popupStage.setResizable(false);      
        popupStage.setScene(this.fxmlWindow);

        Paint color = Color.WHITE;
        String messageType = "Info";
        switch (this.messageType) {
            case WARNING:
                    color = Color.rgb(255, 221, 0);
                    messageType = "Attention";
                break;

            case SUCCESS:
                    color = Color.rgb(134, 235, 108);
                    messageType = "Succès";
                break;

            case ERROR:
                    color = Color.rgb(212, 48, 36);
                    messageType = "Erreur";
                break;
        }

        this.labelMessage.setTextFill(color);
        this.labelMessage.setText(this.message);
        this.labelMessageType.setText(messageType);

        popupStage.show();  
    }

    @Override
    public void hide() {
        popupStage.hide();
    }

    @FXML
    void onCloseButton(ActionEvent event) {
        hide();
    }

    public void setMessageType(PopupMessageTypes messageType) {
        this.messageType = messageType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
    
}
