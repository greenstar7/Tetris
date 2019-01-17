package model.tetrominos;

import java.awt.*;

public class ITetromino extends Tetromino {
    ITetromino(int width, int height, Color color) {
        super(width, height, color);
    }

    @Override
    public void initTiles() {
        this.tetrominoTiles = new Color[][]{
                {null, null, null, null},
                {color, color, color, color},
                {null, null, null, null},
                {null, null, null, null}
        };
    }

    @Override
    public boolean rotateSelf(Color[][] grid) {
        Color[][] oldTetrominoTiles = this.tetrominoTiles.clone();
        if (oldTetrominoTiles[1][0] == null) {
            this.tetrominoTiles = new Color[][]{
                    {null, null, null, null},
                    {color, color, color, color},
                    {null, null, null, null},
                    {null, null, null, null}
            };
        }
        else {
            this.tetrominoTiles = new Color[][]{
                    {null, null, color, null},
                    {null, null, color, null},
                    {null, null, color, null},
                    {null, null, color, null}
            };
        }
        if (isValidPos(grid)) {
            return true;
        }
        else {
            this.tetrominoTiles = oldTetrominoTiles;
            return false;
        }
    }
}
