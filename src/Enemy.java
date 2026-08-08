import java.awt.Graphics;
import java.util.List;

public interface Enemy {
    void update(int playerX, int playerY, double speedMultiplier, List<Projectile> projectiles);
    void draw(Graphics g);
    int getX();
    int getY();
    int getHp();
    void takeDamage(int amount);
    boolean isAlive();
    java.awt.Rectangle getBounds();
}
