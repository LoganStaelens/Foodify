package viewPackage;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import controllerPackage.ApplicationController;

public class Main extends Application {
    public static void main (String[] args) {
        launch(args);
    }

    private Stage primaryStage;
    private Stage popupStage;

    @Override
    public void start(Stage stage) throws IOException
    {
        this.primaryStage = stage;

        SetConnectionWindow();
       
    }

    private void SetConnectionWindow() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginWindow.fxml")));
            primaryStage.setTitle("Foodify");
            primaryStage.getIcons().add(new Image("viewPackage/Foodify.png"));
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private void SetRegisterWindow() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RegisterWindow.fxml")));
            primaryStage.setTitle("Foodify");
            primaryStage.getIcons().add(new Image("viewPackage/Foodify.png"));
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}