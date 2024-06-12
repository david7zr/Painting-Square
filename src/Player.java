import java.awt.*;

public class Player {
    private int x, y;
    private int size;
    private int speed;
    private int dx, dy; // Direction

    private static final int TILE_SIZE = 20;

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.size = TILE_SIZE;
        this.speed = TILE_SIZE; // Move one tile at a time
        this.dx = 0;
        this.dy = 0;
    }

    public void move(Tile[][] tiles, int panelWidth, int panelHeight) {
        // Fill the current tile before moving
        int currentTileX = x / TILE_SIZE;
        int currentTileY = y / TILE_SIZE;
        tiles[currentTileY][currentTileX].fill();

        int nextX = x + dx * speed;
        int nextY = y + dy * speed;

        // Calculate the position of the player after movement
        int nextTileX = nextX / TILE_SIZE;
        int nextTileY = nextY / TILE_SIZE;

        // Check if the next position is within bounds
        if (nextTileX >= 0 && nextTileX < panelWidth / TILE_SIZE && nextTileY >= 0 && nextTileY < panelHeight / TILE_SIZE) {
            // Move the player to the next tile
            x = nextTileX * TILE_SIZE;
            y = nextTileY * TILE_SIZE;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, size, size);
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}
