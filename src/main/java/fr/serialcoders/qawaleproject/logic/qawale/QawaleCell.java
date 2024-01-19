package fr.serialcoders.qawaleproject.logic.qawale;

import fr.serialcoders.qawaleproject.logic.Piece;
import fr.serialcoders.qawaleproject.logic.Cell;
import fr.serialcoders.qawaleproject.logic.quarto.QuartoPiece;

import java.util.ArrayList;
import java.util.LinkedList;

public class QawaleCell extends Cell {

    public QawaleCell() {
        super();
        setContent(new LinkedList<QawalePiece>());
    }

    @Override
    public void add(Piece p) {
        if (p instanceof QawalePiece) {
            super.add(p);
            p.place();
        }
    }

    @Override
    public void remove() {
    }

    @Override
    public Cell copy() {
        QawaleCell retval = new QawaleCell();
        retval.setContent(new ArrayList<>(getContent()));
        return retval;
    }

}
