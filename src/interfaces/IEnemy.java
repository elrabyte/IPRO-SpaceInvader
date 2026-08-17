package interfaces;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import models.Projectile;

public interface IEnemy {
    void applySpeedMultiplier(double speedMultiplier);
    void update(int playerX, int playerY, List<Projectile> projectiles);
    void render(Graphics g);
    int getX();
    int getY();
    int getHp();
    int getMaxHp();
    void takeDamage(int amount);
    boolean isAlive();
    java.awt.Rectangle getHitBox();
    Color getColor();
}
