package fr.serialcoders.qawaleproject.logic.objects;

public interface ICell {
    public boolean add(IPiece p);
    public IPiece getPiece();
    public boolean isEmpty();
    public int getX();
    public int getY();
}
