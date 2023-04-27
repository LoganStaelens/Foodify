package userInterfacePackage;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginWindow.fxml")));
            primaryStage.setTitle("Foodify");
            primaryStage.getIcons().add(new Image("userInterfacePackage/Foodify.png"));
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
}