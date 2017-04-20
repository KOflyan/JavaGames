package Tetris.tests;

import Tetris.logic.Direction;
import Tetris.logic.Piece;
import Tetris.logic.Shape;
import javafx.scene.paint.Color;
import org.junit.Test;

import static org.junit.Assert.*;

public class PieceTest {
    @Test
    public void setParent() throws Exception {
        Piece p = new Piece(1, Direction.DOWN);
        Shape s = new Shape(Color.BLUE);
        p.setParent(s);
        assertEquals(p.shape, s);
    }

    @Test
    public void setDirection() throws Exception {
        Piece p = new Piece(1, Direction.DOWN);
        Shape s = new Shape(Color.BLUE, p);
        p.setDirection(Direction.DOWN);
        assertEquals(p.x, Direction.DOWN.getX());
        assertEquals(p.y, Direction.DOWN.getY());
    }

    @Test
    public void copy() throws Exception {
        Piece p = new Piece(1, Direction.DOWN);
        assert p.copy() != null;
        assert p.copy().directions.equals(p.directions);
    }
}
