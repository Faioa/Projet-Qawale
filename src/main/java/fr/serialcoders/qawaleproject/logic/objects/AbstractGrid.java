package fr.serialcoders.qawaleproject.logic.objects;

public abstract class AbstractGrid {

    private final int col;
    private final int lig;
    protected ICell[][] grid;

    public AbstractGrid(int lig, int col) {
        this.lig = lig;
        this.col = col;
        grid = new ICell[lig][col];
    }

    public void putPiece(IPiece p, int x, int y) {
        if (x < lig && x > 0 && y < col && y > 0) {
            grid[x][y].add(p);
        }
    }

    public abstract boolean movePiece(int oldX, int oldY, int newX, int newY, int indice);

    public ICell getCell(int x, int y) {
        return grid[x][y];
    }

    public void display() {
        System.out.println("+------+------+------+------+");

        for (int i = 0; i < lig; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print("| " + grid[i][j] + " ");
            }
            System.out.println("|\n+------+------+------+------+");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("State of the grid : \n\n");

        for (int i = 0; i < lig; i++) {
            for (int j = 0; j < col; j++) {
                sb.append("(" + i + "; " + j + ") : " + grid[i][j] + "\n");
            }
        }

        return sb.toString();
    }

}
