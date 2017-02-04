package Tetris;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private int width = 800;
    private int height = 800;
    private Timeline timeline = new Timeline();
    private boolean run = false;

    @Override
    public void start(Stage primaryStage) {
        StartPane startPane = new StartPane();
        OverPane overPane = new OverPane();
        ScoresPane scoresPane = new ScoresPane();
        SettingsPane settingsPane = new SettingsPane();
        Scene settingsScene = new Scene(settingsPane);
        Scene startScene = new Scene(startPane);
        Scene overScene = new Scene(overPane);
        Scene scoresScene = new Scene(scoresPane);

        primaryStage.setScene(startScene);

        startPane.getExit().setOnMouseClicked(ev -> System.exit(1));
        startPane.getScores().setOnMouseClicked(ev -> primaryStage.setScene(scoresScene));
        startPane.getStart().setOnMouseClicked(ev -> {
            GamePane gamePane = new GamePane();
            Scene gameScene = new Scene(gamePane);
            primaryStage.setScene(gameScene);
        });
        startPane.getSettings().setOnMouseClicked(ev -> primaryStage.setScene(settingsScene));
        settingsPane.getBack().setOnMouseClicked(ev -> {
            primaryStage.setScene(startScene);
            settingsPane.setDefault();
        });
        scoresPane.getBack().setOnMouseClicked(ev -> primaryStage.setScene(startScene));
        overPane.getExit().setOnMouseClicked(ev -> System.exit(1));
        overPane.getStartOver().setOnMouseClicked(ev -> primaryStage.setScene(startScene));

        settingsPane.getBlue().setOnMouseClicked(ev -> {
            settingsPane.changeColor("blue");
            settingsPane.chooseColorChange("blue");
        });
        settingsPane.getRed().setOnMouseClicked(ev -> {
            settingsPane.changeColor("red");
            settingsPane.chooseColorChange("red");
        });
        settingsPane.getWhite().setOnMouseClicked(ev -> {
            settingsPane.changeColor("white");
            settingsPane.chooseColorChange("white");
        });
        settingsPane.getPurple().setOnMouseClicked(ev -> {
            settingsPane.changeColor("purple");
            settingsPane.chooseColorChange("purple");
        });
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
