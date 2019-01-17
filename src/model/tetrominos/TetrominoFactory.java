package model.tetrominos;

import java.awt.Color;
import java.util.Random;

/**
 * @author Artemii Hrynevych
 * Simple tetromino abstract factory.
 */
public class TetrominoFactory {
    private Random rand;
    private int width;
    private int height;
    public static final Color[] tetrominoColors;

    static {
        tetrominoColors = new Color[]{
                Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE,
                Color.YELLOW, Color.PINK, Color.CYAN
        };
    }

    public TetrominoFactory(int width, int height) {
        this.rand = new Random();
        this.width = width;
        this.height = height;
    }

    /**
     * Function to get new Tetromino object
     * @return new randomly selected Tetromino object
     */
    public Tetromino getNextTetromino() {
        int i = this.rand.nextInt(7);
        switch (i) {
            case 0:
                return new OTetromino(width, height, tetrominoColors[i]);
            case 1:
                return new ITetromino(width, height, tetrominoColors[i]);
            case 2:
                return new STetromino(width, height, tetrominoColors[i]);
            case 3:
                return new ZTetromino(width, height, tetrominoColors[i]);
            case 4:
                return new LTetromino(width, height, tetrominoColors[i]);
            case 5:
                return new JTetromino(width, height, tetrominoColors[i]);
            case 6:
                return new TTetromino(width, height, tetrominoColors[i]);
            default: return null;
        }
    }
}