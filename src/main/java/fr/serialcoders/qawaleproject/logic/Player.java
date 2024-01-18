package fr.serialcoders.qawaleproject.logic;

public class Player {

    private String name;
    private Grid grid;

    public Player(String name, Grid grid) {
        this.name = name;
        this.grid = grid;
    }

    @Override
    public String toString() {
        return name;
    }

    public Grid getGrid() {
        return grid;
    }

}
