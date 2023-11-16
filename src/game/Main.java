package game;

import java.util.*;


import grid.Grid;
import grid.QawalePiece;
import grid.Grid.GridType;
import grid.Piece.PieceColor;
import grid.QawaleCell;
import logic.GameLogic;
import logic.Player;
import logic.QawalePlayer;
import logic.QuartoPlayer;

public class Main {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		int res;
		System.out.println("Bienvenue dans le jeux Quarto / Qawale !");
		do {
			System.out.println("1. Jouer à Qawale");
			System.out.println("2. Jouer à Quarto");
			System.out.println("3. Quitter");
			System.out.print("Choix du jeux: ");
			res=scanner.nextInt();
			
			switch (res) {
				case 1:
					System.out.println("Lancement de Qawale...");
					launchGame(Grid.GridType.QAWALE);
					System.out.println("Merci d'avoir joué à Qawale !");
					break;
				case 2:
					System.out.println("Lancement de Quarto...");
					launchGame(Grid.GridType.QUARTO);
					System.out.println("Merci d'avoir joué à Quarto !");
					break;
				case 3:
					System.out.println("Merci d'avoir joué sur notre console et à bientot!");
					break;
				default:
					System.out.println("Le choix de l'option est invalide. Veuillez choisir une option valide.");	
			}
			
			
			
		} while(res!=3);
		scanner.close();
	}
	
	
	
	public static void initPlayer(Player p1, Player p2, Grid.GridType gType) {
		if(gType==Grid.GridType.QAWALE) {
			p1=new QawalePlayer(PieceColor.RED, "Player 1");
			p2=new QawalePlayer(PieceColor.YELLOW, "Player 2");
		}
		
		if(gType==Grid.GridType.QUARTO) {
			p1=new QuartoPlayer("Player 1");
			p2=new QuartoPlayer("Player 2");
		}
	}
	
	
	
	public static Grid initGrid(Grid.GridType gType) {
		Grid g= new Grid(4, 4, gType);
		if(gType==GridType.QAWALE) {
			g.putPiece(new QawalePiece(PieceColor.CREAM), 0, 0);
			g.putPiece(new QawalePiece(PieceColor.CREAM), 0, 0);
			
			g.putPiece(new QawalePiece(PieceColor.CREAM), 0, 3);
			g.putPiece(new QawalePiece(PieceColor.CREAM), 0, 3);
			
			g.putPiece(new QawalePiece(PieceColor.CREAM), 3, 0);
			g.putPiece(new QawalePiece(PieceColor.CREAM), 3, 0);
			
			g.putPiece(new QawalePiece(PieceColor.CREAM), 3, 3);
			g.putPiece(new QawalePiece(PieceColor.CREAM), 3, 3);
		}
		return g;
	}
	
	
	
	public static void launchGame(Grid.GridType gType) {
		Grid g=initGrid(gType);
		g.display();
		Player p1;
		Player p2;
		if(g.getGridType()==Grid.GridType.QAWALE) {
			p1=new QawalePlayer(PieceColor.RED, "Player 1");
			p2=new QawalePlayer(PieceColor.YELLOW, "Player 2");
		} else {
			p1=new QuartoPlayer("Player 1");
			p2=new QuartoPlayer("Player 2");
		}
		Scanner scanner = new Scanner(System.in);
		int x;
		int y;
		Player currentPlayer=p1;
		while(!currentPlayer.isVide()) {
			if(g.getGridType()==Grid.GridType.QUARTO) {
				QuartoPlayer waitPlayer=(currentPlayer == p1) ? (QuartoPlayer)p2 : (QuartoPlayer)p1;
				System.out.println(waitPlayer.toString()+" choisit la piece que "+currentPlayer.toString()+" va positioner");
				QuartoPlayer.displayListPiece();
				
				int index=scanner.nextInt();
				QuartoPlayer.choosePiece(index);
			}
			do {
				System.out.println(currentPlayer.toString()+", c'est à vous de jouer");
				if(g.getGridType()==Grid.GridType.QAWALE){
					System.out.println("Veuillez placer votre pion dans une case non vide");
				} else {
					System.out.println("Veuillez placer votre pion dans une case vide");
				}
				System.out.println("Entrez la coordonnée x: ");
				x=scanner.nextInt();
				System.out.println("Entrez la coordonnée y: ");
				y=scanner.nextInt();
			} while((x>3 || x<0 ||y>3 || y<0) || ((g.getCell(x, y).isEmpty() && g.getGridType()==Grid.GridType.QAWALE) || ((!g.getCell(x, y).isEmpty()) && g.getGridType()==Grid.GridType.QUARTO)));
			
			
			currentPlayer.putPiece(g,x, y);
			
			if(g.getGridType()==Grid.GridType.QAWALE) {
				System.out.println(currentPlayer.toString()+" à "+currentPlayer.getNbPiece()+" piéces");
				int pileSize=((QawaleCell)g.getCell(x, y)).getContent().size();
				System.out.println("La piece à etait poser sur une pile de "+ (pileSize-1)+" piéces");
				System.out.println("Déplacer les "+pileSize+" piéces");
				GameLogic.moveCaseContent(g, x, y);
				System.out.println("Appuyer sur '1' pour 'Qaaawaallle !'");
			} else {
				g.display();
				System.out.println("Appuyer sur '1' pour 'Quartoooo !'");
			}
			System.out.println("Appuyer sur '0' dans le cas ou vous n'avez pas encore repérer une potentiel victoire");
			x=scanner.nextInt();
			if(x==1) {
				boolean res=GameLogic.victoryLogic(g);
				System.out.print(currentPlayer.toString()+" ");
				if(res) {
					System.out.println("à gagner !");
				} else {
					System.out.println("à perdu !");
				}
				return;
			}
			currentPlayer=(currentPlayer == p1) ? p2 : p1;
		}
		System.out.println("Egalité");
	}
}
