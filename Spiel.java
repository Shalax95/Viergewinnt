package JavaSpiel;

public class Spiel {
    private Board board;
    private Spieler spieler1;
    private Spieler spieler2;
    private Spieler currentSpieler;
    private boolean gameWon;

    public Spiel () {
        this.board = new Board();
        this.spieler1 = new Spieler('X', "Spieler 1");
        this.spieler2 = new Spieler('O', "Spieler 2");
        this.currentSpieler = spieler1;
        this.gameWon = false;
    }


    public boolean placeToken(int column) {
        // Platzieren des Steins und Rückgabe, ob der Zug erfolgreich war
        if (board.placeToken(column, currentSpieler.getSymbol())) {
            if (board.checkWin(currentSpieler.getSymbol())) {
                gameWon = true;
            } else {
                switchSpieler();
            }
            return true;
        }
        return false;
    }

    private void switchSpieler() {
        currentSpieler = (currentSpieler == spieler1) ? spieler2 : spieler1;
    }
    public boolean isGameWon() {
        return gameWon;
    }
    public Spieler getCurrentSpieler(){
        return currentSpieler;
    }
}
