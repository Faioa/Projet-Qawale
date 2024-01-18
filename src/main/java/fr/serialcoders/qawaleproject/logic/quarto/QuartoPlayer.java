package fr.serialcoders.qawaleproject.logic.quarto;

import fr.serialcoders.qawaleproject.logic.Grid;
import fr.serialcoders.qawaleproject.logic.Player;

import java.util.List;

public class QuartoPlayer extends Player {

    private List<QuartoPiece> pool;
    private QuartoPiece currentChoice;

    public QuartoPlayer(String name, Grid grid, List<QuartoPiece> pool) {
        super(name, grid);
        this.pool = pool;
        currentChoice = null;
    }

    public boolean isEmpty() {
        return pool.isEmpty();
    }

    public void putPiece(Grid g, int x, int y) {
        g.putPiece(currentChoice, x, y);
        pool.remove(currentChoice);
    }

    public int getPoolSize() {
        return pool.size();
    }

    public void choosePiece(QuartoPiece piece) {
        currentChoice = piece;
    }

    public QuartoPiece getCurrent() {
        return currentChoice;
    }

    public List<QuartoPiece> getPool() {
        return pool;
    }

}
