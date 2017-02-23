package Tetris;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Random;


import static javafx.scene.media.AudioClip.INDEFINITE;

/**
 * Main.
 */
public class Main extends Application {

    /**
     * Screen width.
     */
    private final int width = 800;

    /**
     * Screen height.
     */
    private final int height = 800;

    private Timeline timeline;
    private boolean run = false;
    private String mediaPath = "Games/src/Tetris/music/Troika.mp3";
    private Random rand = new Random();

    /**
     * Main method.
     * @param primaryStage Stage.
     */
    @Override
    public void start(Stage primaryStage) {
        // Media
        Media media = new Media(new File(mediaPath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        // Panes
        StartPane startPane = new StartPane();
        OverPane overPane = new OverPane();
        ScoresPane scoresPane = new ScoresPane();
        SettingsPane settingsPane = new SettingsPane();
        // Scenes
        Scene settingsScene = new Scene(settingsPane);
        Scene startScene = new Scene(startPane);
        Scene overScene = new Scene(overPane);
        Scene scoresScene = new Scene(scoresPane);
        // Setting default scene
        primaryStage.setScene(startScene);
        // Switching between scenes
        startPane.getExit().setOnMouseClicked(ev -> System.exit(1));
        startPane.getScores().setOnMouseClicked(ev -> primaryStage.setScene(scoresScene));
        startPane.getStart().setOnMouseClicked(ev -> {
            game(primaryStage);
        });
        startPane.getSettings().setOnMouseClicked(ev -> primaryStage.setScene(settingsScene));
        settingsPane.getBack().setOnMouseClicked(ev -> {
            primaryStage.setScene(startScene);
            settingsPane.setDefault();
        });
        scoresPane.getBack().setOnMouseClicked(ev -> primaryStage.setScene(startScene));
        overPane.getExit().setOnMouseClicked(ev -> System.exit(1));
        overPane.getStartOver().setOnMouseClicked(ev -> primaryStage.setScene(startScene));
        // Music options
        settingsPane.getMusicBox().setOnAction(ev -> {
            if (settingsPane.getMusicBox().isSelected()) {
                mediaPlayer.play();
                mediaPlayer.setCycleCount(INDEFINITE);
            } else {
                mediaPlayer.stop();
            }
        });
        // Color choose
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
        // Music is playing by default
        if (settingsPane.getMusicBox().isSelected()) {
            mediaPlayer.play();
            mediaPlayer.setCycleCount(INDEFINITE);
        }
        // Show
        primaryStage.show();
    }

    /**
     * Resolve width of the screen.
     * @return width.
     */
    int getWidth() {
        return width;
    }

    /**
     * Resolve height of the screen.
     * @return height.
     */
    int getHeight() {
        return height;
    }

    /**
     * Game logic.
     * @param stage primaryStage.
     */
    private void game(Stage stage) {
        GamePane gamePane = new GamePane();
        gamePane.drawShapes(1); // Нужно сделать собственный таймлайн в логике, либо как-то обойти
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.015), event -> gamePane.controls()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        stage.setScene(new Scene(gamePane));
        gamePane.requestFocus(); // Потом узнать, зачем
    }

    /**
     * Main.
     * @param args args.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
