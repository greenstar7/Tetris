import controller.TetrisEngine;
import model.TetrisGrid;
import view.TetrisView;
import javax.swing.*;

/**
 * @author Artemii Hrynevych
 * Main program to run tetris game.
 */
public class TetrisGame implements  Runnable{
    private TetrisEngine controller;
    private TetrisGrid model;
    private TetrisView view;
    private JFrame jFrame;
    private int width;
    private int height;

    TetrisGame(int width, int height) {
            this.width = width;
            this.height = height;
            initMVC();
            initJFrame();
    }

    /**
     * Initialize model, view and controller.
     */
    private void initMVC() {
        view = new TetrisView(width, height, TetrisGrid.STANDARD_WIDTH, TetrisGrid.STANDARD_HEIGHT);
        model = new TetrisGrid();
        controller = new TetrisEngine(model, view);
    }

    /**
     * Initialize game's window.
     */
    private void initJFrame() {
        jFrame = new JFrame();
        jFrame.add(view);
        jFrame.pack();
        jFrame.setTitle("Tetris");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Runs the game(view) and controller thread.
     */
    public void run() {
        jFrame.setVisible(true);
        new Thread(controller).start();
    }

    /**
     * Main method just to run tetris game with invokeLater.
     */
    public static void main(String[] argv) {
        SwingUtilities.invokeLater(new TetrisGame(400, 600));
    }
}