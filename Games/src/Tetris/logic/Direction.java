package Tetris.logic;

/**
 * Enum class.
 */
public enum Direction {
    // Direction of the movement corresponding to the piece in center
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);

    private int x, y;

    /**
     * Const.
     */
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Previous direction comparing to the current.
     *
     * @return previous direction obj
     */
    public Direction previousDirection() {
        // ordinal() means current Pole coordinate
        int nextIndex = ordinal() - 1;

        if (nextIndex == -1) {
            nextIndex = Direction.values().length - 1;
        }

        return Direction.values()[nextIndex];
    }

    /**
     * Next direction comparing to the current.
     *
     * @return next direction obj
     */
    public Direction nextDirection() {
        int nextIndex = ordinal() + 1;

        if (nextIndex == Direction.values().length) {
            nextIndex = 0;
        }
        return Direction.values()[nextIndex];
    }

    /**
     * Getter.
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Getter.
     *
     * @return y
     */
    public int getY() {
        return y;
    }
}