package Tetris;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
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
class GamePane extends GridPane {
    private final int SPEED = 3;
    private final int BLOCK_SIZE = 30;

    private Color[] colors = {Color.BLUE, Color.DARKGREY, Color.DARKGREEN, Color.AQUAMARINE, Color.ORANGE};
    private double dx = 13;
    private double dy = SPEED;
    private double x = 400 - BLOCK_SIZE;
    private double y = -400;
    //private Random rand = new Random();
    private Rectangle cube = new Rectangle();
    private Group line = new Group();
    private Group Tshaped = new Group();
    private Group HookShaped = new Group();
    private String current;
    private String position;
    private boolean start = true;

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
     *
     * @return color.
     */
    private Color readFromFile() {
        String color = "";
        Color defaultColor = Color.DARKBLUE;
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
        return defaultColor;
    }

    /**
     * Draw random shape.
     *
     * @param shapeNumber random number.
     */
    void drawShapes(int shapeNumber) {
        switch (shapeNumber) {
            case 0:
                current = "cube";
                drawCube();
                break;
            case 1:
                position = "up";
                current = "line";
                drawLine(true);
                break;
            case 2:
                position = "upRight";
                current = "tshaped";
                drawTShaped();
                break;
            case 3:
                position = "downLeft";
                current = "hook";
                drawHookShaped();
                break;
            case 4:
                position = "downLeft";
                current = "hook2";
                //drawHookShaped2();
                break;
        }
    }

    /**
     * Draw a cube.
     */
    private void drawCube() {
        cube.setHeight(BLOCK_SIZE * 2);
        cube.setWidth(BLOCK_SIZE * 2);
        cube.setFill(colors[0]);
        cube.setTranslateX(x);
        cube.setTranslateY(y);
        getChildren().add(cube);
    }

    /**
     * Draw T-shaped object.
     */
    private Group drawTShaped() {
        Group group = drawConstructLine(true, 3, colors[2]);
        double translateX;
        double translateY;
        if (position.equals("downDown")) {
            translateX = x - BLOCK_SIZE;
            translateY = y + BLOCK_SIZE;
            position = "upLeft";
        } else {
            translateX = x + BLOCK_SIZE;
            translateY = y + BLOCK_SIZE;
            position = "upRight";
        }
        Rectangle rect2 = new Rectangle();
        rect2.setWidth(BLOCK_SIZE);
        rect2.setHeight(BLOCK_SIZE);
        rect2.setTranslateX(translateX);
        rect2.setTranslateY(translateY);
        rect2.setFill(colors[2]);
        group.getChildren().add(rect2);
        group.setTranslateX(x);
        group.setTranslateY(y);
        if (start) {
            Tshaped = group;
            getChildren().add(Tshaped);
        }
        return group;
    }

    private Group drawTShapedDown() {
        Group group = drawConstructLine(false, 3, colors[2]);
        double translateX;
        double translateY;
        if (position.equals("upRight")) {
            translateX = x + BLOCK_SIZE;
            translateY = y + BLOCK_SIZE;
            position = "downDown";
        } else {
            translateX = x + BLOCK_SIZE;
            translateY = y - BLOCK_SIZE;
            position = "downUp";
        }
        Rectangle rect2 = new Rectangle();
        rect2.setWidth(BLOCK_SIZE);
        rect2.setHeight(BLOCK_SIZE);
        rect2.setTranslateX(translateX);
        rect2.setTranslateY(translateY);
        rect2.setFill(colors[2]);
        group.getChildren().add(rect2);
        group.setTranslateX(x);
        group.setTranslateY(y);
        return group;
    }

    /**
     * Basic line for further usage.
     * @param down is line to be vertically or horizontally oriented.
     * @param count how many blocks it consists of.
     * @param color color.
     * @return group.
     */
    private Group drawConstructLine(boolean down, int count, Color color) {
        int hBlock = 0;
        int vBlock = 0;
        int vStep;
        int hStep;
        if (down) {
            vStep = BLOCK_SIZE;
            hStep = 0;
        } else {
            vStep = 0;
            hStep = BLOCK_SIZE;
        }
        Group group = new Group();
        for (int i = 0; i < count; i++) {
            Rectangle rect = new Rectangle();
            rect.setWidth(BLOCK_SIZE);
            rect.setHeight(BLOCK_SIZE);
            rect.setTranslateX(x + hBlock);
            rect.setTranslateY(y + vBlock);
            rect.setFill(color);
            group.getChildren().add(rect);
            hBlock += hStep;
            vBlock += vStep;
        }
        return group;
    }

    /**
     * Draw a line shape Up/Down positions..
     */
    private Group drawLine(boolean up) {
        Group group = drawConstructLine(up, 4, colors[1]);

        group.setTranslateX(x);
        group.setTranslateY(y);

        if (start) {
            line = group;
            getChildren().add(line);
        }
        if (up) {
            position = "up";
        } else {
            position = "down";
        }
        return group;
    }

