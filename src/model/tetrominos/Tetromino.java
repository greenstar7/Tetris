package model.tetrominos;

import utilities.DeepCloner;

import java.awt.Color;

/**
 * @author Artemii Hrynevych
 * Abstract class for tetromino.
 */
public abstract class Tetromino{
    protected int width;
    protected int height;
    protected int y;
    protected int x;
    protected static final int C_X = 2;
    protected static final int C_Y = 1;
    protected static final int N_TILES = 4;
    protected Color color;
    Color[][] tetrominoTiles;

    /**
     * Tetromino constructor
     * @param width X axis size of the grid on which tetromino will be placed.
     * @param height Y axis size of the grid on which tetromino will be placed.
     */
    Tetromino(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.x = width /2;
        this.y = 0;
        this.color = color;
        initTiles();
    }


    /**
     * Function to rotate tetromino counterclockwise.
     * @param grid Tetris game grid
     * @return true if tetromino rotated, false otherwise
     */
    public abstract boolean rotateSelf(Color[][] grid);

    /**
     * Function to init the tetromino tiles with needed color.
     */
    public abstract void initTiles();

    /**
     * Function to move tetromino to the left.
     * @param grid Tetris game grid
     * @return true if tetromino moved left, false otherwise
     */
    public boolean moveLeft(Color[][] grid) {
        boolean moved;
        int oldX = this.x;
        this.x = this.x - 1;
        if (this.isValidPos(grid)) {
            moved = true;
        }
        else {
            this.x = oldX;
            moved = false;
        }
        return moved;
    }

    /**
     * Function to move tetromino to the right.
     * @param grid Tetris game grid
     * @return true if tetromino moved right, false otherwise
     */
    public boolean moveRight(Color[][] grid) {
        boolean moved;
        int oldX = this.x;
        this.x = this.x + 1;
        if (this.isValidPos(grid)) {
            moved = true;
        }
        else {
            this.x = oldX;
            moved = false;
        }
        return moved;
    }

    /**
     * Function to move tetromino down.
     * @param grid Tetris game grid
     * @return true if tetromino moved down, false otherwise
     */
    public boolean moveDown(Color[][] grid) {
        int oldY = this.y;
        this.y = this.y + 1;
        boolean moved = false;
        if (this.isValidPos(grid)) {
            moved = true;
        }
        else {
            this.y = oldY;
        }
        return moved;
    }

    /**
     * Function to move tetromino down as far as possible and place it on grid.
     * @param grid Tetris game grid
     */
    public void dropSelf(Color[][] grid) {
        boolean moved;
        do {
            moved = this.moveDown(grid);
        } while (moved);
//        placeSelf(grid);
    }

    /**
     * Writes down colors of the tetromino to the grid.
     * @param grid Tetris game grid
     */
    public void placeSelf(Color[][] grid) {
        int gridY, gridX;
        for (int row = 0; row < N_TILES; row++) {
            gridY = this.y + row - C_Y;
            if (gridY >= 0 && gridY < this.height) {
                for (int col = 0; col < N_TILES; col++) {
                    gridX = this.x + col - C_X;
                    if (gridX >= 0 && gridX < this.width &&
                            this.tetrominoTiles[row][col] != null) {
                        grid[gridY][gridX] = this.tetrominoTiles[row][col];
                    }
                }
            }
        }
    }

    /**
     * Function to check if the current position if available to place tetromino.
     * <p>It's ugly and bulky but kinda should work just fine, kek</p>
     * @param grid Tetris game grid
     * @return true if position is available for tetromino, false otherwise
     */
    public boolean isValidPos(Color[][] grid) {
        if (this.x < 0 || this.x >= this.width || this.y >= this.height) {
            return false;
        }
        else {
            int gridX, gridY;
            for (int row = 0; row < N_TILES; row++) {
                gridY = this.y + row - C_Y;
                // if we go out of the grid
                if (gridY < 0 || gridY >= this.height) {
                    // check that row in tetromino for tiles
                    for (int col = 0; col < N_TILES; col++) {
                        // if there were any tiles
                        if (this.tetrominoTiles[row][col] != null) {
                            // we cannot place tetromino that way
                            return false;
                        }
                    }
                    // else if we can just ignore that row - go next
                    continue;
                }
                for (int col = 0; col < N_TILES; col++) {
                    gridX = this.x + col - C_X;
                    if (gridX < 0 || gridX >= this.width) {
                        // check that col in tetromino for tiles
                        for (int i = 0; i < N_TILES; i++) {
                            // if there were any tiles
                            if (this.tetrominoTiles[i][col] != null) {
                                // we cannot place tetromino that way
                                return false;
                            }
                        }
                        // else if we can just ignore that col - go next
                        continue;
                    }
                    // if the needed place on the grid is already taken
                    else if (this.tetrominoTiles[row][col] != null
                            && grid[gridY][gridX] != null) {
                        // that means we cant just move the tetromino there
                        return false;
                    }
                }
            }
        }
        return true;
    }
}