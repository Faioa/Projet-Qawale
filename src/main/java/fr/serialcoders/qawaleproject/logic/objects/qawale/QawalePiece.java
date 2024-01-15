package fr.serialcoders.qawaleproject.logic.objects.qawale;

import fr.serialcoders.qawaleproject.logic.objects.AbstractPiece;
import javafx.scene.paint.Color;

public class QawalePiece extends AbstractPiece {

    public QawalePiece(Color color, int x, int y) {
        super(color, null, null, null, x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof QawalePiece)) {
            return false;
        }

        QawalePiece tmp = (QawalePiece) o;

        return this.getColor() == tmp.getColor();
    }

}
