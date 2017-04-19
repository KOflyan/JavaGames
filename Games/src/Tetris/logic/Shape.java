package Tetris.logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static Tetris.logic.GamePane.BLOCK_SIZE;


public class Shape {

    private int x, y;

    private Color color;

    private List<Piece> pieces;

    public Shape(Color color, Piece... pieces) {
        this.color = color;
        this.pieces = new ArrayList<>(Arrays.asList(pieces));
        // Setting pieces to one shape
        for (Piece piece : this.pieces)
            piece.setParent(this);
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;

        pieces.forEach(p -> {
            p.x += dx;
            p.y += dy;
        });
    }

    public void move(Direction dir) {
        move(dir.getX(), dir.getY());
    }

    public void draw(GraphicsContext g) {

        g.setFill(color);
        g.setStroke(Color.BLACK);
        pieces.forEach(p -> {
            g.fillRect(p.x * BLOCK_SIZE, p.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            g.strokeRect(p.x * BLOCK_SIZE, p.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        });
    }

    /**
     * Rotation: just set each piece's direction to its next.
     */
    public void rotate() {
        pieces.forEach(p -> p.setDirection(p.directions.stream()
                .map(Direction::nextDirection)
                .collect(Collectors.toList()).toArray(new Direction[0])));
    }

    /**
     * Borders.
     */
    public void rotateBack() {
        pieces.forEach(p -> p.setDirection(p.directions.stream()
                .map(Direction::previousDirection)
                .collect(Collectors.toList())
                .toArray(new Direction[0])));
    }

    /**
     * Removing concrete blocks in case of full row.
     */
    public void detach(int x, int y) {
        pieces.removeIf(p -> p.x == x && p.y == y);
    }

    /**
     * Return new object with the same parameters
     */
    public Shape copy() {
        return new Shape(color, pieces.stream()
                // Copy every piece linked to parent
                .map(Piece::copy)
                // Collect to list
                .collect(Collectors.toList())
                // Return as array
                .toArray(new Piece[0]));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}