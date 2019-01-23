package controller;

import model.TetrisGrid;
import view.TetrisView;

/**
 * @author Artemii Hrynevych
 * Tetris engine class which is the controller for tetris MVC.
 */
public class TetrisEngine implements Runnable {
    private TetrisGrid model;
    private TetrisView view;
    private int initialLevel;
    private int rowsFilled;
    private long score;
    private int freeFallIterations;

    public TetrisEngine(TetrisGrid model, TetrisView view) {
        this(model, view, 1);
    }

    public TetrisEngine(TetrisGrid model, TetrisView view, int initialLevel) {
        this.model = model;
        this.view = view;
        score = 0;
        freeFallIterations = 0;
        rowsFilled = 0;
        this.initialLevel = initialLevel;
    }

    private long getDelta() {
        return (11 - this.getLevel()) * 50;
    }
    
    private int getLevel() {
        return Math.max(this.initialLevel, getEarnedLevel());
    }

    private int getEarnedLevel() {
        if (rowsFilled == 0)
        {
            return  1;
        }
        else if ((rowsFilled > 0) && (rowsFilled <= 90))
        {
            return  1 + ((rowsFilled - 1) / 10);
        }
        else
        {
            return  10;
        }
    }

    /**
     * Overrides method run, which contains main game loop
     * <p>
     *     Upgrades the grid for the viewer.
     *     Moves Tetromino. If any.
     *     If none - creates new and updates score.
     * </p>
     */
    @Override
    public void run() {
        boolean canMoveDown;
        boolean gameContinues = this.model.spawnTetromino();
        this.updateViewGrid();
        this.updateViewNextTetromino();
        long cycleStartTime = System.currentTimeMillis();
        while (gameContinues) {
            if ((System.currentTimeMillis() - cycleStartTime) >= getDelta()) {
                if (this.model.hasTetromino()) {
                    canMoveDown = this.model.moveTetrominoDown();
                    if (!canMoveDown) {
                        this.placeTetromino();
                    } else {
                        freeFallIterations += 1;
                    }
                }
                else {
                    gameContinues = this.model.spawnTetromino();
                    updateViewNextTetromino();
                }
                this.updateViewGrid();
                cycleStartTime = System.currentTimeMillis();
            }
            else {
                userActionHandler();
            }
        }
        view.gameOver();
    }

    /**
     * Method to update grid stored in view.
     */
    private void updateViewGrid() {
        this.view.updateGrid(this.model.getGridWithTetromino());
    }

    /**
     * Method to upgrade next tetromino stored in view.
     */
    private void updateViewNextTetromino() {
        this.view.updateNextTetrominoGrid(this.model.getNextTetrominoGrid());
    }

    /**
     * Method to tell model to drop the tetromino and place tetromino if dropped.
     */
    private void dropTetromino() {
        if (this.model.dropTetromino()) {
            placeTetromino();
        }
    }

    /**
     * Method to write tetromino on main grid and update the view.
     */
    private void placeTetromino() {
        this.model.placeTetromino();
        this.rowsFilled += this.model.handleAllFilledRows();
        this.updateScore();
        this.updateViewGrid();
    }

    /**
     * Method to tell model to move tetromino left and update view if moved.
     */
    private void moveTetrominoLeft() {
        if (this.model.moveTetrominoLeft()) {
            this.updateViewGrid();
        }
    }

    /**
     * Method to tell model to move tetromino right and update view if moved.
     */
    private void moveTetrominoRight() {
        if (this.model.moveTetrominoRight()) {
            this.updateViewGrid();
        }
    }

    /**
     * Method to tell model to rotate tetromino and update view if rotated.
     */
    private void rotateTetromino() {
        if (this.model.rotateTetromino()) {
            this.updateViewGrid();
        }
    }

    /**
     * Method to update score on view.
     */
    private void updateScore() {
        this.score += ( (21 + 3* initialLevel) - freeFallIterations);
        freeFallIterations = 0;
        this.view.updateScore(score);
    }

    /**
     * Simple action handler which receives user commands from view.
     */
    private void userActionHandler() {
        String command = this.view.getLastCommand();
        switch (command) {
            case "drop":
                this.dropTetromino();
                break;
            case "left":
                this.moveTetrominoLeft();
                break;
            case "right":
                this.moveTetrominoRight();
                break;
            case "rotate":
                this.rotateTetromino();
                break;
            default:
                break;
        }
    }
}
