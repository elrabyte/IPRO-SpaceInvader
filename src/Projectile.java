import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Projectile {
    private int x, y;
    private final int speed;
    private final boolean fromPlayer;
    private boolean active = true;
    private static final int W = 4, H = 10;

    public Projectile(int x, int y, int speed, boolean fromPlayer) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.fromPlayer = fromPlayer;
    }

    public void update() {
        y += speed;
        if (y < -H || y > 700) active = false;
    }

    public void draw(Graphics g) {
        g.setColor(fromPlayer ? Color.CYAN : Color.ORANGE);
        g.fillRect(x - W / 2, y, W, H);
    }

    public boolean isFromPlayer() { return fromPlayer; }
    public boolean isActive() { return active; }
    public void deactivate() { active = false; }
    public Rectangle getBounds() { return new Rectangle(x - W / 2, y, W, H); }
}
