package CookieClicker;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.concurrent.ThreadLocalRandom;

/** FXML handler. */
public class CookieController {

    /** Game initializer. */
    private CookieGame cookieGame = new CookieGame();
    /** Info. */
    @FXML public Button info;
    /** Buy. */
    @FXML public Button buyCursor;
    /** Buy. */
    @FXML public Button buyClicker;
    /** Cookies count. */
    @FXML public Label cookies;
    /** Cursor count. */
    @FXML public Label cursors;
    /** Clicker count. */
    @FXML public Label clickers;
    /** Cursor price. */
    @FXML public Label cursorPrice;
    /** Clicker price. */
    @FXML public Label clickerPrice;
    /** Info display. */
    @FXML public Pane infoPane;
    /** Got it! */
    @FXML public Button ok;
    /** Fun! */
    @FXML public Button fun;
    /** Additional part. */
    @FXML public Label secondState;
    /** Cookie image. */
    @FXML public ImageView cookie;
    /** Additional part. */
    @FXML public ImageView cookieEater;


    /** Set count. */
    private void setCursors() {
        cursors.setText(toStr(cookieGame.getCursorCount()));
    }

    /** Set count. */
    private void setClickers() {
        clickers.setText(toStr(cookieGame.getClickerCount()));
    }

    /** Set cookies. */
    private void setCookies() {
        cookies.setText(toStr(cookieGame.getCookies()));
    }

    /** Set price. */
    private void setCursorPrice() {
        cursorPrice.setText(toStr(cookieGame.getCursorPrice()));
    }

    /** Set price. */
    private void setClickerPrice() {
        clickerPrice.setText(toStr(cookieGame.getClickerPrice()));
    }

    /** Buy. */
    public void setBuyCursor() {
        if (cookieGame.canBuyCursor()) {
            cookieGame.buyCursor();
            setCursors();
            setCursorPrice();
            setCookies();
        }
    }

    /** Buy. */
    public void setBuyClicker() {
        if (cookieGame.canBuyClicker()) {
            cookieGame.buyClicker();
            setClickerPrice();
            setClickers();
            setCookies();
            clickerAction();
        }
    }

    /** Display info. */
    public void infoAction() {
        infoPane.setVisible(true);
    }

    /** Close. */
    public void infoClose() {
        infoPane.setVisible(false);
    }

    /** Casual. */
    public void clickAction() {
        cookieGame.click();
        setCookies();
        if (cookieEater.isVisible()) {
            placeShape();
        }
        setSmallCookies();
    }

    /** Set automatic click. */
    private void clickerAction() {
        if (cookieGame.getClickerCount() == 0) {
            return;
        }
        int rate = cookieGame.getClickerInterval();
        Timeline timeline = new Timeline();
        System.out.println(rate);
        Timeline changeSize = new Timeline(new KeyFrame(Duration.millis(150), ev -> changeCookieBack()));
        KeyFrame keyFrame = new KeyFrame(Duration.millis(rate), ev -> {
                cookieGame.clickerAction();
                setCookies();
                changeCookie();
                changeSize.play();
                setSmallCookies();
        });
            timeline.getKeyFrames().add(keyFrame);
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
    }

    /** Set new size. */
    public void changeCookie() {
        cookie.setScaleX(0.9);
        cookie.setScaleY(0.9);
    }

    /** Resize poor cookie back. */
    public void changeCookieBack() {
        cookie.setScaleX(1);
        cookie.setScaleY(1);
    }

    /** Set fun action. */
    public void setFun() {
        if (cookieEater.isVisible()) {
            cookieEater.setVisible(false);
            secondState.setText("Thanks to Cookie Lord, he is gone...\nfor now.");
            fun.setText("Summon again");
            return;
        }
        placeShape();
        secondState.setText("Avoid clicking on him!");
        fun.setText("Go away, you beast!");
        cookieEater.setVisible(true);
    }

    /** Replace the shape. */
    private void placeShape() {
        double x = generateRandom(cookie.getLayoutX() - cookie.getFitWidth() / 3 - 40,
                cookie.getLayoutX() + cookie.getFitWidth() / 3 - 20 + 1);
        double y = generateRandom(cookie.getLayoutY() - cookie.getFitHeight() + 70, cookie.getLayoutY() - 10);
        cookieEater.setX(x);
        cookieEater.setY(y);
        eaterGrow();
    }

    /** Cookie Eater grows as its hunger! */
    private void eaterGrow() {
        if (cookieGame.getCookies() >= 400) {
            setEaterScale(1.5, 1.5);
        } else if (cookieGame.getCookies() >= 200) {
            secondState.setText("He grows as his hunger does...");
            setEaterScale(1.2, 1.2);
        } else {
            setEaterScale(1, 1);
        }
    }

    /** Change the size of the shape. */
    private void setEaterScale(double dx, double dy) {
        cookieEater.setScaleX(dx);
        cookieEater.setScaleY(dy);
    }

    /** Additional func. */
    public void setDecrement() {
        cookieGame.decrement();
        setCookies();
        placeShape();
    }

    /** Set small cookies animation. */
    private void setSmallCookies() {
        for (int i = 0; i < generateRandom(1, cookieGame.getCursorCount() < 10 ? cookieGame.getCursorCount() : 10); i++) {
            ImageView cookie = new ImageView(new Image(getClass().getResourceAsStream("images/cookie.png")));
            cookie.setLayoutX(generateRandom(25, 210));
            cookie.setLayoutY(generateRandom(50, 260));
            cookie.setOnMouseClicked(ev -> clickAction());
            cookie.setOnMousePressed(ev -> changeCookie());
            cookie.setOnMouseReleased(ev -> changeCookieBack());
            CookieFX.addImageView(cookie);
            double dx = cookie.getLayoutX() > 100 ? 2 : -2;
            double dy = cookie.getLayoutY() > 145 ? 2 : -2;
            Timeline timeline = new Timeline();
            KeyFrame keyFrame = new KeyFrame(Duration.millis(10), ev -> {
                cookie.setTranslateX(cookie.getTranslateX() + dx);
                cookie.setTranslateY(cookie.getTranslateY() + dy);
                if (cookie.getOpacity() > 0) {
                    cookie.setOpacity(cookie.getOpacity() - 0.015);
                } else {
                    timeline.stop();
                }
            });
            timeline.getKeyFrames().add(keyFrame);
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    /** Generate random int. */
    private int generateRandom(int x, int y) {
        return ThreadLocalRandom.current().nextInt(x, y + 1);
    }

    /** Overload for double. */
    private double generateRandom(double x, double y) {
        return ThreadLocalRandom.current().nextDouble(x, y + 1);
    }

    /**
     * To string!
     * @param val int
     * @return str
     */
    private String toStr(final int val) {
        return String.valueOf(val);
    }
}
