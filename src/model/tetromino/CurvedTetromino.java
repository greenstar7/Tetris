package model.tetromino;

import utilities.DeepCloner;

import java.awt.*;

public abstract class CurvedTetromino extends Tetromino {

    CurvedTetromino(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public boolean rotateSelf(Color[][] grid) {
        Point[] oldPoints = new Point[3];
        for (int i = 0; i < points.length; i++) {
            oldPoints[i] = (Point) DeepCloner.deepClone(points[i]);
        }
        for (Point point: points) {
            point.setLocation(-point.getY(), point.getX());
        }
        if (isValidPos(grid)) {
            return true;
        }
        else {
            this.points = oldPoints;
            return false;
        }
    }
}
