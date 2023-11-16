package logic;
import java.util.*;

import grid.Grid;
import grid.Piece.PieceColor;
import grid.Piece.PieceHeight;
import grid.Piece.PieceShape;
import grid.Piece.PieceTexture;
import grid.QawalePiece;
import grid.QuartoPiece;

public class QuartoPlayer extends Player {

	private static List<QuartoPiece> qPieces;
	private static int currentChoice;
	
	public QuartoPlayer(String name) {
		super(name);
		currentChoice=0;
		qPieces=new ArrayList<QuartoPiece>();
		qPieces.add(new QuartoPiece(PieceColor.RED, PieceHeight.SMALL,PieceShape.SQUARED, PieceTexture.HOLLOW));
		qPieces.add(new QuartoPiece(PieceColor.RED, PieceHeight.SMALL,PieceShape.SQUARED, PieceTexture.FULL));
		qPieces.add(new QuartoPiece(PieceColor.RED, PieceHeight.SMALL,PieceShape.ROUNDED, PieceTexture.HOLLOW));
		qPieces.add(new QuartoPiece(PieceColor.RED, PieceHeight.SMALL,PieceShape.ROUNDED, PieceTexture.FULL));
		qPieces.add(new QuartoPiece(PieceColor.YELLOW, PieceHeight.SMALL,PieceShape.SQUARED, PieceTexture.HOLLOW));
		qPieces.add(new QuartoPiece(PieceColor.YELLOW, PieceHeight.SMALL,PieceShape.SQUARED, PieceTexture.FULL));
		qPieces.add(new QuartoPiece(PieceColor.YELLOW, PieceHeight.SMALL,PieceShape.ROUNDED, PieceTexture.HOLLOW));
		qPieces.add(new QuartoPiece(PieceColor.YELLOW, PieceHeight.SMALL,PieceShape.ROUNDED, PieceTexture.FULL));
		qPieces.add(new QuartoPiece(PieceColor.RED, PieceHeight.TALL,PieceShape.ROUNDED, PieceTexture.HOLLOW));
		qPieces.add(new QuartoPiece(PieceColor.RED, PieceHeight.TALL,PieceShape.ROUNDED, PieceTexture.FULL));
		qPieces.add(new QuartoPiece(PieceColor.RED, PieceHeight.TALL,PieceShape.SQUARED, PieceTexture.HOLLOW));
		qPieces.add(new QuartoPiece(PieceColor.RED, PieceHeight.TALL,PieceShape.SQUARED, PieceTexture.FULL));
		qPieces.add(new QuartoPiece(PieceColor.YELLOW, PieceHeight.TALL,PieceShape.ROUNDED, PieceTexture.HOLLOW));
		qPieces.add(new QuartoPiece(PieceColor.YELLOW, PieceHeight.TALL,PieceShape.ROUNDED, PieceTexture.FULL));
		qPieces.add(new QuartoPiece(PieceColor.YELLOW, PieceHeight.TALL,PieceShape.SQUARED, PieceTexture.HOLLOW));
		qPieces.add(new QuartoPiece(PieceColor.YELLOW, PieceHeight.TALL,PieceShape.SQUARED, PieceTexture.FULL));
	}
	
	public static void displayListPiece() {
		for(QuartoPiece qp:qPieces) {
			System.out.println(qp.toString());
		}
	}
	
	public boolean isVide() {
		return qPieces.size()==0;
	}
	
	public void putPiece(Grid g, int x, int y) {
		g.putPiece(qPieces.get(currentChoice), x, y);
		qPieces.remove(currentChoice);
	}
	
	public int getNbPiece() {
		return qPieces.size();
	}
	
	
	public static void choosePiece(int currentChoice) {
		QuartoPlayer.currentChoice=currentChoice;
	}
}