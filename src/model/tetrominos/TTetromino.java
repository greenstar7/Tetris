package model.tetrominos;

import java.awt.*;

public class TTetromino extends CurvedTetromino{

    TTetromino(int width, int height, Color color) {
        super(width, height, color);
    }

    @Override
    public void initTiles() {
        this.tetrominoTiles = new Color[][]{
                {null, null, null, null},
                {null, color, color, color},
                {null, null, color, null},
                {null, null, null, null}
        };
    }
}