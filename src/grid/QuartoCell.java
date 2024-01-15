package grid;

import java.util.LinkedList;

public final class QuartoCell extends Cell {

	public QuartoCell(int x, int y) {
		setX(x);
		setY(y);
		setContent(new LinkedList<QuartoPiece>());
	}

	public void add(QuartoPiece p) {
		if (isEmpty() && !p.isPlaced()) {
			super.add(p);
		}
	}

	@Override
	public void remove() {
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "Empty";
		}

		return getPiece().toString();
	}

}
