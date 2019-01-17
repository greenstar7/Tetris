import controller.TetrisEngine;
import model.TetrisGrid;
import view.TetrisView;
import javax.swing.*;

public class TetrisGame implements  Runnable{
    private TetrisEngine controller;
    private TetrisGrid model;
    private TetrisView view;
    private JFrame jFrame;

    TetrisGame() {
            view = new TetrisView(TetrisGrid.STANDARD_X_SIZE, TetrisGrid.STANDARD_Y_SIZE);
            model = new TetrisGrid();
            controller = new TetrisEngine(model, view);
            jFrame = new JFrame();
            initJFrame();
    }

    private void initJFrame() {
        jFrame.add(view);
        jFrame.setTitle("Tetris");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(203, 430);
    }

    public void run() {
        jFrame.setVisible(true);
        new Thread(controller).start();
    }

    public static void main(String[] argv) {
        SwingUtilities.invokeLater(new TetrisGame());
    }
}