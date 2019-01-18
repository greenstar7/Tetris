package model.tetromino;

import java.awt.*;

public class I extends Tetromino {

    I(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public void initPoints() {
        this.points = new Point[]{
                new Point(-1, 0),
                new Point(-2, 0),
                new Point(1, 0)
        };
    }

    @Override
    public boolean rotateSelf(Color[][] grid) {
        Point[] oldPoints = this.points.clone();
        if (oldPoints[0].getX() == center.getX()) {
            this.points = new Point[]{
                    new Point(-2, 0),
                    new Point(-1, 0),
                    new Point(1, 0)
            };
        }
        else {
            this.points = new Point[]{
                    new Point(0, -2),
                    new Point(0, -1),
                    new Point(0, 1)
            };
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
