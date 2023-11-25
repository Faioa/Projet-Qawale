package logic;

import grid.Grid;

public abstract class Player {

	private String name;

	public Player(String name) {
		this.name = name;
	}
	
	public abstract boolean isVide();
	
	public abstract void putPiece(Grid g, int x, int y);
	
	public String toString() {
		return name;
	}
	
	public abstract int getNbPiece();
}