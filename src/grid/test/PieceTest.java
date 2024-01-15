package grid.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import grid.Piece.PieceColor;
import grid.Piece.PieceHeight;
import grid.Piece.PieceShape;
import grid.Piece.PieceTexture;
import grid.QawalePiece;
import grid.QuartoPiece;

public class PieceTest {

	private QawalePiece firstPieceQawale;
	private QawalePiece secondPieceQawale;
	private QawalePiece thirdPieceQawale;

	private QuartoPiece firstPieceQuarto;
	private QuartoPiece secondPieceQuarto;
	private QuartoPiece thirdPieceQuarto;
	private QuartoPiece fourthPieceQuarto;

	@Before
	public void setUp() {
		firstPieceQawale = new QawalePiece(PieceColor.CREAM);
		secondPieceQawale = new QawalePiece(PieceColor.CREAM);
		thirdPieceQawale = new QawalePiece(PieceColor.RED);

		firstPieceQuarto = new QuartoPiece(PieceColor.RED, PieceHeight.SMALL, PieceShape.ROUNDED,
				PieceTexture.HOLLOW);
		secondPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.SMALL, PieceShape.SQUARED, PieceTexture.FULL);
		thirdPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.TALL, PieceShape.SQUARED, PieceTexture.FULL);
		fourthPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.TALL, PieceShape.SQUARED, PieceTexture.FULL);
	}

	@After
	public void tearDown() {
		firstPieceQawale = null;
		secondPieceQawale = null;
		thirdPieceQawale = null;

		firstPieceQuarto = null;
		secondPieceQuarto = null;
		thirdPieceQuarto = null;
		fourthPieceQuarto = null;
	}

	@Test
	public void testQawale1() {
		assertEquals(true, firstPieceQawale.getColor() == PieceColor.CREAM);

		assertEquals(false, thirdPieceQawale.getColor() == PieceColor.YELLOW);
	}

	@Test
	public void testQawale2() {
		assertEquals(true, firstPieceQawale.compare(secondPieceQawale));
		assertEquals(false, firstPieceQawale.compare(thirdPieceQawale));
	}

	@Test
	public void testQawale3() {
		assertEquals(true, firstPieceQawale.equals(secondPieceQawale));
		assertEquals(false, firstPieceQawale.equals(thirdPieceQawale));
	}

	@Test
	public void testQuarto1() {
		assertEquals(true, firstPieceQuarto.getColor() == PieceColor.RED);
		assertEquals(false, firstPieceQuarto.getColor() == PieceColor.CREAM);

		assertEquals(true, firstPieceQuarto.getHeight() == PieceHeight.SMALL);
		assertEquals(false, firstPieceQuarto.getHeight() == PieceHeight.TALL);

		assertEquals(true, firstPieceQuarto.getShape() == PieceShape.ROUNDED);
		assertEquals(false, firstPieceQuarto.getShape() == PieceShape.SQUARED);

		assertEquals(true, firstPieceQuarto.getTexture() == PieceTexture.HOLLOW);
		assertEquals(false, firstPieceQuarto.getTexture() == PieceTexture.FULL);
	}

	@Test
	public void testQuarto2() {
		assertEquals(true, firstPieceQuarto.compare(secondPieceQuarto));
		assertEquals(false, firstPieceQuarto.compare(thirdPieceQuarto));
	}

	@Test
	public void testQuarto3() {
		assertEquals(false, firstPieceQuarto.equals(secondPieceQuarto));
		assertEquals(false, firstPieceQuarto.equals(thirdPieceQuarto));
		assertEquals(true, thirdPieceQuarto.equals(fourthPieceQuarto));
	}

}
