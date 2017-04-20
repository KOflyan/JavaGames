package Tetris.tests;

import Tetris.logic.Direction;
import Tetris.logic.Piece;
import org.junit.Test;

import static org.junit.Assert.*;

public class DirectionTest {
    private Piece p = new Piece(0, Direction.DOWN);

    @Test
    public void previousDirection() throws Exception {
        assertEquals(p.directions.get(0).previousDirection(), Direction.RIGHT);
    }

    @Test
    public void nextDirection() throws Exception {
        assertEquals(p.directions.get(0).nextDirection(), Direction.LEFT);
    }
}