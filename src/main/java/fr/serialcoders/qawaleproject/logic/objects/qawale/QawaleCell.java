package fr.serialcoders.qawaleproject.logic.objects.qawale;

import fr.serialcoders.qawaleproject.logic.objects.AbstractCell;
import fr.serialcoders.qawaleproject.logic.objects.IPiece;

import java.util.ArrayList;
import java.util.List;

public class QawaleCell extends AbstractCell {

    private List<QawalePiece> content;

    public QawaleCell(int x, int y) {
        super(x, y);
        content = new ArrayList<QawalePiece>();
    }

    @Override
    public boolean add(IPiece p) {
        if (p instanceof  QawalePiece) {
            content.add((QawalePiece) p);
            return true;
        }
        return false;
    }

    public void remove() {
        content.remove(0);
    }

    @Override
    public IPiece getPiece() {
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
