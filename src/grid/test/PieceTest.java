package grid.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import grid.Piece.PieceColor;
import grid.Piece.PieceHeight;
import grid.Piece.PieceShape;
import grid.Piece.PieceTexture;
import grid.QawalePiece;
import grid.QuartoPiece;

public class PieceTest {

	QawalePiece firstPieceQawale = new QawalePiece(PieceColor.CREAM);
	QawalePiece secondPieceQawale = new QawalePiece(PieceColor.CREAM);
	QawalePiece thirdPieceQawale = new QawalePiece(PieceColor.BROWN);

	QuartoPiece firstPieceQuarto = new QuartoPiece(PieceColor.BROWN, PieceHeight.SMALL, PieceShape.ROUNDED,
			PieceTexture.HOLLOW);
	QuartoPiece secondPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.SMALL, PieceShape.SQUARED,
			PieceTexture.FULL);
	QuartoPiece thirdPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.TALL, PieceShape.SQUARED,
			PieceTexture.FULL);
	QuartoPiece fourthPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.TALL, PieceShape.SQUARED,
			PieceTexture.FULL);

	@Test
	public void testQawale1() {
		assertEquals(true, firstPieceQawale.getColor() == PieceColor.CREAM);

		assertEquals(false, thirdPieceQawale.getColor() == PieceColor.ORANGE);
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
		assertEquals(true, firstPieceQuarto.getColor() == PieceColor.BROWN);
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
