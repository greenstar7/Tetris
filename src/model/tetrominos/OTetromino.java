package model.tetrominos;

import java.awt.*;

public class OTetromino extends  Tetromino{

    OTetromino(int width, int height, Color color) {
        super(width, height, color);
    }

    @Override
    public void initTiles() {
        this.tetrominoTiles = new Color[][]{
                {null, null, null, null},
                {null, color, color, null},
                {null, color, color, null},
                {null, null, null, null}
        };
    }

    @Override
    public boolean rotateSelf(Color[][] grid) {
        return true;
    }
}
