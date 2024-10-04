package JavaSpiel;

// Player.java
public class Player {
    private final String name;
    private final Board.CellState chip;

    public Player(String name, Board.CellState chip) {
        this.name = name;
        this.chip = chip;
    }

    public String getName() {
        return name;
    }

    public Board.CellState getChip() {
        return chip;
    }
}
