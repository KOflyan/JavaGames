package Tetris;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Game Settings screen.
 */
class SettingsPane extends GridPane {

    private CheckBox musicOnOff = new CheckBox("Music on/off");
    private Button back = new Button("Back");
    private Button red = new Button("Red");
    private Button white = new Button("White");
    private Button purple = new Button("Purple");
    private Button blue = new Button("Blue");
    private Label colorLabel = new Label("Choose the color!");

    /**
     * Class constructor.
     */
    SettingsPane() {
        int width = Main.getWidth();
        int height = Main.getHeight();
        HBox hbox = new HBox();
        hbox.setTranslateY(0);
        hbox.setTranslateX(140);
        hbox.setPrefSize(155, 40);
        setPrefSize(width, height);
        setAlignment(Pos.BOTTOM_RIGHT);
        BackgroundImage backgr = new BackgroundImage(new Image(getClass().getResourceAsStream("img/love.jpg"),
                width, height, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        setVgap(15);
        setPadding(new Insets(70, 40, 70, 40));
        colorLabel.setAlignment(Pos.BOTTOM_RIGHT);
        setBackground(new Background(backgr));
        back.setPrefSize(130, 40);
        red.setPrefSize(130, 40);
        red.setStyle("-fx-base: darkred");
        white.setPrefSize(130, 40);
        white.setStyle("-fx-base: white");
        purple.setPrefSize(130, 40);
        purple.setStyle("-fx-base: purple");
        blue.setPrefSize(130, 40);
        blue.setStyle("-fx-base: darkblue");
        colorLabel.setTextFill(Color.WHITE);
        colorLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //colorLabel.setTranslateX(120);
        musicOnOff.setTranslateX(150);
        musicOnOff.setTranslateY(-250);
        musicOnOff.setSelected(true);
        musicOnOff.setTextFill(Color.WHITE);
        add(red, 1, 1);
        add(white, 1, 2);
        add(purple, 1, 3);
        add(blue, 1, 4);
        hbox.getChildren().add(colorLabel);
        colorLabel.setAlignment(Pos.CENTER);
        back.setTranslateX(-440);
        back.setTranslateY(220);
        getChildren().addAll(back, hbox, musicOnOff);
    }

    /**
     * Resolve get back button.
     * @return back.
     */
    Button getBack() {
        return back;
    }

    /**
     * Resolve red button.
     * @return red.
     */
    Button getRed() {
        return red;
    }

    /**
     * Resolve white button.
     * @return white.
     */
    Button getWhite() {
        return white;
    }

    /**
     * Resolve purple button.
     * @return purple.
     */
    Button getPurple() {
        return purple;
    }

    /**
     * Resolve bue button.
     * @return blue.
     */
    Button getBlue() {
        return blue;
    }

    /**
     * Writes new color to the file.
     * @param envColor set color.
     */
    void changeColor(String envColor) {
            try {
                File file = new File("Games/src/Tetris/resc/Colors.txt");
                PrintWriter writer = new PrintWriter(file);
                writer.println(envColor);
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }

    /**
     * Changing label text.
     * @param chosen color.
     */
    void chooseColorChange(String chosen) {
        colorLabel.setText("You chose " + chosen + "!");
    }

    /**
     * Resetting label text.
     *
     */
    void setDefault() {
        colorLabel.setText("Choose the color!");
    }

    /**
     * Resolving music checkbox.
     * @return checkbox.
     */
    CheckBox getMusicBox() {
        return musicOnOff;
    }
}
