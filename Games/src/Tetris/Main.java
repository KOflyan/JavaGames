package Tetris;

import Tetris.Interface.OverPane;
import Tetris.Interface.ScoresPane;
import Tetris.Interface.SettingsPane;
import Tetris.Interface.StartPane;
import Tetris.logic.GamePane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;



/** Main. */
public class Main extends Application {

    /** Screen width. */
    private static final int width = 800;
    /** Screen height. */
    private static final int height = 800;
    /** Background image */
    private Image backgroundImage;
    /** Names of tracks. Array. */
    private String[] tracks = {"Troika.mp3", "Loginska.mp3", "Karinka.mp3"};
    /** Current media player */
    public static MediaView current = new MediaView();

    public static String mediaPath = "Games/src/Tetris/music/";

    public static Stage stage;

    /**
     * Main method.
     * @param primaryStage Stage.
     */
    @Override
    public void start(Stage primaryStage) {
        Main.stage = primaryStage;
        setImages();
        panesOnAction(new StartPane(backgroundImage), new SettingsPane(backgroundImage), new OverPane(),
                new ScoresPane());
        //primaryStage.setResizable(false);
        stage.show();
    }

    /** Define background images */
    private void setImages() {
        backgroundImage = new Image(getClass().getResourceAsStream("Interface/love.jpg"),
                width, height, false, true);
    }

    /**
     * Define panes' action events (mostly transitions).
     * @param startPane Start Pane
     * @param settingsPane Settings Pane
     * @param overPane Over Pane
     * @param scoresPane Scores Pane
     */
    private void panesOnAction(StartPane startPane, SettingsPane settingsPane,
                               OverPane overPane, ScoresPane scoresPane){
        Scene startScene = new Scene(startPane);
        Scene settingsScene = new Scene(settingsPane);
        Scene overScene = new Scene(overPane);
        Scene scoresScene = new Scene(scoresPane);
        // Setting default scene
        stage.setScene(startScene);
        // Switching between scenes
        startPane.getExit().setOnMouseClicked(ev -> System.exit(1));
        startPane.getScores().setOnMouseClicked(ev -> stage.setScene(scoresScene));
        startPane.getStart().setOnMouseClicked(ev -> game(stage));
        startPane.getSettings().setOnMouseClicked(ev -> stage.setScene(settingsScene));
        settingsPane.getBack().setOnMouseClicked(ev -> stage.setScene(startScene));
        scoresPane.getBack().setOnMouseClicked(ev -> stage.setScene(startScene));

        settingsAction(settingsPane);
    }

    /**
     * Define settingsPane action events.
     * @param settingsPane Settings Pane
     */
    private void settingsAction(SettingsPane settingsPane) {
        settingsPane.getMusicBox().setOnAction(ev -> {
            if (settingsPane.getMusicBox().isSelected()) {
                current = new MediaView();
                radioAction(settingsPane);
            } else if (current.getMediaPlayer() != null){
                current.getMediaPlayer().stop();
                current = null;
            }
        });
        if (settingsPane.getMusicBox().isSelected()) {
            radioAction(settingsPane);
        }
        setColorChoose(settingsPane);
    }

    private void radioAction(SettingsPane settingsPane) {
        RadioButton troika = settingsPane.getTroika();
        RadioButton loginska = settingsPane.getLoginska();
        RadioButton karinka = settingsPane.getKarinka();
        radioDefault(troika, loginska, karinka);

        troika.setOnAction(ev -> {
            if (troika.isSelected() && current != null) {
                mediaPlay(0);
            }
        });
        loginska.setOnAction(ev -> {
            if (loginska.isSelected() && current != null) {
                mediaPlay(1);
            }
        });
        karinka.setOnAction(ev -> {
            if (karinka.isSelected() && current != null) {
                mediaPlay(2);
            }
        });
    }

    private void radioDefault(RadioButton... buttons) {
        ArrayList<RadioButton> radioButtons = new ArrayList<>(Arrays.asList(buttons));
        RadioButton selected = radioButtons.stream()
                .filter(RadioButton::isSelected)
                .findFirst()
                .get();
        mediaPlay(radioButtons.indexOf(selected));
    }

    private void mediaPlay(int index) {

        if (current == null) {
            return;
        }
        if (current.getMediaPlayer() != null) {
            current.getMediaPlayer().stop();
        }
        current.setMediaPlayer(new MediaPlayer(new Media(new File(mediaPath + tracks[index]).toURI().toString())));
        current.getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
        current.getMediaPlayer().play();
    }


    /**
     * Choose color.
     * @param settingsPane Settings Pane
     */
    private void setColorChoose(SettingsPane settingsPane) {
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
    }

    /**
     * Getter.
     * @return width
     */
    public static int getWidth() {
        return width;
    }

    /**
     * Getter.
     * @return height
     */
    public static int getHeight() {
        return height;
    }

    /**
     * Game logic.
     * @param stage primaryStage
     */
    private void game(Stage stage) {
        GamePane gamePane = new GamePane();
        stage.setScene(gamePane.startGame());
    }

    /**
     * Main.
     * @param args args from cmd
     */
    public static void main(String[] args) {
        launch(args);
    }
}
