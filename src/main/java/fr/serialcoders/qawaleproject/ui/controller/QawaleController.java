package fr.serialcoders.qawaleproject.ui.controller;

import fr.serialcoders.qawaleproject.logic.Cell;
import fr.serialcoders.qawaleproject.logic.Grid;
import fr.serialcoders.qawaleproject.logic.Piece;
import fr.serialcoders.qawaleproject.logic.Player;
import fr.serialcoders.qawaleproject.logic.qawale.QawalePiece;
import fr.serialcoders.qawaleproject.logic.qawale.QawalePlayer;
import fr.serialcoders.qawaleproject.logic.qawale.QawaleStrategy;
import fr.serialcoders.qawaleproject.ui.Boundary;
import fr.serialcoders.qawaleproject.ui.model.qawale.QawaleBoard;
import fr.serialcoders.qawaleproject.ui.model.qawale.QawalePieceStack;
import fr.serialcoders.qawaleproject.ui.model.quarto.SmartGroup;
import javafx.animation.RotateTransition;
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
import javafx.scene.input.KeyCode;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QawaleController {
    private double anchorY;
    private double anchorAngleY = 0;
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private QawalePieceStack lgauche = new QawalePieceStack(0);
    private QawalePieceStack ldroite = new QawalePieceStack(1);
    private Map<Boundary, QawalePieceStack> map = new HashMap<>();
    private Map<Boundary, List<Group>> mapPlateau = new HashMap<>();
    private Group currentGroup = null;
    private boolean dragging;
    private boolean placed;
    private boolean clicked = false;
    private RotateTransition rotation;
    private QawalePieceStack tp = null;
    private QawalePieceStack tp2 = null;

    private List<Group> currentplace = new ArrayList<>();

    private List<Group> currentcase = new ArrayList<>();

    private int cptdepile = 0;

    private Grid grid;
    private QawalePlayer player1;
    private QawalePlayer player2;
    private int currentPlayer;
    private Map<Group, QawalePiece> mapPiecesPlayer1 = new HashMap<>();
    private Map<Group, QawalePiece> mapPiecesPlayer2 = new HashMap<>();

    private QawalePieceStack pilecourante;
    private int test = -1;
    private QawalePlayer myplayer;

    private int oldX;
    private int oldY;

    private int retouroldX;
    private int retouroldY;
    private boolean condition = true;
    private Label labelPlaying1;
    private Label labelPlaying2;
    private Scene scene;
    private Group plateauGroup;

    public void setLabelPlaying1(Label labelPlaying1) {
        this.labelPlaying1 = labelPlaying1;
    }

    public void setLabelPlaying2(Label labelPlaying2) {
        this.labelPlaying2 = labelPlaying2;
    }

    public Group getPlateauGroup() {
        return plateauGroup;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void initialize() {
        grid = new Grid(4, 4, new QawaleStrategy());

        dragging = false;
        placed = false;
        QawaleBoard plateau = new QawaleBoard();
        SmartGroup group = plateau.getBoard();
        rotation = new RotateTransition(Duration.seconds(0.5), group);
        rotation.setAxis(Rotate.Y_AXIS);
        rotation.setToAngle(0);

        Group plateGroup = new Group();
        plateGroup.getChildren().add(group);

        plateGroup.getTransforms().addAll(new Rotate(35, Rotate.X_AXIS),
                new Rotate(0, Rotate.Y_AXIS),
                new Rotate(0, Rotate.Z_AXIS),
                new Translate(20, -100, -300)
        );
        this.plateauGroup = plateGroup;

        initPartie(group);
        List<QawalePiece> piecePlayer1 = new ArrayList<>(mapPiecesPlayer1.values());
        List<QawalePiece> piecePlayer2 = new ArrayList<>(mapPiecesPlayer2.values());

        player1 = new QawalePlayer("Player 1", grid, piecePlayer1);
        player2 = new QawalePlayer("Player 2", grid, piecePlayer2);
        currentPlayer = (int) (Math.random() * 2);

        if (currentPlayer == 0) {
            myplayer = player1;
            labelPlaying1.setOpacity(1);
            labelPlaying2.setOpacity(0);
            pilecourante = lgauche;
        } else {
            myplayer = player2;
            labelPlaying1.setOpacity(0);
            labelPlaying2.setOpacity(1);
            pilecourante = ldroite;
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

            if (mouseX >= 888 && mouseX <= 918 && mouseY >= 20 && mouseY <= 50) {
                showHelp();
            }
            if (mouseX >= 20 && mouseX <= 100 && mouseY >=20 && mouseY <= 50) {
                showPause();
            }

            if (clicked) {
                currentcase = findPlace(mouseX, mouseY);

                // 1st line
                if (mouseX >= 279 && mouseX <= 346 && mouseY >= 260 && mouseY <= 298) {
                    if ((oldX == 0 && oldY == 0))
                        return;
                    if (retouroldX == 0 && retouroldY == 0)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;
                    if ((oldX != 1 && oldY != 0) && (oldX != 0 && oldY != 1))
                        return;

                    depiler(currentplace, currentcase, -122, 116, mouseX, mouseY, cptdepile--, 0, 0);
                    currentplace = currentcase;

                    oldX = 0;
                    oldY = 0;
                }

                if (mouseX >= 365 && mouseX <= 435 && mouseY >= 260 && mouseY <= 298) {
                    if (oldX == 0 && oldY == 1)
                        return;
                    if (retouroldX == 0 && retouroldY == 1)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;
                    if ((oldX == 1 && oldY == 1) || (oldX == 0 && oldY == 0) || (oldX == 0 && oldY == 2)) {
                        condition = false;
                    }
                    if (condition)
                        return;

                    condition = true;

                    depiler(currentplace, currentcase, -40, 116, mouseX, mouseY, cptdepile--, 0, 1);
                    currentplace = currentcase;
                    oldX = 0;
                    oldY = 1;
                }

                if (mouseX >= 463 && mouseX <= 533 && mouseY >= 260 && mouseY <= 298) {
                    if (oldX == 0 && oldY == 2)
                        return;
                    if (retouroldX == 0 && retouroldY == 2)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;

                    if ((oldX == 0 && oldY == 1) || (oldX == 1 && oldY == 2) || (oldX == 0 && oldY == 3)) {
                        condition = false;
                    }
                    if (condition)
                        return;

                    condition = true;

                    depiler(currentplace, currentcase, 40, 116, mouseX, mouseY, cptdepile--, 0, 2);
                    currentplace = currentcase;
                    oldX = 0;
                    oldY = 2;
                }

                if (mouseX >= 560 && mouseX <= 630 && mouseY >= 260 && mouseY <= 298) {
                    if (oldX == 0 && oldY == 3)
                        return;
                    if (retouroldX == 0 && retouroldY == 3)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;

                    if ((oldX == 0 && oldY == 2) || (oldX == 1 && oldY == 3)) {
                        condition = false;
                    }
                    if (condition)
                        return;

                    condition = true;

                    depiler(currentplace, currentcase, 121, 116, mouseX, mouseY, cptdepile--, 0, 3);
                    currentplace = currentcase;

                    oldX = 0;
                    oldY = 3;
                }

                // 2nd line
                if (mouseX >= 270 && mouseX <= 340 && mouseY >= 316 && mouseY <= 349) {
                    if (oldX == 1 && oldY == 0)
                        return;
                    if (retouroldX == 1 && retouroldY == 0)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;

                    if ((oldX == 0 && oldY == 0) || (oldX == 2 && oldY == 0) || (oldX == 1 && oldY == 1)) {
                        condition = false;
                    }
                    if (condition)
                        return;

                    condition = true;

                    depiler(currentplace, currentcase, -121, 40.3, mouseX, mouseY, cptdepile--, 1, 0);

                    currentplace = currentcase;

                    oldX = 1;
                    oldY = 0;
                }

                if (mouseX >= 365 && mouseX <= 435 && mouseY >= 316 && mouseY <= 349) {
                    if (oldX == 1 && oldY == 1)
                        return;
                    if (retouroldX == 1 && retouroldY == 1)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;

                    if ((oldX == 1 && oldY == 0) || (oldX == 1 && oldY == 2) || (oldX == 2 && oldY == 1)
                            || (oldX == 0 && oldY == 1)) {
                        condition = false;
                    }
                    if (condition)
                        return;

                    condition = true;

                    depiler(currentplace, currentcase, -40, 40.3, mouseX, mouseY, cptdepile--, 1, 1);
                    currentplace = currentcase;
                    oldX = 1;
                    oldY = 1;
                }

                if (mouseX >= 463 && mouseX <= 533 && mouseY >= 316 && mouseY <= 349) {
                    if (oldX == 1 && oldY == 2)
                        return;
                    if (retouroldX == 1 && retouroldY == 2)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;

                    if ((oldX == 1 && oldY == 1) || (oldX == 0 && oldY == 2) || (oldX == 1 && oldY == 3)
                            || (oldX == 2 && oldY == 2)) {
                        condition = false;
                    }
                    if (condition)
                        return;

                    condition = true;

                    depiler(currentplace, currentcase, 40, 40.3, mouseX, mouseY, cptdepile--, 1, 2);
                    currentplace = currentcase;
                    oldX = 1;
                    oldY = 2;
                }

                if (mouseX >= 560 && mouseX <= 630 && mouseY >= 316 && mouseY <= 349) {
                    if (oldX == 1 && oldY == 3)
                        return;
                    if (retouroldX == 1 && retouroldY == 3)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;

                    if ((oldX == 1 && oldY == 2) || (oldX == 0 && oldY == 3) || (oldX == 2 && oldY == 3)) {
                        condition = false;
                    }
                    if (condition)
                        return;

                    condition = true;

                    depiler(currentplace, currentcase, 121, 40.3, mouseX, mouseY, cptdepile--, 1, 3);
                    currentplace = currentcase;
                    oldX = 1;
                    oldY = 3;
                }

                // 3rd line
                if (mouseX >= 270 && mouseX <= 340 && mouseY >= 366 && mouseY <= 417) {
                    if (oldX == 2 && oldY == 0)
                        return;
                    if (retouroldX == 2 && retouroldY == 0)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;

                    if ((oldX == 1 && oldY == 0) || (oldX == 2 && oldY == 1) || (oldX == 3 && oldY == 0)) {
                        condition = false;
                    }
                    if (condition)
                        return;

                    condition = true;

                    depiler(currentplace, currentcase, -121.0, -35.4, mouseX, mouseY, cptdepile--, 2, 0);
                    currentplace = currentcase;
                    oldX = 2;
                    oldY = 0;
                }

                if (mouseX >= 365 && mouseX <= 435 && mouseY >= 366 && mouseY <= 417) {
                    if (oldX == 2 && oldY == 1)
                        return;
                    if (retouroldX == 2 && retouroldY == 1)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;

                    if ((oldX == 2 && oldY == 0) || (oldX == 1 && oldY == 1) || (oldX == 3 && oldY == 1)
                            || (oldX == 2 && oldY == 2)) {
                        condition = false;
                    }
                    if (condition)
                        return;

                    condition = true;

                    depiler(currentplace, currentcase, -40, -35.4, mouseX, mouseY, cptdepile--, 2, 1);
                    currentplace = currentcase;
                    oldX = 2;
                    oldY = 1;
                }

                if (mouseX >= 463 && mouseX <= 533 && mouseY >= 366 && mouseY <= 417) {
                    if (oldX == 2 && oldY == 2)
                        return;
                    if (retouroldX == 2 && retouroldY == 2)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;

                    if ((oldX == 2 && oldY == 3) || (oldX == 3 && oldY == 2) || (oldX == 1 && oldY == 2)
                            || (oldX == 2 && oldY == 1)) {
                        condition = false;
                    }
                    if (condition)
                        return;

                    condition = true;

                    depiler(currentplace, currentcase, 40, -35.4, mouseX, mouseY, cptdepile--, 2, 2);
                    currentplace = currentcase;
                    oldX = 2;
                    oldY = 2;
                }

                if (mouseX >= 560 && mouseX <= 630 && mouseY >= 366 && mouseY <= 417) {
                    if (oldX == 2 && oldY == 3)
                        return;
                    if (retouroldX == 2 && retouroldY == 3)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;

                    if ((oldX == 2 && oldY == 2) || (oldX == 1 && oldY == 3) || (oldX == 3 && oldY == 3)) {
                        condition = false;
                    }
                    if (condition)
                        return;

                    condition = true;

                    depiler(currentplace, currentcase, 121, -35.4, mouseX, mouseY, cptdepile--, 2, 3);
                    currentplace = currentcase;
                    oldX = 2;
                    oldY = 3;
                }

                // 4th line
                if (mouseX >= 270 && mouseX <= 340 && mouseY >= 429 && mouseY <= 488) {
                    if (oldX == 3 && oldY == 0)
                        return;
                    if (retouroldX == 3 && retouroldY == 0)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;

                    if ((oldX == 2 && oldY == 0) || (oldX == 3 && oldY == 1)) {
                        condition = false;
                    }
                    if (condition)
                        return;

                    condition = true;

                    depiler(currentplace, currentcase, -121.0, -120, mouseX, mouseY, cptdepile--, 3, 0);
                    currentplace = currentcase;
                    oldX = 3;
                    oldY = 0;
                }

                if (mouseX >= 365 && mouseX <= 435 && mouseY >= 429 && mouseY <= 488) {
                    if (oldX == 3 && oldY == 1)
                        return;
                    if (retouroldX == 3 && retouroldY == 1)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;

                    if ((oldX == 3 && oldY == 0) || (oldX == 2 && oldY == 1) || (oldX == 3 && oldY == 2)) {
                        condition = false;
                    }
                    if (condition)
                        return;

                    condition = true;

                    depiler(currentplace, currentcase, -40, -120, mouseX, mouseY, cptdepile--, 3, 1);
                    currentplace = currentcase;
                    oldX = 3;
                    oldY = 1;
                }

                if (mouseX >= 463 && mouseX <= 533 && mouseY >= 429 && mouseY <= 488) {
                    if (oldX == 3 && oldY == 2)
                        return;
                    if (retouroldX == 3 && retouroldY == 2)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;

                    if ((oldX == 3 && oldY == 1) || (oldX == 2 && oldY == 2) || (oldX == 3 && oldY == 3)) {
                        condition = false;
                    }
                    if (condition)
                        return;

                    condition = true;

                    depiler(currentplace, currentcase, 40, -120, mouseX, mouseY, cptdepile--, 3, 2);
                    currentplace = currentcase;
                    oldX = 3;
                    oldY = 2;
                }

                if (mouseX >= 560 && mouseX <= 630 && mouseY >= 429 && mouseY <= 488) {
                    if (oldX == 3 && oldY == 3)
                        return;
                    if (retouroldX == 3 && retouroldY == 3)
                        return;
                    retouroldX = oldX;
                    retouroldY = oldY;

                    if ((oldX == 3 && oldY == 2) || (oldX == 2 && oldY == 3)) {
                        condition = false;
                    }
                    if (condition)
                        return;

                    condition = true;

                    depiler(currentplace, currentcase, 121, -120, mouseX, mouseY, cptdepile--, 3, 3);
                    currentplace = currentcase;
                    oldX = 3;
                    oldY = 3;
                }

                if (cptdepile == 0) {
                    clicked = false;
                    if (lgauche.size() == 0 && ldroite.size() == 0) {
                        showVictoryDialog("DRAW", "Nobody won this game...", null);
                    }

                    if (grid.verify()) {
                        showVictoryDialog("VICTORY", "won the game !", myplayer);

                    }

                    test = -1;
                    currentPlayer = (currentPlayer + 1) % 2;


                    if (currentPlayer == 0) {
                        myplayer = player1;
                        labelPlaying1.setOpacity(1);
                        labelPlaying2.setOpacity(0);
                        pilecourante = lgauche;
                    } else {
                        myplayer = player2;
                        labelPlaying1.setOpacity(0);
                        labelPlaying2.setOpacity(1);
                        pilecourante = ldroite;
                    }
                }
            }

            else {
                if (findPile(mouseX, mouseY) == pilecourante && test == -1) {
                    tp = findPile(mouseX, mouseY);
                    test++;
                } else {
                    tp = null;
                }

                if (tp != null) {
                    tp2 = tp;
                }

                Group tmp = findPiece(tp);

                if (tmp != null) {
                    currentGroup = tmp;

                    if (currentPlayer == 0) {
                        myplayer = player1;
                        labelPlaying1.setOpacity(1);
                        labelPlaying2.setOpacity(0);
                    } else {
                        myplayer = player2;
                        labelPlaying1.setOpacity(0);
                        labelPlaying2.setOpacity(1);
                    }
                } else {

                    if (currentGroup == null)
                        return;

                    // 1st line
                    if (mouseX >= 279 && mouseX <= 346 && mouseY >= 260 && mouseY <= 298) {
                        if (grid.getCell(0, 0).getContent().isEmpty())
                            return;
                        currentGroup.translateXProperty().set(-122.0);
                        currentGroup.translateYProperty().set(verifierSize(279, 260) * (-15) - 5);
                        currentGroup.translateZProperty().set(116);
                        oldX = 0;
                        oldY = 0;

                        retouroldX = oldX;
                        retouroldY = oldY;

                        myplayer.putPiece(grid, oldX, oldY);

                        currentplace = findPlace(279, 260);
                        currentplace.add(currentGroup);

                        placed = true;
                    }

                    if (mouseX >= 365 && mouseX <= 435 && mouseY >= 260 && mouseY <= 298) {
                        if (grid.getCell(0, 1).getContent().isEmpty())
                            return;
                        currentGroup.translateXProperty().set(-40);
                        currentGroup.translateYProperty().set(verifierSize(365, 260) * (-15) - 5);
                        currentGroup.translateZProperty().set(116);
                        currentplace = findPlace(365, 260);
                        currentplace.add(currentGroup);

                        oldX = 0;
                        oldY = 1;
                        retouroldX = oldX;
                        retouroldY = oldY;
                        myplayer.putPiece(grid, oldX, oldY);

                        placed = true;
                    }

                    if (mouseX >= 463 && mouseX <= 533 && mouseY >= 260 && mouseY <= 298) {
                        if (grid.getCell(0, 2).getContent().isEmpty())
                            return;
                        currentGroup.translateXProperty().set(40);
                        currentGroup.translateYProperty().set(verifierSize(463, 260) * (-15) - 5);
                        currentGroup.translateZProperty().set(116);

                        currentplace = findPlace(463, 260);

                        currentplace.add(currentGroup);
                        oldX = 0;
                        oldY = 2;
                        retouroldX = oldX;
                        retouroldY = oldY;

                        myplayer.putPiece(grid, oldX, oldY);

                        placed = true;
                    }

                    if (mouseX >= 560 && mouseX <= 630 && mouseY >= 260 && mouseY <= 298) {
                        if (grid.getCell(0, 3).getContent().isEmpty())
                            return;
                        currentGroup.translateXProperty().set(121);
                        currentGroup.translateYProperty().set(verifierSize(560, 260) * (-15) - 5);
                        currentGroup.translateZProperty().set(116);
                        currentplace = findPlace(560, 260);

                        currentplace.add(currentGroup);
                        placed = true;
                        oldX = 0;
                        oldY = 3;
                        retouroldX = oldX;
                        retouroldY = oldY;
                        myplayer.putPiece(grid, oldX, oldY);
                    }

                    // 2nd line
                    if (mouseX >= 270 && mouseX <= 340 && mouseY >= 316 && mouseY <= 349) {
                        if (grid.getCell(1, 0).getContent().isEmpty())
                            return;
                        currentGroup.translateXProperty().set(-121.0);
                        currentGroup.translateYProperty().set(verifierSize(270, 316) * (-15) - 5);
                        currentGroup.translateZProperty().set(40.3);

                        currentplace = findPlace(270, 316);

                        currentplace.add(currentGroup);
                        placed = true;

                        oldX = 1;
                        oldY = 0;
                        retouroldX = oldX;
                        retouroldY = oldY;
                        myplayer.putPiece(grid, oldX, oldY);
                    }

                    if (mouseX >= 365 && mouseX <= 435 && mouseY >= 316 && mouseY <= 349) {
                        if (grid.getCell(1, 1).getContent().isEmpty())
                            return;
                        currentGroup.translateXProperty().set(-40);
                        currentGroup.translateYProperty().set(verifierSize(365, 316) * (-15) - 5);
                        currentGroup.translateZProperty().set(40.3);

                        currentplace = findPlace(365, 316);

                        currentplace.add(currentGroup);
                        placed = true;

                        oldX = 1;
                        oldY = 1;
                        retouroldX = oldX;
                        retouroldY = oldY;
                        myplayer.putPiece(grid, oldX, oldY);
                    }

                    if (mouseX >= 463 && mouseX <= 533 && mouseY >= 316 && mouseY <= 349) {
                        if (grid.getCell(1, 2).getContent().isEmpty())
                            return;

                        currentGroup.translateXProperty().set(40);
                        currentGroup.translateYProperty().set(verifierSize(463, 316) * (-15) - 5);
                        currentGroup.translateZProperty().set(40.3);

                        currentplace = findPlace(463, 316);

                        currentplace.add(currentGroup);
                        placed = true;
                        oldX = 1;
                        oldY = 2;
                        retouroldX = oldX;
                        retouroldY = oldY;
                        myplayer.putPiece(grid, oldX, oldY);
                    }

                    if (mouseX >= 560 && mouseX <= 630 && mouseY >= 316 && mouseY <= 349) {
                        if (grid.getCell(1, 3).getContent().isEmpty())
                            return;

                        currentGroup.translateXProperty().set(121);
                        currentGroup.translateYProperty().set(verifierSize(560, 316) * (-15) - 5);
                        currentGroup.translateZProperty().set(40.3);

                        currentplace = findPlace(560, 316);

                        currentplace.add(currentGroup);
                        placed = true;

                        oldX = 1;
                        oldY = 3;
                        retouroldX = oldX;
                        retouroldY = oldY;
                        myplayer.putPiece(grid, oldX, oldY);
                    }

                    // 3rd line
                    if (mouseX >= 270 && mouseX <= 340 && mouseY >= 366 && mouseY <= 417) {
                        if (grid.getCell(2, 0).getContent().isEmpty())
                            return;

                        currentGroup.translateXProperty().set(-121.0);
                        currentGroup.translateYProperty().set(verifierSize(270, 366) * (-15) - 5);
                        currentGroup.translateZProperty().set(-35.4);

                        currentplace = findPlace(270, 366);

                        currentplace.add(currentGroup);
                        placed = true;

                        oldX = 2;
                        oldY = 0;
                        retouroldX = oldX;
                        retouroldY = oldY;
                        myplayer.putPiece(grid, oldX, oldY);
                    }

                    if (mouseX >= 365 && mouseX <= 435 && mouseY >= 366 && mouseY <= 417) {
                        if (grid.getCell(2, 1).getContent().isEmpty())
                            return;

                        currentGroup.translateXProperty().set(-40);
                        currentGroup.translateYProperty().set(verifierSize(365, 366) * (-15) - 5);
                        currentGroup.translateZProperty().set(-35.4);

                        currentplace = findPlace(365, 366);

                        currentplace.add(currentGroup);
                        placed = true;
                        oldX = 2;
                        oldY = 1;

                        retouroldX = oldX;
                        retouroldY = oldY;

                        myplayer.putPiece(grid, oldX, oldY);
                    }

                    if (mouseX >= 463 && mouseX <= 533 && mouseY >= 366 && mouseY <= 417) {
                        if (grid.getCell(2, 2).getContent().isEmpty())
                            return;

                        currentGroup.translateXProperty().set(40);
                        currentGroup.translateYProperty().set(verifierSize(463, 366) * (-15) - 5);
                        currentGroup.translateZProperty().set(-35.4);

                        currentplace = findPlace(463, 366);

                        currentplace.add(currentGroup);
                        placed = true;
                        oldX = 2;
                        oldY = 2;

                        retouroldX = oldX;
                        retouroldY = oldY;

                        myplayer.putPiece(grid, oldX, oldY);
                    }

                    if (mouseX >= 560 && mouseX <= 630 && mouseY >= 366 && mouseY <= 417) {
                        if (grid.getCell(2, 3).getContent().isEmpty())
                            return;

                        currentGroup.translateXProperty().set(121);
                        currentGroup.translateYProperty().set(verifierSize(560, 366) * (-15) - 5);
                        currentGroup.translateZProperty().set(-35.4);

                        currentplace = findPlace(560, 366);

                        currentplace.add(currentGroup);
                        placed = true;
                        oldX = 2;
                        oldY = 3;

                        retouroldX = oldX;
                        retouroldY = oldY;

                        myplayer.putPiece(grid, oldX, oldY);
                    }

                    // 4th line
                    if (mouseX >= 270 && mouseX <= 340 && mouseY >= 429 && mouseY <= 488) {
                        if (grid.getCell(3, 0).getContent().isEmpty())
                            return;

                        currentGroup.translateXProperty().set(-121.0);
                        currentGroup.translateYProperty().set(verifierSize(270, 429) * (-15) - 5);
                        currentGroup.translateZProperty().set(-120);

                        currentplace = findPlace(270, 429);

                        currentplace.add(currentGroup);
                        placed = true;
                        oldX = 3;
                        oldY = 0;

                        retouroldX = oldX;
                        retouroldY = oldY;

                        myplayer.putPiece(grid, oldX, oldY);
                    }

                    if (mouseX >= 365 && mouseX <= 435 && mouseY >= 429 && mouseY <= 488) {
                        if (grid.getCell(3, 1).getContent().isEmpty())
                            return;

                        currentGroup.translateXProperty().set(-40);
                        currentGroup.translateYProperty().set(verifierSize(365, 429) * (-15) - 5);
                        currentGroup.translateZProperty().set(-120);

                        currentplace = findPlace(365, 429);

                        currentplace.add(currentGroup);

                        placed = true;

                        oldX = 3;
                        oldY = 1;

                        retouroldX = oldX;
                        retouroldY = oldY;

                        myplayer.putPiece(grid, oldX, oldY);
                    }

                    if (mouseX >= 463 && mouseX <= 533 && mouseY >= 429 && mouseY <= 488) {
                        if (grid.getCell(3, 2).getContent().isEmpty())
                            return;

                        currentGroup.translateXProperty().set(40);
                        currentGroup.translateYProperty().set(verifierSize(463, 429) * (-15) - 5);
                        currentGroup.translateZProperty().set(-120);

                        currentplace = findPlace(463, 429);

                        currentplace.add(currentGroup);
                        placed = true;

                        oldX = 3;
                        oldY = 2;

                        retouroldX = oldX;
                        retouroldY = oldY;

                        myplayer.putPiece(grid, oldX, oldY);
                    }

                    if (mouseX >= 560 && mouseX <= 630 && mouseY >= 429 && mouseY <= 488) {
                        if (grid.getCell(3, 3).getContent().isEmpty())
                            return;

                        currentGroup.translateXProperty().set(121);
                        currentGroup.translateYProperty().set(verifierSize(560, 429) * (-15) - 5);
                        currentGroup.translateZProperty().set(-120);

                        currentplace = findPlace(560, 429);

                        currentplace.add(currentGroup);
                        placed = true;

                        oldX = 3;
                        oldY = 3;

                        retouroldX = oldX;
                        retouroldY = oldY;

                        myplayer.putPiece(grid, oldX, oldY);
                    }

                    if (placed) {
                        currentGroup = null;
                        if (tp2 != null) {
                            cptdepile = currentplace.size();
                            tp2.delete();
                            clicked = true;
                        }
                        placed = false;
                    }
                }
            }
        });

        group.translateXProperty().set(350);
        group.translateYProperty().set(200);
        group.translateZProperty().set(-100);

        initMouseControl(group, scene);
    }

    private QawalePieceStack findPile(double x, double y) {
        for (Boundary b : map.keySet()) {
            if (b.inBoundary(x, y)) {
                return map.get(b);
            }
        }
        return null;
    }

    private Group findPiece(QawalePieceStack pile) {
        if (pile == null)
            return null;
        if (pile.size() == 0) {
            return null;
        }
        return pile.top();
    }

    private int verifierSize(double x, double y) {
        for (Boundary b : mapPlateau.keySet()) {
            if (b.inBoundary(x, y)) {
                return mapPlateau.get(b).size();
            }
        }
        throw new IllegalStateException();
    }

    private List<Group> findPlace(double x, double y) {
        for (Boundary b : mapPlateau.keySet()) {
            if (b.inBoundary(x, y)) {
                return mapPlateau.get(b);
            }
        }

        return null;
    }

    public void depiler(List<Group> place, List<Group> cases, double x, double z, double mousex, double mousey, int compteur, int newX, int newY) {
        Cell cellprec = grid.getCell(oldX, oldY).copy();

        grid.getCell(oldX, oldY).clear();

        int arret = place.size() - compteur;

        for (int i = arret; i <= place.size() - 1; i++) {
            Group g = place.get(i);

            int size = (verifierSize(mousex, mousey) * (-15) - 5);
            cases.add(g);

            g.translateXProperty().set(x);
            g.translateYProperty().set(size);
            g.translateZProperty().set(z);

            grid.getCell(newX, newY).add(cellprec.getPieceIndex(i));
        }

        for (int i = place.size() - 1; i >= arret; i--) {
            place.remove(i);
        }

        for (int i = 0; i < arret; i++) {
            grid.getCell(oldX, oldY).add(cellprec.getPieceIndex(i));
        }
    }

    private void initPartie(SmartGroup group) {
        QawalePiece tmp;

        // 1st line
        mapPlateau.put(new Boundary(279, 346, 260, 298), new ArrayList<>());
        mapPlateau.put(new Boundary(365, 435, 260, 298), new ArrayList<>());
        mapPlateau.put(new Boundary(463, 533, 260, 298), new ArrayList<>());
        mapPlateau.put(new Boundary(560, 630, 260, 298), new ArrayList<>());

        // 2nd line
        mapPlateau.put(new Boundary(270, 340, 316, 349), new ArrayList<>());
        mapPlateau.put(new Boundary(365, 435, 316, 349), new ArrayList<>());
        mapPlateau.put(new Boundary(463, 533, 316, 349), new ArrayList<>());
        mapPlateau.put(new Boundary(560, 630, 316, 349), new ArrayList<>());

        // 3rd line
        mapPlateau.put(new Boundary(270, 340, 366, 417), new ArrayList<>());
        mapPlateau.put(new Boundary(365, 435, 366, 417), new ArrayList<>());
        mapPlateau.put(new Boundary(463, 533, 366, 417), new ArrayList<>());
        mapPlateau.put(new Boundary(560, 630, 366, 417), new ArrayList<>());

        // 4th line
        mapPlateau.put(new Boundary(270, 340, 429, 488), new ArrayList<>());
        mapPlateau.put(new Boundary(365, 435, 429, 488), new ArrayList<>());
        mapPlateau.put(new Boundary(463, 533, 429, 488), new ArrayList<>());
        mapPlateau.put(new Boundary(560, 630, 429, 488), new ArrayList<>());

        // Pieces
        fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        SmartGroup pieceG = p.startPiece(Color.ORANGE);
        pieceG.translateXProperty().set(-122.0);
        pieceG.translateYProperty().set(-5);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        findPlace(279, 260).add(pieceG);
        grid.putPiece(new QawalePiece(Piece.PieceColor.CREAM), 0, 0);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.ORANGE);
        pieceG.translateXProperty().set(-122.0);
        pieceG.translateYProperty().set(-20);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        findPlace(279, 260).add(pieceG);
        grid.putPiece(new QawalePiece(Piece.PieceColor.CREAM), 0, 0);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.ORANGE);
        pieceG.translateXProperty().set(121.0);
        pieceG.translateYProperty().set(-5);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        findPlace(560, 260).add(pieceG);
        grid.putPiece(new QawalePiece(Piece.PieceColor.CREAM), 0, 3);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.ORANGE);
        pieceG.translateXProperty().set(121.0);
        pieceG.translateYProperty().set(-20);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        findPlace(560, 260).add(pieceG);
        grid.putPiece(new QawalePiece(Piece.PieceColor.CREAM), 0, 3);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.ORANGE);
        pieceG.translateXProperty().set(-121.0);
        pieceG.translateYProperty().set(-5);
        pieceG.translateZProperty().set(-116);
        group.getChildren().add(pieceG);
        findPlace(270, 429).add(pieceG);
        grid.putPiece(new QawalePiece(Piece.PieceColor.CREAM), 3, 0);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.ORANGE);
        pieceG.translateXProperty().set(-121.0);
        pieceG.translateYProperty().set(-20);
        pieceG.translateZProperty().set(-116);
        group.getChildren().add(pieceG);
        findPlace(270, 429).add(pieceG);
        grid.putPiece(new QawalePiece(Piece.PieceColor.CREAM), 3, 0);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.ORANGE);
        pieceG.translateXProperty().set(121.0);
        pieceG.translateYProperty().set(-5);
        pieceG.translateZProperty().set(-120);
        group.getChildren().add(pieceG);
        findPlace(560, 429).add(pieceG);
        grid.putPiece(new QawalePiece(Piece.PieceColor.CREAM), 3, 3);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.ORANGE);
        pieceG.translateXProperty().set(121.0);
        pieceG.translateYProperty().set(-20);
        pieceG.translateZProperty().set(-120);
        group.getChildren().add(pieceG);
        findPlace(560, 429).add(pieceG);
        grid.putPiece(new QawalePiece(Piece.PieceColor.CREAM), 3, 3);

        // Red Pieces
        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BROWN);
        pieceG.translateXProperty().set(-242.0);
        pieceG.translateYProperty().set(-5);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        lgauche.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.RED);
        mapPiecesPlayer1.put(pieceG, tmp);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BROWN);
        pieceG.translateXProperty().set(-242.0);
        pieceG.translateYProperty().set(-20);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        lgauche.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.RED);
        mapPiecesPlayer1.put(pieceG, tmp);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BROWN);
        pieceG.translateXProperty().set(-242.0);
        pieceG.translateYProperty().set(-35);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        lgauche.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.RED);
        mapPiecesPlayer1.put(pieceG, tmp);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BROWN);
        pieceG.translateXProperty().set(-242.0);
        pieceG.translateYProperty().set(-50);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        lgauche.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.RED);
        mapPiecesPlayer1.put(pieceG, tmp);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BROWN);
        pieceG.translateXProperty().set(-242.0);
        pieceG.translateYProperty().set(-65);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        lgauche.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.RED);
        mapPiecesPlayer1.put(pieceG, tmp);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BROWN);
        pieceG.translateXProperty().set(-242.0);
        pieceG.translateYProperty().set(-80);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        lgauche.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.RED);
        mapPiecesPlayer1.put(pieceG, tmp);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BROWN);
        pieceG.translateXProperty().set(-242.0);
        pieceG.translateYProperty().set(-95);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        lgauche.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.RED);
        mapPiecesPlayer1.put(pieceG, tmp);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BROWN);
        pieceG.translateXProperty().set(-242.0);
        pieceG.translateYProperty().set(-110);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        lgauche.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.RED);
        mapPiecesPlayer1.put(pieceG, tmp);

        map.put(new Boundary(122, 185, 154, 302), lgauche);

        // White Pieces
        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BEIGE);
        pieceG.translateXProperty().set(242.0);
        pieceG.translateYProperty().set(-5);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        ldroite.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.YELLOW);
        mapPiecesPlayer2.put(pieceG, tmp);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BEIGE);
        pieceG.translateXProperty().set(242.0);
        pieceG.translateYProperty().set(-20);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        ldroite.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.YELLOW);
        mapPiecesPlayer2.put(pieceG, tmp);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BEIGE);
        pieceG.translateXProperty().set(242.0);
        pieceG.translateYProperty().set(-35);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        ldroite.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.YELLOW);
        mapPiecesPlayer2.put(pieceG, tmp);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BEIGE);
        pieceG.translateXProperty().set(242.0);
        pieceG.translateYProperty().set(-50);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        ldroite.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.YELLOW);
        mapPiecesPlayer2.put(pieceG, tmp);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BEIGE);
        pieceG.translateXProperty().set(242.0);
        pieceG.translateYProperty().set(-65);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        ldroite.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.YELLOW);
        mapPiecesPlayer2.put(pieceG, tmp);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BEIGE);
        pieceG.translateXProperty().set(242.0);
        pieceG.translateYProperty().set(-80);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        ldroite.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.YELLOW);
        mapPiecesPlayer2.put(pieceG, tmp);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BEIGE);
        pieceG.translateXProperty().set(242.0);
        pieceG.translateYProperty().set(-95);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        ldroite.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.YELLOW);
        mapPiecesPlayer2.put(pieceG, tmp);

        p = new fr.serialcoders.qawaleproject.ui.model.qawale.QawalePiece();
        pieceG = p.startPiece(Color.BEIGE);
        pieceG.translateXProperty().set(242.0);
        pieceG.translateYProperty().set(-110);
        pieceG.translateZProperty().set(116);
        group.getChildren().add(pieceG);
        ldroite.add(pieceG);
        tmp = new QawalePiece(Piece.PieceColor.YELLOW);
        mapPiecesPlayer2.put(pieceG, tmp);

        map.put(new Boundary(718, 778, 154, 302), ldroite);

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
        return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255),
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
        anchorPane.setPrefSize(600, 600);

        Pane rulesPane = new Pane();
        rulesPane.setLayoutX(56);
        rulesPane.setLayoutY(90);
        rulesPane.setPrefSize(580, 580);
        rulesPane.setStyle("-fx-background-color: white; -fx-background-radius: 30;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(480);
        scrollPane.setPrefWidth(550);
        scrollPane.setLayoutX(15);
        scrollPane.setLayoutY(15);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        Text rules = new Text("AIM OF THE GAME\n\n" + "To make a line of 4 pebbles of your color: Either horizontally, vertically or diagonally.\n\n\n"
                + "HOW TO PLAY\n\n"
                + "The first player is decided at random. On their turn, the player chooses a stack of pebbles and places a pebble of their color on top. You cannot place a pebble in an empty space\n\n"
                + "Note : A single pebble in a space is considered to be a stack.\n\n"
                + "The active player then takes that stack of pebbles and plays them, starting in an adjacent space. The player places down the pebbles one at a time, starting with the pebble at the bottom of the stack. Each pebble must be placed orthogonally adjacent to the previous one.\n\n"
                + "Note : You cannot go back over a space that you have just passed through. However, you can return to a particular space by circling back to it.\n\n\n"
                + "END OF THE GAME\n\n"
                + "The first player to make a visible line of 4 pebble of their color in a row, column or diagonal wins the game.\n\n"
                + "Note : A counter is considered to be visible if it is the top or only pebble in a stack.\n\n"
                + "If both players have played all of their pebbles and no one has made a line, the game ends in a draw.\n\n"
                + "Note : A stack of 4 pebbles of the same color is not considered to be a line");
        rules.setTextAlignment(TextAlignment.CENTER);
        rules.setWrappingWidth(530);
        rules.setFont(new Font("Snap ITC", 14));
        scrollPane.setContent(rules);

        rulesPane.getChildren().add(scrollPane);

        anchorPane.getChildren().addAll(rulesPane);


        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText("Qawale Rules");
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
            alert.setHeaderText(myplayer.getName() + " " + Message);
        alert.setHeight(200);
        alert.setWidth(200);
        alert.setResizable(false);

        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.setStyle("-fx-background-color: black;");
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        Button homeButton = createButton("Retour à la page d'accueil", 14, Color.BLACK, Color.web("#08d19a"), 843, 20);
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

        vbox.getChildren().addAll(homeButton, restartButton, resumeButton);

        alert.setHeaderText("The game was paused");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.getDialogPane().getChildren().remove(2);
        alert.getDialogPane().setContent(vbox);

        alert.showAndWait();
    }

    private void setNewGame() {
        Stage primaryStage = (Stage) this.scene.getWindow();

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
    }

}
