package fr.serialcoders.qawaleproject.ui.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fr/serialcoders/qawaleproject/fxml/home.fxml"));

        Scene scene = new Scene(root, 700, 400);

        primaryStage.setTitle("SerialCoders' Games");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
