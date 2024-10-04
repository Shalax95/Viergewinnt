package JavaSpiel;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(6, 7);
        GameGUI gui = new GameGUI(game);
        gui.setVisible(true);
    }
}