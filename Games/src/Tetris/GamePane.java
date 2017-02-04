package Tetris;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Game Main screen.
 */
class GamePane extends GridPane{

    /**
     * Class constructor.
     */
    GamePane() {
        int width = new Main().getWidth();
        int height = new Main().getHeight();
        Color color = readFromFile();
        setPrefSize(width, height);
        //setAlignment(Pos.CENTER);
        BackgroundFill back = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        setBackground(new Background(back));
        Rectangle rectLeft = new Rectangle();
        Rectangle rectRight = new Rectangle();
        Rectangle rectBottom = new Rectangle();

        rectBottom.setHeight(70);
        rectBottom.setWidth(width);
        rectBottom.setTranslateX(0);
        rectBottom.setTranslateY(400);
        rectBottom.setFill(color);

        rectRight.setHeight(height + 10);
        rectRight.setWidth(130);
        rectRight.setTranslateX(width - 130);
        rectRight.setTranslateY(0);
        rectRight.setFill(color);

        rectLeft.setHeight(height + 10);
        rectLeft.setWidth(130);
        rectLeft.setTranslateX(0);
        rectLeft.setTranslateY(0);
        rectLeft.setFill(color);
        getChildren().addAll(rectLeft, rectRight, rectBottom);

    }

    /**
     * Read color from file and apply changes.
     * @return color.
     */
    private Color readFromFile() {
        String color = "";
        Color usedColor = Color.DARKBLUE;
        try {
            File file = new File("Games/src/Tetris/resc/Colors.txt");
            Scanner read = new Scanner(file);
            if (read.hasNext()) {
                color = read.next();
            }
            read.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        if (!color.equals("")) {
            switch (color) {
                case "blue":
                    return Color.DARKBLUE;
                case "red":
                    return Color.DARKRED;
                case "white":
                    return Color.WHITE;
                case "purple":
                    return Color.PURPLE;
            }
        }
        return usedColor;
    }
}
