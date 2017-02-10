package Tetris;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Game Over screen.
 */
class OverPane extends StackPane {

    private Button startOver = new Button("Go to Menu");
    private Button exit = new Button("Exit");

    /**
     * Class constructor.
     */
    OverPane() {
        Label label = new Label("Game Over!");
        Label label2 = new Label("Would you like to try again?");
        int width = new Main().getWidth();
        int height = new Main().getHeight();
        setPrefSize(width, height);
        setAlignment(Pos.BOTTOM_CENTER);
        //setPadding(new Insets(50));
        startOver.setPrefSize(130, 30);
        exit.setPrefSize(130, 30);
        startOver.setTranslateX(-220);
        exit.setTranslateX(220);
        startOver.setTranslateY(-130);
        exit.setTranslateY(-130);
        BackgroundImage back = new BackgroundImage(new Image(getClass().getResourceAsStream("img/love.jpg"),
                width, height,false,true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        setBackground(new Background(back));
        label.setTextFill(Color.WHITE);
        label2.setTextFill(Color.WHITE);
        label2.setTranslateY(-600);
        label.setTranslateY(-650);
        label.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 25));
        label2.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 25));
        getChildren().addAll(startOver, exit, label, label2);
    }

    /**
     * Resolve startOver button.
     * @return startOver.
     */
    Button getStartOver() {
        return startOver;
    }

    /**
     * Resolve exit button.
     * @return exit.
     */
    Button getExit() {
        return exit;
    }
}
