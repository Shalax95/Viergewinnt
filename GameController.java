package JavaSpiel;

public class GameController {
    private Spiel spiel;
    private GUI gui;

    public GameController (Spiel spiel, GUI gui) {
        this.spiel = spiel;
        this.gui = gui;
    }

    public void handleMove (int column) {
        if (game.placeToken (column)) {
            gui.updateUI(); // Aktualisiert das Spielfeld in der GUI
            if (game.isGameWon()) {
                gui.showWinMesage (game.getCurrentPlayer().getName());
            }
        }
    }
}
