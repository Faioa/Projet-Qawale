package fr.serialcoders.qawaleproject.ui.model.quarto;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private SmartGroup board;

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 1000;

    public Board(){
        //Circle
        List<Cylinder> circles = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Cylinder circle = prepareCircle();
            circles.add(circle);
        }

        //Board
        Cylinder boardPart = prepareBoard();
        Cylinder plateauBack = prepareBack();
        plateauBack.setTranslateY(1);
        SmartGroup group = new SmartGroup();

        int rows = 4;
        int cols = 4;
        double circleSpacing = 80.0; // Spacing between circles

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int index = row * cols + col;
                if (index < circles.size()) {
                    Cylinder circle = circles.get(index);

                    double x = col * circleSpacing - (cols - 1) * circleSpacing * 0.5; // Position en X
                    double y = row * circleSpacing - (rows - 1) * circleSpacing * 0.5; // Position en Y

                    circle.setTranslateX(x);
                    circle.setTranslateZ(y);
                    circle.setTranslateY(-1);


                    group.getChildren().add(circle);
                }
            }
        }
        group.getChildren().add(boardPart);
        group.getChildren().add(plateauBack);

        group.translateXProperty().set(WIDTH / 2);
        group.translateYProperty().set(HEIGHT /3);
        group.translateZProperty().set(-500);

        this.board=group;
    }

    public SmartGroup getBoard() {
        return board;
    }

    private Cylinder prepareCircle() {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.MOCCASIN);
        Cylinder cylinder = new Cylinder(32,5); // Paramètres : rayon, hauteur
        cylinder.setMaterial(material);
        return cylinder;
    }

    private Cylinder prepareBoard() {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BROWN);
        Cylinder cylinder = new Cylinder(169+70, 5); // Paramètres : rayon, hauteur
        cylinder.setMaterial(material);
        return cylinder;
    }

    private Cylinder prepareBack() {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.MOCCASIN);
        Cylinder cylinder = new Cylinder(175+70, 5); // Paramètres : rayon, hauteur
        cylinder.setMaterial(material);
        return cylinder;
    }

}
