import java.util.List;

public class Grid {
	private int li;
    private int col;
    private Case[][] grid;
    
	public Grid(int li, int col) {
		this.li = li;
        this.col = col;
        
        for(int i=0;i<li;i++){
            for(int j=0;j<col;j++){
                grid[i][j] = new Case();
            }
        }
	}
	
	public void movePiece(int oldX, int oldY, int newX, int newY) {
		if (grid[oldX][oldY].isEmpty()) {
			return;
		}
		List<Piece> oldList = grid[oldX][oldY].getContent();
		List<Piece> newList = grid[newX][newY].getContent();
		newList.add(0, oldList.get(0));
		oldList.remove(0);
	}
	
	public Case getCase(int x, int y) {
		return grid[x][y].copy;
	}
}
