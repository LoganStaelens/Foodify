package viewPackage;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;

public class Foodify extends Application {
    private Stage primaryStage;
    private Stage popupStage;

    private Window loginWindow;
    private Window registerWindow;

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
            LoadWindows();

            SetLoginWindow();

            UUID.randomUUID().toString();

        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

    private void LoadWindows() throws IOException {
        this.loginWindow = new LoginWindow(primaryStage, popupStage, new FXMLLoader(getClass().getResource("LoginWindow.fxml")));
        this.registerWindow = new RegisterWindow(primaryStage, popupStage, new FXMLLoader(getClass().getResource("RegisterWindow.fxml")));
    }

    public void SetLoginWindow() {
        this.loginWindow.show();     
    }

    public void SetRegisterWindow() {
        this.registerWindow.show();
    }


    public static void main (String[] args) {
        launch(args);
    }
}