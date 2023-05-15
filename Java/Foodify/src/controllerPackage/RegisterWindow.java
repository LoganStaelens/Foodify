package controllerPackage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
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
import modelPackage.User;
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

            input_choicebox_country.getItems().clear();

            List<Country> countries = this.userManager.getCountries();

            for(Country country : countries) {
                input_choicebox_country.getItems().add(country.GetCountryName());
            }
            input_choicebox_country.setValue(countries.get(0).GetCountryName());
        }
        catch (DBConnectionException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de l'initialisation de la fenêtre de création d'utilisateurs");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadWindow();
    }

    @Override
    public void show() {
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
            String firstName = input_textfield_first_name.getText();
            String lastName = input_textfield_last_name.getText();
            String passwd = input_textfield_password.getText();
            String passwdVerify = input_textfield_verify_password.getText();
            String passwdHash = this.userManager.hashPassword(passwd);
            String passwdVerifyHash = this.userManager.hashPassword(passwdVerify);
            String email = input_textfield_email.getText();
            LocalDate birthDate = input_date_picker_birthDate.getValue();         
            String phoneNumber = input_textfield_phone_number.getText();
            String street = input_textfield_street.getText();
            String numberHouse = input_textfield_number.getText();
            int numberVerify;
            String city = input_textfield_city.getText();
            String postCode = input_textfield_post_code.getText();
            boolean emailFound = this.userManager.findUserByEmail(email);

            if (firstName.isBlank()) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Aucun prénom n'a été choisi.");
                return;
            }

            if(firstName.length() >= User.FIRST_NAME_MAX_LENGTH) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le prénom est trop long, la longueur maximale est de " + User.FIRST_NAME_MAX_LENGTH + " caractères.");
                return;
            }

            if (lastName.isBlank()) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Aucun nom n'a été choisi.");
                return;
            }

            if(lastName.length() >= User.LAST_NAME_MAX_LENGTH) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le nom est trop long, la longueur maximale est de " + User.LAST_NAME_MAX_LENGTH + " caractères.");
                return;
            }

            if (passwd.isBlank() || passwdVerify.isBlank()) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Aucun mot de passe n'a été choisi.");
                return;
            }

            if (email.isBlank()) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Aucun email n'a été choisi.");
                return;
            }

            if(email.length() >= User.EMAIL_MAX_LENGTH) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "L'email est trop long, la longueur maximale est de " + User.EMAIL_MAX_LENGTH + " caractères.");
                return;
            }

            if(phoneNumber.length() >= User.PHONE_NUMBER_MAX_LENGTH) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le numéro de téléphone est trop long, la longueur maximale est de " + User.PHONE_NUMBER_MAX_LENGTH + " caractères.");
                return;
            }

            if (birthDate == null) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "La date de naissance n'est pas valide.");
                return;
            }

            if (street.isBlank()) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Aucune rue n'a été choisi.");
                return;
            }

            if(street.length() >= Address.STREET_MAX_LENGTH) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "La rue est trop longue, la longueur maximale est de " + Address.STREET_MAX_LENGTH + " caractères.");
                return;
            }

            if (numberHouse.isBlank()) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Aucun numéro de rue n'a été choisi.");
                return;
            }

            try {
                numberVerify = Integer.parseInt(numberHouse);
            } 
            catch (NumberFormatException e) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Il y a des lettres dans le numéro de rue !");
                return;
            }

            if (city.isBlank()) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Aucune ville n'a été choisi.");
                return;
            }

            if(city.length() >= City.NAME_MAX_LENGTH) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "La ville est trop longue, la longueur maximale est de " + City.NAME_MAX_LENGTH + " caractères.");
                return;
            }

            if (postCode.isBlank()) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Aucun code de postal n'a été choisi.");
                return;
            }

            if(postCode.length() >= City.POSTCODE_MAX_LENGTH) {
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Le code postal est trop long, la longueur maximale est de " + City.POSTCODE_MAX_LENGTH + " caractères.");
                return;
            }
            
            if (this.userManager.verifyPassword(passwdHash, passwdVerifyHash) && !emailFound) {
                this.userManager.createNewUser(
                    firstName,
                    lastName,
                    Gender.valueOf(input_choice_boc_gender.getValue().toUpperCase()),
                    email,
                    birthDate,
                    phoneNumber,
                    new Address(street, numberVerify, new City(city, postCode, input_choicebox_country.getValue())),
                    passwdHash
                );
                Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.SUCCESS, "Compte créé ! Veuillez désormais vous connectez.");
                Foodify.getInstance().setLoginWindow();
            }
            else {
                if (emailFound) {
                    Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "L'adresse mail existe déjà !");
                }
                else {
                    Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.WARNING, "Les mots de passes ne correspondent pas");
                }
            }
            
        } catch (DBConnectionException | HashException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur lors de la création d'un utilisateur");
        } catch (NumberFormatException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur nombre");
        } catch (StringTooLongException e) {
            Foodify.getInstance().setPopupMessageDialogWindow(PopupMessageTypes.ERROR, "Erreur texte");
        }
    }
}
