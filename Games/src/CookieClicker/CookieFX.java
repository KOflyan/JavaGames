package CookieClicker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class CookieFX extends Application {
    public static Pane current;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = FXMLLoader.load(getClass().getResource("Cookie.fxml"));
        current = pane;
        primaryStage.setScene(new Scene(pane));
        primaryStage.setTitle("Cookie Clicker!");
        primaryStage.setResizable(false);
        primaryStage.show();
        //pane.setOnMouseClicked(ev -> System.out.println(ev.getX() + " " + ev.getY()));
    }

    public static void addImageView(ImageView imageView) {
        imageView.setScaleX(0.2);
        imageView.setScaleY(0.2);
        imageView.setOpacity(1);
        current.getChildren().add(imageView);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
