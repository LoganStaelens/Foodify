package controllerPackage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import businessPackage.IUserManager;
import businessPackage.UserManager;
import exceptionPackage.DBConnectionException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import viewPackage.Foodify;

public class RegisterWindow extends Window implements Initializable {

    @FXML
    private TextField cr_texetfield_first_name;

    @FXML
    private TextField cr_texetfield_last_name;

    @FXML
    private TextField cr_texetfield_email;

    @FXML
    private TextField cr_texetfield_phone_number;

    @FXML
    private TextField cr_texetfield_password;

    @FXML
    private TextField cr_texetfield_verify_password;

    @FXML
    private ChoiceBox<String> cr_choice_box_gender;

    private IUserManager userManager;

    public RegisterWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlWindow) throws IOException {
        super(mainStage, popupStage);
        fxmlWindow.setController(this);

        this.userManager = new UserManager();

        this.fxmlWindow = new Scene(fxmlWindow.load());
    }

    void choiceBoxLoad() {
        
        try {
            List<String> genders = this.userManager.getGenders();
            cr_choice_box_gender.getItems().clear();
            for (String gender : genders) {
                cr_choice_box_gender.getItems().add(gender);
            }
            cr_choice_box_gender.setValue(genders.get(0));
        }
        catch (DBConnectionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBoxLoad();
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
    public void cancelButton (ActionEvent event) {
        Foodify.getInstance().setLoginWindow();
    }

    @FXML
    public void cr_onButtonCreate (ActionEvent event) {
        
        try {
            this.userManager.createNewUser(
                cr_texetfield_first_name.getText(),
                cr_texetfield_last_name.getText(),
                cr_choice_box_gender.getValue(),
                cr_texetfield_email.getText(),
                // Birthdate,
                cr_texetfield_phone_number.getText(),
                // Address,
                cr_texetfield_verify_password.getText()
            );
        }
        catch (DBConnectionException e) {

        }
    }
}
