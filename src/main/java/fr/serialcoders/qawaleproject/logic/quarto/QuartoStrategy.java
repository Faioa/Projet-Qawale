package fr.serialcoders.qawaleproject.logic.quarto;

import fr.serialcoders.qawaleproject.logic.Cell;
import fr.serialcoders.qawaleproject.logic.Grid;
import fr.serialcoders.qawaleproject.logic.Piece;
import fr.serialcoders.qawaleproject.logic.Strategy;

public class QuartoStrategy implements Strategy {

    @Override
    public boolean verify(Grid grid) {
        int maxAge;
        boolean win;

        for (int j = 0; j < 4; j++) {
            win = true;

            // Lines
            maxAge = 0;
            for (int k = 0; k < 3; k++) {
                Piece p1 = grid.getCell(j, k).getPiece();
                for (int i = k; i < 3 - k; i++) {
                    Piece p2 = grid.getCell(j, i + 1).getPiece();
                    if (p1 == null || !p1.compare(p2)) {
                        win = false;
                        break;
                    }
                    if (maxAge < p1.getAge())
                        maxAge = p1.getAge();
                    if (maxAge < p2.getAge())
                        maxAge = p2.getAge();
                }
                if (!win)
                    break;
            }
            if (win) {
                if (win(maxAge, grid.getAge()))
                    return true;
            }

            // Columns
            maxAge = 0;
            for (int k = 0; k < 3; k++) {
                Piece p1 = grid.getCell(k, j).getPiece();
                for (int i = k; i < 3 - k; i++) {
                    Piece p2 = grid.getCell(i + 1, j).getPiece();
                    if (p1 == null || !p1.compare(p2)) {
                        win = false;
                        break;
                    }
                    if (maxAge < p1.getAge())
                        maxAge = p1.getAge();
                    if (maxAge < p2.getAge())
                        maxAge = p2.getAge();
                }
                if (!win)
                    break;
            }
            if (win) {
                if (win(maxAge, grid.getAge()))
                    return true;
            }
        }

        // 1st Diagonal
        maxAge = 0;
        win = true;
        for (int k = 0; k < 3; k++) {
            Piece p1 = grid.getCell(k, k).getPiece();
            for (int i = k; i < 3 - k; i++) {
                Piece p2 = grid.getCell(i + 1, i + 1).getPiece();
                if (p1 == null || !p1.compare(p2)) {
                    win = false;
                    break;
                }
                if (maxAge < p1.getAge())
                    maxAge = p1.getAge();
                if (maxAge < p2.getAge())
                    maxAge = p2.getAge();
            }
            if (!win)
                break;
        }
        if (win) {
            if (win(maxAge, grid.getAge()))
                return true;
        }

        // 2nd Diagonal
        maxAge = 0;
        win = true;
        for (int k = 0; k < 3; k++) {
            Piece p1 = grid.getCell(k, 3 - k).getPiece();
            for (int i = 0; i < 3; i++) {
                Piece p2 = grid.getCell(i + 1, 3 - i - 1).getPiece();

                if (p1 == null || !p1.compare(p2)) {
                    win = false;
                    break;
                }
                if (maxAge < p1.getAge())
                    maxAge = p1.getAge();
                if (maxAge < p2.getAge())
                    maxAge = p2.getAge();
            }
            if (!win)
                break;
        }
        if (win) {
            if (win(maxAge, grid.getAge()))
                return true;
        }

        return false;
    }

    @Override
    public Cell createCell() {
        return new QuartoCell();
    }

    private boolean win(int maxAge, int gridAge) {
        if (gridAge >= maxAge && gridAge <= maxAge + 1)
            return true;
        return false;
    }
}
