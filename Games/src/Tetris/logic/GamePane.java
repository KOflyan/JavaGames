package Tetris.logic;

import Tetris.Interface.OverPane;
import Tetris.Interface.SettingsPane;
import Tetris.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;

/** Game panel. */
public class GamePane {
    /** Size of one block. */
    public static final int BLOCK_SIZE = 40;
    /** Matrix first array size. */
    private static final int GRID_WIDTH = 15;
    /** Matrix second array size. */
    private static final int GRID_HEIGHT = 20;
    /** Obvious. */
    private static final int width = Main.getWidth();
    /** Obvious. */
    private static final int height = Main.getHeight();
    /** Increase fps by... */
    private static final double dFps = 0.2;
    /** Measure the time of animation running. */
    private static int countSeconds = 0;
    /** Score. */
    private int score = 0;
    /**Score label. */
    private Label scoreLabel = new Label("SCORE: 0");
    /** Draw. */
    private GraphicsContext g;
    /** Matrix. */
    private int[][] grid = new int[GRID_WIDTH][GRID_HEIGHT];
    /** Original pieces */
    private List<Shape> originalShapes = new ArrayList<>();
    /** Copied originals */
    private List<Shape> shapes = new ArrayList<>();
    /** Timeline. */
    private Timeline timeline = new Timeline();
    /** Current shape. */
    private Shape currentShape;
    /** Pane. */
    private Pane pane;

    /** Set pane and initial things. */
    private void createContent() {
        pane = new Pane();
        pane.setPrefSize(width, height);

        Canvas canvas = new Canvas(GRID_WIDTH * BLOCK_SIZE, GRID_HEIGHT * BLOCK_SIZE);
        g = canvas.getGraphicsContext2D();
        pane.getChildren().add(canvas);

        createLShape();
        createSShape();
        createRestShapes();

        show();
        setPanePrefs();
    }

    /** Pane settings. */
    private void setPanePrefs() {
        Color color = readFromFile();
        BackgroundFill background  = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        pane.setBackground(new Background(background));
        scoreLabel.setFont(Font.font("System", 25));
        scoreLabel.setTextFill(readFromFile() == Color.WHITE ? Color.BLACK : Color.WHITE);
        scoreLabel.setTranslateX(30);
        scoreLabel.setTranslateY(40);
        pane.getChildren().addAll(drawLeftBorder(color), drawRightBorder(color), scoreLabel);
    }

    /**
     * Draw left border.
     * @param color color
     * @return rect
     */
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

    /**
     * Draw right border.
     * @param color color
     * @return rect
     */
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

    /** Read color from file. */
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

    /** L. */
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

    /** S. */
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

    /** Line, T, Cube. */
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

    /** Fall update. */
    private void update() {
        moveShape(p -> p.move(Direction.DOWN), p -> p.move(Direction.UP), true);
    }

    /** Update when moves by key. */
    private void reDraw() {
        g.clearRect(0, 0, GRID_WIDTH * BLOCK_SIZE, GRID_HEIGHT * BLOCK_SIZE);

        shapes.forEach(p -> p.draw(g));
    }

    /**
     * Place.
     * @param piece piece
     */
    private void placePiece(Piece piece) {
        grid[piece.x][piece.y]++;
    }

    /**
     * Remove piece.
     * @param piece piece
     */
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

    /** Check if game over. */
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

    /** Remove the row. */
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

