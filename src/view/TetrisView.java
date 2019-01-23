package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Artemii Hryenvych
 * Tetris view class for tetris MVC.
 */
public class TetrisView extends JPanel {
    private static final double GAME_SCORE_AREA_RATIO = 0.75;
    private int tileSize;
    private int nextTetrominoTileSize;
    private Color[][] grid;
    private Color[][] nextTetrominoGrid;
    private int gridWidth;
    private int gridHeight;
    private int width;
    private int height;
    private int gameWidth;
    private int sideWidth;
    private boolean gameOver;
    private volatile String lastCommand;
    private long score;

    public TetrisView(int width, int height, int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        initDimensions(width, height);
        initTileSize();
        revisitDimensions();
        addKeyBindings();
        initView();
        resetLastCommand();
        this.setPreferredSize(new Dimension(this.width, this.height));
    }

    private void revisitDimensions() {
        this.height = tileSize*gridHeight;
        this.gameWidth = tileSize*gridWidth;
        this.width = gameWidth + sideWidth + 1;
    }

    private void initDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        this.gameWidth = (int) Math.floor(width * GAME_SCORE_AREA_RATIO);
        this.sideWidth = width - this.gameWidth - 1;
    }

    private void initTileSize() {
        int xSize = gameWidth/gridWidth;
        int ySize = height/gridHeight;
        if (xSize < ySize) {
            tileSize = xSize;
        }
        else {
            tileSize = ySize;
        }
        nextTetrominoTileSize = sideWidth/6;
    }

    private void initView() {
        this.grid = new Color[gridHeight][gridWidth];
        this.nextTetrominoGrid = new Color[4][4];
        gameOver = false;
        score = 0;
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
            fillSidePanel(g2d);
            drawScorePanel(g2d);
            drawNextTetrominoPanel(g2d);
        }
    }

    /**
     * Function to draw the grid of the side panel with next tetromino.
     * @param g2d
     */
    private void drawNextTetrominoPanel(Graphics2D g2d) {
        g2d.setColor(new Color(255, 200, 0));
        g2d.drawString(
                "NEXT",
                this.width - this.sideWidth + nextTetrominoTileSize,
                this.height - 6 * nextTetrominoTileSize
        );

        int x, y;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (nextTetrominoGrid[row][col] != null) {
                    g2d.setPaint(nextTetrominoGrid[row][col]);
                } else {
                    g2d.setPaint(Color.BLACK);
                }
                y = this.height - nextTetrominoTileSize - (4-row) * nextTetrominoTileSize;
                x = this.width - this.sideWidth + nextTetrominoTileSize + col * nextTetrominoTileSize;
                g2d.fillRect(x, y, nextTetrominoTileSize, nextTetrominoTileSize);
                g2d.setPaint(Color.LIGHT_GRAY);
                g2d.drawRect(x, y, nextTetrominoTileSize, nextTetrominoTileSize);
            }
        }
    }

    /**
     * Function to fill the side panel with color.
     * @param g2d
     */
    private void fillSidePanel(Graphics2D g2d) {
        g2d.setPaint(new Color(0, 8, 52));
        g2d.fillRect(width-sideWidth, 0, sideWidth, height);
    }

    /**
     * Function to draw the side panel score.
     * @param g2d
     */
    private void drawScorePanel(Graphics2D g2d) {
        g2d.setColor(new Color(255, 233, 0));
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());

        g2d.drawString(
                "SCORE:",
                this.width - this.sideWidth + nextTetrominoTileSize,
                metrics.getHeight()
        );

        String scoreString = Long.toString(this.score);
        g2d.drawString(
                scoreString,
                this.width - this.sideWidth + nextTetrominoTileSize,
                metrics.getHeight() * 2
        );
    }

    /**
     * Function to draw tetris game grid.
     * @param g2d
     */
    private void drawGameGrid(Graphics2D g2d) {
        for (int row = 0; row < gridHeight; row++) {
            for (int col = 0; col < gridWidth; col++) {
                if (grid[row][col] != null) {
                    g2d.setPaint(grid[row][col]);
                } else {
                    g2d.setPaint(Color.BLACK);
                }
                g2d.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
                g2d.setPaint(Color.LIGHT_GRAY);
                g2d.drawRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }
    }

    /**
     * Function to draw GAME OVER sreen.
     * @param g2d
     */
    private void drawFinalScreen(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fill(this.getBounds());

        Font currentFont = g2d.getFont();
        int gameOverFontSize = currentFont.getSize() * width/100;

        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.FAMILY, currentFont.getFamily());
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_SEMIBOLD);
        attributes.put(TextAttribute.SIZE, gameOverFontSize);
        g2d.setFont(Font.getFont(attributes));
        g2d.setColor(Color.RED);

        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        g2d.drawString(
                "GAME OVER!",
                (width - metrics.stringWidth("GAME OVER!")) / 2,
                height/2
        );

        attributes.put(TextAttribute.SIZE, (gameOverFontSize*0.5));
        g2d.setFont(Font.getFont(attributes));
        metrics = g2d.getFontMetrics(g2d.getFont());
        String scoreString = "Score: "+score;
        g2d.drawString(
                scoreString,
                (width - metrics.stringWidth(scoreString)) / 2,
                height/2 + gameOverFontSize
        );
    }

    /**
     * Updating state of the game grid.
     * @param grid new game grid
     */
    public void updateGrid(Color[][] grid) {
        this.grid = grid.clone();
        this.repaint();
    }

    /**
     * Updating state of the next tetromino grid.
     * @param nextTetrominoGrid new new tetromino grid
     */
    public void updateNextTetrominoGrid(Color[][] nextTetrominoGrid) {
        this.nextTetrominoGrid = nextTetrominoGrid.clone();
        this.repaint();
    }

    /**
     * Updating game score
     * @param score updated game score
     */
    public void updateScore(long score) {
        this.score = score;
    }

    /**
     * Function to receive signal from controller that game is over.
     */
    public void gameOver() {
        gameOver = true;
        this.repaint();
    }

    /**
     * Setting the last user command for controller to read it.
     * @param command
     */
    private void setLastCommand(String command) {
        this.lastCommand = command;
    }

    /**
     * Function for controller to get the last user input.
     */
    public String getLastCommand() {
        String command = this.lastCommand;
        this.resetLastCommand();
        return command;
    }

    /**
     * Clearing the last user input command.
     */
    private void resetLastCommand() {
        this.lastCommand = "none";
    }

    private final Action saveDropAction = new AbstractAction() {
        public void actionPerformed(ActionEvent event) {
            setLastCommand("drop");
        }
    };

    private final Action saveLeftAction = new AbstractAction() {
        public void actionPerformed(ActionEvent event) {
            setLastCommand("left");
        }
    };

    private final Action saveRightAction = new AbstractAction() {
        public void actionPerformed(ActionEvent event) {
            setLastCommand("right");
        }
    };

    private final Action saveRotateAction = new AbstractAction() {
        public void actionPerformed(ActionEvent event) {
            setLastCommand("rotate");
        }
    };

    /**
     * Adds key bindings for commands to send to the controller.
     */
    private void addKeyBindings() {
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "drop down");
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "drop down");
        this.getActionMap().put("drop down", saveDropAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "move left");
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "move left");
        this.getActionMap().put("move left", saveLeftAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("D"),"move right" );
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),"move right" );
        this.getActionMap().put("move right", saveRightAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"rotate" );
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"rotate" );
        this.getActionMap().put("rotate", saveRotateAction);
    }
}
