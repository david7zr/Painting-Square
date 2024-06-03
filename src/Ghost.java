import java.awt.*;
import java.util.Random;

public class Ghost {
    private int x, y;
    private int size;
    private Color color;
    private int dx, dy;
    private Random random;

    public Ghost(int startX, int startY, Color color) {
        this.x = startX;
        this.y = startY;
        this.size = 17; //Size of enemies
        this.color = color;
        this.random = new Random();
        setRandomDirection();
    }

    public void move() {
        x += dx;
        y += dy;
        if (x < 0 || x > 780) dx = -dx;
        if (y < 0 || y > 580) dy = -dy;
    }

    private void setRandomDirection() {
        dx = random.nextInt(10) + 5; //Random speed between 10 and 5
        dy = random.nextInt(10) + 5; //Random speed between 10 and 5
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
