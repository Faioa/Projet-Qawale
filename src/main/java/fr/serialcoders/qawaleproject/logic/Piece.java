package fr.serialcoders.qawaleproject.logic;

public abstract class Piece {

    public enum PieceColor {
        RED, CREAM, YELLOW
    }

    public enum PieceHeight {
        TALL, SMALL
    }

    public enum PieceShape {
        SQUARED, ROUNDED
    }

    public enum PieceTexture {
        HOLLOW, FULL
    }

    private PieceColor color;
    private boolean placed = false;
    private int age;

    public Piece(PieceColor color) {
        age = 0;
        this.color = color;
    }

    public PieceColor getColor() {
        return color;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void place() {
        placed = true;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public abstract boolean compare(Piece p);

    @Override
    public String toString() {
        if (color == PieceColor.YELLOW) {
            return "Y";
        }
        if (color == PieceColor.CREAM) {
            return "C";
        }
        return "R";
    }

}
