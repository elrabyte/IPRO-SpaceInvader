import java.awt.*;
import java.util.List;
import java.util.Random;

public class EnemyShip implements Enemy {
    private int x, y;
    private int hp = 1;
    private static final int SIZE = 16;
    private static final int shootCoolDown = 60;
    private int currentShootCooldown = shootCoolDown;

    public EnemyShip(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void update(int playerX, int playerY, double speedMultiplier, List<Projectile> projectiles) {
        y += (int)(2 * speedMultiplier);
        currentShootCooldown--;
        if (currentShootCooldown <= 0) {
            projectiles.add(new Projectile(x, y + SIZE, 5, false));
            currentShootCooldown = shootCoolDown;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        int[] xp = {x, x - SIZE, x + SIZE};
        int[] yp = {y + SIZE, y - SIZE, y - SIZE};
        g.fillPolygon(xp, yp, 3);
    }

    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public int getHp() { return hp; }
    @Override public void takeDamage(int amount) { hp -= amount; }
    @Override public boolean isAlive() { return hp > 0; }
    @Override public Rectangle getBounds() { return new Rectangle(x - SIZE, y - SIZE, SIZE * 2, SIZE * 2); }
}
