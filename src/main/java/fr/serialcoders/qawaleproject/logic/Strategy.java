package fr.serialcoders.qawaleproject.logic;

public interface Strategy {
    public boolean verify(Grid grid);

    public Cell createCell();

}