package Tetris.logic;

import Tetris.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;


public class GamePane {

    private static final int BLOCK_SIZE = 40;
    private static final int GRID_WIDTH = 15;
    private static final int GRID_HEIGHT = 20;
    private static final int width = Main.getWidth();
    private static final int height = Main.getHeight();
    private static final double dFps = 0.2;

    private final double fps = 0.5;
    private static int countSeconds = 0;

    private GraphicsContext g;
    /** Matrix. */
    private int[][] grid = new int[GRID_WIDTH][GRID_HEIGHT];
    /** Original pieces */
    private List<Shape> originalShapes = new ArrayList<>();
    /** Copied originals */
    private List<Shape> shapes = new ArrayList<>();
    /** Timeline. */
    private Timeline timeline;
    /** Current shape. */
    private Shape currentShape;


    private Parent createContent() {
        Pane pane = new Pane();
        pane.setPrefSize(width, height);

        Canvas canvas = new Canvas(GRID_WIDTH * BLOCK_SIZE, GRID_HEIGHT * BLOCK_SIZE);
        g = canvas.getGraphicsContext2D();
        g.setStroke(Color.BLACK);

        pane.getChildren().addAll(canvas);
        createLShape();
        createSShape();
        createRestShapes();

        show();
        setPanePrefs(pane);
        return pane;
    }

    private void setPanePrefs(Pane pane) {
        Color color = readFromFile();
        BackgroundFill background  = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        pane.setBackground(new Background(background));
        pane.getChildren().addAll(drawLeftBorder(color), drawRightBorder(color));
        pane.requestFocus();
    }

    private Rectangle drawLeftBorder(Color color) {

        Rectangle rectLeft = new Rectangle();

        rectLeft.setHeight(height);
        rectLeft.setWidth(width / 4);
        rectLeft.setTranslateX(0);
        rectLeft.setTranslateY(0);
        rectLeft.setFill(color);
        rectLeft.setStroke(Color.BLACK);
        rectLeft.setStrokeWidth(2);
        return rectLeft;
    }

    private Rectangle drawRightBorder(Color color) {
        Rectangle rectRight = new Rectangle();
        // Borders
        rectRight.setHeight(height);
        rectRight.setWidth(width / 4);
        rectRight.setTranslateX(width - 200);
        rectRight.setTranslateY(0);
        rectRight.setFill(color);
        rectRight.setStroke(Color.BLACK);
        rectRight.setStrokeWidth(2);
        return rectRight;
    }

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

    private void createLShape() {
        // L
        originalShapes.add(new Shape(Color.AQUAMARINE,
                new Piece(0, Direction.DOWN),
                new Piece(1, Direction.LEFT),
                new Piece(1, Direction.RIGHT),
                new Piece(1, Direction.RIGHT, Direction.DOWN)
        ));
        // Reverted L
        originalShapes.add(new Shape(Color.DARKGREEN,
                new Piece(0, Direction.DOWN),
                new Piece(1, Direction.LEFT),
                new Piece(1, Direction.RIGHT),
                new Piece(1, Direction.LEFT, Direction.DOWN)
        ));
    }

    private void createSShape() {
        // S
        originalShapes.add(new Shape(Color.ORANGE,
                new Piece(0, Direction.DOWN),
                new Piece(1, Direction.LEFT),
                new Piece(1, Direction.RIGHT, Direction.DOWN),
                new Piece(1, Direction.DOWN)
        ));
        originalShapes.add(new Shape(Color.BROWN,
                new Piece(0, Direction.DOWN),
                new Piece(1, Direction.RIGHT),
                new Piece(1, Direction.DOWN),
                new Piece(1, Direction.LEFT, Direction.DOWN)
        ));
    }

    private void createRestShapes() {
        // Line
        originalShapes.add(new Shape(Color.DARKBLUE,
                new Piece(0, Direction.DOWN),
                new Piece(1, Direction.RIGHT),
                new Piece(1, Direction.LEFT),
                new Piece(2, Direction.LEFT)
        ));
        // T
        originalShapes.add(new Shape(Color.DEEPPINK,
                new Piece(0, Direction.DOWN),
                new Piece(1, Direction.LEFT),
                new Piece(1, Direction.RIGHT),
                new Piece(1, Direction.DOWN)
        ));
        // Cube
        originalShapes.add(new Shape(Color.CORAL,
                new Piece(0, Direction.DOWN),
                new Piece(1, Direction.RIGHT),
                new Piece(1, Direction.RIGHT, Direction.DOWN),
                new Piece(1, Direction.DOWN)));
    }

    private void update() {
        moveShape(p -> p.move(Direction.DOWN), p -> p.move(Direction.UP), true);
    }

