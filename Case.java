import java.util.*;

public class Case{
    private List<Pion> contenu;

    public Case(){
        contenu=new ArrayList<Pion>();
    }
    public void add(Pion p){
        contenu.add(p);
    }
    public void remove(){
        contenu.remove(0);
    }
    public List<Pion> getContenu(){
        return contenu;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if (!contenu.isEmpty()){
            int i=0;
            while(i<(contenu.size()-1)){
                sb.append(contenu.get(i)+"-");
            }
            sb.append(contenu.get(contenu.size()-1));

        }
        return sb.toString();

    }
}