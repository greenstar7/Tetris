package model.tetrominos;

import java.awt.*;

public abstract class CurvedTetromino extends Tetromino {

    CurvedTetromino(int width, int height, Color color) {
        super(width, height, color);
    }

    @Override
    public boolean rotateSelf(Color[][] grid) {
        Color[][] oldTetrominoTiles = tetrominoTiles.clone();
        tetrominoTiles = new Color[N_TILES][N_TILES];
        int newX, newY;
        for (int row = 0; row < N_TILES-1; row++) {
            newY = row - C_Y;
            for (int col = 1; col < N_TILES; col++) {
                if (oldTetrominoTiles[row][col] != null) {
                    newX = col - C_X;
                    tetrominoTiles[-newX + C_Y][newY + C_X] = oldTetrominoTiles[row][col];
                }
            }
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
