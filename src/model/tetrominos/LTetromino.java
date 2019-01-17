package model.tetrominos;

import java.awt.*;

public class LTetromino extends CurvedTetromino{

    LTetromino(int width, int height, Color color) {
        super(width, height, color);
    }

    @Override
    public void initTiles() {
        this.tetrominoTiles = new Color[][]{
                {null, null, null, null},
                {null, color, color, color},
                {null, color, null, null},
                {null, null, null, null}
        };
    }
}
