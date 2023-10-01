package logic;

import grid.Piece.PieceColor;

public class QawalePlayer extends Player {

	private PieceColor colorChoice;

	public QawalePlayer(PieceColor colorChoice, String name) {
		super(name);
		this.colorChoice = colorChoice;
	}
}