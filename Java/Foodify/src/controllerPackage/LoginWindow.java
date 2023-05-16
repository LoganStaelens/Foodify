package controllerPackage;

import java.io.IOException;

import businessPackage.IUserManager;
import businessPackage.LoginResult;
import businessPackage.UserManager;
import exceptionPackage.DBConnectionException;
import exceptionPackage.HashException;
import exceptionPackage.StringTooLongException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import viewPackage.Foodify;

public class LoginWindow extends Window {

    @FXML
    private TextField textfieldLoginId;

    @FXML
    private TextField textfieldPasswd;

    @FXML
    private Label label;

    private IUserManager userManager;

    public LoginWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlLoader) throws IOException {
        super(mainStage, popupStage);
        fxmlLoader.setController(this);
        this.fxmlWindow = new Scene(fxmlLoader.load());
        this.userManager = new UserManager();
        this.fxmlWindow.getStylesheets().add(getClass().getResource("/viewPackage/style.css").toExternalForm());
    }

    @Override
    public void show() {

        mainStage.setScene(this.fxmlWindow);
        mainStage.show();
    }

    @FXML
    public void onButtonLogin(ActionEvent event) {
        
        try {
            LoginResult result = userManager.login(textfieldLoginId.getText(), textfieldPasswd.getText());

            label.setVisible(true);
            label.setTextFill(Paint.valueOf("fb0f0f"));

            switch(result.getStatus()) {
                case SUCCESS:
                    Foodify.getInstance().setUser(result.getUser());
                    Foodify.getInstance().setUserWindow();
                break;

                case SUCCESS_ADMIN:
                    Foodify.getInstance().setAdminWindow();
                    
                break;

                case EMAIL_INCORRECT:
                    label.setText("Email incorrect");
                break;

                case PASSWD_INCORRECT:
                    label.setText("Mot de passe incorrect");
                break;
            }
        } catch (HashException | DBConnectionException | StringTooLongException e) {
            e.printStackTrace();
        }    
    }

    @FXML
    public void onButtonRegister(ActionEvent event) {
        Foodify.getInstance().setRegisterWindow();
    }   
}
