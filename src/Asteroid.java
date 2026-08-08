import java.awt.*;
import java.util.List;

public class Asteroid implements Enemy {
    private double x, y;
    private int hp = 3;
    private static final int RADIUS = 18;
    private double vx, vy;

    public Asteroid(int startX, int startY, int playerX, int playerY) {
        this.x = startX;
        this.y = startY;
        double dx = playerX - startX;
        double dy = playerY - startY;
        double len = Math.sqrt(dx * dx + dy * dy);
        if (len == 0) len = 1;
        vx = dx / len;
        vy = dy / len;
    }

    @Override
    public void update(int playerX, int playerY, double speedMultiplier, List<Projectile> projectiles) {
        x += vx * 2 * speedMultiplier;
        y += vy * 2 * speedMultiplier;
    }

    @Override
    public void draw(Graphics g) {
        int hp3Color = Math.max(0, Math.min(255, hp * 60 + 60));
        g.setColor(new Color(hp3Color, hp3Color / 2, 30));
        g.fillOval((int) x - RADIUS, (int) y - RADIUS, RADIUS * 2, RADIUS * 2);
        g.setColor(Color.GRAY);
        g.drawOval((int) x - RADIUS, (int) y - RADIUS, RADIUS * 2, RADIUS * 2);
    }

    @Override public int getX() { return (int) x; }
    @Override public int getY() { return (int) y; }
    @Override public int getHp() { return hp; }
    @Override public void takeDamage(int amount) { hp -= amount; }
    @Override public boolean isAlive() { return hp > 0; }
    @Override public Rectangle getBounds() { return new Rectangle((int) x - RADIUS, (int) y - RADIUS, RADIUS * 2, RADIUS * 2); }
}
