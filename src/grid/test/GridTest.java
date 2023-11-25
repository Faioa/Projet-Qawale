package grid.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import grid.Grid;
import grid.Grid.GridType;
import grid.Piece.PieceColor;
import grid.Piece.PieceHeight;
import grid.Piece.PieceShape;
import grid.Piece.PieceTexture;
import grid.QawalePiece;
import grid.QuartoPiece;

public class GridTest {

	Grid gridQawale = new Grid(4, 4, GridType.QAWALE);
	Grid gridQuarto = new Grid(4, 4, GridType.QUARTO);

	QawalePiece firstPieceQawale = new QawalePiece(PieceColor.CREAM);
	QawalePiece secondPieceQawale = new QawalePiece(PieceColor.BROWN);

	QuartoPiece firstPieceQuarto = new QuartoPiece(PieceColor.BROWN, PieceHeight.SMALL, PieceShape.ROUNDED,
			PieceTexture.HOLLOW);
	QuartoPiece secondPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.SMALL, PieceShape.SQUARED,
			PieceTexture.FULL);
	QuartoPiece thirdPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.TALL, PieceShape.SQUARED,
			PieceTexture.FULL);

	@Test
	public void testQawale1() {
		assertEquals(true, gridQawale.getCell(0, 0).isEmpty());

		gridQawale.putPiece(firstPieceQawale, 0, 0);
		gridQawale.putPiece(secondPieceQawale, 0, 0);
		assertEquals(false, gridQawale.getCell(0, 0).isEmpty());

		assertEquals(true, gridQawale.getCell(0, 1).isEmpty());
		gridQawale.movePiece(0, 0, 0, 1);
		assertEquals(false, gridQawale.getCell(0, 1).isEmpty());
		assertEquals(false, gridQawale.getCell(0, 0).isEmpty());

		assertEquals(true, gridQawale.getCell(1, 0).isEmpty());
		gridQawale.movePiece(0, 0, 1, 0);
		assertEquals(false, gridQawale.getCell(1, 0).isEmpty());
		assertEquals(true, gridQawale.getCell(0, 0).isEmpty());

		assertEquals(true, gridQawale.getCell(0, 1).getPiece().equals(firstPieceQawale));
		assertEquals(true, gridQawale.getCell(1, 0).getPiece().equals(secondPieceQawale));
	}

	@Test
	public void testQuarto1() {
		assertEquals(true, gridQuarto.getCell(0, 0).isEmpty());

		gridQuarto.putPiece(firstPieceQuarto, 0, 0);
		assertEquals(false, gridQuarto.getCell(0, 0).isEmpty());
	}

}
