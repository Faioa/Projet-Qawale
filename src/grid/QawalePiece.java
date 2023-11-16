package grid;

public class QawalePiece extends Piece {

	public QawalePiece(PieceColor color) {
		super(color);
	}

	@Override
	public boolean compare(Piece p) {
		if (p == this) {
			return true;
		}
		if (!(p instanceof QawalePiece)) {
			return false;
		}

		QawalePiece tmp = (QawalePiece) p;

		return this.getColor() == tmp.getColor();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof QawalePiece)) {
			return false;
		}

		QawalePiece tmp = (QawalePiece) o;

		return this.getColor() == tmp.getColor();
	}

}
