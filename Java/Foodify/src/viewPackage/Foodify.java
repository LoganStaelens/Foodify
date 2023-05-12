package viewPackage;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;

import controllerPackage.AdminWindow;
import controllerPackage.IYesNoPopupListener;
import controllerPackage.LoginWindow;
import controllerPackage.PopupMessageDialogWindow;
import controllerPackage.PopupMessageTypes;
import controllerPackage.PopupYesNoResult;
import controllerPackage.PopupYesNoWindow;

public class Foodify extends Application {
    private Stage primaryStage;
    private Stage popupStage;
    private Stage popupDialogStage;

    private LoginWindow loginWindow;
    private RegisterWindow registerWindow;
    private AdminWindow adminWindow;

    private PopupMessageDialogWindow popupMessageDialogWindow;
    private PopupYesNoWindow popupYesNoWindow;

    private static Foodify instance;

    public static Foodify getInstance() {
        return instance;
    }

    @Override
    public void start(Stage stage)
    {
        instance = this;

        this.primaryStage = stage;
        this.popupStage = new Stage(primaryStage.getStyle());
        this.popupDialogStage = new Stage(primaryStage.getStyle());
        
        try {
            loadWindows();

            setLoginWindow();
            

            UUID.randomUUID().toString();

        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

    private void loadWindows() throws IOException {
        this.loginWindow = new LoginWindow(primaryStage, popupStage, new FXMLLoader(getClass().getResource("LoginWindow.fxml")));
        this.registerWindow = new RegisterWindow(primaryStage, popupStage, new FXMLLoader(getClass().getResource("RegisterWindow.fxml")));
        this.adminWindow = new AdminWindow(primaryStage, popupStage, new FXMLLoader(getClass().getResource("AdminWindow.fxml")));
        this.popupMessageDialogWindow = new PopupMessageDialogWindow(popupDialogStage, popupDialogStage, new FXMLLoader(getClass().getResource("PopupMessageDialog.fxml")));
        this.popupYesNoWindow = new PopupYesNoWindow(popupDialogStage, popupDialogStage, new FXMLLoader(getClass().getResource("PopupYesNo.fxml")));
    }

    public void setLoginWindow() {
        this.loginWindow.show();     
    }

    public void setRegisterWindow() {
        this.registerWindow.show();
    }

    public void setAdminWindow() {
        this.adminWindow.show();
    }

    public void setPopupMessageDialogWindow(PopupMessageTypes messageType, String message) {
        this.popupMessageDialogWindow.setMessageType(messageType);
        this.popupMessageDialogWindow.setMessage(message);
        this.popupMessageDialogWindow.show();
    }

    public void setPopupYesNoWindow(String message, IYesNoPopupListener listener) {
        this.popupYesNoWindow.setMessage(message);
        this.popupYesNoWindow.setListener(listener);
        this.popupYesNoWindow.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}