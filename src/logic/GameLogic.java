package logic;

import java.util.Scanner;


import grid.Cell;
import grid.Grid;
import grid.Piece;
import grid.QawaleCell;
import grid.Grid.GridType;

public final class GameLogic {
	public enum Movement {
		NONE, UP, DOWN, LEFT, RIGHT
	};

	private GameLogic instance = new GameLogic();

	private GameLogic() {
	}
	
	
	public static void moveCaseContent(Grid g, int x, int y) {
		
		if (g.getGridType() == GridType.QUARTO) {
			return;
		}
		
		Cell c = g.getCell(x, y);
		int oldX = x;
		int oldY = y;
		int newX, newY;
		Scanner scan = new Scanner(System.in);
		Movement m = Movement.NONE;

		int nbInter = 0;
		int maxInter = ((QawaleCell) c).getContent().size();
		int nbIter=maxInter;
		while (nbInter < maxInter) {
			g.display();
			System.out.println("Rentrez la nouvelle coordonnée x de la piece :");
			newX = Integer.parseInt(scan.nextLine());
			System.out.println("Rentrez la nouvelle coordonnée y de la piece :");
			newY = Integer.parseInt(scan.nextLine());

			if (newX < 0 || newX > 3 || newY < 0 || newY > 3) {
				System.out.println("Les coordonnées ne sont pas corrects.");
				continue;
			} else if (newX == oldX && newY == oldY) {
				System.out.println("La pièce ne doit pas être placée au même endroit.");
				continue;
			} else if (oldX != newX && oldY != newY) {
				System.out.println("La pièce ne peut pas être déplacée en diagonale.");
				continue;
			} else if ((m == Movement.DOWN && newX == oldX && oldY > newY)
					|| (m == Movement.UP && newX == oldX && oldY < newY)
					|| (m == Movement.LEFT && newX > oldX && oldY == newY)
					|| (m == Movement.RIGHT && newX < oldX && oldY == newY)) {
				System.out.println("La pièce ne doit pas revenir en arrière.");
				continue;
			} else {
				g.movePiece(oldX, oldY, newX, newY);
				oldX = newX;
				oldY = newY;
				if (newX == oldX && oldY > newY) {
					m = Movement.UP;
				} else if (newX == oldX && oldY < newY) {
					m = Movement.DOWN;
				} else if (newX > oldX && oldY == newY) {
					m = Movement.RIGHT;
				} else {
					m = Movement.LEFT;
				}
			}
			nbInter++;
		}
		scan.close();
    g.display();
	}

	public static void victoryLogic(Piece piece, Grid g) throws VictoryException, DefeatException {

		/*
		 * Expliquer la logique de la fonction en voc
		 */

		// Analyse des lignes
		for (int i = 0; i < 4; i++) {
			if (piece.compare(g.getCell(i, 0).getPiece()) && piece.compare(g.getCell(i, 1).getPiece())
					&& piece.compare(g.getCell(i, 1).getPiece()) && piece.compare(g.getCell(i, 3).getPiece())) {
				return true;
			}
		}

		// Analyse des colonnes
		for (int j = 0; j < 4; j++) {
			if (piece.compare(g.getCell(0, j).getPiece()) && piece.compare(g.getCell(1, j).getPiece())
					&& piece.compare(g.getCell(2, j).getPiece()) && piece.compare(g.getCell(3, j).getPiece())) {
				return true;
			}
		}

		// Verifications des diagonales
		if (piece.compare(g.getCell(0, 0).getPiece()) && piece.compare(g.getCell(1, 1).getPiece())
				&& piece.compare(g.getCell(2, 2).getPiece()) && piece.compare(g.getCell(3, 3).getPiece())) {
			return true;
		}

		if (piece.compare(g.getCell(0, 3).getPiece()) && piece.compare(g.getCell(1, 2).getPiece())
				&& piece.compare(g.getCell(2, 1).getPiece()) && piece.compare(g.getCell(3, 0).getPiece())) {
			return true;
		}
		return false;
	}

}