    private void reDraw() {
        g.clearRect(0, 0, GRID_WIDTH * BLOCK_SIZE, GRID_HEIGHT * BLOCK_SIZE);

        shapes.forEach(p -> p.draw(g));
    }

    private void placePiece(Piece piece) {
        grid[piece.x][piece.y]++;
    }

    private void removePiece(Piece piece) {
        grid[piece.x][piece.y]--;
    }

    /**
     * Check if in bounds.
     * @param piece piece
     * @return bool
     */
    private boolean inBounds(Piece piece) {
        return piece.x < 5 || piece.x >= GRID_WIDTH
                || piece.y < 0 || piece.y >= GRID_HEIGHT;
    }

    /**
     * Make a move.
     * @param isOk if possible
     * @param isNotOk if not
     * @param endMove end move
     */
    private void moveShape(Consumer<Shape> isOk, Consumer<Shape> isNotOk, boolean endMove) {
        currentShape.getPieces().forEach(this::removePiece);

        isOk.accept(currentShape);
        boolean beyondScreen = currentShape.getPieces().stream().anyMatch(this::inBounds);

        if (!beyondScreen) {
            currentShape.getPieces().forEach(this::placePiece);
        } else {
            isNotOk.accept(currentShape);
            currentShape.getPieces().forEach(this::placePiece);

            if (endMove) {
                removeRow();
            }
            return;
        }

        if (!isValidState()) {
            currentShape.getPieces().forEach(this::removePiece);

            isNotOk.accept(currentShape);

            currentShape.getPieces().forEach(this::placePiece);

            // If the end of the screen
            if (endMove) {
                removeRow();
            }
        }
    }

    private boolean isValidState() {
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 5; x < GRID_WIDTH; x++) {
                if (grid[x][y] > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Remove the row */
    private void removeRow() {
        List<Integer> rows = collectRows();
        rows.forEach(row -> {
            for (int x = 5; x < GRID_WIDTH; x++) {
                for (Shape shape : shapes) {
                    shape.detach(x, row);
                }
                grid[x][row]--;
            }
        });
        replacePieces(rows);
    }

    private void replacePieces(List<Integer> rows) {
        rows.forEach(row -> shapes.forEach(shape ->
                shape.getPieces().stream()
                        // Separate certain pieces (if above the row we removed)
                        .filter(p -> p.y < row)
                        // Replacing pieces
                        .forEach(p -> {
                            removePiece(p);
                            p.y++;
                            placePiece(p);
                        })));
        System.out.println(rows.size());
        show();
    }

    private List<Integer> collectRows() {
        // List of rows to remove
        List<Integer> rows = new ArrayList<>();

        // Represents main loop
        mainLoop:
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 5; x < GRID_WIDTH; x++) {
                if (grid[x][y] != 1) {
                    // Continues the main loop without adding y to the rows.
                    continue mainLoop;
                }
            }
            // The row we need to remove
            rows.add(y);
        }
        return rows;
    }

    private void show() {
        Shape shape = originalShapes.get(new Random().nextInt(originalShapes.size())).copy();
        shape.move(GRID_WIDTH / 2 + 3, 0);

        currentShape = shape;

        shapes.add(shape);
        shape.getPieces().forEach(this::placePiece);
    }

    public Scene startGame() {
        Scene scene = new Scene(createContent());
        timeline = new Timeline(new KeyFrame(Duration.seconds(fps), ev -> {

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP: moveShape(p -> p.rotate(), p -> p.rotateBack(), false);
                    break;
                case LEFT: moveShape(p -> p.move(Direction.LEFT), p -> p.move(Direction.RIGHT), false);
                    break;
                case RIGHT: moveShape(p -> p.move(Direction.RIGHT), p -> p.move(Direction.LEFT), false);
                    break;
                case DOWN: moveShape(p -> p.move(Direction.DOWN), p -> p.move(Direction.UP), true);
                    break;
                case SPACE:
                    if (timeline.getStatus() == Animation.Status.RUNNING) {
                        timeline.pause();
                    } else {
                        timeline.play();
                    }
                    break;

            }
            reDraw();
        });
            update();
            reDraw();
            if (!isValidState()) {
                System.exit(1);
            }
            if (countSeconds != 0 && countSeconds % Duration.minutes(1).toSeconds() == 0) {
                System.out.println("Incremented!");
                timeline.setRate(timeline.getRate() + dFps);
            }
            countSeconds++;
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        return scene;
    }
}

// TODO:
/* Decide about borders - they do not match the grid at this point; */ /** Done! */
/* Problem with music; */ /** Somehow Solved! */

/* Implement score count and stages */ /**Half way there */

// Set stroke for graphic context;
// Set game over screen appearance;
// Add music & animations
// Pause


