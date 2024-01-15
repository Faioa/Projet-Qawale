package fr.serialcoders.qawaleproject.logic.objects;

import java.util.List;

public class Player {

    private String name;
    private IPiece currentChoice;
    private AbstractGrid grid;
    private List<IPiece> pool;

    public Player(String name, AbstractGrid grid, List<IPiece> pool) {
        this.name = name;
        this.grid = grid;
        this.pool = pool;
        currentChoice = null;
    }

    public boolean isEmpty() {
        return pool.isEmpty();
    }

    public boolean putPiece(int x, int y) {
        if (currentChoice != null) {
            grid.putPiece(currentChoice, x, y);
            return true;
        }
        return false;
    }

    public String toString() {
        return name;
    }

    public int getPoolSize() {
        return pool.size();
    }

    public boolean choosePiece(IPiece piece) {
        currentChoice = piece;
        return true;
    }

    public IPiece getCurrentChoice() {
        return currentChoice;
    }

    public AbstractGrid getGrid() {
        return grid;
    }

}
