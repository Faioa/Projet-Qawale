package fr.serialcoders.qawaleproject.ui.controller;

import fr.serialcoders.qawaleproject.logic.Grid;
import fr.serialcoders.qawaleproject.logic.Piece;
import fr.serialcoders.qawaleproject.logic.Player;
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
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

public class QuartoController {

    private double anchorY;
    private double anchorAngleY = 0;
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private Map<Boundary, Group> map=new HashMap<>();
    private Map<Boundary, Button> buttonsBounds = new HashMap<>();
    private Map<Group, QuartoPiece> mapPieces = new HashMap<    >();
    private Group currentGroup=null;
    private boolean dragging;
    private boolean placed;
    private RotateTransition rotation;
    private Grid grid;
    private QuartoPlayer player1;
    private QuartoPlayer player2;
    private int currentPlayer;
    private boolean selecting;
    private Scene scene;
    private Label labelPlaying1;
    private Label labelPlaying2;
    private Group group;
    private Button buttonPause;
    private Button buttonEndTurn;
    private Button buttonHelp;
    private Button buttonQuarto;

    public void initialize() {
        dragging = false;
        placed = true;
        selecting = true;

        grid = new Grid(4, 4, new QuartoStrategy());

        buttonsBounds.put(new Boundary(20, 100, 20, 50), buttonPause);
        buttonPause.setOnAction((event) -> {
            showPause();
        });

        buttonsBounds.put(new Boundary(356, 446, 620, 650), buttonQuarto);
        buttonQuarto.setOnAction((event) -> {
            boolean result = grid.verify();
            int winnerId = result ? currentPlayer : (currentPlayer + 1) % 2;
            Player winner = winnerId == 0 ? player1 : player2;

            if (result)
                showVictoryDialog("VICTORY", "won the game rightfully !", winner);
            else
                showVictoryDialog("VICTORY", "won the game because its opponent called a wrong Quarto!", winner);
        });

        buttonsBounds.put(new Boundary(888, 918, 20, 50), buttonHelp);
        buttonHelp.setOnAction(event -> {
            showHelp();
        });

        buttonsBounds.put(new Boundary(466, 556, 620, 650), buttonEndTurn);
        buttonEndTurn.setOnAction((event) -> {

            if (player1.getPoolSize() == 0 && player2.getPoolSize() == 0) {
                showVictoryDialog("DRAW", "Nobody won this game...", null);
            }

            if (selecting || !placed)
                return;
            placed = false;

            currentPlayer = (currentPlayer + 1) % 2;
            if (currentPlayer == 0) {
                labelPlaying1.setText("Playing");
                labelPlaying1.setOpacity(1);
                labelPlaying2.setOpacity(0);
            } else {
                labelPlaying2.setText("Playing");
                labelPlaying1.setOpacity(0);
                labelPlaying2.setOpacity(1);
            }
        });
        buttonEndTurn.setPrefSize(90, 30);

        Board plateau = new Board();
        SmartGroup group2 = plateau.getBoard();
        rotation = new RotateTransition(Duration.seconds(0.5), group);
        rotation.setAxis(Rotate.Y_AXIS);
        rotation.setToAngle(0);

        Group plateGroup = new Group();
        plateGroup.getChildren().add(group2);
        plateGroup.getTransforms().addAll(
                new Rotate(35, Rotate.X_AXIS),
                new Rotate(0, Rotate.Y_AXIS),
                new Rotate(0, Rotate.Z_AXIS),
                new Translate(20, -100, -300)
        );


        initPartie(group2);

        List<QuartoPiece> values = new ArrayList<>(mapPieces.values());
        player1 = new QuartoPlayer("Player 1", grid, values);
        player2 = new QuartoPlayer("Player 2", grid, values);

        currentPlayer = new Random().nextInt(2);
        if (currentPlayer == 0) {
            labelPlaying1.setOpacity(1);
            labelPlaying2.setOpacity(0);
        } else {
            labelPlaying1.setOpacity(0);
            labelPlaying2.setOpacity(1);
        }

        scene.setOnMouseReleased((event) -> {
            if (dragging) {
                dragging = false;
                rotation.setFromAngle(angleY.get() % 360);
                rotation.playFromStart();
                angleY.set(0);
                return;
            }

            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();

            Node tmpButton = findButton(mouseX, mouseY);
            if (tmpButton != null) {
                tmpButton.fireEvent(new ActionEvent());
                return;
            }

            Group tmp = findPiece(mouseX, mouseY);

            // Selecting a piece for the opponent
            if (tmp != null) {
                currentGroup = tmp;
                selecting = false;

                if (currentPlayer == 0) {
                    player2.choosePiece(mapPieces.get(currentGroup));
                    labelPlaying1.setOpacity(0.5);
                } else {
                    player1.choosePiece(mapPieces.get(currentGroup));
                    labelPlaying2.setOpacity(0.5);
                }
            }
            // Placing a piece on the board
            else {
                if (selecting || placed || currentGroup == null)
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
                    selecting = true;
                    if (currentPlayer == 0)
                        labelPlaying1.setText("Selecting");
                    else
                        labelPlaying2.setText("Selecting");
                }
            }
        });

