package model.tetromino;

import java.awt.*;

public class S extends CurvedTetromino {

    S(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public void initPoints() {
        this.points = new Point[]{
                new Point(1, 0),
                new Point(0, 1),
                new Point(-1, 1)
        };
    }

}
