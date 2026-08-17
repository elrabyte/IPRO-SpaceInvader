package interfaces;
import java.awt.Graphics;
import java.util.List;

import models.Projectile;

public interface IEnemy {
    void update(int playerX, int playerY, double speedMultiplier, List<Projectile> projectiles);
    void render(Graphics g);
    int getX();
    int getY();
    int getHp();
    void takeDamage(int amount);
    boolean isAlive();
    java.awt.Rectangle getHitBox();
}
