import java.awt.*;

public class Tile {
    private int x, y, size;
    private boolean filled;

    public Tile(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.filled = false;
    }

    public void fill() {
        this.filled = true;
    }

    public boolean isFilled() {
        return filled;
    }

    public void draw(Graphics g) {
        if (filled) {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, size, size);
        } else {
            g.setColor(Color.GRAY);
            g.drawRect(x, y, size, size);
        }
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
}
