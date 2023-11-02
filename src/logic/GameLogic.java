package logic;

import java.util.List;

import grid.Cell;
import grid.Grid;
import grid.Piece;
import grid.QawaleCell;

public final class GameLogic {
	public enum Movement {
		NONE, UP, DOWN, LEFT, RIGHT
	};

	private GameLogic instance = new GameLogic();

	private GameLogic() {
	}

	public static void moveCaseContent(Grid g, Cell initial, List<? extends Cell> movesList)
			throws RuntimeException, WrongMoveException {
		int oldX = initial.getX();
		int oldY = initial.getY();
		int newX, newY;
		Movement m = Movement.NONE;

		try {
			if (((QawaleCell) initial).getContent().size() < movesList.size()) {
				throw new WrongMoveException(
						"There is not the same number of moves as the number of pieces that needs to be moved.");
			}
		} catch (RuntimeException e) {
			System.out.println(
					"Impossible cast ! The content will not be moved. This case's content needs to be a List implementation.");
			throw e;
		}

		for (Cell c : movesList) {
			newX = c.getX();
			newY = c.getY();

			if (newX == oldX && newY == oldY) {
				throw new WrongMoveException("This piece must not be placed on the initial/previous cell.");
			} else if (oldX != newX && oldY != newY) {
				throw new WrongMoveException("This piece cannot be moved diagonally.");
			} else if ((m == Movement.DOWN && newX == oldX && oldY > newY)
					|| (m == Movement.UP && newX == oldX && oldY < newY)
					|| (m == Movement.LEFT && newX > oldX && oldY == newY)
					|| (m == Movement.RIGHT && newX < oldX && oldY == newY)) {
				throw new WrongMoveException("This piece cannot be moved backwards.");
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
		}
	}

	public static boolean victoryLogic(Piece piece, Grid g) {

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
