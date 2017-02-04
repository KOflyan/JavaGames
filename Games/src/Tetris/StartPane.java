package Tetris;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * Game Start screen.
 */
class StartPane extends GridPane {
    private Button start = new Button("Start");
    private Button exit = new Button("Exit");
    private Button scores = new Button("Scores");


    /**
     * Class constructor.
     */
    StartPane() {
        int width = new Main().getWidth();
        int height = new Main().getHeight();
        setPrefSize(width, height);
        setAlignment(Pos.BOTTOM_RIGHT);
        BackgroundImage back = new BackgroundImage(new Image(getClass().getResourceAsStream("img/love.jpg"),
                width, height,false,true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        setVgap(25);
        setPadding(new Insets(70, 40, 70, 40));
        setBackground(new Background(back));
        start.setPrefSize(130, 40);
        scores.setPrefSize(130, 40);
        exit.setPrefSize(130, 40);
        add(start, 1, 3);
        add(scores, 1, 4);
        add(exit, 1, 5);
    }

    /**
     * Resolve exit button.
     * @return exit.
     */
    Button getExit() {
        return exit;
    }

    /**
     * Resolve start button.
     * @return start.
     */
    Button getStart() {
        return start;
    }

    /**
     * Resolve score screen button.
     * @return scores.
     */
    Button getScores() {
        return scores;
    }
}
