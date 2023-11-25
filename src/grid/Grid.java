package grid;

import java.util.List;

public class Grid {

	public enum GridType {
		QAWALE, QUARTO
	}

	private int col;
	private int lig;
	private Cell[][] grid;
	private GridType type;

	public Grid(int lig, int col, GridType type) {
		this.lig = lig;
		this.col = col;
		this.type = type;
		grid = new Cell[lig][col];

		for (int i = 0; i < lig; i++) {
			for (int j = 0; j < col; j++) {
				/*
				 * Possible d'utiliser une factory ici ? Mais, les pieces ont trop de parametres
				 * pour que ce soit efficace : on aurait une classe par type de piece (une piece
				 * Qawale orange, une rouge, le pire est donc pour Quarto...).
				 */
				if (type == GridType.QAWALE) {
					grid[i][j] = new QawaleCell(j, i);
				} else {
					grid[i][j] = new QuartoCell(j, i);
				}
			}
		}
	}

	public Grid.GridType getGridType() {
		return type;
	}

	public void putPiece(Piece p, int x, int y) {
		if (x < lig && y < col) {
			grid[x][y].add(p);
		}
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

	public Cell getCell(int x, int y) {
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
