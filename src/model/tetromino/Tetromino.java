package model.tetromino;

import java.awt.*;

/**
 * @author Artemii Hrynevych
 * Abstract class for tetromino.
 */
public abstract class Tetromino extends AbstractTetromino{
    /**
     * Tetromino constructor
     * @param color - color of the tetromino to be placed on grid
     */
    Tetromino(int x, int y, Color color) {
        super(x, y, color);
        initPoints();
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
    public abstract void initPoints();

    /**
     * Function to get tetromino representation as 4x4 array.
     * @return Color[4][4] array
     */
    @Override
    public Color[][] asArray() {
        Color[][] array = new Color[4][4];
        int y = 1;
        int x = 2;
        array[y][x] = color;
        for (Point point: points) {
            array[y+(int)point.getY()][x+(int)point.getX()] = color;
        }
        return array;
    }

    /**
     * Function to move tetromino to the left.
     * @param grid Tetris game grid
     * @return true if tetromino moved left, false otherwise
     */
    @Override
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
    @Override
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
    @Override
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
     * Writes down colors of the tetromino to the grid.
     * @param grid Tetris game grid
     */
    @Override
    public void placeSelf(Color[][] grid) {
        grid[y][x] = color;
        for (Point point: points) {
            grid[y+(int)point.getY()][x+(int)point.getX()] = color;
        }
    }

    /**
     * Function to check if the current position if available to place tetromino.
     * @param grid Tetris game grid
     * @return true if position is available for tetromino, false otherwise
     */
    public boolean isValidPos(Color[][] grid) {
        if (y < 0 || y >= grid.length) {
            return false;
        }
        else if (x < 0 || x >= grid[0].length) {
            return false;
        }
        else if (grid[y][x] != null) {
            return false;
        }
        else {
            int gridX, gridY;
            for (Point point: points) {
                gridY = y+(int)point.getY();
                gridX = x+(int)point.getX();
                if (gridY < 0 || gridY >= grid.length) {
                    return false;
                }
                else if (gridX < 0 || gridX >= grid[0].length) {
                    return false;
                }
                else if (grid[gridY][gridX] != null) {
                    return false;
                }
            }
            return true;
        }
    }
}