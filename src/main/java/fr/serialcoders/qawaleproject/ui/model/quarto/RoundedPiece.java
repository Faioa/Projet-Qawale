package fr.serialcoders.qawaleproject.ui.model.quarto;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

public class RoundedPiece implements Piece {

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    public Group startPiece(Color color, boolean tall, boolean hollow) {
        Cylinder circle = prepareCircle(color);
        Cylinder cylinder = prepareCylinder(color, tall);
        if(tall) {
            circle.setTranslateY(-75.8);
        } else {
            circle.setTranslateY(-32.80);
        }

        Group group = new Group();
        group.getChildren().addAll(cylinder, circle);

        if(hollow) {
            Cylinder hole = prepareHole();
            if(tall) {
                hole.setTranslateY(-75.9);
            } else {
                hole.setTranslateY(-33);
            }
            group.getChildren().add(hole);
        }

        return group;
    }

    private Cylinder prepareCylinder(Color color, boolean tall) {
        PhongMaterial material = new PhongMaterial();

        if(color.equals(Color.BEIGE))
            material.setDiffuseMap(new Image(getClass().getResourceAsStream("/fr/serialcoders/qawaleproject/texture/beigeLine.jpg")));
        if(color.equals(Color.BROWN))
            material.setDiffuseMap(new Image(getClass().getResourceAsStream("/fr/serialcoders/qawaleproject/texture/woodLine.jpg")));

        Cylinder cylinder;
        if(tall) {
            cylinder = new Cylinder(20, 150);
        } else {
            cylinder = new Cylinder(20, 65);
        }

        cylinder.setMaterial(material);
        return cylinder;
    }

    private Cylinder prepareCircle(Color color) {
        PhongMaterial material = new PhongMaterial();

        if(color.equals(Color.BEIGE))
            material.setDiffuseMap(new Image(getClass().getResourceAsStream("/fr/serialcoders/qawaleproject/texture/beige.jpg")));
        if(color.equals(Color.BROWN))
            material.setDiffuseMap(new Image(getClass().getResourceAsStream("/fr/serialcoders/qawaleproject/texture/wood.jpg")));

        Cylinder cylinder = new Cylinder(20,0);
        cylinder.setMaterial(material);
        return cylinder;
    }

    private Cylinder prepareHole() {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BLACK);
        Cylinder cylinder = new Cylinder(10,0);
        cylinder.setMaterial(material);
        return cylinder;
    }

}
