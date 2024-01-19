package fr.serialcoders.qawaleproject.ui.model.quarto;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;

public class SquaredPiece implements Piece {

    private double anchorX;
    private double anchorAngleX = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private Group group;

    public Group startPiece(Color color, boolean tall, boolean hollow) {
        Box box = prepareBox(color, tall);
        Box square=prepareSquare(color);
        if(tall) {
            square.setTranslateY(-75.2);
        } else {
            square.setTranslateY(-32.56);
        }

        group = new Group();
        group.getChildren().addAll(box, square);

        if(hollow) {
            Cylinder cylinderTop = prepareCircleTop();
            if(tall) {
                cylinderTop.setTranslateY(-75.3);
            } else {
                cylinderTop.setTranslateY(-32.6);

            }
            group.getChildren().add(cylinderTop);
        }

        return group;
    }

    public Group getPiece() {
        return group;
    }

    private Box prepareBox(Color color, boolean tall) {
        PhongMaterial material = new PhongMaterial();

        if(color.equals(Color.BEIGE))
            material.setDiffuseMap(new Image(getClass().getResourceAsStream("/fr/serialcoders/qawaleproject/texture/beigeLine.jpg")));
        if(color.equals(Color.BROWN))
            material.setDiffuseMap(new Image(getClass().getResourceAsStream("/fr/serialcoders/qawaleproject/texture/woodLine.jpg")));

        Box box;
        if(tall) {
            box = new Box(35, 150, 35);
        } else {
            box = new Box(35, 65, 35);

        }

        box.setMaterial(material);
        return box;
    }


    private Box prepareSquare(Color color) {
        PhongMaterial material = new PhongMaterial();
        if(color.equals(Color.BEIGE))
            material.setDiffuseMap(new Image(getClass().getResourceAsStream("/fr/serialcoders/qawaleproject/texture/beige.jpg")));
        if(color.equals(Color.BROWN))
            material.setDiffuseMap(new Image(getClass().getResourceAsStream("/fr/serialcoders/qawaleproject/texture/wood.jpg")));
        Box box = new Box(35, 0, 35);
        box.setMaterial(material);
        return box;
    }


    private Cylinder prepareCircleTop() {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BLACK);
        Cylinder cylinder = new Cylinder(10,0);
        cylinder.setMaterial(material);
        return cylinder;
    }

}
