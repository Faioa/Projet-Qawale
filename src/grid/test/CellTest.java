package grid.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
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

	private QawalePiece firstPieceQawale;
	private QawalePiece secondPieceQawale;
	private QawalePiece thirdPieceQawale;

	private QuartoPiece firstPieceQuarto;
	private QuartoPiece secondPieceQuarto;

	private QawaleCell cellQawale;
	private QuartoCell cellQuarto;

	@Before
	public void setUp() {
		firstPieceQawale = new QawalePiece(PieceColor.CREAM);
		secondPieceQawale = new QawalePiece(PieceColor.ORANGE);
		thirdPieceQawale = new QawalePiece(PieceColor.BROWN);

		firstPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.TALL, PieceShape.SQUARED, PieceTexture.FULL);
		secondPieceQuarto = new QuartoPiece(PieceColor.BROWN, PieceHeight.SMALL, PieceShape.ROUNDED,
				PieceTexture.HOLLOW);

		cellQawale = new QawaleCell(0, 0);
		cellQuarto = new QuartoCell(0, 0);
	}

	@After
	public void tearDown() {
		firstPieceQawale = null;
		secondPieceQawale = null;
		thirdPieceQawale = null;

		firstPieceQuarto = null;
		secondPieceQuarto = null;

		cellQawale = null;
		cellQuarto = null;
	}

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
