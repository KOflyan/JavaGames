package Tetris.Interface;

import Tetris.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/** Game Over screen. */
public class OverPane extends StackPane {
    /** Define start again button. */
    private Button startOver = new Button("Go to Menu");
    /** Define exit button. */
    private Button exit = new Button("Exit");

    /**
     * Class constructor.
     * @param backgroundImage background image
     */
    public OverPane(Image backgroundImage) {
        setPanePrefs(Main.getWidth(), Main.getHeight(), backgroundImage);
        getChildren().addAll(startOver, exit);
    }

    /**
     * Set pane preferences.
     * @param width pane width
     * @param height pane height
     * @param image background image
     */
    private void setPanePrefs(int width, int height, Image image) {
        setPrefSize(width, height);
        setAlignment(Pos.BOTTOM_CENTER);
        setBackground(image);
        setLabels();
    }

    /** Set pane labels. */
    private void setLabels() {
        Label label = new Label("Game Over!");
        Label label2 = new Label("Would you like to try again?");
        label.setTextFill(Color.WHITE);
        label2.setTextFill(Color.WHITE);
        label2.setTranslateY(-600);
        label.setTranslateY(-650);
        label.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 25));
        label2.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 25));
        getChildren().addAll(label, label2);
    }

    /**
     * Set background.
     * @param image background image.
     */
    private void setBackground(Image image) {
        BackgroundImage back = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        setBackground(new Background(back));
        setButtons();
    }

    /** Set buttons. */
    private void setButtons() {
        startOver.setPrefSize(130, 30);
        exit.setPrefSize(130, 30);
        startOver.setTranslateX(-220);
        exit.setTranslateX(220);
        startOver.setTranslateY(-130);
        exit.setTranslateY(-130);
    }

    /**
     * Resolve startOver button.
     * @return startOver.
     */
    public Button getStartOver() {
        return startOver;
    }

    /**
     * Resolve exit button.
     * @return exit.
     */
    public Button getExit() {
        return exit;
    }
}
