package model;

import model.tetrominos.Tetromino;
import model.tetrominos.TetrominoFactory;
import utilities.DeepCloner;

import java.awt.Color;

/**
 * @author Artemii Hrynevych
 * Tetris grid class which is the main model for MVC.
 */
public class TetrisGrid {
    public static final int STANDARD_X_SIZE = 10;
    public static final int STANDARD_Y_SIZE = 20;
    private int width;
    private int height;
    private Color[][] grid;
    private TetrominoFactory tetrominoFactory;
    private Tetromino tetromino;

    /**
     * Tetris grid constructor.
     * <p> Initializes size of the grid and fills Color grid with nulls.
     * Also initializes it's own TetrominoFactory.</p>
     * @param width size of X axis of the grid
     * @param height size of Y axis of the grid
     */
    public TetrisGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.tetrominoFactory = new TetrominoFactory(width, height);
        this.grid = new Color[height][width];
//        this.tetromino = tetrominoFactory.getNextTetromino();
    }

    /**
     * Standard constructor, initializes the TetrisGrid with standard sizes.
     */
    public TetrisGrid() {
        this(STANDARD_X_SIZE, STANDARD_Y_SIZE);
    }

    /**
     * Method to get the filled grid including tetromino.
     * @return 2d array of Color's, which represents current tetris game grid
     */
    public Color[][] getGridWithTetromino() {
        Color[][] gridWithTetromino = new Color[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                gridWithTetromino[y][x] = (Color) DeepCloner.deepClone(grid[y][x]);
            }
        }
        if (hasTetromino()){
            this.tetromino.placeSelf(gridWithTetromino);
        }
        return gridWithTetromino;
    }

    /**
     * Method to get the tetromino object
     * @return true if there is tetromino object, false otherwise
     */
    public boolean hasTetromino() {
        return this.tetromino != null;
    }

    /**
     * Method to move tetromino one row down
     * @return true if tetromino was moved, false otherwise
     */
    public boolean moveTetrominoDown() {
        if (hasTetromino()) {
            return this.tetromino.moveDown(this.grid);
        }
        else {
            return false;
        }
    }

    /**
     * Method to move tetromino one column left
     * @return true if tetromino was moved, false otherwise
     */
    public boolean moveTetrominoLeft() {
        if (hasTetromino()) {
            return this.tetromino.moveLeft(this.grid);
        }
        else {
            return false;
        }
    }

    /**
     * Method to move tetromino one column right
     * @return true if tetromino was moved, false otherwise
     */
    public boolean moveTetrominoRight() {
        if (hasTetromino()) {
            return this.tetromino.moveRight(this.grid);
        }
        else {
            return false;
        }
    }

    /**
     * Method to rotate tetromino counterclockwise
     * @return true if tetromino was rotated, false otherwise
     */
    public boolean rotateTetromino() {
        if (hasTetromino()) {
            return this.tetromino.rotateSelf(this.grid);
        }
        else {
            return false;
        }
    }

    /**
     * Method to place tetromino on the grid and make it null.
     * <p>
     *     Copies color values on the grid and 'deletes' tetromino to show,
     *     that new one is needed.
     * </p>
     */
    public void placeTetromino() {
        if (hasTetromino()) {
            this.tetromino.placeSelf(this.grid);
            this.tetromino = null;
        }
    }

    /**
     * Method to move tetromino as far down as possible and then place it.
     */
    public void dropTetromino() {
        if (hasTetromino()) {
            boolean movedDown;
            do {
                movedDown = tetromino.moveDown(grid);
            } while (movedDown);
            this.tetromino.placeSelf(this.grid);
            this.tetromino = null;
        }
    }

    /**
     * Method to spawn new tetromino on the grid.
     * @return true if tetromino can be spawned, false otherwise.
     */
    public boolean spawnTetromino() {
        // getting next tetromino object
        boolean spawned = false;
        this.tetromino = this.tetrominoFactory.getNextTetromino();
        // checking if we can actually spawn it and return the check result.
        if (this.tetromino.isValidPos(grid)) {
            spawned = true;
        }
        else {
            this.tetromino = null;
        }
        return spawned;
    }

    /**
     * Method to remove filled rows and shift remaining ones.
     * @return true if at least one row was filled
     */
    public void handleAllFilledRows() {
        int row = height -1;
        int topRow = 0;
        while (row >= topRow) {
            if (isFilledRow(row)) {
                shiftRows(row, topRow);
                topRow = topRow + 1;
            }
            else {
                row = row - 1;
            }
        }
    }

    /**
     * Method to check if the given row on the grid is filled.
     * @param row grid's row index to check
     * @return true if given row is fille, false otherwise
     */
    private boolean isFilledRow(int row) {
        boolean filled = true;
        for (int col = 0; col < width; col++) {
            if (grid[row][col] == null) {
                filled = false;
                break;
            }
        }
        return filled;
    }

    /**
     * Method to shift rows one row down
     * @param row starting row
     * @param min the last row to shift
     */
    private void shiftRows(int row, int min) {
        for ( ; row > min; row--) {
            this.grid[row] = this.grid[row-1].clone();
        }
        this.grid[min] = new Color[this.width];
    }
}