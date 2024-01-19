package fr.serialcoders.qawaleproject.logic.qawale;

import fr.serialcoders.qawaleproject.logic.*;

import java.util.Scanner;

public class QawaleStrategy implements Strategy {

    public boolean verify(Grid g) {
        for (int j = 0; j < 4; j++) {
            Piece piece = g.getCell(j, j).getPiece();
            if (piece == null || piece.getColor() == Piece.PieceColor.CREAM)
                continue;

            // Lines
            for (int i = 0; i < 4; i++) {
                if (piece.compare(g.getCell(i, 0).getPiece()) && piece.compare(g.getCell(i, 1).getPiece())
                        && piece.compare(g.getCell(i, 2).getPiece()) && piece.compare(g.getCell(i, 3).getPiece())) {
                    return true;
                }
            }

            // Columns
            for (int i = 0; i < 4; i++) {
                if (piece.compare(g.getCell(0, i).getPiece()) && piece.compare(g.getCell(1,i).getPiece())
                        && piece.compare(g.getCell(2, i).getPiece()) && piece.compare(g.getCell(3, i).getPiece())) {

                    return true;
                }
            }
        }

        // Diagonals
        boolean diag1 = true;
        for (int i = 0; i <3; i++) {
            Piece p = g.getCell(i, i).getPiece();
            if (p == null || p.getColor() == Piece.PieceColor.CREAM) {
                diag1 = false;
                break;
            }
            if (!p.compare(g.getCell(i+1, i+1).getPiece())) {
                diag1 = false;
                break;
            }
        }

        boolean diag2 = true;
        for (int i = 0; i <3; i++) {
            Piece p = g.getCell(i, 3 - i).getPiece();
            if (p == null || p.getColor() == Piece.PieceColor.CREAM) {
                diag2 = false;
                break;
            }
            if (!p.compare(g.getCell(i+1, 3 - i - 1).getPiece())) {
                diag2 = false;
                break;
            }
        }

        return diag1 || diag2;
    }

    @Override
    public Cell createCell() {
        return new QawaleCell();
    }

}
