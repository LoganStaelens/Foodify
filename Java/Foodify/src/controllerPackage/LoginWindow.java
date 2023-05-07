package controllerPackage;

import java.io.IOException;

import javax.security.auth.login.LoginContext;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import modelPackage.LoginEventArgs;
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

    private ILoginController loginController;

    public LoginWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.fxmlWindow = new Scene(fxmlLoader.load());
        this.loginController = new LoginController();
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
        loginController.Login(textfield_login_id.getText(), textfield_passwd.getText(), new LoginEventHandler());
    }

    @FXML
    public void switchRegisterAction(ActionEvent event) {
        Foodify.getInstance().setRegisterWindow();
    }

    class LoginEventHandler implements EventHandler<LoginEventArgs> {

        @Override
        public void handle(LoginEventArgs arg0) {
            Platform.runLater(() -> {
                label.setVisible(true);
                label.setTextFill(Paint.valueOf("fb0f0f"));
                switch(arg0.getStatus()) {
                    case SUCCESS:
                        label.setTextFill(Paint.valueOf("3e8329"));
                        label.setText("Connection etablie");
                        Foodify.getInstance().setAdminWindow();
                        break;
                    
                    case LOGIN_INCORRECT:
                        label.setText("Email incorrecte");
                        break;

                    case PASSWD_INCORRECT:
                        label.setText("Mot de passe incorrect");
                        break;

                    case ERROR:
                        label.setText("Une erreur est survenue");
                        break;
                }
                
            });
            
        }
        
    }
    
}
