package controllerPackage;

import java.io.IOException;

import businessPackage.ILoginManager;
import businessPackage.LoginManager;
import businessPackage.LoginResult;
import exceptionPackage.DBConnectionException;
import exceptionPackage.HashException;
import exceptionPackage.StringTooLongException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import viewPackage.Foodify;

public class LoginWindow extends Window {

    @FXML
    private TextField textfield_login_id;

    @FXML
    private TextField textfield_passwd;

    @FXML
    private Button button_login;

    @FXML
    private Button button_register;

    @FXML
    private Label label;

    private ILoginManager loginManager;

    public LoginWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.fxmlWindow = new Scene(fxmlLoader.load());
        this.loginManager = new LoginManager();
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

    @FXML
    public void loginAction(ActionEvent event) {
        
        try {
            LoginResult result = loginManager.login(textfield_login_id.getText(), textfield_passwd.getText());

            label.setVisible(true);
            label.setTextFill(Paint.valueOf("fb0f0f"));

            switch(result.getStatus()) {
                case SUCCESS:
                    label.setTextFill(Paint.valueOf("3e8329"));
                    label.setText("Connection etablie");
                    Foodify.getInstance().setAdminWindow();
                break;

                case EMAIL_INCORRECT:
                    label.setText("Email incorrecte");
                break;

                case PASSWD_INCORRECT:
                    label.setText("Mot de passe incorrect");
                break;
            }
        } catch (HashException | DBConnectionException | StringTooLongException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
        
    }

    @FXML
    public void switchRegisterAction(ActionEvent event) {
        Foodify.getInstance().setRegisterWindow();
    }   
}
