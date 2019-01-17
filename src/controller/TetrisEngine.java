package controller;

import model.TetrisGrid;
import view.TetrisView;


public class TetrisEngine implements Runnable {
    private static final long delta = 500L;
    private TetrisGrid model;
    private TetrisView view;

    public TetrisEngine(TetrisGrid model, TetrisView view) {
        this.model = model;
        this.view = view;
        // register TetrisEngine as controller of the view in order to
        // get user input from view
        this.view.registerController(this);
    }

    public void run() {
        //TODO run method in TetrisEngine
        boolean canMoveDown;
        boolean spawned = this.model.spawnTetromino();
        view.updateGrid(model.getGridWithTetromino());
        long cycleStartTime = System.currentTimeMillis();
        while (spawned) {
            if ((System.currentTimeMillis() - cycleStartTime) >= delta) {
                if (this.model.hasTetromino()) {
                    canMoveDown = this.model.moveTetrominoDown();
                    if (!canMoveDown) {
                        this.model.placeTetromino();
                        this.model.handleAllFilledRows();
                    }
                }
                else {
                    spawned = this.model.spawnTetromino();
                }
                view.updateGrid(model.getGridWithTetromino());
                cycleStartTime = System.currentTimeMillis();
            }
        }
        System.out.println("GAME OVER!");
    }

    public void userActionHandler(String command) {
        switch (command) {
            case "down":
                this.model.dropTetromino();
                this.model.handleAllFilledRows();
                break;
            case "left":
                this.model.moveTetrominoLeft();
                break;
            case "right":
                this.model.moveTetrominoRight();
                break;
            case "rotate":
                this.model.rotateTetromino();
                break;
            default:
                return;
        }
        this.view.updateGrid(this.model.getGridWithTetromino());
    }
}
