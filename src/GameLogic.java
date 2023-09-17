import java.util.Scanner;

public abstract final class GameLogic {
	public enum Movement {NONE, UP, DOWN, LEFT, RIGHT};
	
	public static moveCaseContent(Grid g, int x, int y) {
		Case c = g.getCase(x, y);
		int oldX = x;
		int oldY = y;
		int newX, newY;
		Scanner scan = new Scanner(System.in);
		Movement d = Movement.NONE;
		
		while (! c.getContent().isEmpty()) {
			System.out.println("Rentrez la nouvelle coordonnée x de la piece :");
			newX = Integer.parseInt(scan.nextLine());
			System.out.println("Rentrez la nouvelle coordonnée y de la piece :");
			newY = Integer.parseInt(scan.nextLine());
			
			if (newX == oldX && newY == oldY) {
				System.out.println("La pièce ne doit pas être placée au même endroit.");
				continue;
			} else if (oldX != newX && oldY != newY) {
				System.out.println("La pièce ne peut pas être déplacée en diagonale.");
				continue;
			} else if (
					(d == Movement.DOWN && newX == oldX && oldY > newY)
					|| (d == Movement.UP && newX == oldX && oldY < newY)
					|| (d == Movement.LEFT && newX > oldX && oldY == newY)
					|| (d == Movement.RIGHT && newX < oldX && oldY == newY)) {
				System.out.println("La pièce ne doit pas revenir en arrière.");
				continue;
			} else {
				g.movePiece(oldX, oldY, newX, newY);
				oldX = newX;
				oldY = newY;
			}
		}
	}

}