    /**
     * Place pieces down.
     * @param rows list
     */
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
        if (rows.size() > 0) {
            scoreLabel.setText("SCORE: " + (score += calculateBonus(rows.size())));
        }
        show();
    }

    /**
     * Bonus points.
     * @param rowsDispatch how many full rows are removed.
     * @return score
     */
    private int calculateBonus(int rowsDispatch) {
        int bonus = rowsDispatch >= 4 ? 10 : rowsDispatch > 2 ? 5 : 0;
        return rowsDispatch * 5 + bonus;
    }

    /** Collect full rows. */
    private List<Integer> collectRows() {
        // List of rows to remove
        List<Integer> rows = new ArrayList<>();

        // Label
        outer:
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 5; x < GRID_WIDTH; x++) {
                if (grid[x][y] != 1) {
                    // Continues the main loop without adding y to the rows
                    continue outer;
                }
            }
            // The row we need to remove
            rows.add(y);
        }
        return rows;
    }

    /** Draw next shape. */
    private void show() {
        Shape shape = originalShapes.get(new Random().nextInt(originalShapes.size())).copy();
        shape.move(GRID_WIDTH / 2 + 3, 0);

        currentShape = shape;

        shapes.add(shape);
        shape.getPieces().forEach(this::placePiece);
    }

    /** Game start. */
    public Scene startGame() {
        OverPane over = new OverPane();

        createContent();
        Scene scene = new Scene(pane);
        movementController(scene);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), ev -> {
                if (!isValidState()) {
                    timeline.stop();
                    OverPane.dumpScoresToFile(score);
                    gameOverAction(over);
                }

                if (countSeconds != 0 && countSeconds % Duration.minutes(1).toSeconds() == 0
                        && timeline.getStatus() != Animation.Status.PAUSED) {
                    System.out.println("Incremented!");
                    timeline.setRate(timeline.getRate() + dFps);
                    scoreLabel.setText("SCORE: " + (score += 5));
                }
                countSeconds++;
                update();
                reDraw();
            });

            timeline.getKeyFrames().add(keyFrame);
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        return scene;
    }

    /**
     * Set on key pressed etc.
     * @param scene scene
     */
    private void movementController(Scene scene) {
        scene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case UP: moveShape(p -> p.rotate(), p -> p.rotateBack(), false);
                    break;
                case LEFT: moveShape(p -> p.move(Direction.LEFT), p -> p.move(Direction.RIGHT), false);
                    break;
                case RIGHT: moveShape(p -> p.move(Direction.RIGHT), p -> p.move(Direction.LEFT), false);
                    break;
                case DOWN: moveShape(p -> p.move(Direction.DOWN), p -> p.move(Direction.UP), true);
                    break;
                case P:
                    if (timeline.getStatus() == Animation.Status.RUNNING) {
                        timeline.pause();
                        pauseAction();

                    } else {
                        for (Node node : pane.getChildren()) {
                            if (node instanceof GridPane) {
                                pane.getChildren().remove(node);
                                break;
                            }
                        }
                        timeline.play();
                    }
                    break;
            }
            reDraw();
        });

    }

    /**
     * Actions on game over screen.
     * @param over game over pane
     */
    private void gameOverAction(OverPane over) {
        Main m = new Main();
        over.getExit().setOnMouseClicked(ev -> System.exit(1));
        over.getStartOver().setOnMouseClicked(ev -> {
            Main.stage.close();
            m.start(new Stage());
        });
        Main.stage.setScene(new Scene(over));
        if (Main.current.getMediaPlayer() != null) {
            Main.current.getMediaPlayer().stop();

            Main.current.setMediaPlayer(new MediaPlayer(new Media(
                    new File(Main.mediaPath + "Over.mp3").toURI().toString())));
            Main.current.getMediaPlayer().play();
        }
    }

    /** Action on pause. */
    private void pauseAction() {
        Label label = new Label("Press P to continue!");
        MediaPlayer player = Main.current.getMediaPlayer();
        label.setFont(Font.font("System", 24));
        label.setTextFill(Color.WHITE);
        label.setAlignment(Pos.CENTER);
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(25);
        Main m = new Main();
        pane.setBackground(new Background(new BackgroundFill(Color.DARKRED, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setOpacity(0.65);
        pane.setPrefWidth(300);
        pane.setPrefHeight(400);
        pane.setTranslateX(250);
        pane.setTranslateY(150);

        Button music = new Button();
        music.setText(player.getStatus() == MediaPlayer.Status.STOPPED ? "Music on" : "Music off");
        music.setOnMouseClicked(ev -> {
            if (player.getStatus() == MediaPlayer.Status.STOPPED) {
                music.setText("Music off");
                player.play();

            } else {
                music.setText("Music on");
                player.stop();
            }
        });
        Button backToMenu = new Button("Back to menu");
        backToMenu.setOnMouseClicked(ev -> {
            Main.stage.close();
            m.start(new Stage());
        });
        setButtonStyle(music, backToMenu);
        changeMusicAction(pane);
        pane.add(music, 1, 2);
        pane.add(backToMenu, 1, 4);
        pane.add(label, 1, 0);
        this.pane.getChildren().add(pane);

        pane.requestFocus();
    }

    private void changeMusicAction(GridPane pane) {
        Button changeMusic = new Button("Change music");
        pane.add(changeMusic, 1,3);
        MediaView current = Main.current;
        changeMusic.setOnMouseClicked(ev -> {
            if (current.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
                Main.current.getMediaPlayer().stop();
                current.setMediaPlayer(new MediaPlayer(new Media(
                        new File(Main.mediaPath + getPath(current)).toURI().toString())));
                current.getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
                Main.current = current;
                Main.current.getMediaPlayer().play();
            }
        });
        setButtonStyle(changeMusic);
    }
    private String getPath(MediaView m) {
        String[] filePath = m.getMediaPlayer().getMedia().getSource().split("/");
        switch (filePath[filePath.length - 1]) {
            case "Troika.mp3" :
                return "Loginska.mp3";
            case "Loginska.mp3" :
                return "Karinka.mp3";
            case "Karinka.mp3" :
                return "Troika.mp3";
        }
        return null;
    }

    /** Just style, yo.
     *
     * @param buttons array
     */
    private void setButtonStyle(Button... buttons) {
        for (Button b : buttons) {
            b.setPrefWidth(145);
            b.setPrefHeight(50);
            b.setStyle("-fx-base: crimson; -fx-border-color: black");
            b.setFont(Font.font("System", 18));
            GridPane.setHalignment(b, HPos.CENTER);
        }
    }
}

