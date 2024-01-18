package fr.serialcoders.qawaleproject.logic.quarto;

import fr.serialcoders.qawaleproject.logic.Cell;
import fr.serialcoders.qawaleproject.logic.Piece;

import java.util.LinkedList;

public class QuartoCell extends Cell {

    public QuartoCell() {
        super();
        setContent(new LinkedList<QuartoPiece>());
    }

    @Override
    public void add(Piece p) {
        if (isEmpty() && !p.isPlaced() && p instanceof QuartoPiece) {
            super.add(p);
            p.place();
        }
    }

    @Override
    public void remove() {
    }

}