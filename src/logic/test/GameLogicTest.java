package logic.test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import grid.Grid;
import grid.Grid.GridType;
import grid.Piece.PieceColor;
import grid.Piece.PieceHeight;
import grid.Piece.PieceShape;
import grid.Piece.PieceTexture;
import grid.QawalePiece;
import grid.QuartoPiece;
import logic.DefeatException;
import logic.GameLogic;
import logic.VictoryException;

public class GameLogicTest {

	Grid gridQawale = new Grid(4, 4, GridType.QAWALE);
	Grid gridQuarto = new Grid(4, 4, GridType.QUARTO);

	QawalePiece firstPieceQawale = new QawalePiece(PieceColor.CREAM);
	QawalePiece secondPieceQawale = new QawalePiece(PieceColor.CREAM);
	QawalePiece thirdPieceQawale = new QawalePiece(PieceColor.CREAM);
	QawalePiece fourthPieceQawale = new QawalePiece(PieceColor.CREAM);
	QawalePiece fifthPieceQawale = new QawalePiece(PieceColor.BROWN);

	QuartoPiece firstPieceQuarto = new QuartoPiece(PieceColor.BROWN, PieceHeight.SMALL, PieceShape.ROUNDED,
			PieceTexture.HOLLOW);
	QuartoPiece secondPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.SMALL, PieceShape.SQUARED,
			PieceTexture.FULL);
	QuartoPiece thirdPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.SMALL, PieceShape.ROUNDED,
			PieceTexture.FULL);
	QuartoPiece fourthPieceQuarto = new QuartoPiece(PieceColor.BROWN, PieceHeight.SMALL, PieceShape.SQUARED,
			PieceTexture.FULL);
	QuartoPiece fifthPieceQuarto = new QuartoPiece(PieceColor.CREAM, PieceHeight.TALL, PieceShape.SQUARED,
			PieceTexture.FULL);

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

		assertThrows(VictoryException.class, () -> {
			GameLogic.victoryLogic(firstPieceQawale, gridQawale);
		});

		gridQawale.putPiece(fifthPieceQawale, 0, 3);

		assertThrows(DefeatException.class, () -> {
			GameLogic.victoryLogic(firstPieceQawale, gridQawale);
		});
	}

	@Test
	public void testQuarto1() {
		gridQuarto.putPiece(firstPieceQuarto, 0, 0);
		gridQuarto.putPiece(secondPieceQuarto, 0, 1);
		gridQuarto.putPiece(thirdPieceQuarto, 0, 2);
		gridQuarto.putPiece(fourthPieceQuarto, 0, 3);

		assertThrows(VictoryException.class, () -> {
			GameLogic.victoryLogic(firstPieceQuarto, gridQuarto);
		});
	}

	@Test
	public void testQuarto2() {
		gridQuarto.putPiece(firstPieceQuarto, 0, 0);
		gridQuarto.putPiece(secondPieceQuarto, 0, 1);
		gridQuarto.putPiece(thirdPieceQuarto, 0, 2);
		gridQuarto.putPiece(fifthPieceQuarto, 0, 3);

		assertThrows(DefeatException.class, () -> {
			GameLogic.victoryLogic(firstPieceQuarto, gridQuarto);
		});
	}

}
