package controller;

import model.TetrisGrid;
import view.TetrisView;
import java.util.concurrent.locks.ReentrantLock;

public class TetrisEngine implements Runnable {
    private TetrisGrid model;
    private TetrisView view;
    private int level;
    private long delta;
    private long score;
    private int freeFallIterations;
    private ReentrantLock movementLock = new ReentrantLock();

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
                movementLock.lock();
                try {
                    if (this.model.hasTetromino()) {
                        canMoveDown = this.model.moveTetrominoDown();
                        if (!canMoveDown) {
                            this.placeTetromino();
                            this.model.handleAllFilledRows();
                        } else {
                            freeFallIterations += 1;
                        }
                    } else {
                        gameContinues = this.model.spawnTetromino();
                    }
                    this.updateView();
                }
                finally {
                    movementLock.unlock();
                }
                cycleStartTime = System.currentTimeMillis();
            }
        }
        view.gameOver();
        System.out.println("GAME OVER!");
        System.out.println("Score: " + this.score);
    }

    private void updateView() {
        this.view.updateGrid(this.model.getGridWithTetromino());
        view.updateScore(score);
    }

    private void dropTetromino() {
        this.model.dropTetromino();
        placeTetromino();
    }

    private void placeTetromino() {
        this.model.placeTetromino();
        this.updateScore();
    }

    private void updateScore() {
        this.score += ( (21 + 3*level) - freeFallIterations);
        freeFallIterations = 0;
    }
    /**
     * Simple action handler which receives user commands from view.
     * @param command received string command from the view.
     */
    public void userActionHandler(String command) {
        boolean gotLock = movementLock.tryLock();
        System.out.println("Got lock: " + gotLock);
        if (gotLock) {
            try {
                if (this.model.hasTetromino()) {
                    boolean actionPerformed = false;
                    switch (command) {
                        case "down":
                            this.dropTetromino();
                            this.model.handleAllFilledRows();
                            break;
                        case "left":
                            actionPerformed = this.model.moveTetrominoLeft();
                            break;
                        case "right":
                            actionPerformed = this.model.moveTetrominoRight();
                            break;
                        case "rotate":
                            actionPerformed = this.model.rotateTetromino();
                            break;
                        default:
                            return;
                    }
                    if (actionPerformed) {
                        this.updateView();
                    }
                }
            }
            finally {
                movementLock.unlock();
            }
        }
    }
}
