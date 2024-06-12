import java.awt.*;
import java.util.Random;

public class Ghost {
    private int x, y;
    private int size;
    private int speed;
    private Color color;
    private int dx, dy; // Direction

    private static final int TILE_SIZE = 20;
    private static final int SPEED = TILE_SIZE / 2; // Adjust speed for faster movement

    public Ghost(int startX, int startY, Color color) {
        this.x = startX;
        this.y = startY;
        this.size = TILE_SIZE;
        this.color = color;
        this.speed = SPEED;

        Random rand = new Random();
        this.dx = rand.nextBoolean() ? 1 : -1;
        this.dy = rand.nextBoolean() ? 1 : -1;
    }

    public void move(int panelWidth, int panelHeight, boolean[][] filledPixels) {
        int nextX = x + dx * speed;
        int nextY = y + dy * speed;

        // Bounce off walls
        if (nextX < 0 || nextX >= panelWidth - size) {
            dx = -dx;
        } else {
            x = nextX;
        }

        if (nextY < 0 || nextY >= panelHeight - size) {
            dy = -dy;
        } else {
            y = nextY;
        }

        // Bounce off filled pixels
        int tileX = x / TILE_SIZE;
        int tileY = y / TILE_SIZE;
        if (filledPixels[tileY][tileX]) {
            dx = -dx;
            dy = -dy;
        }
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, size, size);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}
