package viewPackage;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;

import controllerPackage.AdminWindow;
import controllerPackage.LoginWindow;
import controllerPackage.Window;

public class Foodify extends Application {
    private Stage primaryStage;
    private Stage popupStage;

    private Window loginWindow;
    private Window registerWindow;
    private Window adminWindow;

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


    public static void main (String[] args) {
        launch(args);
    }
}