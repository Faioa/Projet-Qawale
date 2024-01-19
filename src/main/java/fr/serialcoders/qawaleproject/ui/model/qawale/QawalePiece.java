package fr.serialcoders.qawaleproject.ui.model.qawale;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

public class QawalePiece {

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 1000;

    public Group startPiece(Color color) {
        Cylinder cylinder = new Cylinder(25, 10);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        cylinder.setMaterial(material);

        Group group = new Group();
        group.getChildren().add(cylinder);

        group.translateXProperty().set(WIDTH / 2);
        group.translateYProperty().set(HEIGHT / 2);
        group.translateZProperty().set(-1500);

        return group;
    }

}
