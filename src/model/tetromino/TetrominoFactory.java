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
    public static final Color[] tetrominoColors;

    static {
        tetrominoColors = new Color[]{
                Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE,
                Color.YELLOW, Color.PINK, Color.CYAN
        };
    }

    public TetrominoFactory(int width) {
        this.rand = new Random();
        this.startX = (int) Math.ceil(width/2.0);
        this.startY = 0;
    }

    /**
     * Function to get new Tetromino object
     * @return new randomly selected Tetromino object
     */
    public Tetromino getNextTetromino() {
        int i = this.rand.nextInt(7);
        switch (i) {
            case 0:
                return new O(startX, startY, tetrominoColors[i]);
            case 1:
                return new I(startX, startY, tetrominoColors[i]);
            case 2:
                return new S(startX, startY, tetrominoColors[i]);
            case 3:
                return new Z(startX, startY, tetrominoColors[i]);
            case 4:
                return new L(startX, startY, tetrominoColors[i]);
            case 5:
                return new J(startX, startY, tetrominoColors[i]);
            case 6:
                return new T(startX, startY, tetrominoColors[i]);
            default: return null;
        }
    }
}