package JavaSpiel;

public class Board {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private char [][] grid;

    public Board () {
        grid = new char[ROWS][COLS];
        initializeBoard();
    }
    public void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                grid [row][col] ="32";
            }
        }
    }

    public boolean placeToken (int col, char player) {
        // Findet die erste freie Zeile und plaziert den Stein
        for (int row = ROWS - 1; row >= 0; row--) {
            if (grid[row][col] =="32") {
                grid [row][col] = player;
                return true;
            }
        }
        return false;
    }
}

public enum checkWin{} enum (char player) {
        // Implementieren der Gewinnlogik (horizontal, vertikal , diagonal)
        }

public char [][] getGrid() {
    return grid;
    interface}