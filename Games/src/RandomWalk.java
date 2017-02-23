
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Arrays.asList;

/**
 * Random Walk.
 */
public class RandomWalk extends Application {

    private final int STEP = 40;
    private final int W = 750;
    private final int H = 780;
    private final int rad = 4;
    private static int index = 0;
    private boolean isStart = true;
    private Button start = new Button("Start");
    private Pane pane = new Pane();
    private Timeline timeline = new Timeline();
    private ArrayList<ArrayList<Double>> points = new ArrayList<>();
    private ArrayList<ArrayList<Double>> pointsVisited = new ArrayList<>();
    private ArrayList<Integer> check = new ArrayList<>();
    private Random rand = new Random();
    private Label over = new Label("Game over!");

    /**
     * Class constructor.
     */
    public RandomWalk() {
        pane.getChildren().add(start);
        start.setPrefSize(100, 25);
        start.setTranslateX(W - 210 + STEP);
        start.setTranslateY(H - 80);
    }

    /**
     * Main
     */
    @Override
    public void start(Stage primaryStage) {
        drawWeb();
        appendPoints();
        gameStart();

        primaryStage.setScene(new Scene(pane, W, H));
        primaryStage.show();
        pane.requestFocus();
    }

    /**
     * Append cross points to array
     */
    private void appendPoints() {
        int index = 0;
        double h_step = STEP;
        double v_step = STEP;
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                ArrayList<Double> p = new ArrayList<>();
                p.add(0, v_step);
                p.add(1, h_step);
                points.add(index, p);
                h_step += STEP;
                index += 1;
            }

            h_step = STEP;
            v_step += STEP;
        }
    }

    /**
     * Draw paths
     */
    private void drawWeb() {
        double step = 0;
        for (int i = 0; i < 17; i++) {
            pane.getChildren().add(new Line(STEP + step, STEP, step + STEP, 680));
            pane.getChildren().add(new Line(STEP, step + STEP, 680, step + STEP));
            step += STEP;
        }
    }

    /**
     * Button action
     */
    private void gameStart() {
        start.setOnMouseClicked(e -> {

            isStart = true;
            pane.getChildren().add(new Circle(points.get(144).get(0), points.get(144).get(1), rad));
            pointsVisited.add(points.get(144));
            game();
        });
    }

    /**
     * Draw smth.
     * @param x1 x1
     * @param y1 y1
     * @param x2 x2
     * @param y2 y2
     */
    private void drawThings(double x1, double y1, double x2, double y2){
        pane.getChildren().add(new Circle(x1, y1, rad));
        pane.getChildren().add(new Line(x1, y1, x2, y2));
        pane.getChildren().add(new Circle(x2, y2, rad));
    }

    /**
     * Game loop
     */
    private void game() {
        KeyFrame frame = new KeyFrame(Duration.seconds(0.3), e -> {
            if (isStart) {
                double x1 = pointsVisited.get(index).get(0);
                double y1 = pointsVisited.get(index).get(1);
                double x2 = x1;
                double y2 = y1;

                ArrayList<Double> p = new ArrayList<>();
                ArrayList<Integer> r = new ArrayList<>();

                int direction;
                while (true) {
                    direction = rand.nextInt(4);
                    if (!r.contains(direction)) {
                        r.add(0, direction);
                        break;
                    }
                }
                switch (direction) {
                    case 0:
                        x2 = x1 - STEP;
                        break;

                    case 1:
                        x2 = x1 + STEP;
                        break;

                    case 2:
                        y2 = y1 - STEP;
                        break;

                    case 3:
                        y2 = y1 + STEP;
                        break;
                    default:
                        break;
                }
                p.add(0, x2);
                p.add(1, y2);

                if (index + 1 == points.size()) {
                    timeline.stop();
                    pane.getChildren().add(over);
                }
                if (pointsVisited.contains(new ArrayList<>(asList(x1 + STEP, y1))) &&
                        pointsVisited.contains(new ArrayList<>(asList(x1 - STEP, y1))) &&
                        pointsVisited.contains(new ArrayList<>(asList(x1, y1 - STEP))) &&
                        pointsVisited.contains(new ArrayList<>(asList(x1, y1 + STEP)))
                        ) {
                    timeline.stop();
                    pane.getChildren().add(over);
                }
                if (!(x2 < STEP || x2 > 680 || y2 < STEP || y2 > 680) && !pointsVisited.contains(p)) {

                    pointsVisited.add(index + 1, p);
                    drawThings(x1, y1, x2, y2);
                    index += 1;
                    check.clear();
                }
                else {
                    if (!check.contains(direction)) {
                        check.add(direction);
                    }
                }
                if (check.size() == 4) {
                    timeline.stop();
                    pane.getChildren().add(over);
                }
            }
        });
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void startAgain() {

        pane.getChildren().clear();
        pointsVisited.clear();
        check.clear();
        drawWeb();
        gameStart();
        pane.getChildren().add(start);
        index = 0;
    }

    public static void main(String[] args){
        launch(args);
    }
}
