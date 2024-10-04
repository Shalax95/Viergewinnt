package JavaSpiel;

// GameGUI.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

class GameGUI extends JFrame {
    private final Game game;
    private final JButton[] columnButtons;
    private final JLabel[][] cellLabels;
    private final int cellDiameter = 120;
    private final int coinDiameter = 100;

    public GameGUI(Game game) {
        this.game = game;
        setTitle("Vier Gewinnt - 3D Look");
        setSize(cellDiameter * game.getBoard().getGrid()[0].length + 28,
                cellDiameter * game.getBoard().getGrid().length + 80);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        setResizable(false);


        JPanel boardPanel = new JPanel(new GridLayout(game.getBoard().getGrid().length, game.getBoard().getGrid()[0].length)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoardBackground(g);
            }
        };
        cellLabels = new JLabel[game.getBoard().getGrid().length][game.getBoard().getGrid()[0].length];

        // Initialisiere jede Spielfeldzelle
        for (int row = 0; row < game.getBoard().getGrid().length; row++) {
            for (int col = 0; col < game.getBoard().getGrid()[0].length; col++) {
                cellLabels[row][col] = new JLabel();
                cellLabels[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                cellLabels[row][col].setVerticalAlignment(SwingConstants.CENTER);
                cellLabels[row][col].setOpaque(false);
                boardPanel.add(cellLabels[row][col]);
            }
        }


        JPanel controlPanel = new JPanel(new GridLayout(1, game.getBoard().getGrid()[0].length));
        columnButtons = new JButton[game.getBoard().getGrid()[0].length];
        for (int col = 0; col < columnButtons.length; col++) {
            columnButtons[col] = createStyledButton();
            int finalCol = col;
            columnButtons[col].addActionListener(e -> handleColumnClick(finalCol));
            controlPanel.add(columnButtons[col]);
        }

        // Füge die Panels zum Hauptfenster hinzu
        add(controlPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        updateBoard();  // Brett aktualisieren
    }

    // Methode, die aufgerufen wird, wenn eine Spalte geklickt wird
    private void handleColumnClick(int col) {
        if (!game.makeMove(col)) {
            JOptionPane.showMessageDialog(this, "Diese Spalte ist voll oder ungültig. Wähle eine andere Spalte.");
            return;
        }

        updateBoard();

        if (game.checkWin()) {
            String winner = game.getCurrentPlayer().getChip() == Board.CellState.PLAYER_ONE ? "Spieler 2" : "Spieler 1";
            JOptionPane.showMessageDialog(this, winner + " hat gewonnen!");
            resetGame();
        } else if (game.isBoardFull()) {
            JOptionPane.showMessageDialog(this, "Unentschieden! Das Spielfeld ist voll.");
            resetGame();
        }
    }

    // Erstellt die stilisierten Buttons für die Spaltenauswahl
    private JButton createStyledButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(60, 40)); // Bevorzugte Größe für Layout
        button.setIcon(createArrowIcon(new Color(70, 130, 180))); // Pfeil-Icon für Einwurf
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setRolloverEnabled(true);
        button.setRolloverIcon(createArrowIcon(new Color(100, 150, 200)));
        return button;
    }

    // Erstellt ein Pfeil-Icon für die Spaltenauswahl
    private Icon createArrowIcon(Color color) {
        int width = 40, height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fillPolygon(new int[]{5, width - 5, width / 2}, new int[]{5, 5, height - 5}, 3); // Zeichne Pfeilform
        g2.dispose();
        return new ImageIcon(image);
    }

    // Zeichnet den Hintergrund des Spielfelds mit transparenten Löchern und 3D-Effekt
    private void drawBoardBackground(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Zeichne das blaue Brett mit leichtem Schatten für den 3D-Effekt
        g2.setColor(new Color(30, 144, 255)); // Blaue Farbe für das Brett
        g2.fillRect(0, 0, getWidth(), getHeight()); // Hintergrund des Bretts

        // Schattierung hinzufügen für den 3D-Look des Bretts
        g2.setColor(new Color(0, 0, 0, 50)); // Leichter Schatten
        g2.fillRect(5, 5, getWidth() - 10, getHeight() - 10); // Schatten um das Brett

        // Löcher mit transparenten Bereichen, damit die Spielchips durchscheinen
        for (int row = 0; row < game.getBoard().getGrid().length; row++) {
            for (int col = 0; col < game.getBoard().getGrid()[0].length; col++) {
                int x = col * cellDiameter + 10; // X-Position des Lochs
                int y = row * cellDiameter + 10; // Y-Position des Lochs

                // Loch-Schatten für 3D-Effekt
                g2.setColor(new Color(0, 0, 0, 70));
                g2.fillOval(x + 5, y + 5, cellDiameter - 20, cellDiameter - 20);

                // Zeichne das Loch mit transparentem Innenbereich
                g2.setColor(new Color(255, 255, 255, 0)); // Transparent
                g2.fillOval(x, y, cellDiameter - 20, cellDiameter - 20);
            }
        }
    }

    // Aktualisiert die visuelle Darstellung des Spielfelds
    private void updateBoard() {
        Board.CellState[][] grid = game.getBoard().getGrid();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                switch (grid[row][col]) {
                    case PLAYER_ONE -> cellLabels[row][col].setIcon(createRoundCoinIcon(Color.RED));
                    case PLAYER_TWO -> cellLabels[row][col].setIcon(createRoundCoinIcon(Color.YELLOW));
                    case EMPTY -> cellLabels[row][col].setIcon(null); // Leere Zellen bleiben transparent
                }
            }
        }
    }

    // Erstellt ein realistisches rundes Spielchip-Icon mit 3D-Effekt, zentriert im Feld
    private Icon createRoundCoinIcon(Color color) {
        // Erstelle ein Bild mit der Größe des Chips (kleiner als die Zelle, aber groß genug)
        BufferedImage image = new BufferedImage(coinDiameter, coinDiameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Zeichne 3D-Schatten für Tiefe
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillOval(5, 5, coinDiameter - 10, coinDiameter - 10);

        // Zeichne den Chip mit Farbverlauf für den 3D-Look
        g2.setPaint(new GradientPaint(0, 0, color.brighter(), coinDiameter, coinDiameter, color.darker()));
        g2.fillOval(0, 0, coinDiameter - 10, coinDiameter - 10);

        // Glanzeffekt für den 3D-Look
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawArc(5, 5, coinDiameter - 20, coinDiameter - 20, 45, 90);

        g2.dispose();
        return new ImageIcon(image);
    }

    // Setzt das Spiel zurück und startet neu
    private void resetGame() {
        game.getBoard().getGrid();
        setVisible(false);
        new GameGUI(new Game(6, 7)).setVisible(true);
    }
}
