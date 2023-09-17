public class PionQuarto extends Pion{
    private PieceHeight height;
    private PieceShape shape;
    private PieceTexture texture;

    public PionQuarto(int coul,int h,int sha,int text){
        // défintion couleur (0 pour beige et autre pour marron)
        if(coul == 0){
            color=PieceColor.BEIGE;
        }else{
            color=PieceColor.BROWN;
        }
        // définiton hauteur (0 pour SMALL et autre pour TALL)
        if(h == 0){
            height=PieceHeight.SMALL;
        }else{
            height=PieceHeight.TALL;
        }
        //défintion shape (0 pour carré et autre pour rond)
        if(sha == 0){
            shape=PieceShape.SQUARED;
        }else{
            shape=PieceShape.ROUNDED;
        }
        // défintion texture (0 pour HOLLOW et 1 pour FULL)
        if(text == 0){
            texture=PieceTexture.HOLLOW;
        }else{
            texture=PieceTexture.FULL;
        }
        
    }
    public PieceHeight getHeight(){
            return height;
        }
    public PieceShape getShape(){
        return shape;
        }
    public PieceTexture getTexture(){
        return texture;
        }
}