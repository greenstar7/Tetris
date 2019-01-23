package controller;

import model.TetrisGrid;
import view.TetrisView;

public class TetrisEngine implements Runnable {
    private TetrisGrid model;
    private TetrisView view;
    private int level;
    private long delta;
    private long score;
    private int freeFallIterations;

    public TetrisEngine(TetrisGrid model, TetrisView view) {
        this.model = model;
        this.view = view;
        // register TetrisEngine as controller of the view in order to
        // get user input from view
        this.view.registerController(this);
        level = 1;
        delta = (11 - level) * 50;
        score = 0;
        freeFallIterations = 0;
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
        view.updateGrid(model.getGridWithTetromino());
        view.updateScore(score);
        long cycleStartTime = System.currentTimeMillis();
        while (gameContinues) {
            if ((System.currentTimeMillis() - cycleStartTime) >= delta) {
                if (this.model.hasTetromino()) {
                    canMoveDown = this.model.moveTetrominoDown();
                    if (!canMoveDown) {
                        this.placeTetromino();
                        freeFallIterations = 0;
                    } else {
                        freeFallIterations += 1;
                    }
                }
                else {
                    gameContinues = this.model.spawnTetromino();
                }
                this.updateView();
                cycleStartTime = System.currentTimeMillis();
            }
            else {
                userActionHandler();
            }
        }
        view.gameOver();
        System.out.println("GAME OVER!");
        System.out.println("Score: " + this.score);
    }

    private void updateView() {
        this.view.updateGrid(this.model.getGridWithTetromino());
    }

    private void dropTetromino() {
        this.model.dropTetromino();
        placeTetromino();
    }

    private void placeTetromino() {
        this.model.placeTetromino();
        this.model.handleAllFilledRows();
        this.updateView();
        this.updateScore();
    }

    private void moveTetrominoLeft() {
        this.model.moveTetrominoLeft();
        this.updateView();
    }

    private void moveTetrominoRight() {
        this.model.moveTetrominoRight();
        this.updateView();
    }

    private void rotateTetromino() {
        this.model.rotateTetromino();
        this.updateView();
    }

    private void updateScore() {
        this.score += ( (21 + 3*level) - freeFallIterations);
        freeFallIterations = 0;
        this.view.updateScore(score);
    }
    /**
     * Simple action handler which receives user commands from view.
     */
    public void userActionHandler() {
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
