package tests;

import grid.Grid;
import grid.Grid.GridType;
import grid.Piece;
import grid.Piece.PieceColor;
import grid.QawalePiece;

public class TestMain {
	public static void main(String[] args) {

		Grid grid = new Grid(4, 4, GridType.QAWALE);

		Piece p1 = new QawalePiece(PieceColor.ORANGE);
		Piece p2 = new QawalePiece(PieceColor.CREAM);
		Piece p3 = new QawalePiece(PieceColor.BROWN);

		grid.PutPiece(p1, 0, 0);
		grid.PutPiece(p2, 1, 0);
		grid.PutPiece(p3, 0, 1);

		grid.display();

		System.out.println(grid.toString());
	}
}