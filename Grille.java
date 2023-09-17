
public class Grille{
    private int nblig;
    private int nbcol;
    private Case[][] grille;

    public Grille(int lig,int col){
        nblig=lig;
        nbcol=col;
        grille = new Case[lig][col];
        for(int i=0;i<lig;i++){
            for(int j=0;j<col;j++){
                grille[i][j]=new Case();
            }
        }
    }

    public void PoserPion(Pion p,int i,int j){
        if(i<nblig && j<nbcol){
            grille[i][j].add(p);
        }
    }
    /* public void move(){

        à compléter
    }*/
    
    @Override
    public String toString() {
    StringBuilder sb = new StringBuilder();
    
    // Ligne supérieure de la bordure
    sb.append("+------+------+------+------+\n");

    for (int i = 0; i < nblig; i++) {
        sb.append("|"); // Début de la ligne
        for (int j = 0; j < nbcol; j++) {
            sb.append(" " + grille[i][j] + "     |"); // Ajoute chaque élément de la grille entre des barres verticales
        }
        sb.append("\n"); // Fin de la ligne
        
        // Ligne horizontale de la bordure (sauf pour la dernière ligne)
        if (i < nblig-1) {
            sb.append("+------+------+------+------+\n");
        }
    }
    
    // Ligne inférieure de la bordure
    sb.append("+------+------+------+------+\n");
    
    return sb.toString();
}

    
    

}