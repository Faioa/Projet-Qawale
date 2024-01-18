package fr.serialcoders.qawaleproject.logic;

import fr.serialcoders.qawaleproject.logic.qawale.QawaleCell;
import fr.serialcoders.qawaleproject.logic.quarto.QuartoCell;

import java.util.List;

public class Grid {

    private int col;
    private int lig;
    private Cell[][] grid;
    private int age;
    private Strategy strategy;

    public Grid(int lig, int col, Strategy strategy) {
        this.lig = lig;
        this.col = col;
        this.strategy = strategy;
        age = 0;
        grid = new Cell[lig][col];

        for (int i = 0; i < lig; i++) {
            for (int j = 0; j < col; j++) {
                    grid[i][j] = this.strategy.createCell();
            }
        }
    }

    public void putPiece(Piece p, int x, int y) {
        p.setAge(++age);
        if (x < lig && y < col) {
            grid[x][y].add(p);
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void movePiece(int oldX, int oldY, int newX, int newY) {
        if (grid[oldX][oldY].isEmpty()) {
            return;
        }

        List<Piece> oldList = grid[oldX][oldY].getContent();
        List<Piece> newList = grid[newX][newY].getContent();

        newList.add(oldList.get(0));
        grid[oldX][oldY].remove();
    }

    public boolean verify() {
        return strategy.verify(this);
    }

    public Cell getCell(int x, int y) {
        return grid[x][y];
    }

}
