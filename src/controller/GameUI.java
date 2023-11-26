package controller;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
        Scene quartoScene = createQuartoScene(primaryStage);

        // Nouvelle scène pour Qawale
        Scene qawaleScene = createQuartoScene(primaryStage);

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
    
    
    
    private Group createPiece(String type, String color, double size) {
    	Group pieceGroup = new Group();

        if (type.equals("circle")) {
            Circle circle = new Circle(size / 2);
            pieceGroup.getChildren().add(circle);
        } else if (type.equals("circleWithHole")) {
            Circle outerCircle = new Circle(size / 2);
            Circle hole = new Circle(size / 4);
            pieceGroup.getChildren().addAll(outerCircle, hole);
        } else if (type.equals("square")) {
            Rectangle square = new Rectangle(size, size);
            pieceGroup.getChildren().add(square);
        } else if (type.equals("squareWithHole")) {
            Rectangle square = new Rectangle(size, size);
            Circle hole = new Circle(size / 2);
            pieceGroup.getChildren().addAll(square, hole);
        }

        // Appliquer la couleur
        Paint pieceColor = Color.WHITE; // Par défaut blanc
        if (color.equals("yellow")) {
            pieceColor = Color.YELLOW;
        }

        for (Node node : pieceGroup.getChildren()) {
            if (node instanceof Shape) {
                ((Shape) node).setFill(pieceColor);
            }
        }

        return pieceGroup;

}

    
    
    private VBox createPiecesPane() {
    	VBox piecesPane = new VBox(10);
        piecesPane.setAlignment(Pos.CENTER);

        String[] types = {"circle", "circleWithHole", "square", "squareWithHole"};
        String[] colors = {"white", "yellow"};
        double pieceSize = 50;

        for (String type : types) {
            for (String color : colors) {
                // Créer les grandes pièces
                Group largePiece = createPiece(type, color, pieceSize * 2);
                piecesPane.getChildren().add(largePiece);

                // Créer les petites pièces
                Group smallPiece = createPiece(type, color, pieceSize);
                piecesPane.getChildren().add(smallPiece);
            }
        }

        return piecesPane;
    }
    
    
    
    
    
    
    
    
    

    
    private GridPane createChessBoard() {
    	 /*GridPane gridPane = new GridPane();
    	    gridPane.setAlignment(Pos.CENTER);

    	    int numRows = 4;
    	    int numCols = 4;

    	    for (int row = 0; row < numRows; row++) {
    	        for (int col = 0; col < numCols; col++) {
    	            Rectangle square = new Rectangle(100, 100); // Chaque case est un rectangle de 100x100 pixels
    	            square.setFill((row + col) % 2 == 0 ? Color.BEIGE : Color.BROWN);

    	            gridPane.add(square, col, row);
    	        }
    	    }

    	    gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, null))); // Fond blanc

    	    return gridPane;*/
    	
    	GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        int numRows = 4;
        int numCols = 4;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Circle circle = new Circle(50); // Chaque case est un cercle de rayon 50 pixels

                // Couleur uniforme pour toutes les cases
                LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.rgb(222, 184, 135)),
                        new Stop(1, Color.rgb(222, 184, 135)));

                circle.setFill(gradient);
                circle.setStroke(Paint.valueOf("#FFDAB9")); // Couleur de la bordure crème
                circle.setStrokeWidth(3); // Épaisseur de la bordure

                gridPane.add(circle, col, row);
            }
        }

        gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, null))); // Fond blanc

        return gridPane;
    	
    	
    }
    
    private Scene createQuartoScene(Stage primaryStage) {
        /*Text gameTitle = new Text("Quarto");
        gameTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        GridPane gridPane = createChessBoard(); // Créez un plateau 2D pour Quarto

        Button pauseButton = createMenuButton("Pause");
        Button helpButton = createMenuButton("Help");

        VBox gameLayout = new VBox(20, gameTitle, gridPane, pauseButton, helpButton);
        gameLayout.setAlignment(Pos.CENTER);

        StackPane gameRoot = new StackPane();
        gameRoot.getChildren().add(gameLayout);

        return new Scene(gameRoot, 400, 300);*/
    	Text gameTitle = new Text("Quarto");
        gameTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        GridPane gridPane = createChessBoard(); // Créez un plateau 2D pour Quarto

        VBox piecesPane = createPiecesPane();

        Button pauseButton = createMenuButton("Pause");
        Button helpButton = createMenuButton("Help");

        HBox gameLayout = new HBox(20, piecesPane, gridPane, pauseButton, helpButton);
        gameLayout.setAlignment(Pos.CENTER);

        StackPane gameRoot = new StackPane();
        gameRoot.getChildren().add(gameLayout);

        return new Scene(gameRoot, 800, 300);
    }

    private GridPane createQuartoGrid() {
        GridPane gridPane = new GridPane();
        // Ajoutez ici la logique pour créer un plateau 2D de 4x4 cellules
        // Vous pouvez utiliser des boutons ou d'autres éléments graphiques pour représenter les cellules.
        
        

        int numRows = 4;
        int numCols = 4;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                ToggleButton cellButton = new ToggleButton(); // Utilisez un ToggleButton pour représenter une cellule
                cellButton.setMinSize(50, 50); // Définissez la taille de la cellule
                cellButton.setStyle("-fx-background-color: white;"); // Style de base (modifiable)
                gridPane.add(cellButton, col, row);

                // Vous pouvez ajouter des actions aux boutons si nécessaire
                // cellButton.setOnAction(event -> handleCellClick(row, col));
            }
        }

        
        
        return gridPane;
    }

    private VBox createPlayerList() {
        VBox playerList = new VBox(10);
        playerList.getChildren().addAll(new Text("Joueur 1"), new Text("Joueur 2"));
        return playerList;
    }
}
























