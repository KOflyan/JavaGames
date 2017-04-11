package Tetris.logic;

import java.util.Arrays;
import java.util.List;

public class Piece {
    /** Distance from the piece to the center piece of the shape. */
    public int distance;
    public List<Direction> directions;
    public Shape shape;
    public int x, y;

    public Piece(int distance, Direction... direction) {
        this.distance = distance;
        this.directions = Arrays.asList(direction);
    }

    /**
     * Set the particular piece as a part of something bigger.
     * @param parent Shape obj
     */
    public void setParent(Shape parent) {
        this.shape = parent;
        x = shape.getX();
        y = shape.getY();

        for (Direction d : directions) {
            x += distance * d.getX(); // multiplying so the pieces stay in places and won't collide
            y += distance * d.getY();
        }

    }
    /** We need a collection here to deal with more complex structures as cube, for instance. */
    public void setDirection(Direction... direction) {
        this.directions = Arrays.asList(direction);

        x = shape.getX();
        y = shape.getY();

        for (Direction d : directions) {
            x += distance * d.getX();
            y += distance * d.getY();
        }
    }

    /**
     * New object with same properties.
     * @return new Piece
     */
    public Piece copy() {
        return new Piece(distance, directions.toArray(new Direction[0]));
    }
}