package Tetris;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private int width = 700;
    private int height = 800;
    private Timeline timeline = new Timeline();
    private boolean run = false;

    @Override
    public void start(Stage primaryStage) {
        StartPane startPane = new StartPane();
        OverPane overPane = new OverPane();
        ScoresPane scoresPane = new ScoresPane();
        Scene startScene = new Scene(startPane);
        Scene overScene = new Scene(overPane);
        Scene scoresScene = new Scene(scoresPane);

        primaryStage.setScene(overScene);

        startPane.getExit().setOnMouseClicked(ev -> System.exit(1));
        startPane.getScores().setOnMouseClicked(ev -> primaryStage.setScene(scoresScene));
        scoresPane.getBack().setOnMouseClicked(ev -> primaryStage.setScene(startScene));
        overPane.getExit().setOnMouseClicked(ev -> System.exit(1));
        overPane.getStartOver().setOnMouseClicked(ev -> primaryStage.setScene(startScene));

        primaryStage.show();
    }
    int getWidth() {
        return this.width;
    }
    int getHeight() {
        return this.height;
    }
    void game() {
        if (run) {

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
