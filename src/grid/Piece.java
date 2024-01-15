package grid;

public abstract class Piece {

	public enum PieceColor {
		RED, CREAM, YELLOW
	}

	public enum PieceHeight {
		TALL, SMALL
	}

	public enum PieceShape {
		SQUARED, ROUNDED
	}

	public enum PieceTexture {
		HOLLOW, FULL
	}

	private PieceColor color;
	private boolean placed = false;

	public Piece(PieceColor color) {
		this.color = color;
	}

	public PieceColor getColor() {
		return color;
	}

	public boolean isPlaced() {
		return placed;
	}

	public void place() {
		placed = true;
	}

	public abstract boolean compare(Piece p);

	@Override
	public String toString() {
		if (color == PieceColor.YELLOW) {
			return "Y";
		}
		if (color == PieceColor.CREAM) {
			return "C";
		}
		return "R";
	}

}
