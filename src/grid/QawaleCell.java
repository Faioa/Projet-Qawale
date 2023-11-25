package grid;

import java.util.LinkedList;

public final class QawaleCell extends Cell {

	public QawaleCell(int x, int y) {
		setX(x);
		setY(y);
		setContent(new LinkedList<QawalePiece>());
	}

	public void add(QawalePiece p) {
		super.add(p);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (!isEmpty()) {
			int i = 0;
			int size = getContent().size();
			while (i < (size - 1)) {
				sb.append(getContent().get(i++) + "-");
			}
			sb.append(getPiece());

		}
		return sb.toString();
	}
}
