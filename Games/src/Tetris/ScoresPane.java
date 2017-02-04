package Tetris;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * Game Scores screen.
 */
class ScoresPane extends GridPane {
    private Button back = new Button("Back");

    /**
     * Class constructor.
     */
    ScoresPane() {
        int width = new Main().getWidth();
        int height = new Main().getHeight();
        BackgroundImage bck = new BackgroundImage(new Image(getClass().getResourceAsStream("img/love.jpg"),
                width, height,false,true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        setBackground(new Background(bck));
        setPrefSize(width, height);
        setAlignment(Pos.BOTTOM_LEFT);
        setPadding(new Insets(50));
        back.setPrefSize(100, 25);
        getChildren().add(back);
    }

    /**
     * Resolve back button.
     * @return back.
     */
    Button getBack() {
        return back;
    }
}
