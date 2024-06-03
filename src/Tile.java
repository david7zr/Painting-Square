import java.awt.*;

public class Tile {
    private int x, y;
    private int size;
    private boolean filled;

    public Tile(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.filled = false;
    }

    public void fill() {
        filled = true;
    }

    public boolean isFilled() {
        return filled;
    }

    public void draw(Graphics g) {
        if (filled) {
            g.setColor(new Color(0,158,133)); // Filled color
            g.fillRect(x, y, size, size);
        } else {
            g.setColor(Color.BLACK); // Unfilled color
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
