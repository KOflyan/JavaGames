package Tetris.Interface;

import Tetris.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/** Game Scores screen. */
public class ScoresPane extends GridPane {
    /** Define back button */
    private Button back = new Button("Back");

    /**
     * Class constructor.
     * @param backgroundImage background image
     */
    public ScoresPane(Image backgroundImage) {
        setPanePrefs(Main.getWidth(), Main.getHeight(), backgroundImage);
        back.setPrefSize(130, 40);
        getChildren().add(back);
    }

    /**
     * Set pane preferences.
     * @param width pane width
     * @param height pane height
     * @param image background image
     */
    private void setPanePrefs(int width, int height, Image image) {
        setPrefSize(width, height);
        setAlignment(Pos.BOTTOM_LEFT);
        setPadding(new Insets(50));
        setBackground(image);
    }

    /** Set background. */
    private void setBackground(Image image) {
        BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        setBackground(new Background(background));
    }

    /**
     * Resolve back button.
     * @return back.
     */
    public Button getBack() {
        return back;
    }
}
