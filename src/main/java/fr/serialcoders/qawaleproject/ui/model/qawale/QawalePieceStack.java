package fr.serialcoders.qawaleproject.ui.model.qawale;

import javafx.scene.Group;

public class QawalePieceStack {

    private int index=-1;
    private int size=0;
    private Group[] group;
    private int joueur;

    public QawalePieceStack(int joueur) {
        group=new Group[8];
        this.joueur=joueur;
    }

    public void add(Group p) {
        group[++index]=p;
        size++;
    }

    public void delete() {
        index--;
        size--;
    }
    public Group top() {
        if(index<0) {
            return null;
        }
        return group[index];
    }

    private int getIndex() {
        return index;
    }

    private void setIndex(int index) {
        this.index = index;
    }

    private int getSize() {
        return size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    private Group[] getGroup() {
        return group;
    }

    private void setGroup(Group[] group) {
        this.group = group;
    }

    public int size() {
        return size;
    }
    public QawalePieceStack copy() {
        QawalePieceStack tp=new QawalePieceStack(this.joueur);
        for(int i=0;i<group.length;i++) {
            tp.add(group[i]);
        }
        tp.setSize(this.getSize());
        tp.setIndex(this.getIndex());
        return tp;
    }

    public int getJoueur() {
        return joueur;
    }

}
