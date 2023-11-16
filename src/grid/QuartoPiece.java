package grid;

public class QuartoPiece extends Piece {

	private PieceHeight height;
	private PieceShape shape;
	private PieceTexture texture;

	public QuartoPiece(PieceColor color, PieceHeight height, PieceShape shape, PieceTexture texture) {
		super(color);
		this.height = height;
		this.shape = shape;
		this.texture = texture;
	}

	public PieceHeight getHeight() {
		return height;
	}

	public PieceShape getShape() {
		return shape;
	}

	public PieceTexture getTexture() {
		return texture;
	}

	@Override
	public String toString() {
		StringBuilder retval = new StringBuilder();
		retval.append(super.toString());

		if (height == PieceHeight.SMALL) {
			retval.append('S');
		} else {
			retval.append('T');
		}

		if (shape == PieceShape.ROUNDED) {
			retval.append('R');
		} else {
			retval.append('S');
		}

		if (texture == PieceTexture.FULL) {
			retval.append('F');
		} else {
			retval.append('H');
		}

		return retval.toString();
	}

	@Override
	public boolean compare(Piece p) {
		if (p == this) {
			return true;
		}
		if (!(p instanceof QuartoPiece)) {
			return false;
		}

		QuartoPiece tmp = (QuartoPiece) p;

		return this.getColor() == tmp.getColor() || this.getHeight() == tmp.getHeight()
				|| this.getShape() == tmp.getShape() || this.getTexture() == tmp.getTexture();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof QuartoPiece)) {
			return false;
		}

		QuartoPiece tmp = (QuartoPiece) o;

		return this.getColor() == tmp.getColor() && this.getHeight() == tmp.getHeight()
				&& this.getShape() == tmp.getShape() && this.getTexture() == tmp.getTexture();
	}

}
