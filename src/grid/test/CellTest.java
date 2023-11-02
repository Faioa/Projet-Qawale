package grid.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import grid.Piece.PieceColor;
import grid.Piece.PieceHeight;
import grid.Piece.PieceShape;
import grid.Piece.PieceTexture;
import grid.QawaleCell;
import grid.QawalePiece;
import grid.QuartoCell;
import grid.QuartoPiece;

public class CellTest {

	QawalePiece firstPieceQawale = new QawalePiece(PieceColor.CREAM);
	QawalePiece secondPieceQawale = new QawalePiece(PieceColor.ORANGE);
	QawalePiece thirdPieceQawale = new QawalePiece(PieceColor.BROWN);
	QuartoPiece firstPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.TALL, PieceShape.SQUARED,
			PieceTexture.FULL);
	QuartoPiece secondPieceQuarto = new QuartoPiece(PieceColor.BROWN, PieceHeight.SMALL, PieceShape.ROUNDED,
			PieceTexture.HOLLOW);

	QawaleCell cellQawale = new QawaleCell(0, 0);
	QuartoCell cellQuarto = new QuartoCell(0, 1);

	@Test
	public void testQawale1() {
		assertEquals(true, cellQawale.isEmpty());

		cellQawale.add(firstPieceQawale);
		cellQawale.add(secondPieceQawale);
		assertEquals(false, cellQawale.isEmpty());

		cellQawale.remove();

		assertEquals(false, cellQawale.isEmpty());

		cellQawale.remove();

		assertEquals(true, cellQawale.isEmpty());
	}

	@Test
	public void testQawale2() {
		cellQawale.add(firstPieceQawale);
		cellQawale.add(secondPieceQawale);
		cellQawale.add(thirdPieceQawale);

		assertEquals(true, cellQawale.getPiece().equals(thirdPieceQawale));

		assertEquals(false, cellQawale.getPiece().equals(firstPieceQawale));
	}

	@Test
	public void testQuarto1() {
		assertEquals(true, cellQuarto.isEmpty());

		cellQuarto.add(firstPieceQuarto);
		assertEquals(false, cellQuarto.isEmpty());
	}

	@Test
	public void testQuarto2() {
		cellQuarto.add(firstPieceQuarto);

		assertEquals(true, cellQuarto.getPiece().equals(firstPieceQuarto));

		assertEquals(false, cellQuarto.getPiece().equals(secondPieceQuarto));
	}
}
