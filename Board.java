package JavaSpiel;
import java.util.Arrays;

public class Board {
    public enum CellState {
        EMPTY, PLAYER_ONE, PLAYER_TWO
    }

    private final int rows;
    private final int cols;
    private final CellState[][] grid;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new CellState[rows][cols];
        for (CellState[] row : grid) {
            Arrays.fill(row, CellState.EMPTY);
        }
    }

    public boolean placeChip(int col, CellState chip) {
        if (col < 0 || col >= cols) {
            return false;
        }

        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][col] == CellState.EMPTY) {
                grid[row][col] = chip;
                return true;
            }
        }
        return false; // Column is full
    }

    public boolean isFull() {
        for (int col = 0; col < cols; col++) {
            if (grid[0][col] == CellState.EMPTY) {
                return false;
            }
        }
        return true;
    }

    public boolean checkWin() {
        return checkHorizontal() || checkVertical() || checkDiagonals();
    }

    private boolean checkHorizontal() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols - 3; col++) {
                if (grid[row][col] != CellState.EMPTY &&
                        grid[row][col] == grid[row][col + 1] &&
                        grid[row][col] == grid[row][col + 2] &&
                        grid[row][col] == grid[row][col + 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVertical() {
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows - 3; row++) {
                if (grid[row][col] != CellState.EMPTY &&
                        grid[row][col] == grid[row + 1][col] &&
                        grid[row][col] == grid[row + 2][col] &&
                        grid[row][col] == grid[row + 3][col]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < cols - 3; col++) {
                if (grid[row][col] != CellState.EMPTY &&
                        grid[row][col] == grid[row + 1][col + 1] &&
                        grid[row][col] == grid[row + 2][col + 2] &&
                        grid[row][col] == grid[row + 3][col + 3]) {
                    return true;
                }
            }
            for (int col = 3; col < cols; col++) {
                if (grid[row][col] != CellState.EMPTY &&
                        grid[row][col] == grid[row + 1][col - 1] &&
                        grid[row][col] == grid[row + 2][col - 2] &&
                        grid[row][col] == grid[row + 3][col - 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    public CellState[][] getGrid() {
        return grid;
    }
}
