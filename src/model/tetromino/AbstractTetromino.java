package model.tetromino;

import java.awt.*;

public abstract class AbstractTetromino {
    public static final int ySpawn = 0;
    protected Point center;
    protected Point[] points;
    protected Color color;
    protected int x;
    protected int y;

    AbstractTetromino(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.center = new Point(0, 0);
    }

    abstract void initPoints();
    abstract boolean rotateSelf(Color[][] grid);
    abstract boolean moveLeft(Color[][] grid);
    abstract boolean moveRight(Color[][] grid);
    abstract boolean moveDown(Color[][] grid);
    abstract void dropSelf(Color[][] grid);
    abstract void placeSelf(Color[][] grid);
    abstract Color[][] asArray();
}
