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
    private int width = new Main().getWidth();
    private int height = new Main().getHeight();
    private Color[] colors = {Color.BLUE, Color.DARKGREY, Color.DARKGREEN, Color.AQUAMARINE, Color.VIOLET};
    private final int BLOCK_SIZE = 25;
    private double dx = 5;
    private double dy = 2;
    private final int SPEED = 2;
    private double x = 400 - BLOCK_SIZE;
    private double y = -400;
    private Random rand = new Random();
    private Rectangle rect = new Rectangle();
    private Group line = new Group();
    private Group Tshaped = new Group();
    private Group HookShaped = new Group();
    private String current;
    private String position = "up1";
    private boolean start = true;

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
     *
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
     *
     * @param shapeNumber random number.
     */
    void drawShapes(int shapeNumber) {
        switch (shapeNumber) {
            case 0:
                drawCube();
                break;
            case 1:
                drawLine(true);
                break;
            case 2:
                drawTShaped(true);
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
        current = "cube";
    }

    /**
     * Draw T-shaped object.
     */
    private Group drawTShaped(boolean up) {
        Group group = drawConstructLine(up, 3, colors[2]);
        int step;
        if (up) {
            position = "up";
            step = BLOCK_SIZE;
        }
        Rectangle rect2 = new Rectangle();
        rect2.setWidth(BLOCK_SIZE);
        rect2.setHeight(BLOCK_SIZE);
        rect2.setTranslateX(x + BLOCK_SIZE);
        rect2.setTranslateY(y + BLOCK_SIZE);
        rect2.setFill(colors[2]);
        group.getChildren().add(rect2);
        group.setTranslateX(x);
        group.setTranslateY(y);
        if (start) {
            Tshaped = group;
            getChildren().add(Tshaped);
        }
        current = "tshaped";
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
     * Draw a line shape.
     */
    private Group drawLine(boolean up) {
        Group group = drawConstructLine(up, 4, colors[1]);

        group.setTranslateX(x);
        group.setTranslateY(y);
        current = "line";
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
     * Draw Hook-shaped object. Up position 1.
     */
    private Group drawHookShaped() {
        Group group = drawConstructLine(true, 3, colors[3]);
        Rectangle rect2 = new Rectangle();
        rect2.setWidth(BLOCK_SIZE);
        rect2.setHeight(BLOCK_SIZE);
        rect2.setTranslateX(x + BLOCK_SIZE);
        rect2.setTranslateY(y + BLOCK_SIZE * 2);
        rect2.setFill(colors[3]);
        group.getChildren().add(rect2);
        group.setTranslateX(x);
        group.setTranslateY(y);
        current = "hook";
        position = "up1";
        if (start) {
            HookShaped = group;
            getChildren().add(HookShaped);
        }
        return group;
    }

    /**
     * Down position 1.
     * @return group.
     */
    private Group drawHookShapedDown() {
        Group group = drawConstructLine(false, 3, colors[3]);
        Rectangle rect2 = new Rectangle();
        rect2.setWidth(BLOCK_SIZE);
        rect2.setHeight(BLOCK_SIZE);
        rect2.setTranslateX(x + BLOCK_SIZE * 2);
        rect2.setTranslateY(y - BLOCK_SIZE);
        rect2.setFill(colors[3]);
        group.getChildren().add(rect2);
        position = "down2";
        return group;
    }

    /**
     * Up position 2.
     * @return group.
     */
    private Group drawHookShapedUP() {
        Group group = drawConstructLine(true, 3, colors[3]);
        Rectangle rect2 = new Rectangle();
        rect2.setWidth(BLOCK_SIZE);
        rect2.setHeight(BLOCK_SIZE);
        rect2.setTranslateX(x - BLOCK_SIZE);
        rect2.setTranslateY(y);
        rect2.setFill(colors[3]);
        group.getChildren().add(rect2);
        position = "up2";
        return group;
    }

    /**
     * Down position 2.
     * @return group.
     */
    private Group drawHookShapedDown2() {
        Group group = drawConstructLine(false, 3, colors[3]);
        Rectangle rect2 = new Rectangle();
        rect2.setWidth(BLOCK_SIZE);
        rect2.setHeight(BLOCK_SIZE);
        rect2.setTranslateX(x);
        rect2.setTranslateY(y + BLOCK_SIZE);
        rect2.setFill(colors[3]);
        group.getChildren().add(rect2);
        position = "down1";
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
            if (ev.getCode() == KeyCode.DOWN) {
                if (dy <= 8) {
                    dy += 2;
                }
            }
            if (ev.getCode() == KeyCode.RIGHT) {
                if (dx == 0) x -= 5;
                x += dx;
            }
            else if (ev.getCode() == KeyCode.LEFT) {
                if (dx == 0) x += 5;
                x -= dx;
            }
            if (current.equals("hook") && ev.getCode() == KeyCode.UP) {
                Group toGo = new Group();
                switch (position) {
                    case "up1":
                        toGo = drawHookShapedDown2();
                        break;
                    case "down1":
                        toGo = drawHookShapedUP();
                        break;
                    case "up2":
                        toGo = drawHookShapedDown();
                        break;
                    case "down2":
                        toGo = drawHookShaped();
                        break;
                }
                getChildren().remove(HookShaped);
                HookShaped.getChildren().clear();
                HookShaped.getChildren().add(toGo);
                getChildren().add(HookShaped);
            } else if (current.equals("line") && ev.getCode() == KeyCode.UP) {
                Group toGo = new Group();
                switch (position) {
                    case "up":
                        toGo = drawLine(false);
                        break;
                    case "down":
                        toGo = drawLine(true);
                        break;
                }
                getChildren().remove(line);
                line.getChildren().clear();
                line.getChildren().add(toGo);
                getChildren().add(line);
            }
        });
        setOnKeyReleased(ev -> {
            if (ev.getCode() == KeyCode.DOWN) {
                dy = SPEED;
            }
        });
        rect.setTranslateX(x);
        rect.setTranslateY(y);
        line.setTranslateX(x);
        line.setTranslateY(y);
        HookShaped.setTranslateX(x);
        HookShaped.setTranslateY(y);
        Tshaped.setTranslateX(x);
        Tshaped.setTranslateY(y);
    }
}
