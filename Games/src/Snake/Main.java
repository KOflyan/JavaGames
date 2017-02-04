package Snake;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    /** Direction class */
    public enum Direction {
        UP, RIGHT, LEFT, DOWN
    }
    private final int BLOCK_SIZE = 30;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private final int SPEED = 8;

    private Direction direction = null;
    private boolean running = false;

    private Timeline timeline = new Timeline();
    private ObservableList<Node> snake;

    private Parent CreateContent(){
        Random rand = new Random();
        Pane root = new Pane();
        root.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(0), new Insets(0))));
        root.setPrefSize(WIDTH, HEIGHT);
        Group SnakeBody = new Group();
        snake = SnakeBody.getChildren(); // ???

        Rectangle food = new Rectangle(BLOCK_SIZE / 2, BLOCK_SIZE / 2);
        food.setFill(Color.BLACK);
        food.setTranslateX(rand.nextInt(WIDTH - BLOCK_SIZE / 2)); // Draw apple
        food.setTranslateY(rand.nextInt(HEIGHT - BLOCK_SIZE / 2));

        KeyFrame frame = new KeyFrame(Duration.seconds(0.01), event -> { // FPS
            if (running && direction != null) {

                boolean toRemove = snake.size() > 1;
                Node tail = toRemove ? snake.remove(snake.size() - 1) : snake.get(0); // For pieces to go with snake



                switch (direction) {
                    case UP:
                        tail.setTranslateX(snake.get(0).getTranslateX());
                        tail.setTranslateY(snake.get(0).getTranslateY() - SPEED);
                        break;
                    case DOWN:
                        tail.setTranslateX(snake.get(0).getTranslateX());
                        tail.setTranslateY(snake.get(0).getTranslateY() + SPEED);
                        break;
                    case LEFT:
                        tail.setTranslateX(snake.get(0).getTranslateX() - SPEED);
                        tail.setTranslateY(snake.get(0).getTranslateY());
                        break;
                    case RIGHT:
                        tail.setTranslateX(snake.get(0).getTranslateX() + SPEED);
                        tail.setTranslateY(snake.get(0).getTranslateY());
                        break;
                }

                double tailX = tail.getTranslateX();
                double tailY = tail.getTranslateY();
                if (toRemove) {
                    snake.add(0, tail);
                }
                for (Node rect : snake) {
                    if (rect != tail && tail.getTranslateX() == root.getTranslateX() &&
                            tail.getTranslateY() == rect.getTranslateY()) {
                        restartGame();
                        break;
                    }
                }
                if (tail.getTranslateX() < 0 || tail.getTranslateX() >= WIDTH - BLOCK_SIZE ||
                        tail.getTranslateY() < 0 || tail.getTranslateY() >= HEIGHT - BLOCK_SIZE) {
                    restartGame();
                }
                if (tail.getTranslateX() >= food.getTranslateX() - BLOCK_SIZE && tail.getTranslateX() <=
                        food.getTranslateX() + BLOCK_SIZE
                        && tail.getTranslateY() >= food.getTranslateY() - BLOCK_SIZE && tail.getTranslateY() <=
                        food.getTranslateY() + BLOCK_SIZE) {

                    food.setTranslateX(rand.nextInt(WIDTH - BLOCK_SIZE / 2));
                    food.setTranslateY(rand.nextInt(HEIGHT - BLOCK_SIZE / 2));
                    Rectangle rect = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
                    rect.setTranslateX(tailX);
                    rect.setTranslateY(tailY);
                    rect.setFill(Color.BLACK);
                    snake.add(rect);
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE); // How many times we run main game loop

        root.getChildren().addAll(food, SnakeBody);

        return root;
    }

    private  void restartGame(){
        stopGame();
        startGame();
    }
    private void startGame(){
        Rectangle head = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        head.setFill(Color.BLACK);
        snake.add(head);
        timeline.play();
        running = true;
    }
    private void stopGame(){
        running = false;
        timeline.stop();
        snake.clear();
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(CreateContent());
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case UP:
                    if (direction != Direction.DOWN)
                        direction = Direction.UP;
                    break;
                case DOWN:
                    if (direction != Direction.UP)
                        direction = Direction.DOWN;
                    break;
                case LEFT:
                    if (direction != Direction.RIGHT)
                        direction = Direction.LEFT;
                    break;
                case RIGHT:
                    if (direction != Direction.LEFT)
                        direction = Direction.RIGHT;
                    break;
            }

        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("It\'s a Snake!");
        primaryStage.show();
        startGame();
    }
    public static void main(String[] args){
        launch(args);
    }
}
