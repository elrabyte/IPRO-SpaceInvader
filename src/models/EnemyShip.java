package models;
import java.awt.*;
import java.util.List;

import interfaces.IEnemy;

public class EnemyShip implements IEnemy {
    private int x, y;
    private static final int maxHp = 1;
    private int hp = maxHp;
    private static final int SIZE = 16;
    private static final int shootCoolDown = 80;
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
    public void render(Graphics g) {
        g.setColor(Color.RED);
        int bottomX = x;
        int topLeftX = x - SIZE;
        int topRightX = x + SIZE;
        int bottomY = y + SIZE;
        int topY = y - SIZE;
        
        int[] xCoords = {bottomX, topLeftX, topRightX};
        int[] yCoords = {bottomY, topY, topY};
        g.fillPolygon(xCoords, yCoords, 3);
    }

    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public int getHp() { return hp; }
    @Override public void takeDamage(int amount) { hp -= amount; }
    @Override public boolean isAlive() { return hp > 0; }
    @Override public Rectangle getHitBox() { return new Rectangle(x - SIZE, y - SIZE, SIZE * 2, SIZE * 2); }
    @Override public int getMaxHp() { return maxHp; }
    @Override public Color getColor() { return Color.RED; }
}
