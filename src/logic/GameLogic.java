package logic;

import java.util.Scanner;


import grid.Cell;
import grid.Grid;
import grid.Piece;
import grid.QawaleCell;
import grid.Grid.GridType;

public final class GameLogic {
	public enum Movement {
		NONE, UP, DOWN, LEFT, RIGHT
	};

	private GameLogic instance = new GameLogic();

	private GameLogic() {
	}
	
	
	public static void moveCaseContent(Grid g, int x, int y) {
		
		if (g.getGridType() == GridType.QUARTO) {
			return;
		}
		
		Cell c = g.getCell(x, y);
		int oldX = x;
		int oldY = y;
		int newX, newY;
		Scanner scan = new Scanner(System.in);
		Movement m = Movement.NONE;

		int nbInter = 0;
		int maxInter = ((QawaleCell) c).getContent().size();
		int nbIter=maxInter;
		while (nbInter < maxInter) {
			g.display();
			System.out.println("Rentrez la nouvelle coordonnée x de la piece :");
			newX = Integer.parseInt(scan.nextLine());
			System.out.println("Rentrez la nouvelle coordonnée y de la piece :");
			newY = Integer.parseInt(scan.nextLine());

			if (newX < 0 || newX > 3 || newY < 0 || newY > 3) {
				System.out.println("Les coordonnées ne sont pas corrects.");
				continue;
			} else if (newX == oldX && newY == oldY) {
				System.out.println("La pièce ne doit pas être placée au même endroit.");
				continue;
			} else if (oldX != newX && oldY != newY) {
				System.out.println("La pièce ne peut pas être déplacée en diagonale.");
				continue;
			} else if ((m == Movement.DOWN && newX == oldX && oldY > newY)
					|| (m == Movement.UP && newX == oldX && oldY < newY)
					|| (m == Movement.LEFT && newX > oldX && oldY == newY)
					|| (m == Movement.RIGHT && newX < oldX && oldY == newY)) {
				System.out.println("La pièce ne doit pas revenir en arrière.");
				continue;
			} else {
				for(int i=nbIter;i>0;i--) {
					g.movePiece(oldX, oldY, newX, newY, i);
				}
				nbIter--;

				if (newX == oldX && oldY > newY) {
					m = Movement.UP;
				} else if (newX == oldX && oldY < newY) {
					m = Movement.DOWN;
				} else if (newX > oldX && oldY == newY) {
					m = Movement.RIGHT;
				} else {
					m = Movement.LEFT;
				}
				oldX=newX;
				oldY=newY;
				
			}
			nbInter++;
		}
		g.display();
	}

	public static boolean victoryLogic(Grid g) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Vous avez crier !");
		System.out.println("Votre allignement gagnant est situé sur: ");
		System.out.println("1. Ligne");
		System.out.println("2. Colonne");
		System.out.println("3. Diagonal");
		int res=scanner.nextInt();
		if(res==1) {
			System.out.println("Quel est le numéro de la ligne ?");
			int numLigne=scanner.nextInt();
			Piece piece=g.getCell(numLigne, 0).getPiece();
			
			// Analyse des lignes
			
			if (piece!=null && piece.compare(g.getCell(numLigne, 1).getPiece())
					&& piece.compare(g.getCell(numLigne, 2).getPiece()) && piece.compare(g.getCell(numLigne, 3).getPiece())) {
				return true;
			}
		}
		
		
		
		if(res==2) {
			System.out.println("Quel est le numéro de la colonne ?");
			int numCol=scanner.nextInt();
			Piece piece=g.getCell(0, numCol).getPiece();
			// Analyse des colonnes
			if (piece!=null && piece.compare(g.getCell(1, numCol).getPiece())
					&& piece.compare(g.getCell(2, numCol).getPiece()) && piece.compare(g.getCell(3, numCol).getPiece())) {
				return true;
			}
		}

		if(res==3) {
			// Verifications des diagonales
			Piece piece=g.getCell(0, 0).getPiece();
			if (piece!=null && piece.compare(g.getCell(1, 1).getPiece())
					&& piece.compare(g.getCell(2, 2).getPiece()) && piece.compare(g.getCell(3, 3).getPiece())) {
				return true;
			}
			
			piece=g.getCell(0, 3).getPiece();
			if (piece!=null && piece.compare(g.getCell(1, 2).getPiece())
					&& piece.compare(g.getCell(2, 1).getPiece()) && piece.compare(g.getCell(3, 0).getPiece())) {
				return true;
			}
		}
		return false;
	}

}
