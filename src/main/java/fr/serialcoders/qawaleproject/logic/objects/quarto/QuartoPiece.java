package fr.serialcoders.qawaleproject.logic.objects.quarto;

import fr.serialcoders.qawaleproject.logic.objects.AbstractPiece;
import javafx.scene.paint.Color;

public class QuartoPiece extends AbstractPiece {

    public QuartoPiece(Color color, PieceHeight height, PieceShape shape, PieceTexture texture, int x, int y) {
        super(color, height, shape, texture, x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof QuartoPiece)) {
            return false;
        }

        QuartoPiece tmp = (QuartoPiece) o;

        return this.getColor() == tmp.getColor() && this.getHeight() == tmp.getHeight()
                && this.getShape() == tmp.getShape() && this.getTexture() == tmp.getTexture();
    }

}
