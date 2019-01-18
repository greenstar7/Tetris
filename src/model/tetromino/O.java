package model.tetromino;

import java.awt.*;

public class O extends Tetromino{

    O(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public void initPoints() {
        this.points = new Point[]{
                new Point(-1, 0),
                new Point(-1, 1),
                new Point(0, 1)
        };
    }

    @Override
    public boolean rotateSelf(Color[][] grid) {
        return true;
    }
}
