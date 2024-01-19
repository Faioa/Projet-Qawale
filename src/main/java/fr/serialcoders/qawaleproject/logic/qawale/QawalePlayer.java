package fr.serialcoders.qawaleproject.logic.qawale;

import fr.serialcoders.qawaleproject.logic.Grid;
import fr.serialcoders.qawaleproject.logic.Player;
import fr.serialcoders.qawaleproject.logic.quarto.QuartoPiece;

import java.util.List;

public class QawalePlayer extends Player {

    private List<QawalePiece> pool;
    private QawalePiece currentChoice;

    public QawalePlayer (String name, Grid grid, List<QawalePiece> pool) {
        super(name, grid);
        this.pool = pool;
        currentChoice = null;
    }

    public boolean isEmpty() {
        return pool.isEmpty();
    }

    public void putPiece(Grid g, int x, int y) {
        g.putPiece(pool.get(0), x, y);
        pool.remove(0);
    }

    public int getPoolSize() {
        return pool.size();
    }

    public void choosePiece(QawalePiece piece) {
        currentChoice = piece;
    }

    public QawalePiece getCurrent() {
        return currentChoice;
    }

    public List<QawalePiece> getPool() {
        return pool;
    }
}
