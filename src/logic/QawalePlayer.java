package logic;

import java.util.ArrayList;
import java.util.List;

import grid.Grid;
import grid.Piece.PieceColor;
import grid.QawalePiece;

public class QawalePlayer extends Player {

	private PieceColor colorChoice;
	List<QawalePiece> qPieces;

	public QawalePlayer(PieceColor colorChoice, String name) {
		super(name);
		this.colorChoice = colorChoice;
		qPieces=new ArrayList<QawalePiece>();
		for(int i=0; i<8;i++) {
			qPieces.add(new QawalePiece(colorChoice));
		}
	}
	
	public boolean isVide() {
		return qPieces.size()==0;
	}
	
	public void putPiece(Grid g, int x, int y) {
		g.putPiece(qPieces.get(0), x, y);
		qPieces.remove(0);
	}
	
	public PieceColor getPieceColor() {
		return colorChoice;
	}
	
	public int getNbPiece() {
		return qPieces.size();
	}
	
	
}