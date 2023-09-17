public class PionQawale extends Pion{
    public PionQawale(int coul){
        // défintion couleur (0 pour beige et autre pour marron)
        if(coul == 0){
            color=PieceColor.BEIGE;
        }else{
            color=PieceColor.BROWN;
        }
    }
    public String toString(){
        if(getColor() == PieceColor.BEIGE){
            return "BEI";
        }
        else{
            return "BRO";
        }
    }
}