package fr.serialcoders.qawaleproject.logic.objects;

import javafx.scene.paint.Color;

public interface IPiece {
    public enum PieceHeight {
        TALL, SMALL
    }

    public enum PieceShape {
        SQUARED, ROUNDED
    }

    public enum PieceTexture {
        HOLLOW, FULL
    }
    public boolean compare(IPiece piece);
    public Color getColor();
    public PieceHeight getHeight();
    public PieceShape getShape();
    public PieceTexture getTexture();
    public int getX();
    public int getY();
}
