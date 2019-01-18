package view;

import controller.TetrisEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class TetrisView extends JPanel {
    private int tileSize;
    private TetrisEngine controller;
    private Color[][] grid;
    private int gridWidth;
    private int gridHeight;
    private int width;
    private int height;
    private boolean gameOver;
    private long score;

    public TetrisView(int width, int height, int gridWidth, int gridHeight) {
        this.width = width;
        this.height = height;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        initTileSize();
        addKeyBindings();
        initView();
    }

    private void initTileSize() {
        int xSize = width/gridWidth;
        int ySize = height/gridHeight;
        if (xSize < ySize) {
            tileSize = xSize;
        }
        else {
            tileSize = ySize;
        }
    }
    
    private void initView() {
        this.grid = new Color[gridHeight][gridWidth];
        gameOver = false;
        score = 0;
    }

    public void registerController(TetrisEngine controller) {
        this.controller = controller;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g;

        if (gameOver) {
            drawFinalScreen(g2d);
        }
        else {
            drawGameGrid(g2d);
            // TODO Score panel
            // TODO Next Tetromino panel
        }
    }

    private void drawGameGrid(Graphics2D g2d) {
        for (int row = 0; row < gridHeight; row++) {
            for (int col = 0; col < gridWidth; col++) {
                if (grid[row][col] != null) {
                    g2d.setPaint(grid[row][col]);
                } else {
                    g2d.setColor(Color.BLACK);
                }
                g2d.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
                g2d.setPaint(Color.LIGHT_GRAY);
                g2d.drawRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }
    }

    private void drawFinalScreen(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fill(this.getBounds());
        Font currentFont = g2d.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.FAMILY, currentFont.getFamily());
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_SEMIBOLD);
        attributes.put(TextAttribute.SIZE, (currentFont.getSize() * tileSize/10));
        g2d.setFont(Font.getFont(attributes));
        g2d.setColor(Color.RED);
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        g2d.drawString("GAME OVER!", (width - metrics.stringWidth("GAME OVER!")) / 2, height/2);
        // TODO score string
    }

    public void updateGrid(Color[][] grid) {
        this.grid = grid.clone();
        this.repaint();
    }

    public void updateScore(long score) {
        this.score = score;
    }

    public void gameOver() {
        gameOver = true;
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

    /**
     * Adds key bindings for commands to send to the controller.
     */
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
