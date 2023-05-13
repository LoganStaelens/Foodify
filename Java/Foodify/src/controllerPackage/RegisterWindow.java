package controllerPackage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import viewPackage.Foodify;

public class RegisterWindow extends Window {

    public RegisterWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlWindow) throws IOException {
        super(mainStage, popupStage);
        fxmlWindow.setController(this);

        this.fxmlWindow = new Scene(fxmlWindow.load());
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
    public void cancelButton(ActionEvent event) {
        Foodify.getInstance().setLoginWindow();
    }
}
