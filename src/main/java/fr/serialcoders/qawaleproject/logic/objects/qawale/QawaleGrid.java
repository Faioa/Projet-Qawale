package fr.serialcoders.qawaleproject.logic.objects.qawale;

import fr.serialcoders.qawaleproject.logic.objects.AbstractGrid;

import java.util.List;

public class QawaleGrid extends AbstractGrid {

    public QawaleGrid(int lig, int col) {
        super(lig, col);
        for (int i = 0; i < lig; i++) {
            for (int j = 0; j < col; j++) {
                    grid[i][j] = new QawaleCell(i, j);
            }
        }
    }

    @Override
    public boolean movePiece(int oldX, int oldY, int newX, int newY, int indice) {
        if (grid[oldX][oldY].isEmpty()) {
            return false;
        }
        List<QawalePiece> oldList = ((QawaleCell) grid[oldX][oldY]).getContent();
        List<QawalePiece> newList = ((QawaleCell) grid[newX][newY]).getContent();
        newList.add(oldList.get(((QawaleCell) grid[oldX][oldY]).getContent().size()-indice));
        oldList.remove(((QawaleCell) grid[oldX][oldY]).getContent().size()-indice);
        return true;
    }
}
