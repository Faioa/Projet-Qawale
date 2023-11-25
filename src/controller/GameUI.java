package controller;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Serial Coder Games");

        Text title = new Text("SERIAL CODER GAMES");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 48));

        Button playButton = createMenuButton("Play");
        Button settingsButton = createMenuButton("Settings");
        Button quitButton = createMenuButton("Quit");

        VBox menuOptions = new VBox(20, title, playButton, settingsButton, quitButton);
        menuOptions.setAlignment(Pos.CENTER);

        StackPane root = new StackPane();
        root.getChildren().add(menuOptions);

        Scene scene = new Scene(root, 700, 600);
        primaryStage.setScene(scene);

        // Nouvelle scène pour les options Qawale et Quarto
        Scene playOptionsScene = createPlayOptionsScene(primaryStage, scene);

        // Nouvelle scène pour Quarto
        Scene quartoScene = createGameScene("Quarto");

        // Nouvelle scène pour Qawale
        Scene qawaleScene = createGameScene("Qawale");

        // Action du bouton Play
        playButton.setOnAction(event -> {
            primaryStage.setScene(playOptionsScene);
        });

        // Action du bouton Quit
        quitButton.setOnAction(event -> {
            primaryStage.close();
        });

        primaryStage.show();
    }

    private Button createMenuButton(String buttonText) {
        Button button = new Button(buttonText);
        button.setStyle("-fx-font-size: 18; -fx-padding: 10; -fx-min-width: 150;");
        return button;
    }

    private Scene createPlayOptionsScene(Stage primaryStage, Scene previousScene) {
        Text playOptionsTitle = new Text("Select a Game");
        playOptionsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        ChoiceBox<String> gameOptions = new ChoiceBox<>();
        gameOptions.getItems().addAll("Qawale", "Quarto");
        gameOptions.getSelectionModel().selectFirst(); // Sélection par défaut (aucune)

        Button okButton = createMenuButton("OK");
        Button backButton = createMenuButton("Back");

        VBox playOptionsLayout = new VBox(20, playOptionsTitle, gameOptions, okButton, backButton);
        playOptionsLayout.setAlignment(Pos.CENTER);

        StackPane playOptionsRoot = new StackPane();
        playOptionsRoot.getChildren().add(playOptionsLayout);

        Scene playOptionsScene = new Scene(playOptionsRoot, 400, 300);

        // Action du bouton Back
        backButton.setOnAction(event -> {
            primaryStage.setScene(previousScene); // Revenir à la scène précédente
        });
        
     // Nouvelle scène pour Quarto
        Scene quartoScene = createGameScene("Quarto");

        // Nouvelle scène pour Qawale
        Scene qawaleScene = createGameScene("Qawale");

        // Action du bouton OK
        okButton.setOnAction(event -> {
            String selectedGame = gameOptions.getValue();
            if ("Quarto".equals(selectedGame)) {
                primaryStage.setScene(quartoScene);
            } else if ("Qawale".equals(selectedGame)) {
                primaryStage.setScene(qawaleScene);
            }
        });

        return playOptionsScene;
    }

    private Scene createGameScene(String gameName) {
        Text gameTitle = new Text(gameName);
        gameTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Button pauseButton = createMenuButton("Pause");
        Button helpButton = createMenuButton("Help");

        VBox gameLayout = new VBox(20, gameTitle, createPlayerList(), pauseButton, helpButton);
        gameLayout.setAlignment(Pos.CENTER);

        StackPane gameRoot = new StackPane();
        gameRoot.getChildren().add(gameLayout);

        return new Scene(gameRoot, 400, 300);
    }

    private VBox createPlayerList() {
        VBox playerList = new VBox(10);
        playerList.getChildren().addAll(new Text("Joueur 1"), new Text("Joueur 2"));
        return playerList;
    }
}
