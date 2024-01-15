package logic.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import grid.Grid;
import grid.Grid.GridType;
import grid.Piece.PieceColor;
import grid.Piece.PieceHeight;
import grid.Piece.PieceShape;
import grid.Piece.PieceTexture;
import grid.QawalePiece;
import grid.QuartoPiece;
import logic.GameLogic;

public class GameLogicTest {

	private Grid gridQawale;
	private Grid gridQuarto;

	private QawalePiece firstPieceQawale;
	private QawalePiece secondPieceQawale;
	private QawalePiece thirdPieceQawale;
	private QawalePiece fourthPieceQawale;
	private QawalePiece fifthPieceQawale;

	private QuartoPiece firstPieceQuarto;
	private QuartoPiece secondPieceQuarto;
	private QuartoPiece thirdPieceQuarto;
	private QuartoPiece fourthPieceQuarto;
	private QuartoPiece fifthPieceQuarto;

	@Before
	public void setUp() {
		gridQawale = new Grid(4, 4, GridType.QAWALE);
		gridQuarto = new Grid(4, 4, GridType.QUARTO);

		firstPieceQawale = new QawalePiece(PieceColor.CREAM);
		secondPieceQawale = new QawalePiece(PieceColor.CREAM);
		thirdPieceQawale = new QawalePiece(PieceColor.CREAM);
		fourthPieceQawale = new QawalePiece(PieceColor.CREAM);
		fifthPieceQawale = new QawalePiece(PieceColor.RED);

		firstPieceQuarto = new QuartoPiece(PieceColor.RED, PieceHeight.SMALL, PieceShape.ROUNDED, PieceTexture.HOLLOW);
		secondPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.SMALL, PieceShape.SQUARED, PieceTexture.FULL);
		thirdPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.SMALL, PieceShape.ROUNDED, PieceTexture.FULL);
		fourthPieceQuarto = new QuartoPiece(PieceColor.RED, PieceHeight.SMALL, PieceShape.SQUARED, PieceTexture.FULL);
		fifthPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.TALL, PieceShape.SQUARED, PieceTexture.FULL);
	}

	@After
	public void tearDown() {
		gridQawale = null;
		gridQuarto = null;

		firstPieceQawale = null;
		secondPieceQawale = null;
		thirdPieceQawale = null;
		fourthPieceQawale = null;
		fifthPieceQawale = null;

		firstPieceQuarto = null;
		secondPieceQuarto = null;
		thirdPieceQuarto = null;
		fourthPieceQuarto = null;
		fifthPieceQuarto = null;
	}

	/*
	 * A completer, mais il faut modifier game logic pour : - Supprimer les actions
	 * peut etre ameliorer les tests pour la victoires en jouant avec les diagonales
	 * et les verticales
	 */
	@Test
	public void testQawale1() {
		gridQawale.putPiece(firstPieceQawale, 0, 0);
		gridQawale.putPiece(secondPieceQawale, 0, 1);
		gridQawale.putPiece(thirdPieceQawale, 0, 2);
		gridQawale.putPiece(fourthPieceQawale, 0, 3);

		assertTrue(GameLogic.victoryLogic(firstPieceQawale, gridQawale));

		gridQawale.putPiece(fifthPieceQawale, 0, 3);

		assertFalse(GameLogic.victoryLogic(firstPieceQawale, gridQawale));
	}

	@Test
	public void testQuarto1() {
		gridQuarto.putPiece(firstPieceQuarto, 0, 0);
		gridQuarto.putPiece(secondPieceQuarto, 0, 1);
		gridQuarto.putPiece(thirdPieceQuarto, 0, 2);
		gridQuarto.putPiece(fourthPieceQuarto, 0, 3);

		assertTrue(GameLogic.victoryLogic(firstPieceQuarto, gridQuarto));
	}

	@Test
	public void testQuarto2() {
		gridQuarto.putPiece(firstPieceQuarto, 0, 0);
		gridQuarto.putPiece(secondPieceQuarto, 0, 1);
		gridQuarto.putPiece(thirdPieceQuarto, 0, 2);
		gridQuarto.putPiece(fifthPieceQuarto, 0, 3);

		assertFalse(GameLogic.victoryLogic(firstPieceQuarto, gridQuarto));
	}

}
