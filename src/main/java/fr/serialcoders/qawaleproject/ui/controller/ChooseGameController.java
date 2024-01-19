package fr.serialcoders.qawaleproject.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class ChooseGameController {

    @FXML
    private Button qawaleButton;

    @FXML
    private Button quartoButton;

    @FXML
    private Button retourButton;

    @FXML
    private void handleQawaleButton(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) qawaleButton.getScene().getWindow();
        primaryStage.setTitle("Qawale");
        primaryStage.setResizable(false);
        Image backgroundImage = new Image(getClass().getResource("/fr/serialcoders/qawaleproject/img/qawale.jpg").toExternalForm());


        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitHeight(700);
        backgroundImageView.setFitWidth(1000);

        StackPane root = new StackPane(backgroundImageView);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(932, 486);

        Label labelQuarto = createLabel("Qawale", 18, 447, 0);
        Label labelPlayer1 = createLabel("Player 1", 13, 14, 180);
        Label labelPlaying1 = createLabel("Playing", 11, 14, 200);
        Label labelPlayer2 = createLabel("Player 2", 13, 830, 186);
        Label labelPlaying2 = createLabel("Playing", 11, 830, 206);

        Button buttonHelp = createButton("?", 14, Color.BLACK, Color.web("#08d19a"), 888, 20);
        buttonHelp.setPrefSize(30, 30);

        Button buttonPause = createButton("Pause", 14, Color.BLACK, Color.web("#08d19a"), 20, 20);
        buttonPause.setPrefSize(80, 30);

        AnchorPane piecePane = new AnchorPane();
        piecePane.setAccessibleRole(javafx.scene.AccessibleRole.SCROLL_PANE);
        piecePane.setDisable(true);
        piecePane.setLayoutX(83);
        piecePane.setLayoutY(49);
        piecePane.setPrefSize(828, 381);
        QawaleController qa=new QawaleController();
        qa.setLabelPlaying1(labelPlaying1);
        qa.setLabelPlaying2(labelPlaying2);

        Camera camera = new PerspectiveCamera();

        anchorPane.getChildren().addAll(
                buttonPause, labelQuarto, labelPlayer1, labelPlaying1,
                labelPlayer2, labelPlaying2, buttonHelp, piecePane);

        root.getChildren().add(anchorPane);
        Scene scene = new Scene(root, 932, 700,true, SceneAntialiasing.BALANCED);
        qa.setScene(scene);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);
        qa.initialize();
        piecePane.getChildren().add(qa.getPlateauGroup());
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    @FXML
    private void handleQuartoButton(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) quartoButton.getScene().getWindow();

        Label labelQuarto = createLabel("Quarto", 18, 447, 0);
        Label labelPlayer1 = createLabel("Player 1", 13, 14, 180);
        Label labelPlaying1 = createLabel("Selecting", 11, 14, 200);
        Label labelPlayer2 = createLabel("Player 2", 13, 830, 186);
        Label labelPlaying2 = createLabel("Selecting", 11, 830, 206);

        primaryStage.setTitle("Quarto");
        primaryStage.setResizable(false);

        Image backgroundImage = new Image(getClass().getResource("/fr/serialcoders/qawaleproject/img/quarto.jpg").toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitHeight(700);
        backgroundImageView.setFitWidth(1000);

        StackPane root = new StackPane(backgroundImageView);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(932, 486);
        labelPlaying2.setOpacity(0);

        //Pause
        Button buttonPause = createButton("Pause", 14, Color.BLACK, Color.web("#08d19a"), 20, 20);
        buttonPause.setPrefSize(80, 30);

        // End Game
        Button buttonQuarto = createButton("Quarto !", 14, Color.BLACK, Color.web("#08d19a"), 356, 620);
        buttonQuarto.setPrefSize(90, 30);

        // End Turn
        Button buttonEndTurn = createButton("End Turn", 14, Color.BLACK, Color.web("#08d19a"), 466, 620);

        // Help
        Button buttonHelp = createButton("?", 14, Color.BLACK, Color.web("#08d19a"), 888, 20);
        buttonHelp.setPrefSize(30, 30);

        AnchorPane piecePane = new AnchorPane();
        piecePane.setAccessibleRole(javafx.scene.AccessibleRole.SCROLL_PANE);
        piecePane.setDisable(true);
        piecePane.setLayoutX(83);
        piecePane.setLayoutY(49);
        piecePane.setPrefSize(828, 381);

        QuartoController controller = new QuartoController();
        controller.setButtonQuarto(buttonQuarto);
        controller.setButtonHelp(buttonHelp);
        controller.setButtonPause(buttonPause);
        controller.setButtonEndTurn(buttonEndTurn);
        controller.setLabelPlaying1(labelPlaying1);
        controller.setLabelPlaying2(labelPlaying2);

        anchorPane.getChildren().addAll(piecePane,
                buttonPause, labelQuarto, labelPlayer1, labelPlaying1,
                labelPlayer2, labelPlaying2, buttonQuarto, buttonEndTurn, buttonHelp);

        root.getChildren().addAll(anchorPane);

        Camera camera = new PerspectiveCamera();
        Scene scene = new Scene(root, 932, 700,true, SceneAntialiasing.BALANCED);
        controller.setScene(scene);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        controller.initialize();

        piecePane.getChildren().add(controller.getGroup());

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    @FXML
    private void handleRetourButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) retourButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fr/serialcoders/qawaleproject/fxml/home.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Label createLabel(String text, double fontSize, double layoutX, double layoutY) {
        Label label = new Label(text);
        label.setFont(new Font("Snap ITC", fontSize));
        label.setTextFill(Color.web("#08d19a"));
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        return label;
    }

    private Button createButton(String text, double fontSize, Color textColor, Color bgColor, double layoutX, double layoutY) {
        Button button = new Button(text);
        button.setFont(new Font("Snap ITC", fontSize));
        button.setTextFill(textColor);
        button.setStyle("-fx-background-color: " + toRGBCode(bgColor) + ";");
        button.setLayoutX(layoutX);
        button.setLayoutY(layoutY);
        return button;
    }

    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

}
