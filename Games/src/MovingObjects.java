import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MovingObjects extends Application {
    private int x = 400;
    private int y = 100;
    private int dx = 2;
    private int dy = 2;
    private Rectangle rect = new Rectangle();

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();


        int width = 800;
        int height = 800;

        rect.setWidth(80);
        rect.setHeight(80);
        rect.setTranslateX(x);
        rect.setTranslateY(y);
        Timeline timeline;
        pane.getChildren().add(rect);
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), ev -> increaseSpeed()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        primaryStage.setScene(new Scene(pane, width, height));
        primaryStage.show();
    }
    void increaseSpeed() {
        y += dy;
        rect.setTranslateX(x);
        rect.setTranslateY(y);

    }
}
