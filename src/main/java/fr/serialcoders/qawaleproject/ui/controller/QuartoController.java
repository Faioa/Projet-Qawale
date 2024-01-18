package fr.serialcoders.qawaleproject.ui.controller;

import fr.serialcoders.qawaleproject.logic.Grid;
import fr.serialcoders.qawaleproject.logic.Piece;
import fr.serialcoders.qawaleproject.logic.quarto.QuartoPiece;
import fr.serialcoders.qawaleproject.logic.quarto.QuartoPlayer;
import fr.serialcoders.qawaleproject.logic.quarto.QuartoStrategy;
import fr.serialcoders.qawaleproject.ui.Boundary;
import fr.serialcoders.qawaleproject.ui.model.quarto.Board;
import fr.serialcoders.qawaleproject.ui.model.quarto.RoundedPiece;
import fr.serialcoders.qawaleproject.ui.model.quarto.SquaredPiece;
import fr.serialcoders.qawaleproject.ui.model.quarto.SmartGroup;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuartoController extends Application {

    private double anchorY;
    private double anchorAngleY = 0;
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private StackPane root;
    private Map<Boundary, Group> map=new HashMap<>();
    private Map<Group, QuartoPiece> mapPieces = new HashMap<Group, QuartoPiece>();
    private Group currentGroup=null;
    private boolean dragging;
    private boolean placed;
    private RotateTransition rotation;
    private Grid grid;
    private QuartoPlayer player1;
    private QuartoPlayer player2;
    private int currentPlayer;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quarto");
        primaryStage.setResizable(false);
        dragging = false;
        placed = false;
        root = new StackPane();
        root.setStyle("-fx-background-color: #0E0E0D;");

        grid = new Grid(4, 4, new QuartoStrategy());

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(932, 486);

        Label labelPause = createLabel("Pause ||", 14, 20, 0);
        Label labelQuarto = createLabel("Quarto", 18, 447, 0);
        Label labelPlayer1 = createLabel("Player 1", 13, 14, 180);
        Label labelPlaying1 = createLabel("Playing", 11, 14, 200);
        labelPlaying1.setDisable(true);
        Label labelPlayer2 = createLabel("Player 2", 13, 830, 186);
        Label labelPlaying2 = createLabel("Playing", 11, 830, 206);
        labelPlaying2.setOpacity(0);

        Button buttonQuarto = createButton("Quarto", 14, Color.BLACK, Color.web("#fec704"), 838, 430);
        buttonQuarto.setOnAction((event) -> {
            if (grid.verify())
                System.out.println("Gagné !");
            else
                System.out.println("Perdu !");
        });
        Button buttonHelp = createButton("?", 14, Color.BLACK, Color.web("#fec704"), 843, 20);

        AnchorPane piecePane = new AnchorPane();
        piecePane.setAccessibleRole(javafx.scene.AccessibleRole.SCROLL_PANE);
        piecePane.setDisable(true);
        piecePane.setLayoutX(83);
        piecePane.setLayoutY(49);
        piecePane.setPrefSize(828, 381);

        Board plateau = new Board();
        SmartGroup group=plateau.getBoard();
        rotation = new RotateTransition(Duration.seconds(0.5), group);
        rotation.setAxis(Rotate.Y_AXIS);
        rotation.setToAngle(0);

        Group plateGroup = new Group();
        plateGroup.getChildren().add(group);
        plateGroup.getTransforms().addAll(
                new Rotate(35, Rotate.X_AXIS),
                new Rotate(0, Rotate.Y_AXIS),
                new Rotate(0, Rotate.Z_AXIS),
                new Translate(20, -100, -300)
        );

        piecePane.getChildren().add(plateGroup);

        anchorPane.getChildren().addAll(piecePane,
                labelPause, labelQuarto, labelPlayer1, labelPlaying1,
                labelPlayer2, labelPlaying2, buttonQuarto, buttonHelp);

        root.getChildren().add(anchorPane);

        // A supprimer
        Camera camera = new PerspectiveCamera();
        Scene scene = new Scene(root, 932, 700,true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        initPartie(group);

        List<QuartoPiece> values = new ArrayList<>(mapPieces.values());
        System.out.println(mapPieces.size());
        player1 = new QuartoPlayer("player1", grid, values);
        player2 = new QuartoPlayer("player2", grid, values);
        currentPlayer = 0;

        scene.setOnMouseReleased((event) -> {
            if (dragging) {
                dragging = false;
                rotation.setFromAngle(angleY.get()%360);
                rotation.playFromStart();
                angleY.set(0);
                return;
            }

            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();

            Group tmp = findPiece(mouseX, mouseY);

            if (tmp != null && currentGroup == null) {
                currentGroup = tmp;
                currentPlayer = (currentPlayer + 1) % 2;
                if (currentPlayer == 0) {
                    player1.choosePiece(mapPieces.get(currentGroup));
                    labelPlaying1.setOpacity(1);
                    labelPlaying2.setOpacity(0);
                } else {
                    player2.choosePiece(mapPieces.get(currentGroup));
                    labelPlaying1.setOpacity(0);
                    labelPlaying2.setOpacity(1);
                }
            }
            else {
                if (currentGroup == null)
                    return;

                QuartoPlayer p = currentPlayer == 0 ? player1 : player2;

                //1st line
                if (mouseX >= 279 && mouseX <= 346 && mouseY >= 260 && mouseY <= 298) {
                    if (!grid.getCell(0, 0).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(-122.0);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(116);
                    p.putPiece(grid, 0, 0);
                    placed = true;
                }

                if (mouseX >= 365 && mouseX <= 435 && mouseY >= 260 && mouseY <= 298) {
                    if (!grid.getCell(0, 1).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(-40);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(116);
                    p.putPiece(grid, 0, 1);
                    placed = true;
                }

                if (mouseX >= 463 && mouseX <= 533 && mouseY >= 260 && mouseY <= 298) {
                    if (!grid.getCell(0, 2).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(40);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(116);
                    p.putPiece(grid, 0, 2);
                    placed = true;
                }

                if (mouseX >= 560 && mouseX <= 630 && mouseY >= 260 && mouseY <= 298) {
                    if (!grid.getCell(0, 3).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(121);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(116);
                    p.putPiece(grid, 0, 3);
                    placed = true;
                }

                //2nd line
                if (mouseX >= 270 && mouseX <= 340 && mouseY >= 316 && mouseY <= 349) {
                    if (!grid.getCell(1, 0).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(-121.0);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(34.6);
                    p.putPiece(grid, 1, 0);
                    placed = true;
                }

                if (mouseX >= 365 && mouseX <= 435 && mouseY >= 316 && mouseY <= 349) {
                    if (!grid.getCell(1, 1).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(-40);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(40.3);
                    p.putPiece(grid, 1, 1);
                    placed = true;
                }

                if (mouseX >= 463 && mouseX <= 533 && mouseY >= 316 && mouseY <= 349) {
                    if (!grid.getCell(1, 2).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(40);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(40.3);
                    p.putPiece(grid, 1, 2);
                    placed = true;
                }

                if (mouseX >= 560 && mouseX <= 630 && mouseY >= 316 && mouseY <= 349) {
                    if (!grid.getCell(1, 3).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(121);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(40.3);
                    p.putPiece(grid, 1, 3);
                    placed = true;
                }

                //3rd line
                if (mouseX >= 270 && mouseX <= 340 && mouseY >= 366 && mouseY <= 417) {
                    if (!grid.getCell(2, 0).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(-121.0);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(-35.4);
                    p.putPiece(grid, 2, 0);
                    placed = true;
                }

                if (mouseX >= 365 && mouseX <= 435 && mouseY >= 366 && mouseY <= 417) {
                    if (!grid.getCell(2, 1).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(-40);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(-35.4);
                    p.putPiece(grid, 2, 1);
                    placed = true;
                }

                if (mouseX >= 463 && mouseX <= 533 && mouseY >= 366 && mouseY <= 417) {
                    if (!grid.getCell(2, 2).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(40);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(-35.4);
                    p.putPiece(grid, 2, 2);
                    placed = true;
                }

                if (mouseX >= 560 && mouseX <= 630 && mouseY >= 366 && mouseY <= 417) {
                    if (!grid.getCell(2, 3).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(121);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(-35.4);
                    p.putPiece(grid, 2, 3);
                    placed = true;
                }

                //4th line
                if (mouseX >= 270 && mouseX <= 340 && mouseY >= 429 && mouseY <= 488) {
                    if (!grid.getCell(3, 0).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(-121);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(-120);
                    p.putPiece(grid, 3, 0);
                    placed = true;
                }

                if (mouseX >= 365 && mouseX <= 435 && mouseY >= 429 && mouseY <= 488) {
                    if (!grid.getCell(3, 1).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(-40);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(-120);
                    p.putPiece(grid, 3, 1);
                    placed = true;
                }

                if (mouseX >= 463 && mouseX <= 533 && mouseY >= 429 && mouseY <= 488) {
                    if (!grid.getCell(3, 2).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(40);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(-120);
                    p.putPiece(grid, 3, 2);
                    placed = true;
                }

                if (mouseX >= 560 && mouseX <= 630 && mouseY >= 429 && mouseY <= 488) {
                    if (!grid.getCell(3, 3).getContent().isEmpty())
                        return;
                    currentGroup.translateXProperty().set(121);
                    currentGroup.translateYProperty().set(-40);
                    currentGroup.translateZProperty().set(-120);
                    p.putPiece(grid, 3, 3);
                    placed = true;
                }

                if (placed) {
                    removeValue(currentGroup);
                    currentGroup = null;
                    placed = false;
                    if (currentPlayer == 0)
                        labelPlaying1.setOpacity(0.5);
                    else
                        labelPlaying2.setOpacity(0.5);
                    buttonQuarto.fire();
                }
            }
        });

        group.translateXProperty().set(350);
        group.translateYProperty().set(200);
        group.translateZProperty().set(-100);

        initMouseControl(group, scene);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void removeValue(Group g) {
        for (Boundary b : map.keySet()) {
            if (map.get(b).equals(g)) {
                map.remove(b);
                return;
            }
        }
    }

    private Label createLabel(String text, double fontSize, double layoutX, double layoutY) {
        Label label = new Label(text);
        label.setFont(new Font("Snap ITC", fontSize));
        label.setTextFill(Color.web("#fec704"));
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        return label;
    }

    private Group findPiece(double x, double y) {
        for(Boundary b: map.keySet()) {
            if(b.inBoundary(x, y)) {
                return map.get(b);
            }
        }
        return null;
    }

    private void initPartie(SmartGroup group) {
        QuartoPiece tmp;

        //Beige textured pieces
        SquaredPiece p = new SquaredPiece();
        SmartGroup pieceG = p.startPiece(Color.BEIGE, false, true);
        pieceG.translateXProperty().set(-100.0);
        pieceG.translateYProperty().set(-40);
        pieceG.translateZProperty().set(260);
        group.getChildren().add(pieceG);
        tmp = new QuartoPiece(Piece.PieceColor.CREAM, Piece.PieceHeight.SMALL, Piece.PieceShape.SQUARED, Piece.PieceTexture.HOLLOW);
        mapPieces.put(pieceG, tmp);
        map.put(new Boundary(324,362, 128, 206), pieceG);

        p = new SquaredPiece();
        pieceG = p.startPiece(Color.BEIGE, false, false);
        pieceG.translateXProperty().set(-150.0);
        pieceG.translateYProperty().set(-40);
        pieceG.translateZProperty().set(230);
        group.getChildren().add(pieceG);
        tmp = new QuartoPiece(Piece.PieceColor.CREAM, Piece.PieceHeight.SMALL, Piece.PieceShape.SQUARED, Piece.PieceTexture.FULL);
        mapPieces.put(pieceG, tmp);
        map.put(new Boundary(264,307, 142, 222), pieceG);

        p = new SquaredPiece();
        pieceG = p.startPiece(Color.BEIGE, true, true);
        pieceG.translateXProperty().set(-200);
        pieceG.translateYProperty().set(-80);
        pieceG.translateZProperty().set(200);
        group.getChildren().add(pieceG);
        tmp = new QuartoPiece(Piece.PieceColor.CREAM, Piece.PieceHeight.TALL, Piece.PieceShape.ROUNDED, Piece.PieceTexture.HOLLOW);
        mapPieces.put(pieceG, tmp);
        map.put(new Boundary(193,236, 71,239), pieceG);

        p = new SquaredPiece();
        pieceG = p.startPiece(Color.BEIGE, true, false);
        pieceG.translateXProperty().set(-240.0);
        pieceG.translateYProperty().set(-80);
        pieceG.translateZProperty().set(140);
        group.getChildren().add(pieceG);
        tmp = new QuartoPiece(Piece.PieceColor.CREAM, Piece.PieceHeight.TALL, Piece.PieceShape.SQUARED, Piece.PieceTexture.FULL);
        mapPieces.put(pieceG, tmp);
        map.put(new Boundary(130,178, 99,275), pieceG);

        RoundedPiece pr = new RoundedPiece();
        SmartGroup pieceR = pr.startPiece(Color.BEIGE, true, false);
        pieceR.translateXProperty().set(-270.0);
        pieceR.translateYProperty().set(-80);
        pieceR.translateZProperty().set(60);
        group.getChildren().add(pieceR);
        tmp = new QuartoPiece(Piece.PieceColor.CREAM, Piece.PieceHeight.TALL, Piece.PieceShape.ROUNDED, Piece.PieceTexture.FULL);
        mapPieces.put(pieceR, tmp);
        map.put(new Boundary(68,120, 140,327), pieceR);

        pr = new RoundedPiece();
        pieceR = pr.startPiece(Color.BEIGE, true, true);
        pieceR.translateXProperty().set(-280.0);
        pieceR.translateYProperty().set(-80);
        pieceR.translateZProperty().set(-70);
        group.getChildren().add(pieceR);
        tmp = new QuartoPiece(Piece.PieceColor.CREAM, Piece.PieceHeight.TALL, Piece.PieceShape.ROUNDED, Piece.PieceTexture.HOLLOW);
        mapPieces.put(pieceR, tmp);
        map.put(new Boundary(5,64, 222,426), pieceR);

        pr = new RoundedPiece();
        pieceR = pr.startPiece(Color.BEIGE, false, true);
        pieceR.translateXProperty().set(-230.0);
        pieceR.translateYProperty().set(-40);
        pieceR.translateZProperty().set(-200);
        group.getChildren().add(pieceR);
        tmp = new QuartoPiece(Piece.PieceColor.CREAM, Piece.PieceHeight.SMALL, Piece.PieceShape.ROUNDED, Piece.PieceTexture.HOLLOW);
        mapPieces.put(pieceR, tmp);
        map.put(new Boundary(62,123, 433,550), pieceR);

        pr = new RoundedPiece();
        pieceR = pr.startPiece(Color.BEIGE, false, false);
        pieceR.translateXProperty().set(-150);
        pieceR.translateYProperty().set(-40);
        pieceR.translateZProperty().set(-290);
        group.getChildren().add(pieceR);
        tmp = new QuartoPiece(Piece.PieceColor.CREAM, Piece.PieceHeight.SMALL, Piece.PieceShape.ROUNDED, Piece.PieceTexture.FULL);
        mapPieces.put(pieceR, tmp);
        map.put(new Boundary(158,222, 528,654), pieceR);

        //Wood textured pieces
        p = new SquaredPiece();
        pieceG = p.startPiece(Color.BROWN, false, true);
        pieceG.translateXProperty().set(100.0);
        pieceG.translateYProperty().set(-40);
        pieceG.translateZProperty().set(260);
        group.getChildren().add(pieceG);
        tmp = new QuartoPiece(Piece.PieceColor.RED, Piece.PieceHeight.SMALL, Piece.PieceShape.SQUARED, Piece.PieceTexture.HOLLOW);
        mapPieces.put(pieceG, tmp);
        map.put(new Boundary(541,578, 127,205), pieceG);

        p = new SquaredPiece();
        pieceG = p.startPiece(Color.BROWN, false, false);
        pieceG.translateXProperty().set(150.0);
        pieceG.translateYProperty().set(-40);
        pieceG.translateZProperty().set(230);
        group.getChildren().add(pieceG);
        tmp = new QuartoPiece(Piece.PieceColor.RED, Piece.PieceHeight.SMALL, Piece.PieceShape.SQUARED, Piece.PieceTexture.FULL);
        mapPieces.put(pieceG, tmp);
        map.put(new Boundary(596,635, 142,222), pieceG);

        p = new SquaredPiece();
        pieceG = p.startPiece(Color.BROWN, true, true);
        pieceG.translateXProperty().set(200);
        pieceG.translateYProperty().set(-80);
        pieceG.translateZProperty().set(200);
        group.getChildren().add(pieceG);
        tmp = new QuartoPiece(Piece.PieceColor.RED, Piece.PieceHeight.TALL, Piece.PieceShape.SQUARED, Piece.PieceTexture.HOLLOW);
        mapPieces.put(pieceG, tmp);
        map.put(new Boundary(659,698, 71,240), pieceG);

        p = new SquaredPiece();
        pieceG = p.startPiece(Color.BROWN, true, false);
        pieceG.translateXProperty().set(240.0);
        pieceG.translateYProperty().set(-80);
        pieceG.translateZProperty().set(140);
        group.getChildren().add(pieceG);
        tmp = new QuartoPiece(Piece.PieceColor.RED, Piece.PieceHeight.TALL, Piece.PieceShape.SQUARED, Piece.PieceTexture.FULL);
        mapPieces.put(pieceG, tmp);
        map.put(new Boundary(712,758, 98,275), pieceG);

        pr = new RoundedPiece();
        pieceR = pr.startPiece(Color.BROWN, true, false);
        pieceR.translateXProperty().set(270.0);
        pieceR.translateYProperty().set(-80);
        pieceR.translateZProperty().set(60);
        group.getChildren().add(pieceR);
        tmp = new QuartoPiece(Piece.PieceColor.RED, Piece.PieceHeight.TALL, Piece.PieceShape.ROUNDED, Piece.PieceTexture.FULL);
        mapPieces.put(pieceR, tmp);
        map.put(new Boundary(769,814, 140,328), pieceR);

        pr = new RoundedPiece();
        pieceR = pr.startPiece(Color.BROWN, true, true);
        pieceR.translateXProperty().set(280.0);
        pieceR.translateYProperty().set(-80);
        pieceR.translateZProperty().set(-70);
        group.getChildren().add(pieceR);
        tmp = new QuartoPiece(Piece.PieceColor.RED, Piece.PieceHeight.TALL, Piece.PieceShape.ROUNDED, Piece.PieceTexture.HOLLOW);
        mapPieces.put(pieceR, tmp);
        map.put(new Boundary(808,865, 223,426), pieceR);

        pr = new RoundedPiece();
        pieceR = pr.startPiece(Color.BROWN, false, true);
        pieceR.translateXProperty().set(230.0);
        pieceR.translateYProperty().set(-40);
        pieceR.translateZProperty().set(-200);
        group.getChildren().add(pieceR);
        tmp = new QuartoPiece(Piece.PieceColor.RED, Piece.PieceHeight.SMALL, Piece.PieceShape.ROUNDED, Piece.PieceTexture.HOLLOW);
        mapPieces.put(pieceR, tmp);
        map.put(new Boundary(763,829, 434,547), pieceR);

        pr = new RoundedPiece();
        pieceR = pr.startPiece(Color.BROWN, false, false);
        pieceR.translateXProperty().set(150);
        pieceR.translateYProperty().set(-40);
        pieceR.translateZProperty().set(-290);
        group.getChildren().add(pieceR);
        tmp = new QuartoPiece(Piece.PieceColor.RED, Piece.PieceHeight.SMALL, Piece.PieceShape.ROUNDED, Piece.PieceTexture.FULL);
        mapPieces.put(pieceR, tmp);
        map.put(new Boundary(660,728, 532,650), pieceR);
    }

    private Line createLine(double endX, double endY, double startX, double startY) {
        Line line = new Line();
        line.setEndX(endX);
        line.setEndY(endY);
        line.setStartX(startX);
        line.setStartY(startY);
        line.setStroke(Color.web("#fec704"));
        return line;
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

    public static void main(String[] args) {
        launch(args);
    }

    private void initMouseControl(SmartGroup group, Scene scene) {
        Rotate xRotate = new Rotate(0, Rotate.Y_AXIS);
        group.getTransforms().addAll(xRotate);
        xRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(event -> {
            anchorY = event.getSceneX();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged((event) -> {
            if (!dragging)
                dragging = true;
            angleY.set(anchorAngleY - (event.getSceneX() - anchorY));
        });
    }

}
