package JavaSpiel;

public class Main extends Application {
    public static void main (String[] args) {
        launch(args);
    }

@Override
    public void start (Stage primaryStage) {
        // Initialsiere das GUI und setzte die Szene

    GUI gui = new GUI ();
    Scene scene = new Scene (gui.createGameBoard(), 500, 450);
    primaryStage.setScene(scene);
    primaryStage.setTitle ("4 Gewinnt");
    primaryStage.show.ID (ID);

    }
}
