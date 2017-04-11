package Tetris.Interface;

import Tetris.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/** Game Settings screen. */
public class SettingsPane extends GridPane {
    /** Typical button width. */
    private final int buttonWidth = 130;
    /** And height. */
    private final int buttonHeight = 40;
    /** Define general music switch. */
    private CheckBox musicOnOff = new CheckBox("Music on/off");
    /** Troika track switch. */
    private RadioButton troika = new RadioButton("Troika");
    /** Loginska track switch. */
    private RadioButton loginska = new RadioButton("Loginska");
    /** Karinka track switch. */
    private RadioButton karinka = new RadioButton("Karinka");
    /** Back button. */
    private Button back = new Button("Back");
    /** Red color choose. */
    private Button red = new Button("Red");
    /** White color choose. */
    private Button white = new Button("White");
    /** Purple color choose. */
    private Button purple = new Button("Purple");
    /** Blue color choose. */
    private Button blue = new Button("Blue");
    /** Label which appears after choosing color */
    private Label colorLabel = new Label("Choose the color!");

    /**
     * Class constructor.
     * @param backgroundImage background image
     */
    public SettingsPane(Image backgroundImage) {
        setPanePrefs(Main.getWidth(), Main.getHeight(), backgroundImage);

        add(red, 1, 1);
        add(white, 1, 2);
        add(purple, 1, 3);
        add(blue, 1, 4);

        back.setTranslateX(-440);
        back.setTranslateY(220);
        getChildren().addAll(back, musicOnOff, troika, loginska, karinka);
    }

    /**
     * Set pane preferences.
     * @param width pane width
     * @param height pane height
     * @param image background image
     */
    private void setPanePrefs(int width, int height, Image image) {
        setPrefSize(width, height);
        setAlignment(Pos.BOTTOM_RIGHT);
        setVgap(15);
        setPadding(new Insets(70, 40, 70, 40));
        setHbox();
        setBackground(image);
        setDefault();

    }

    /** Define Hbox for color label, so it wont change the column width when appears. */
    private void setHbox() {
        setButtons();
        HBox hbox = new HBox();
        hbox.setTranslateY(0);
        hbox.setTranslateX(140);
        // Fixed size
        hbox.setPrefSize(155, 40);
        hbox.getChildren().add(colorLabel);
        getChildren().add(hbox);
    }

    /**
     * Set background.
     * @param image background image.
     */
    private void setBackground(Image image) {
        BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        setBackground(new Background(background));
    }
    /** Set buttons. */
    private void setButtons() {
        colorLabel.setAlignment(Pos.BOTTOM_RIGHT);
        back.setPrefSize(buttonWidth, buttonHeight);
        red.setPrefSize(buttonWidth, buttonHeight);
        red.setStyle("-fx-base: darkred");
        white.setPrefSize(buttonWidth, buttonHeight);
        white.setStyle("-fx-base: white");
        purple.setPrefSize(buttonWidth, buttonHeight);
        purple.setStyle("-fx-base: purple");
        blue.setPrefSize(buttonWidth, buttonHeight);
        blue.setStyle("-fx-base: darkblue");
        colorLabel.setTextFill(Color.WHITE);
        colorLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 20));
        musicOnOff.setTranslateX(155);
        musicOnOff.setTranslateY(-350);
        musicOnOff.setSelected(true);
        musicOnOff.setTextFill(Color.WHITE);
        setRadio();
    }

    /** Set radio buttons */
    private void setRadio() {
        ToggleGroup group = new ToggleGroup();
        int x = 155;
        troika.setTranslateX(x);
        troika.setTranslateY(-300);
        troika.setTextFill(Color.WHITE);
        loginska.setTranslateX(x);
        loginska.setTranslateY(-270);
        loginska.setTextFill(Color.WHITE);
        karinka.setTranslateX(x);
        karinka.setTranslateY(-240);
        karinka.setTextFill(Color.WHITE);
        troika.selectedProperty().setValue(true);
        group.getToggles().addAll(troika, loginska, karinka);
    }

    /**
     * Resolve get back button.
     * @return back.
     */
    public Button getBack() {
        return back;
    }

    /**
     * Resolve red button.
     * @return red.
     */
    public Button getRed() {
        return red;
    }

    /**
     * Resolve white button.
     * @return white.
     */
    public Button getWhite() {
        return white;
    }

    /**
     * Resolve purple button.
     * @return purple.
     */
    public Button getPurple() {
        return purple;
    }

    /**
     * Resolve blue button.
     * @return blue.
     */
    public Button getBlue() {
        return blue;
    }

    /**
     * Resolve music checkbox.
     * @return checkbox.
     */
    public CheckBox getMusicBox() {
        return musicOnOff;
    }

    /**
     * Resolve troika.
     * @return troika
     */
    public RadioButton getTroika() {
        return troika;
    }

    /**
     * Resolve loginska.
     * @return loginska
     */
    public RadioButton getLoginska() {
        return loginska;
    }

    /**
     * Resolve karinka.
     * @return karinka
     */
    public RadioButton getKarinka() {
        return karinka;
    }

    /**
     * Writes new color to the file.
     * @param envColor set color.
     */
    public void changeColor(String envColor) {
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
    public void chooseColorChange(String chosen) {
        colorLabel.setText("You chose " + chosen + "!");
    }

    /** Resetting label text. */
    public void setDefault() {
        colorLabel.setText("Choose the color!");
    }
}