/*import javafx.application.Application;
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
}*/





/*import com.jme3.app.SimpleApplication;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

public class GameUI extends SimpleApplication {

    private Node qawaleNode;

    public static void main(String[] args) {
        GameUI app = new GameUI();
        AppSettings settings = new AppSettings(true);
        settings.setResolution(800, 600);
        app.setSettings(settings);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        setupQawale();

        inputManager.addMapping("Qawale", new KeyTrigger(keyInput.KEY_Q));
        inputManager.addListener(actionListener, "Qawale");
    }

    private void setupQawale() {
        qawaleNode = new Node("Qawale");

        // Ajouter les éléments du plateau de jeu Qawale ici (ex. plateau de jeu, joueurs, etc.)
        // ...

        // Ajouter les pièces aux coins du plateau (4 paires superposées)
        addPiece(qawaleNode, -5, 0, -5);
        addPiece(qawaleNode, 5, 0, -5);
        addPiece(qawaleNode, -5, 0, 5);
        addPiece(qawaleNode, 5, 0, 5);

        // Ajouter les pièces à côté du plateau pour le joueur 1
        addPlayerPieces(qawaleNode, -10, 0, 0, 8);

        // Ajouter les pièces à côté du plateau pour le joueur 2
        addPlayerPieces(qawaleNode, 10, 0, 0, 8);

        // Ajouter le nœud Qawale à la scène
        rootNode.attachChild(qawaleNode);
    }

    private void addPiece(Node parent, float x, float y, float z) {
    	Spatial pieceSpatial = assetManager.loadModel("controller/obj/grand-carree-jaune.obj");

        if (pieceSpatial instanceof Node) {
            // Si le modèle est une Node, vous pouvez extraire le Mesh du premier enfant
            Node pieceNode = (Node) pieceSpatial;
            if (pieceNode.getChildren().size() > 0 && pieceNode.getChild(0) instanceof Geometry) {
                Geometry pieceGeometry = (Geometry) pieceNode.getChild(0);
                Mesh pieceMesh = pieceGeometry.getMesh();

                // Continuez avec le reste du code pour créer et attacher la Geometry
                Geometry pieceGeometryFinal = new Geometry("Piece", pieceMesh);
                Material pieceMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                pieceMaterial.setColor("Color", ColorRGBA.Blue);
                pieceGeometryFinal.setMaterial(pieceMaterial);
                pieceGeometryFinal.setLocalTranslation(x, y, z);

                parent.attachChild(pieceGeometryFinal);
            } else {
                // Gérer le cas où le modèle Node n'a pas d'enfant Geometry
                System.err.println("Le modèle Node ne contient pas de Geometry.");
            }
        } else if (pieceSpatial instanceof Geometry) {
            // Si le modèle est directement une Geometry, vous pouvez extraire le Mesh
            Geometry pieceGeometry = (Geometry) pieceSpatial;
            Mesh pieceMesh = pieceGeometry.getMesh();

            // Continuez avec le reste du code pour créer et attacher la Geometry
            Geometry pieceGeometryFinal = new Geometry("Piece", pieceMesh);
            Material pieceMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            pieceMaterial.setColor("Color", ColorRGBA.Blue);
            pieceGeometryFinal.setMaterial(pieceMaterial);
            pieceGeometryFinal.setLocalTranslation(x, y, z);

            parent.attachChild(pieceGeometryFinal);
        } else {
            // Gérer d'autres types de Spatial si nécessaire
            System.err.println("Type de Spatial non pris en charge.");
        }
    }

    private void addPlayerPieces(Node parent, float startX, float startY, float startZ, int count) {
        float yOffset = 0.2f;

        for (int i = 0; i < count; i++) {
            addPiece(parent, startX, startY + i * yOffset, startZ);
        }
    }

    private ActionListener actionListener = (name, keyPressed, tpf) -> {
        if (name.equals("Qawale") && !keyPressed) {
            switchToScreen(qawaleNode);
        }
    };

    private void switchToScreen(Node screenNode) {
        qawaleNode.setCullHint(Spatial.CullHint.Always);
        screenNode.setCullHint(Spatial.CullHint.Never);
    }
}*/

















