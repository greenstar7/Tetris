package view;

import controller.TetrisEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TetrisView extends JPanel {
    public static final int tileSize = 20;
    private TetrisEngine controller;
    private Color[][] grid;
    private int width;
    private int height;

    public TetrisView(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Color[height][width];
        addKeyBindings();
    }

    public void registerController(TetrisEngine controller) {
        this.controller = controller;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g;

        for (int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                g2d.setPaint(Color.LIGHT_GRAY);
                g2d.drawRect(col*tileSize, row*tileSize, tileSize, tileSize);
                if (grid[row][col] != null) {
                    g2d.setPaint(grid[row][col]);
                }
                else {
                    g2d.setColor(Color.BLACK);
                }
                g2d.fillRect(col*tileSize+1, row*tileSize+1, tileSize-1, tileSize-1);
            }
        }
    }

    public void updateGrid(Color[][] grid) {
        this.grid = grid.clone();
        this.repaint();
    }

    private final Action sendDropAction = new AbstractAction() {
        public void actionPerformed(ActionEvent event) {
            controller.userActionHandler("down");
        }
    };

    private final Action sendLeftAction = new AbstractAction() {
        public void actionPerformed(ActionEvent event) {
            controller.userActionHandler("left");
        }
    };

    private final Action sendRightAction = new AbstractAction() {
        public void actionPerformed(ActionEvent event) {
            controller.userActionHandler("right");
        }
    };

    private final Action sendRotateAction = new AbstractAction() {
        public void actionPerformed(ActionEvent event) {
            controller.userActionHandler("rotate");
        }
    };
    private void addKeyBindings() {
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "drop down");
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "drop down");
        this.getActionMap().put("drop down", sendDropAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "move left");
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "move left");
        this.getActionMap().put("move left", sendLeftAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("D"),"move right" );
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),"move right" );
        this.getActionMap().put("move right", sendRightAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"rotate" );
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"rotate" );
        this.getActionMap().put("rotate", sendRotateAction);
    }
}
