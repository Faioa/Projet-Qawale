package fr.serialcoders.qawaleproject.logic.objects.qawale;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class QawalePieceFactory {

    public List<QawalePiece> createPieces(Color color) {
        List<QawalePiece> retval = new ArrayList<>(8);
        for (int i = 0; i < 8; i++)
            retval.add(new QawalePiece(color, -1, -1));
        return retval;
    }

    public QawalePiece createOne(Color color) {
        return new QawalePiece(color, -1, -1);
    }
}
