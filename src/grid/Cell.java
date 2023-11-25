package grid;

import java.util.List;

public abstract class Cell {

	private int x;
	private int y;
	private List<Piece> content;
	private Piece piece = null;

	protected void add(Piece p) {
		content.add(p);
		piece = p;
	}

	public List<Piece> getContent() {
		return content;
	}

	public Piece getPiece() {
		return piece;
	}

	private void remove(int i) {
		if (content.size() > i) {
			content.remove(i);
		}
	}

	public void remove() {
		remove(0);
	}

	public boolean isEmpty() {
		return content.isEmpty();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	protected void setX(int x) {
		this.x = x;
	}

	protected void setY(int y) {
		this.y = y;
	}

	@SuppressWarnings("unchecked")
	protected void setContent(List<? extends Piece> content) {
		this.content = (List<Piece>) content;
	}

	@Override
	public String toString() {
		return content.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Cell)) {
			return false;
		}

		Cell tmp = (Cell) o;

		return tmp.getX() == getX() && tmp.getY() == getY() && getPiece().equals(tmp.getPiece());
	}

}
