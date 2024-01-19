package fr.serialcoders.qawaleproject.ui.model.qawale;

import fr.serialcoders.qawaleproject.ui.Boundary;
import fr.serialcoders.qawaleproject.ui.model.quarto.SmartGroup;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QawaleBoard {

    private SmartGroup board;
    private static final int BOARD_SIZE = 4;
    private static final int WIDTH = 1400;
    private static final int HEIGHT = 1000;
    private Map<Boundary, Group> map=new HashMap<>();
    private long mousePressedTime;
    private int clickCount=0;
    private Group currentGroup=null;

    public QawaleBoard(){
        //Circle
        List<Cylinder> circles = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Cylinder circle = prepareCircle();
            circles.add(circle);
        }

        //Board
        Box plateaue = preparePlateau();
        Box plateau_back = preparePlateauBack();
        plateau_back.setTranslateY(10);
        plateaue.setTranslateY(8);
        //circle.setTranslateY(-10);
        SmartGroup group = new SmartGroup();

        int rows = 4;
        int cols = 4;
        double circleSpacing = 80.0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int index = row * cols + col;
                if (index < circles.size()) {
                    Cylinder circle = circles.get(index);

                    double x = col * circleSpacing - (cols - 1) * circleSpacing * 0.5;
                    double y = row * circleSpacing - (rows - 1) * circleSpacing * 0.5;

                    circle.setTranslateX(x);
                    circle.setTranslateZ(y);
                    circle.setTranslateY(-1);

                    group.getChildren().add(circle);
                }
            }
        }
        group.getChildren().add(plateaue);
        group.getChildren().add(plateau_back);

        Camera camera = new PerspectiveCamera();
        Scene scene = new Scene(group, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        scene.setOnMouseReleased(event -> {
            long mouseReleasedTime = System.currentTimeMillis();
            long clickDuration = mouseReleasedTime - mousePressedTime;

            if (clickDuration < 130) {
                double mouseX = event.getSceneX();
                double mouseY = event.getSceneY();

                clickCount++;
                if (clickCount == 1) {
                    currentGroup=findPiece(mouseX, mouseY);
                } else if (clickCount == 2) {
                    //1st line
                    if (mouseX >= 279 && mouseX <= 346 && mouseY >= 260 && mouseY <= 298) {
                        currentGroup.translateXProperty().set(-122.0);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(116);
                    }

                    if (mouseX >= 365 && mouseX <= 435 && mouseY >= 260 && mouseY <= 298) {

                        currentGroup.translateXProperty().set(-40);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(116);
                    }

                    if (mouseX >= 463 && mouseX <= 533 && mouseY >= 260 && mouseY <= 298) {
                        currentGroup.translateXProperty().set(40);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(116);
                    }

                    if (mouseX >= 560 && mouseX <= 630 && mouseY >= 260 && mouseY <= 298) {
                        currentGroup.translateXProperty().set(121);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(116);
                    }

                    //2nd line
                    if (mouseX >= 270 && mouseX <= 340 && mouseY >= 316 && mouseY <= 349) {
                        currentGroup.translateXProperty().set(-121.0);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(34.6);
                    }

                    if (mouseX >= 365 && mouseX <= 435 && mouseY >= 316 && mouseY <= 349) {
                        currentGroup.translateXProperty().set(-40);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(40.3);
                    }

                    if (mouseX >= 463 && mouseX <= 533 && mouseY >= 316 && mouseY <= 349) {
                        currentGroup.translateXProperty().set(40);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(40.3);
                    }

                    if (mouseX >= 560 && mouseX <= 630 && mouseY >= 316 && mouseY <= 349) {
                        currentGroup.translateXProperty().set(121);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(40.3);
                    }

                    //3rd line
                    if (mouseX >= 270 && mouseX <= 340 && mouseY >= 366 && mouseY <= 417) {
                        currentGroup.translateXProperty().set(-121.0);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(-35.4);
                    }

                    if (mouseX >= 365 && mouseX <= 435 && mouseY >= 366 && mouseY <= 417) {
                        currentGroup.translateXProperty().set(-40);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(-35.4);
                    }

                    if (mouseX >= 463 && mouseX <= 533 && mouseY >= 366 && mouseY <= 417) {
                        currentGroup.translateXProperty().set(40);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(-35.4);
                    }

                    if (mouseX >= 560 && mouseX <= 630 && mouseY >= 366 && mouseY <= 417) {
                        currentGroup.translateXProperty().set(121);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(-35.4);
                    }

                    //4th line
                    if (mouseX >= 270 && mouseX <= 340 && mouseY >= 429 && mouseY <= 488) {
                        currentGroup.translateXProperty().set(-121.0);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(-120);
                    }

                    if (mouseX >= 365 && mouseX <= 435 && mouseY >= 429 && mouseY <= 488) {
                        currentGroup.translateXProperty().set(-40);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(-120);
                    }

                    if (mouseX >= 463 && mouseX <= 533 && mouseY >= 429 && mouseY <= 488) {
                        currentGroup.translateXProperty().set(40);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(-120);
                    }

                    if (mouseX >= 560 && mouseX <= 630 && mouseY >= 429 && mouseY <= 488) {
                        currentGroup.translateXProperty().set(121);
                        currentGroup.translateYProperty().set(-40);
                        currentGroup.translateZProperty().set(-120);
                    }
                    clickCount = 0;
                }
            }
        });

        camera.getTransforms().addAll(
                new Rotate(-30, Rotate.X_AXIS),
                new Rotate(0, Rotate.Y_AXIS),
                new Rotate(0, Rotate.Z_AXIS),
                new Translate(0, 0, 1050)
        );

        group.translateXProperty().set(WIDTH / 2);
        group.translateYProperty().set(HEIGHT /3);
        group.translateZProperty().set(-500);

        this.board =group;
    }

    public SmartGroup getBoard() {
        return board;
    }

    private Group findPiece(double x, double y) {
        for(Boundary b: map.keySet()) {
            if(b.inBoundary(x, y)) {
                return map.get(b);
            }
        }
        return null;
    }

    private Cylinder prepareCircle() {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.SILVER);
        Cylinder cylinder = new Cylinder(32,3);
        cylinder.setMaterial(material);
        return cylinder;
    }


    private Box preparePlateau() {PhongMaterial borderMaterial = new PhongMaterial();
        Box borders = new Box(BOARD_SIZE * 90, 3, BOARD_SIZE * 90);
        borderMaterial.setDiffuseColor(Color.GREY);
        borders.setMaterial(borderMaterial);

        return borders;
    }


    private Box preparePlateauBack() {PhongMaterial borderMaterial = new PhongMaterial();
        Box borders = new Box(BOARD_SIZE * 95, 3, BOARD_SIZE * 95); // Dimensions du rectangle
        borderMaterial.setDiffuseColor(Color.GREY);
        return borders;
    }

}
