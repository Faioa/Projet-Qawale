package fr.serialcoders.qawaleproject.logic.objects.quarto;

import fr.serialcoders.qawaleproject.logic.objects.AbstractCell;
import fr.serialcoders.qawaleproject.logic.objects.IPiece;

public class QuartoCell extends AbstractCell {

    private IPiece piece;

    public QuartoCell(int x, int y) {
        super(x, y);
        piece = null;
    }

    @Override
    public boolean add(IPiece p) {
        if (piece == null && p instanceof QuartoPiece) {
            piece = p;
            return true;
        }
        return false;
    }

    @Override
    public IPiece getPiece() {
        return piece;
    }

    @Override
    public boolean isEmpty() {
        return piece == null;
    }

    @Override
    public String toString() {
        if (piece == null) {
            return "Empty";
        }

        return piece.toString();
    }

}
