import controller.TetrisEngine;
import model.TetrisGrid;
import view.TetrisView;
import javax.swing.*;
import java.awt.*;

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

    private void initMVC() {
        view = new TetrisView(width, height, TetrisGrid.STANDARD_WIDTH, TetrisGrid.STANDARD_HEIGHT);
        model = new TetrisGrid();
        controller = new TetrisEngine(model, view);
    }
    private void initJFrame() {
        jFrame = new JFrame();
        view.setPreferredSize(new Dimension(width+1, height+1));
        jFrame.add(view);
        jFrame.pack();
        jFrame.setTitle("Tetris");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void run() {
        jFrame.setVisible(true);
        new Thread(controller).start();
    }

    public static void main(String[] argv) {
        SwingUtilities.invokeLater(new TetrisGame(300, 600));
    }
}