public class Pion{
    protected PieceColor color;

    public Pion(){
        color=PieceColor.ORANGE;
    }
    public PieceColor getColor(){
        return color;
    }
    public String toString(){
        return "O";
    }
}