package controller;

import java.util.Collection;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class Game3DIntegration extends JFXPanel {

    private Game3D game3D;

    public Game3DIntegration() {
        game3D = new Game3D();
        Scene scene = new Scene(new StackPane(), 400, 300);
        setScene(scene);
    }

    public void startGame3D() {
        // Ajouter le Game3D à la scène JavaFX
        ((StackPane) getScene().getRoot()).getChildren().addAll((Collection<? extends Node>) game3D.getGuiViewPort());
        game3D.start();  // Démarrer le Game3D
    }
}