        group2.translateXProperty().set(350);
        group2.translateYProperty().set(200);
        group2.translateZProperty().set(-100);

        initMouseControl(group2, scene);

        group = plateGroup;
    }

    public void setLabelPlaying1(Label labelPlaying1) {
        this.labelPlaying1 = labelPlaying1;
    }

    public void setLabelPlaying2(Label labelPlaying2) {
        this.labelPlaying2 = labelPlaying2;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setButtonPause(Button buttonPause) {
        this.buttonPause = buttonPause;
    }

    public void setButtonEndTurn(Button buttonEndTurn) {
        this.buttonEndTurn = buttonEndTurn;
    }

    public void setButtonHelp(Button buttonHelp) {
        this.buttonHelp = buttonHelp;
    }

    public void setButtonQuarto(Button buttonQuarto) {
        this.buttonQuarto = buttonQuarto;
    }

    public Scene getScene() {
        return scene;
    }

    public Label getLabelPlaying1() {
        return labelPlaying1;
    }

    public Label getLabelPlaying2() {
        return labelPlaying2;
    }

    public Group getGroup() {
        return group;
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
        label.setTextFill(Color.web("#08d19a"));
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        return label;
    }

    private Group findPiece(double x, double y) {
        for(Boundary b : map.keySet()) {
            if(b.inBoundary(x, y)) {
                return map.get(b);
            }
        }
        return null;
    }

    private Button findButton(double x, double y) {
        for(Boundary b : buttonsBounds.keySet()) {
            if(b.inBoundary(x, y)) {
                return buttonsBounds.get(b);
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
        tmp = new QuartoPiece(Piece.PieceColor.CREAM, Piece.PieceHeight.TALL, Piece.PieceShape.SQUARED, Piece.PieceTexture.HOLLOW);
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
    private void showPause() {
        Stage primaryStage = (Stage) scene.getWindow();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pause");
        alert.setHeight(200);
        alert.setWidth(200);
        alert.setResizable(false);
        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.setStyle("-fx-background-color: black;");
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        Button homeButton = createButton("Home", 14, Color.BLACK, Color.web("#08d19a"), 843, 20);
        homeButton.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fr/serialcoders/qawaleproject/fxml/home.fxml"));
                Camera camera = new PerspectiveCamera();
                Scene scene = new Scene(root, 700, 400);
                scene.setFill(Color.SILVER);
                scene.setCamera(camera);
                primaryStage.setTitle("SerialCoders' Games");
                primaryStage.setScene(scene);
                primaryStage.show();
                Platform.runLater(alert::close);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        Button restartButton = createButton("Restart", 14, Color.BLACK, Color.web("#08d19a"), 843, 20);
        restartButton.setOnAction(e -> {
            setNewGame();
            Platform.runLater(alert::close);
            primaryStage.show();
        });

        Button resumeButton = createButton("Resume", 14, Color.BLACK, Color.web("#08d19a"), 843, 20);
        resumeButton.setOnAction(e -> {
            Platform.runLater(alert::close);
        });

        vbox.getChildren().addAll(homeButton,restartButton, resumeButton);

        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText("The game was paused");
        alert.getDialogPane().getChildren().remove(2);
        alert.getDialogPane().setContent(vbox);

        alert.showAndWait();
    }
    private void showHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rules");
        alert.setHeaderText(null);
        alert.setHeight(650);
        alert.setWidth(700);
        alert.setResizable(false);
        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.setStyle("-fx-background-color: black;");

        VBox anchorPane = new VBox();
        anchorPane.setAlignment(Pos.CENTER);
        anchorPane.setPrefSize(600, 611);

        Label titleLabel = new Label("SerialCoders' Games");
        titleLabel.setAlignment(javafx.geometry.Pos.CENTER);
        titleLabel.setPrefSize(204, 300);
        titleLabel.setTextFill(Color.web("#08d19a"));
        titleLabel.setFont(new Font("Snap ITC", 18.0));

        Pane rulesPane = new Pane();
        rulesPane.setLayoutX(56);
        rulesPane.setLayoutY(90);
        rulesPane.setPrefSize(412, 550);
        rulesPane.setStyle("-fx-background-color: white; -fx-background-radius: 30;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(480);
        scrollPane.setPrefWidth(550);
        scrollPane.setLayoutX(15);
        scrollPane.setLayoutY(15);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        Text rules = new Text("AIM OF THE GAME\n\n" + "To make a line of 4 similar pieces by color, shape, height or texture : Either horizontally, vertically or diagonally.\n\n\n"
                + "HOW TO PLAY\n\n"
                + "The first player is decided at random. On their turn, the player chooses a piece for its opponent who then needs to place it on the board.\n\n"
                + "Note : You need to select a piece whihc was not already placed on the board and you can only place one piece per spot.\n\n"
                + "The active player then select a piece for its opponent and so on.\n\n\n"
                + "END OF THE GAME\n\n"
                + "The first active player to recognize a line of 4 similar pieces presses \"Quarto\". If it was an effective line of 4 similar pieces, the player wins. Otherwise, it loses.\n\n"
                + "Note : If both players don't recognize the line on their first turn following its formation, this line becomes invalidated for the rest of the game.\n\n"
                + "Note : If both players have played all of their pieces and no one has recognized a line, the game ends in a draw.\n\n");
        rules.setTextAlignment(TextAlignment.CENTER);
        rules.setWrappingWidth(530);
        rules.setFont(new Font("Snap ITC", 14));
        scrollPane.setContent(rules);

        rulesPane.getChildren().add(scrollPane);

        anchorPane.getChildren().addAll(rulesPane);

        alert.getDialogPane().setContent(anchorPane);

        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText("Quarto Rules");
        alert.getDialogPane().setContent(anchorPane);

        alert.showAndWait();
    }
    private void showVictoryDialog(String title, String Message, Player myplayer) {
        Stage primaryStage = (Stage) scene.getWindow();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        if (myplayer == null)
            alert.setHeaderText(Message);
        else
            alert.setHeaderText(myplayer + " " + Message);
        alert.setHeight(200);
        alert.setWidth(400);
        alert.setResizable(false);

        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.setStyle("-fx-background-color: black;");
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        Button homeButton = createButton("Home", 14, Color.BLACK, Color.web("#08d19a"), 843, 20);
        homeButton.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fr/serialcoders/qawaleproject/fxml/home.fxml"));
                Camera camera = new PerspectiveCamera();
                Scene scene = new Scene(root, 700, 400);
                scene.setFill(Color.SILVER);
                scene.setCamera(camera);
                primaryStage.setTitle("SerialCoders' Games");
                primaryStage.setScene(scene);
                primaryStage.show();
                Platform.runLater(alert::close);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        Button newGameButton = createButton("New Game", 14, Color.BLACK, Color.web("#08d19a"), 843, 20);
        newGameButton.setOnAction(e -> {
            setNewGame();
            Platform.runLater(alert::close);
            primaryStage.show();
        });

        vbox.getChildren().addAll(homeButton, newGameButton);

        alert.initStyle(StageStyle.UNDECORATED);
        alert.getDialogPane().getChildren().remove(2);
        alert.getDialogPane().setContent(vbox);

        alert.showAndWait();

    }

    private void setNewGame() {
        Stage primaryStage = (Stage) this.scene.getWindow();

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
    }

}
