package grid;

public class Grid {

	public enum GridType {
		QAWALE, QUARTO
	}

	private int col;
	private int lig;
	private Cell[][] grid;

	public Grid(int lig, int col, GridType type) {
		this.lig = lig;
		this.col = col;
		grid = new Cell[lig][col];

		for (int i = 0; i < lig; i++) {
			for (int j = 0; j < col; j++) {
				if (type == GridType.QAWALE) {
					grid[i][j] = new QawaleCell();
				} else {
					grid[i][j] = new QuartoCell();
				}
			}
		}
	}

	public void PutPiece(Piece p, int x, int y) {
		if (x < lig && y < col) {
			grid[x][y].add(p);
		}
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
