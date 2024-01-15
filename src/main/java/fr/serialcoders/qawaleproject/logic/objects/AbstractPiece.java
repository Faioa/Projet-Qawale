package fr.serialcoders.qawaleproject.logic.objects;

import javafx.scene.paint.Color;

public abstract class AbstractPiece implements IPiece {

    private final Color color;
    private final PieceHeight height;
    private final PieceShape shape;
    private final PieceTexture texture;
    private int x;
    private int y;

    public AbstractPiece(Color color, PieceHeight height, PieceShape shape, PieceTexture texture, int x, int y) {
        this.color = color;
        this.height = height;
        this.shape = shape;
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public PieceHeight getHeight() {
        return height;
    }

    @Override
    public PieceShape getShape() {
        return shape;
    }

    @Override
    public PieceTexture getTexture() {
        return texture;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean compare(IPiece p) {
        return this.color.equals(p.getColor()) || this.height == p.getHeight() || this.shape == p.getShape() || this.texture == p.getTexture();
    }

    @Override
    public String toString() {
        return "(" + x +";" + y + ") " + color.toString() + " " + height + " " + shape + " " + texture;
    }

}
