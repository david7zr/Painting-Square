import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Player player;
    private ArrayList<Ghost> ghosts;
    private Tile[][] tiles;
    private boolean gameOver;

    private static final int TILE_SIZE = 20;
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;

    public GamePanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this); // Add key listener

        initializeGame();

        timer = new Timer(30, this); // Reduce timer delay for faster updates
        timer.start();
    }

    private void initializeGame() {
        player = new Player(TILE_SIZE, TILE_SIZE); // Start at the first tile
        ghosts = new ArrayList<>();
        ghosts.add(new Ghost(100, 100, Color.RED));
        ghosts.add(new Ghost(200, 200, Color.GREEN)); // Add a second ghost

        int rows = PANEL_HEIGHT / TILE_SIZE;
        int cols = PANEL_WIDTH / TILE_SIZE;
        tiles = new Tile[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                tiles[y][x] = new Tile(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE);
            }
        }

        gameOver = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    private void update() {
        if (!gameOver) {
            player.move(tiles, getWidth(), getHeight()); // Pass the 'tiles' array to the move method
            for (Ghost ghost : ghosts) {
                ghost.move(getWidth(), getHeight(), getFilledPixels());
            }
            checkCollisions();
            fillEnclosedArea();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                tile.draw(g);
            }
        }
        player.draw(g);
        for (Ghost ghost : ghosts) {
            ghost.draw(g);
        }

        // Draw progress indicator
        int filledTiles = 0;
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile.isFilled()) {
                    filledTiles++;
                }
            }
        }
        double completionPercentage = (double) filledTiles / (tiles.length * tiles[0].length) * 100;

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Progress: " + String.format("%.2f", completionPercentage) + "%", getWidth() / 2 - 60, 20);

        // Draw Game Over message
        if (gameOver) {
            g.setColor(Color.PINK);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over", getWidth() / 2 - 150, getHeight() / 2);
        }
    }

    private void checkCollisions() {
        for (Ghost ghost : ghosts) {
            if (player.getBounds().intersects(ghost.getBounds())) {
                gameOver = true;
            }
        }
    }

    private boolean[][] getFilledPixels() {
        boolean[][] filledPixels = new boolean[tiles.length][tiles[0].length];
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {
                filledPixels[y][x] = tiles[y][x].isFilled();
            }
        }
        return filledPixels;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT:
                player.setDirection(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                player.setDirection(1, 0);
                break;
            case KeyEvent.VK_UP:
                player.setDirection(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                player.setDirection(0, 1);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                player.setDirection(0, 0);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    private void fillEnclosedArea() {
        boolean[][] filledPixels = getFilledPixels();

        // Flood fill starting from each unfilled border pixel to mark all reachable pixels
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {
                if (!filledPixels[y][x] && isBorderPixel(x, y)) {
                    floodFill(x, y, filledPixels);
                }
            }
        }

        // Fill all unmarked unfilled pixels
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {
                if (!filledPixels[y][x] && !isGhostInTile(x, y)) {
                    tiles[y][x].fill();
                }
            }
        }
    }

    private boolean isBorderPixel(int x, int y) {
        return x == 0 || x == tiles[0].length - 1 || y == 0 || y == tiles.length - 1;
    }

    private void floodFill(int x, int y, boolean[][] filledPixels) {
        if (x < 0 || x >= tiles[0].length || y < 0 || y >= tiles.length || filledPixels[y][x]) {
            return;
        }
        filledPixels[y][x] = true;
        floodFill(x + 1, y, filledPixels);
        floodFill(x - 1, y, filledPixels);
        floodFill(x, y + 1, filledPixels);
        floodFill(x, y - 1, filledPixels);
    }

    private boolean isGhostInTile(int tileX, int tileY) {
        for (Ghost ghost : ghosts) {
            int ghostTileX = ghost.getX() / TILE_SIZE;
            int ghostTileY = ghost.getY() / TILE_SIZE;
            if (ghostTileX == tileX && ghostTileY == tileY) {
                return true;
            }
        }
        return false;
    }
}
