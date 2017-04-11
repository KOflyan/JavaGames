package Tetris.Interface;

import Tetris.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * Game Start screen.
 */
public class StartPane extends GridPane {
    private Button start = new Button("Start");
    private Button exit = new Button("Exit");
    private Button scores = new Button("Scores");
    private Button settings = new Button("Settings");

    /**
     * Class constructor.
     * @param backgroundImage background image
     */
    public StartPane(Image backgroundImage) {
        setPanePrefs(Main.getWidth(), Main.getHeight(), backgroundImage);
        add(start, 1, 3);
        add(scores, 1, 4);
        add(settings,1,5);
        add(exit, 1, 6);
    }

    /**
     * Set pane preferences.
     * @param width pane width
     * @param height pane height
     * @param image background image
     */
    private void setPanePrefs(int width, int height, Image image) {
        setPrefSize(width, height);
        setAlignment(Pos.BOTTOM_RIGHT);
        setVgap(15);
        setPadding(new Insets(70, 40, 70, 40));
        setBackground(image);
    }

    /**
     * Set background.
     * @param image background image.
     */
    private void setBackground(Image image) {
        BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        setBackground(new Background(background));
        setButtons();
    }

    /** Set buttons. */
    private void setButtons() {
        start.setPrefSize(130, 40);
        scores.setPrefSize(130, 40);
        exit.setPrefSize(130, 40);
        settings.setPrefSize(130, 40);
    }

    /**
     * Resolve exit button.
     * @return exit.
     */
    public Button getExit() {
        return exit;
    }

    /**
     * Resolve start button.
     * @return start.
     */
    public Button getStart() {
        return start;
    }

    /**
     * Resolve score screen button.
     * @return scores.
     */
    public Button getScores() {
        return scores;
    }

    /**
     * Resolve settings screen button.
     * @return settings.
     */
    public Button getSettings() {
        return settings;
    }
}

