package Tetris;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Game Main screen.
 */
class GamePane extends GridPane{
    private int width = new Main().getWidth();
    private int height = new Main().getHeight();
    private Color[] colors = {Color.BLUE, Color.DARKGREY, Color.DARKGREEN, Color.AQUAMARINE, Color.VIOLET};
    private final int BLOCK_SIZE = 25;
    private double dx = 3;
    private double dy = 4;
    private double x = width / 2 - BLOCK_SIZE;
    private double y = -400;
    private Random rand = new Random();
    private Rectangle rect = new Rectangle();
    private Group line = new Group();
    private Group Tshaped = new Group();
    private Group HookShaped = new Group();

    /**
     * Class constructor.
     */
    GamePane() {

        Color color = readFromFile();
        setPrefSize(width, height);
        //setAlignment(Pos.CENTER);
        BackgroundFill back = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        setBackground(new Background(back));
        Rectangle rectLeft = new Rectangle();
        Rectangle rectRight = new Rectangle();
        Rectangle rectBottom = new Rectangle();
        // Borders
        rectBottom.setHeight(70);
        rectBottom.setWidth(width);
        rectBottom.setTranslateX(0);
        rectBottom.setTranslateY(400);
        rectBottom.setFill(color);

        rectRight.setHeight(height + 10);
        rectRight.setWidth(200);
        rectRight.setTranslateX(width - 200);
        rectRight.setTranslateY(0);
        rectRight.setFill(color);

        rectLeft.setHeight(height + 10);
        rectLeft.setWidth(200);
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
        } catch (IOException ex) {
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

    int nextShape() {
        return rand.nextInt(5);
    }

    /**
     * Draw random shape.
     * @param shapeNumber random number.
     */
    void drawShapes(int shapeNumber) {
        switch (shapeNumber) {
            case 0:
                drawCube();
                break;
            case 1:
                drawLine();
                break;
            case 2:
                drawTShaped();
                break;
            case 3:
                drawHookShaped();
                break;
        }
    }

    /**
     * Draw a cube.
     */
    private void drawCube() {
        //Rectangle rect = new Rectangle();
        //Group cube = new Group();
        rect.setHeight(BLOCK_SIZE * 2);
        rect.setWidth(BLOCK_SIZE * 2);
        //rect.setTranslateX(200);
        //rect.setTranslateY(200);
        rect.setFill(colors[0]);
        rect.setTranslateX(x);
        rect.setTranslateY(y);
        //cube.setTranslateX(x);
        //cube.setTranslateY(y);
        /*
        x, y + BLOCK_SIZE, x + BLOCK_SIZE * 2, y + BLOCK_SIZE
        x + BLOCK_SIZE, y, x + BLOCK_SIZE, y + 2 * BLOCK_SIZE
        Line first = new Line();
        Line second = new Line();
        first.setFill(Color.BLACK);
        second.setFill(Color.BLACK);
        cube.getChildren().add(rect);
        cube.getChildren().add(first);
        cube.getChildren().add(second);
        first.setStartX(x);
        first.setStartY(y + BLOCK_SIZE);
        first.setEndX(x + BLOCK_SIZE * 2);
        first.setEndY(y + BLOCK_SIZE);
        */
        getChildren().add(rect);
        }

    /**
     * Draw a line.
     */
    private void drawLine() {
        int block = 0;
        for (int i = 0; i < 4; i++) {
            Rectangle rect = new Rectangle();
            rect.setWidth(BLOCK_SIZE);
            rect.setHeight(BLOCK_SIZE);
            rect.setTranslateX(x);
            rect.setTranslateY(y + block);
            rect.setFill(colors[1]);
            line.getChildren().add(rect);
            block += BLOCK_SIZE;
        }
        line.setTranslateX(x);
        line.setTranslateY(y);
        getChildren().add(line);

    }

    /**
     * Draw T-shaped object.
     */
    private void drawTShaped() {
        int block = 0;
        for (int i = 0; i < 3; i++) {
            Rectangle rect = new Rectangle();
            rect.setWidth(BLOCK_SIZE);
            rect.setHeight(BLOCK_SIZE);
            rect.setTranslateX(x);
            rect.setTranslateY(y + block);
            rect.setFill(colors[2]);
            Tshaped.getChildren().add(rect);
            block += BLOCK_SIZE;
        }
        Rectangle rect2 = new Rectangle();
        rect2.setWidth(BLOCK_SIZE);
        rect2.setHeight(BLOCK_SIZE);
        rect2.setTranslateX(x + BLOCK_SIZE);
        rect2.setTranslateY(y + BLOCK_SIZE);
        rect2.setFill(colors[2]);
        Tshaped.getChildren().add(rect2);
        getChildren().add(Tshaped);
    }

    /**
     * Draw Hook-shaped object.
     */
    private void drawHookShaped() {
        int block = 0;
        for (int i = 0; i < 3; i++) {
            Rectangle rect = new Rectangle();
            rect.setWidth(BLOCK_SIZE);
            rect.setHeight(BLOCK_SIZE);
            rect.setTranslateX(x);
            rect.setTranslateY(y + block);
            rect.setFill(colors[2]);
            HookShaped.getChildren().add(rect);
            block += BLOCK_SIZE;
        }
        Rectangle rect2 = new Rectangle();
        rect2.setWidth(BLOCK_SIZE);
        rect2.setHeight(BLOCK_SIZE);
        rect2.setTranslateX(x + BLOCK_SIZE);
        rect2.setTranslateY(y + BLOCK_SIZE * 2);
        rect2.setFill(colors[3]);
        HookShaped.getChildren().add(rect2);
        getChildren().add(HookShaped);
    }

    /**
     * Increase speed.
     */
    void increaseSpeed() {
        //x += dx;
        y += dy;
        rect.setTranslateX(x);
        rect.setTranslateY(y);
        line.setTranslateX(x);
        line.setTranslateY(y);
        HookShaped.setTranslateX(x);
        HookShaped.setTranslateY(y);
        Tshaped.setTranslateX(x);
        Tshaped.setTranslateY(y);
    }

    /**
     * Select difficulty.
     * @param difficulty integer.
     */
    void difficultySelect(int difficulty) {
        switch (difficulty) {
            case 0: dy = 3;
                break;
            case 1: dy = 5;
                break;
            case 2: dy = 7;
                break;
        }
    }
}
