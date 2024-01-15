package fr.serialcoders.qawaleproject.logic.objects.quarto;

import fr.serialcoders.qawaleproject.logic.objects.AbstractGrid;

public class QuartoGrid extends AbstractGrid {

    public QuartoGrid(int lig, int col) {
        super(lig, col);
        for (int i = 0; i < lig; i++) {
            for (int j = 0; j < col; j++) {
                grid[i][j] = new QuartoCell(i, j);
            }
        }
    }

    @Override
    public boolean movePiece(int oldX, int oldY, int newX, int newY, int indice) {
        return false;
    }
}
