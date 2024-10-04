package JavaSpiel;

public class Game {
    private final Board board;
    private final Player[] players;
    private int currentPlayerIndex;

    public Game(int rows, int cols) {
        this.board = new Board(rows, cols);
        this.players = new Player[]{new Player("Spieler 1", Board.CellState.PLAYER_ONE),
                new Player("Spieler 2", Board.CellState.PLAYER_TWO)};
        this.currentPlayerIndex = 0;
    }

    public Board getBoard() {
        return board;
    }
    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    }

    public boolean makeMove(int col) {
        boolean success = board.placeChip(col, getCurrentPlayer().getChip());
        if (success) {
            nextPlayer();
        }
        return success;
    }
    public boolean checkWin() {
        return board.checkWin();
    }

    public boolean isBoardFull() {
        return board.isFull();
    }
}
