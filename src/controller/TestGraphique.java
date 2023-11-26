package controller;

/*import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestGraphique extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Test");

        Label label = new Label("Hello, JavaFX!");
        StackPane root = new StackPane();
        root.getChildren().add(label);

        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }
}
*/

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class TestGraphique extends SimpleApplication {

    public static void main(String[] args) {
    	TestGraphique   app = new TestGraphique();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        // Crée une boîte 3D (c'est juste un exemple, vous pouvez utiliser un autre objet)
        Box boxMesh = new Box(1, 1, 1);
        Geometry boxGeometry = new Geometry("Box", boxMesh);
        Material boxMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        boxMaterial.setColor("Color", ColorRGBA.Blue);
        boxGeometry.setMaterial(boxMaterial);

        // Crée un texte "Bonjour" en utilisant BitmapText
        BitmapText helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
        helloText.setColor(ColorRGBA.White);
        helloText.setText("Bonjour");
        helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);

        // Attache la boîte et le texte à la scène
        rootNode.attachChild(boxGeometry);
        guiNode.attachChild(helloText);
    }
}
