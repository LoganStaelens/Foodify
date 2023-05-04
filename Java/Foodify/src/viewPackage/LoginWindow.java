package viewPackage;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginWindow extends Window{

    public LoginWindow(Stage mainStage, Stage popupStage, Scene fxmlWindow) {
        super(mainStage, popupStage, fxmlWindow);
    }

    @FXML
    private TextField textfield_login_id;

    @FXML
    private TextField textfield_passwd;

    @FXML
    private Button button_login;

    @FXML
    private Button button_register;

    @Override
    public void show() {
        mainStage.setTitle("Foodify");
        mainStage.getIcons().add(new Image("viewPackage/Foodify.png"));
        mainStage.setResizable(false);
        mainStage.setScene(this.fxmlWindow);
        mainStage.show();
    }


    
}
