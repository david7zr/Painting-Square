import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Player player;
    private List<Ghost> ghosts;
    private List<Tile> tiles;

    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        timer = new Timer(16, this);
        timer.start();

        player = new Player(400, 300); // Center of the screen
        ghosts = new ArrayList<>();
        ghosts.add(new Ghost(100, 50, Color.ORANGE));
        ghosts.add(new Ghost(700, 50, Color.BLUE));
        ghosts.add(new Ghost(400, 50, Color.RED));
        ghosts.add(new Ghost(400, 50, Color.CYAN));
        ghosts.add(new Ghost(400, 50, Color.MAGENTA));


        tiles = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 30; j++) {
                tiles.add(new Tile(i * 20, j * 20, 20));
            }
        }
    }

    private void update() {
        player.move();
        for (Ghost ghost : ghosts) {
            ghost.move();
        }
        checkCollisions();
        checkWinCondition();
    }

    private void checkCollisions() {
        //Boundary collision
        if (player.getX() < 0) player.setDirection(0, 0);
        if (player.getX() > getWidth() - player.getSize()) player.setDirection(0, 0);
        if (player.getY() < 0) player.setDirection(0, 0);
        if (player.getY() > getHeight() - player.getSize()) player.setDirection(0, 0);

        //Tile collision and fill logic
        for (Tile tile : tiles) {
            if (player.getX() < tile.getX() + tile.getSize() &&
                    player.getX() + player.getSize() > tile.getX() &&
                    player.getY() < tile.getY() + tile.getSize() &&
                    player.getY() + player.getSize() > tile.getY() && !tile.isFilled()) {
                tile.fill();
            }
        }

        //Ghost collision logic
        for (Ghost ghost : ghosts) {
            if (player.getX() < ghost.getX() + ghost.getSize() &&
                    player.getX() + player.getSize() > ghost.getX() &&
                    player.getY() < ghost.getY() + ghost.getSize() &&
                    player.getY() + player.getSize() > ghost.getY()) {
                // TODO: Handle collision with ghosts (e.g., end game, lose a life)
                System.out.println("Collision with ghost! Game over.");
                timer.stop();
            }
        }
    }

    private void checkWinCondition() {
        int filledTiles = 0;
        for (Tile tile : tiles) {
            if (tile.isFilled()) {
                filledTiles++;
            }
        }
        if (filledTiles > (tiles.size() * 0.8)) { // Example win condition: 80% tiles filled
            System.out.println("You win!");
            timer.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Tile tile : tiles) {
            tile.draw(g);
        }
        player.draw(g);
        for (Ghost ghost : ghosts) {
            ghost.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            player.setDirection(-5, 0); // Change -5 to your desired speed for left movement
        } else if (key == KeyEvent.VK_RIGHT) {
            player.setDirection(5, 0); // Change 5 to your desired speed for right movement
        } else if (key == KeyEvent.VK_UP) {
            player.setDirection(0, -5); // Change -5 to your desired speed for up movement
        } else if (key == KeyEvent.VK_DOWN) {
            player.setDirection(0, 5); // Change 5 to your desired speed for down movement
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.setDirection(0, 0);
    }
}