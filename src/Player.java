import java.awt.*;

public class Player {
    private int x, y;
    private int size;
    private Color color;
    private int dx, dy; //Velocity

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.size = 15; //Example size
        this.color = Color.PINK; //Player color
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, size, size);
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