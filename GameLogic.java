public class GameLogic{

    private Grille grille;
    
    public GameLogic(Grille grille){
        this.grille=grille;
    }

    public void victoryLogic(Piece piece) throws VictoryException, DefeatException{

        //Analyse des lignes
        for(int i=0; i<4; i++){
            if(piece.equals(g.getCase(i,0).getPiece()) && piece.equals(g.getCase(i,1).getPiece()) && piece.equals(g.getCase(i,1).getPiece()) && piece.equals(g.getCase(i,3).getPiece())){
                throw new VictoryException("");
            }
        }

        //Analyse des colonnes
        for(int j=0; j<4; j++){
            if(piece.equals(g.getCase(0,j).getLastPiece()) && piece.equals(g.getCase(1,j).getPiece()) && piece.equals(g.getCase(2,j).getPiece()) && piece.equals(g.getCase(3,j).getPiece())){
                throw new VictoryException("");
            }
        }

        //Verifications des diagonales
        if(piece.equals(g.getCase(0,0).getPiece()) && piece.equals(g.getCase(1,1).getPiece()) && piece.equals(g.getCase(2,2).getPiece()) && piece.equals(g.getCase(3,3).getPiece())){
            throw new VictoryException("");
        }

        if(piece.equals(g.getCase(0,3).getPiece()) && piece.equals(g.getCase(1,2).getPiece()) && piece.equals(g.getCase(2,1).getPiece()) && piece.equals(g.getCase(3,0).getPiece())){
            throw new VictoryException("");
        }
        throw new DefeatException("");
    }
}