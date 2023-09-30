package grid;

public abstract class Piece {

	public enum PieceColor {
		BROWN, ORANGE, CREAM
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

	public Piece(PieceColor color) {
		this.color = color;
	}

	public PieceColor getColor() {
		return color;
	}

	@Override
	public String toString() {
		if (color == PieceColor.ORANGE) {
			return "O";
		}
		if (color == PieceColor.CREAM) {
			return "C";
		}
		return "B";
	}

}
