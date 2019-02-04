package model.tetromino;

import java.awt.Color;
import java.util.Random;

/**
 * @author Artemii Hrynevych
 * Simple tetromino abstract factory.
 */
public class TetrominoFactory {
    private Random rand;
    private int startX;
    private int startY;
    private int last;
    private static final Color[] tetrominoColors;

    static {
        tetrominoColors = new Color[]{
                Color.YELLOW, // O
                Color.CYAN, // I
                Color.GREEN, // S
                Color.RED, // Z
                Color.ORANGE, // L
                Color.BLUE, // J
                new Color(128, 0, 128) // T
        };
    }

    public TetrominoFactory(int width) {
        this.rand = new Random();
        this.startX = (int) Math.ceil(width/2.0);
        this.startY = 2;
    }

    /**
     * Function to get new Tetromino object
     * @return new randomly selected Tetromino object
     */
    public Tetromino getNextTetromino() {
        this.last = this.rand.nextInt(7);
        switch (this.last) {
            case 0:
                return new O(startX, startY, tetrominoColors[this.last]);
            case 1:
                return new I(startX, startY, tetrominoColors[this.last]);
            case 2:
                return new S(startX, startY, tetrominoColors[this.last]);
            case 3:
                return new Z(startX, startY, tetrominoColors[this.last]);
            case 4:
                return new L(startX, startY, tetrominoColors[this.last]);
            case 5:
                return new J(startX, startY, tetrominoColors[this.last]);
            case 6:
                return new T(startX, startY, tetrominoColors[this.last]);
            default: return null;
        }
    }

    /**
     * Method to get the clone of the last tetromino
     */
    public Tetromino getLastClone() {
        switch (this.last) {
            case 0:
                return new O(startX, startY, tetrominoColors[this.last]);
            case 1:
                return new I(startX, startY, tetrominoColors[this.last]);
            case 2:
                return new S(startX, startY, tetrominoColors[this.last]);
            case 3:
                return new Z(startX, startY, tetrominoColors[this.last]);
            case 4:
                return new L(startX, startY, tetrominoColors[this.last]);
            case 5:
                return new J(startX, startY, tetrominoColors[this.last]);
            case 6:
                return new T(startX, startY, tetrominoColors[this.last]);
            default: return null;
        }
    }
}