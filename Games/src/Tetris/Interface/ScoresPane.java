package Tetris.Interface;

import Tetris.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.io.File;
import java.io.IOException;
import java.util.*;

/** Game Scores screen. */
public class ScoresPane extends Pane {
    /** Define back button */
    private Button back = new Button("Back");

    /** Class constructor. */
    public ScoresPane() {
        setPanePrefs(Main.getWidth(), Main.getHeight());
        back.setPrefSize(130, 40);
        back.setTranslateX(25);
        back.setTranslateY(685);
        back.setStyle("-fx-border-color: white; -fx-base: black;");
        back.setTextFill(Color.WHITE);
        getChildren().add(back);
        addScores();
    }

    /**
     * Set pane preferences.
     * @param width pane width
     * @param height pane height
     */
    private void setPanePrefs(int width, int height) {
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        setPrefSize(width, height);
        setPadding(new Insets(50));
    }

    /** Get scores from the file. */
    private List<Integer> getScores() {
        Set<Integer> scores = new HashSet<>();
        try {
            File file = new File("Games/src/Tetris/resc/Scores.txt");
            Scanner read = new Scanner(file);
            while (read.hasNext()) {
                scores.add(Integer.valueOf(read.next()));
            }
            read.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return separateBest(scores);
    }

    /** Separate the best 10 scores.
     *
     * @param scores Set
     * @return list
     */
    private List<Integer> separateBest(Set<Integer> scores) {
        List<Integer> scoresList = new ArrayList<>(scores);
        scoresList.sort(Collections.reverseOrder());
        if (scoresList.size() >= 10) {
            return scoresList.subList(0, 10);
        }
        return scoresList;
    }

    /** Add scores to the pane. */
    private void addScores() {
        List<Integer> bestScores = getScores();
        int index = 1;
        int x = Main.getWidth() / 2 + 75;
        int y = 150;
        Label indexLabel = new Label("Index");
        Label scoreLabel = new Label("Scores");
        setLabelStyle(indexLabel, 35);
        setLabelStyle(scoreLabel, 35);
        indexLabel.setTranslateX(x - 235);
        indexLabel.setTranslateY(y - 60);
        scoreLabel.setTranslateX(x);
        scoreLabel.setTranslateY(y - 60);
        getChildren().addAll(indexLabel, scoreLabel);
        for (int score : bestScores) {
            Label ind = new Label(String.valueOf(index));
            Label lbl = new Label(String.valueOf(score));
            lbl.setPrefSize(100, 15);
            lbl.setAlignment(Pos.CENTER);
            setLabelStyle(ind, 30);
            setLabelStyle(lbl, 30);
            ind.setTranslateX(x - 235);
            ind.setTranslateY(y);
            ind.setPrefSize(50, 15);

            ind.setAlignment(Pos.CENTER);
            lbl.setTranslateX(x);
            lbl.setTranslateY(y);
            Line line = new Line(ind.getTranslateX(), ind.getTranslateY(), scoreLabel.getTranslateX() + 150, lbl.getTranslateY());
            line.setStroke(Color.WHITE);
            //line.setFill((Color.WHITE));
            line.setStrokeWidth(3);
            getChildren().addAll(lbl, ind, line);
            y += 50;
            ++index;
        }
        Line v = new Line(scoreLabel.getTranslateX() - 100, scoreLabel.getTranslateY(), scoreLabel.getTranslateX() - 100, y);
        v.setStroke(Color.WHITE);
        v.setStrokeWidth(3);
        getChildren().add(v);
    }

    /**
     * Set style.
     * @param label label
     * @param fontSize font size
     */
    private void setLabelStyle(Label label, int fontSize) {
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("System", fontSize));
    }

    /**
     * Resolve back button.
     * @return back.
     */
    public Button getBack() {
        return back;
    }
}
