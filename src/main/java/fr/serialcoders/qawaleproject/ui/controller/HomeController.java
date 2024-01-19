package fr.serialcoders.qawaleproject.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button play;

    @FXML
    private Button settings;

    @FXML
    private Button quit;

    Stage stage=null;

    @FXML
    void handlePlayButton(ActionEvent event) throws IOException {
        stage = (Stage) play.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/serialcoders/qawaleproject/fxml/chooseGame.fxml"));
        ChooseGameController cj = new ChooseGameController();

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleSettingsButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Settings");
        alert.setResizable(false);
        alert.setHeaderText("WORK IN PROGRESS");
        alert.getDialogPane().getChildren().remove(1,3);
        alert.showAndWait();
    }

    @FXML
    void handleQuitButton(ActionEvent event) {
        stage = (Stage) quit.getScene().getWindow();
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }

}
