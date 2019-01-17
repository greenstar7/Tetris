package model.tetrominos;

import java.awt.*;

public class ZTetromino extends CurvedTetromino {

    ZTetromino(int width, int height, Color color) {
        super(width, height, color);
    }

    @Override
    public void initTiles() {
        this.tetrominoTiles = new Color[][]{
                {null, null, null, null},
                {null, color, color, null},
                {null, null, color, color},
                {null, null, null, null}
        };
    }
}