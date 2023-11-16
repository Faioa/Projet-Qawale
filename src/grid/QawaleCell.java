package grid;

import java.util.ArrayList;
import java.util.List;

public class QawaleCell implements Cell {

	private List<QawalePiece> content;

	public QawaleCell() {
		content = new ArrayList<QawalePiece>();
	}

	@Override
	public void add(Piece p) {
		content.add((QawalePiece)p);
	}

	public void remove() {
		content.remove(0);
	}

	@Override
	public Piece getPiece() {
		if(content.size()==0) {
			return null;
		}
		return content.get(content.size() - 1);
	}

	public List<QawalePiece> getContent() {
		return content;
	}

	@Override
	public boolean isEmpty() {
		return content.size() == 0;
	}
	
  
  @Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (!content.isEmpty()) {
			int i = 0;
			while (i < (content.size() - 1)) {
				sb.append(content.get(i++) + "-");
			}
			sb.append(content.get(content.size() - 1));

		}
		return sb.toString();
	}

}
