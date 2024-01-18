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
            Piece origin = grid.getCell(j, j).getPiece();
            if (origin == null)
                continue;

            //Lines
            maxAge = 0;
            for (int i = 0; i < 4; i++) {
                Piece lig=  grid.getCell(j, i).getPiece();
                if (lig== null || !origin.compare(lig)) {
                    win = false;
                    break;
                }
                if (maxAge < lig.getAge())
                    maxAge = lig.getAge();
            }
            if (win) {
                if (win(maxAge, grid.getAge()))
                    return true;
            }

            //Columns
            maxAge = 0;
            for (int i = 0; i < 4; i++) {
                Piece col =  grid.getCell(i, j).getPiece();
                if (col == null || !origin.compare(col)) {
                    win = false;
                    break;
                }
                if (maxAge < col.getAge())
                    maxAge = col.getAge();
            }
            if (win) {
                if (win(maxAge, grid.getAge()))
                    return true;
            }
        }

        // Diagonal 1
        maxAge = 0;
        win = true;
        for (int i = 0; i < 3; i++) {
            Piece p = grid.getCell(i, i).getPiece();
            Piece p2 = grid.getCell(i+1, i+1).getPiece();
            if (p == null || p2 == null || !p.compare(p2)) {
                win = false;
                break;
            }
            if (maxAge < p.getAge())
                maxAge = p.getAge();
            if (maxAge < p2.getAge())
                maxAge = p2.getAge();
        }
        if (win) {
            if (win(maxAge, grid.getAge()))
                return true;
        }

        // Diagonal 2
        maxAge = 0;
        win = true;
        for (int i = 0; i < 3;  i++) {
            Piece p = grid.getCell(i, 3 - i).getPiece();
            Piece p2 = grid.getCell(i+1, 3 - i - 1).getPiece();

            if (p == null || p2 == null || !p.compare(p2)) {
                win = false;
                break;
            }
            if (maxAge < p.getAge())
                maxAge = p.getAge();
            if (maxAge < p2.getAge())
                maxAge = p2.getAge();
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
