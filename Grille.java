public class Grille{
    private int nblig;
    private int nbcol;
    private Case[][] grille;

    public Grille(int lig,int col){
        nblig=lig;
        nbcol=col;
        for(int i=0;i<lig;i++){
            for(int j=0;j<col;j++){
                grille[i][j]=new Case();
            }
        }
    }
    /* public void move(){

        à compléter
    }*/
    
    @Override
    public String toString() {
    StringBuilder sb = new StringBuilder();
    
    // Ligne supérieure de la bordure
    sb.append("+---+---+---+---+\n");

    for (int i = 0; i < 4; i++) {
        sb.append("|"); // Début de la ligne
        for (int j = 0; j < 4; j++) {
            sb.append(" " + grille[i][j] + " |"); // Ajoute chaque élément de la grille entre des barres verticales
        }
        sb.append("\n"); // Fin de la ligne
        
        // Ligne horizontale de la bordure (sauf pour la dernière ligne)
        if (i < 3) {
            sb.append("+---+---+---+---+\n");
        }
    }
    
    // Ligne inférieure de la bordure
    sb.append("+---+---+---+---+\n");
    
    return sb.toString();
}

}