    /**
     * Draw 1st type Hook-shaped object. Up positions.
     */
    private Group drawHookShaped() {
        Group group = drawConstructLine(true, 3, colors[3]);
        double translateX;
        double translateY;
        if (position.equals("downLeft")) {
            translateX = x + BLOCK_SIZE;
            translateY = y;
            position = "upRight";
        } else {
            translateX = x - BLOCK_SIZE;
            translateY = y + BLOCK_SIZE * 2;
            position = "upLeft";
        }
        Rectangle rect2 = new Rectangle();
        rect2.setWidth(BLOCK_SIZE);
        rect2.setHeight(BLOCK_SIZE);
        rect2.setTranslateX(translateX);
        rect2.setTranslateY(translateY);
        rect2.setFill(colors[3]);
        group.getChildren().add(rect2);
        group.setTranslateX(x);
        group.setTranslateY(y);

        if (start) {
            HookShaped = group;
            getChildren().add(HookShaped);
        }
        return group;
    }

    /**
     * Down positions.
     * @return group.
     */
    private Group drawHookShapedDown() {
        Group group = drawConstructLine(false, 3, colors[3]);
        double translateX;
        double translateY;
        if (position.equals("upRight")) {
            translateX = x + BLOCK_SIZE * 2;
            translateY = y + BLOCK_SIZE;
            position = "downRight";
        } else {
            translateX = x;
            translateY = y + BLOCK_SIZE;
            position = "downLeft";
        }
        Rectangle rect2 = new Rectangle();
        rect2.setWidth(BLOCK_SIZE);
        rect2.setHeight(BLOCK_SIZE);
        rect2.setTranslateX(translateX);
        rect2.setTranslateY(translateY);
        rect2.setFill(colors[3]);
        group.getChildren().add(rect2);
        return group;
    }

    /**
     * Select difficulty.
     *
     * @param difficulty integer.
     */
    void difficultySelect(int difficulty) {
        switch (difficulty) {
            case 0:
                dy = 3;
                break;
            case 1:
                dy = 5;
                break;
            case 2:
                dy = 7;
                break;
        }
    }

    /**
     * Game heart.
     */
    void controls() {
        start = false;
        y += dy;
        /*
        switch (current){
            case "cube":
                if (x <= 200 + BLOCK_SIZE || x >= 600 - BLOCK_SIZE) {
                    dx = 0;
                } else {
                    dx = 5;
                }
                break;
        }
        */
        setOnKeyPressed(ev -> {
            Group toGo;
            if (ev.getCode() == KeyCode.DOWN) {
                if (dy <= 12) {
                    dy += 2;
                }
            }
            if (ev.getCode() == KeyCode.RIGHT) {
                x += dx;
            } else if (ev.getCode() == KeyCode.LEFT) {
                x -= dx;
            }
            if (current.equals("hook") && ev.getCode() == KeyCode.UP) {
                if (position.equals("downRight") || position.equals("downLeft")) {
                    toGo = drawHookShaped();
                } else {
                    toGo = drawHookShapedDown();
                }
                getChildren().remove(HookShaped);
                HookShaped.getChildren().clear();
                HookShaped.getChildren().add(toGo);
                getChildren().add(HookShaped);
            } else if (current.equals("line") && ev.getCode() == KeyCode.UP) {
                if (position.equals("up")) {
                    toGo = drawLine(false);
                } else {
                    toGo = drawLine(true);
                }
                getChildren().remove(line);
                line.getChildren().clear();
                line.getChildren().add(toGo);
                getChildren().add(line);
            } else if (current.equals("tshaped") && ev.getCode() == KeyCode.UP) {
                if (position.equals("upRight") || position.equals("upLeft")) {
                    toGo = drawTShapedDown();
                } else {
                    toGo = drawTShaped();
                }
                getChildren().remove(Tshaped);
                Tshaped.getChildren().clear();
                Tshaped.getChildren().add(toGo);
                getChildren().add(Tshaped);
            }
        });

        setOnKeyReleased(ev -> {
            if (ev.getCode() == KeyCode.DOWN) {
                dy = SPEED;
            }
        });
        switch (current) {
            case "cube":
                cube.setTranslateX(x);
                cube.setTranslateY(y);
                break;
            case "line":
                line.setTranslateX(x);
                line.setTranslateY(y);
                break;
            case "hook":
                HookShaped.setTranslateX(x);
                HookShaped.setTranslateY(y);
                break;
            case "tshaped":
                Tshaped.setTranslateX(x);
                Tshaped.setTranslateY(y);
                break;
        }
    }
}
