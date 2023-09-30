package grid;

public class QuartoCell implements Cell {
	private Piece piece;

	public QuartoCell() {
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
}
