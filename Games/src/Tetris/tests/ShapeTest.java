package Tetris.tests;

import Tetris.logic.Direction;
import Tetris.logic.GamePane;
import Tetris.logic.Piece;
import Tetris.logic.Shape;
import javafx.scene.paint.Color;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShapeTest {
    private Piece p = new Piece(0, Direction.DOWN);
    private Shape s = new Shape(Color.BLUE, p);

    @Test
    public void move() throws Exception {
        s.move(5, 5);
        assertEquals(s.getX(), 5);
        assertEquals(s.getY(), 5);
    }

    @Test
    public void move1() throws Exception {
        s.move(Direction.DOWN.getX(), Direction.DOWN.getY());
        assertEquals(s.getX(), Direction.DOWN.getX());
        assertEquals(s.getY(), Direction.DOWN.getY());
    }

    @Test
    public void rotate() throws Exception {
        s.rotate();
        s.getPieces().forEach(p -> assertEquals(p.directions.get(0), Direction.LEFT));
    }

    @Test
    public void rotateBack() throws Exception {
        s.rotateBack();
        s.getPieces().forEach(p -> assertEquals(p.directions.get(0), Direction.RIGHT));

    }

    @Test
    public void detach() throws Exception {
        s.move(5, 5);
        p.x = 5;
        p.y = 5;
        s.detach(5, 5);
        assertEquals(s.getPieces().size(), 0);

    }

    @Test
    public void copy() throws Exception {
        Shape s = new Shape(Color.BLUE, p);
        assert s.copy() != null;
        assert s.copy().getX() == s.getX();
        assert s.copy().getY() == s.getY();
    }

    @Test
    public void getPieces() throws Exception {
        assertEquals(s.getPieces().get(0), p);
    }

}