package viewPackage;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Foodify extends Application {
    private Stage primaryStage;
    private Stage popupStage;

    private Window loginWindow;

    @Override
    public void start(Stage stage)
    {
        this.primaryStage = stage;
        this.popupStage = new Stage(primaryStage.getStyle());
        
        try {
            LoadWindows();

            SetLoginWindow();

        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

    private void LoadWindows() throws IOException {
        this.loginWindow = new LoginWindow(primaryStage, popupStage, new FXMLLoader(getClass().getResource("LoginWindow.fxml")));
    }

    private void SetLoginWindow() {
        this.loginWindow.show();     
    }

    public static void main (String[] args) {
        launch(args);
    }
}