package fr.serialcoders.qawaleproject.logic.objects.quarto;

import javafx.scene.paint.Color;
import fr.serialcoders.qawaleproject.logic.objects.IPiece.*;

import java.util.ArrayList;
import java.util.List;

public class QuartoFactory {

    public List<QuartoPiece> createPieces() {
        List<QuartoPiece> retval = new ArrayList<>(16);
        retval.add(new QuartoPiece(Color.RED, PieceHeight.SMALL,PieceShape.SQUARED, PieceTexture.HOLLOW, -1, -1));
        retval.add(new QuartoPiece(Color.RED, PieceHeight.SMALL,PieceShape.SQUARED, PieceTexture.FULL, -1, -1));
        retval.add(new QuartoPiece(Color.RED, PieceHeight.SMALL,PieceShape.ROUNDED, PieceTexture.HOLLOW, -1, -1));
        retval.add(new QuartoPiece(Color.RED, PieceHeight.SMALL,PieceShape.ROUNDED, PieceTexture.FULL, -1, -1));
        retval.add(new QuartoPiece(Color.YELLOW, PieceHeight.SMALL,PieceShape.SQUARED, PieceTexture.HOLLOW, -1, -1));
        retval.add(new QuartoPiece(Color.YELLOW, PieceHeight.SMALL,PieceShape.SQUARED, PieceTexture.FULL, -1, -1));
        retval.add(new QuartoPiece(Color.YELLOW, PieceHeight.SMALL,PieceShape.ROUNDED, PieceTexture.HOLLOW, -1, -1));
        retval.add(new QuartoPiece(Color.YELLOW, PieceHeight.SMALL,PieceShape.ROUNDED, PieceTexture.FULL, -1, -1));
        retval.add(new QuartoPiece(Color.RED, PieceHeight.TALL,PieceShape.ROUNDED, PieceTexture.HOLLOW, -1, -1));
        retval.add(new QuartoPiece(Color.RED, PieceHeight.TALL,PieceShape.ROUNDED, PieceTexture.FULL, -1, -1));
        retval.add(new QuartoPiece(Color.RED, PieceHeight.TALL,PieceShape.SQUARED, PieceTexture.HOLLOW, -1, -1));
        retval.add(new QuartoPiece(Color.RED, PieceHeight.TALL,PieceShape.SQUARED, PieceTexture.FULL, -1, -1));
        retval.add(new QuartoPiece(Color.YELLOW, PieceHeight.TALL,PieceShape.ROUNDED, PieceTexture.HOLLOW, -1, -1));
        retval.add(new QuartoPiece(Color.YELLOW, PieceHeight.TALL,PieceShape.ROUNDED, PieceTexture.FULL, -1, -1));
        retval.add(new QuartoPiece(Color.YELLOW, PieceHeight.TALL,PieceShape.SQUARED, PieceTexture.HOLLOW, -1, -1));
        retval.add(new QuartoPiece(Color.YELLOW, PieceHeight.TALL,PieceShape.SQUARED, PieceTexture.FULL, -1, -1));
        return retval;
    }

    public QuartoPiece createOne(Color color, PieceHeight height, PieceShape shape, PieceTexture texture) {
        return new QuartoPiece(color, height, shape, texture, -1, -1);
    }

}
