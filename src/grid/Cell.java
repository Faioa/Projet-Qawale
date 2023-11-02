package grid;

public interface Cell {
	public void add(Piece p);

	public Piece getPiece();

	public boolean isEmpty();

	public int getX();

	public int getY();
}
