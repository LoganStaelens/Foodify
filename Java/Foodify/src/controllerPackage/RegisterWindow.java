package controllerPackage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import businessPackage.IUserManager;
import businessPackage.UserManager;
import exceptionPackage.DBConnectionException;
import exceptionPackage.HashException;
import exceptionPackage.StringTooLongException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modelPackage.Address;
import modelPackage.City;
import modelPackage.Country;
import modelPackage.Gender;
import viewPackage.Foodify;

public class RegisterWindow extends Window implements Initializable {

    @FXML
    private TextField input_textfield_first_name;

    @FXML
    private TextField input_textfield_last_name;

    @FXML
    private TextField input_textfield_email;

    @FXML
    private TextField input_textfield_phone_number;

    @FXML
    private TextField input_textfield_password;

    @FXML
    private TextField input_textfield_verify_password;

    @FXML
    private ChoiceBox<String> input_choice_boc_gender;

    @FXML
    private ChoiceBox<String> input_choicebox_country;

    @FXML
    private DatePicker input_date_picker_birthDate;

    @FXML
    private TextField input_textfield_city;

    @FXML
    private TextField input_textfield_number;

    @FXML
    private TextField input_textfield_post_code;

    @FXML
    private TextField input_textfield_street;

    private IUserManager userManager;
    

    public RegisterWindow(Stage mainStage, Stage popupStage, FXMLLoader fxmlWindow) throws IOException {
        super(mainStage, popupStage);
        fxmlWindow.setController(this);

        this.userManager = new UserManager();

        this.fxmlWindow = new Scene(fxmlWindow.load());
        this.fxmlWindow.getStylesheets().add(getClass().getResource("../viewPackage/style.css").toExternalForm());
    }

    void loadWindow() {
        
        try {
            List<String> genders = this.userManager.getGenders();
            input_choice_boc_gender.getItems().clear();
            for (String gender : genders) {
                input_choice_boc_gender.getItems().add(gender);
            }
            input_choice_boc_gender.setValue(genders.get(0));
        }
        catch (DBConnectionException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la recuperation des genres");
        }

        input_choicebox_country.getItems().clear();

        try {
            List<Country> countries = this.userManager.getCountries();

            for(Country country : countries) {
                input_choicebox_country.getItems().add(country.GetCountryName());
            }
            input_choicebox_country.setValue(countries.get(0).GetCountryName());
        }
        catch (DBConnectionException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la recuperation des pays");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadWindow();
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
    public void onButtonCreate (ActionEvent event) {
        
        try {
            String passwdHash = this.userManager.hashPassword(input_textfield_password.getText());
            String passwdVerifyHash = this.userManager.hashPassword(input_textfield_verify_password.getText());
            
            if (this.userManager.verifyPassword(passwdHash, passwdVerifyHash)) {
                this.userManager.createNewUser(
                    input_textfield_first_name.getText(),
                    input_textfield_last_name.getText(),
                    Gender.valueOf(input_choice_boc_gender.getValue().toUpperCase()),
                    input_textfield_email.getText(),
                    input_date_picker_birthDate.getValue(),
                    input_textfield_phone_number.getText(),
                    new Address(input_textfield_street.getText(), Integer.parseInt(input_textfield_number.getText()), new City(input_textfield_city.getText(), input_textfield_post_code.getText(), input_choicebox_country.getValue())),
                    passwdHash
                );
            }
            else {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Les mots de passes ne correspondent pas");
            }
            
            
        
        } catch (DBConnectionException | HashException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la creation d'un utilisateur");
        } catch (NumberFormatException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur nombre");
        } catch (StringTooLongException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur texte");
        }
    }
}
