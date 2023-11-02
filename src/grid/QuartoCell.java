package grid;

public class QuartoCell implements Cell {

	private Piece piece;
	private int x;
	private int y;

	public QuartoCell(int x, int y) {
		this.x = x;
		this.y = y;
		piece = null;
	}

	@Override
	public void add(Piece p) {
		if (piece == null) {
			piece = p;
		}
	}

	@Override
	public Piece getPiece() {
		return piece;
	}

	@Override
	public boolean isEmpty() {
		return piece == null;
	}

	@Override
	public String toString() {
		if (piece == null) {
			return "Empty";
		}

		return piece.toString();
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof QuartoCell)) {
			return false;
		}

		QuartoCell tmp = (QuartoCell) o;

		return tmp.getX() == x && tmp.getY() == y && piece.equals(tmp.getPiece());
	}

}
