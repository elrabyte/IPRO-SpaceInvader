package models;
import java.awt.*;
import java.util.List;

import helpers.ColorHelper;
import interfaces.IEnemy;

public class Asteroid implements IEnemy {
    private double x, y;
    private static int maxHp = 2;
    private int hp = maxHp;
    private static final int RADIUS = 18;
    private double vx, vy;

    public Asteroid(int startX, int startY, int playerX, int playerY) {
        this.x = startX;
        this.y = startY;
        double distanceX = playerX - startX;
        double distanceY = playerY - startY;
        double distanceToPlayer = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        if (distanceToPlayer == 0) distanceToPlayer = 1; // avoid division by zero
        vx = distanceX / distanceToPlayer;
        vy = distanceY / distanceToPlayer;
    }

    @Override
    public void applySpeedMultiplier(double speedMultiplier) {
        x += vx * 2 * speedMultiplier;
        y += vy * 2 * speedMultiplier;
    }

    @Override
    public void update(int playerX, int playerY, List<Projectile> projectiles) {
    }

    @Override
    public void render(Graphics g) {
        var colorHelper = new ColorHelper();
        g.setColor(colorHelper.getColorForHp(getColor(), hp, maxHp));
        g.fillOval((int) x - RADIUS, (int) y - RADIUS, RADIUS * 2, RADIUS * 2);
        g.setColor(Color.GRAY);
        g.drawOval((int) x - RADIUS, (int) y - RADIUS, RADIUS * 2, RADIUS * 2);
    }

    @Override public int getX() { return (int) x; }
    @Override public int getY() { return (int) y; }
    @Override public int getHp() { return hp; }
    @Override public int getMaxHp() { return maxHp; }
    @Override public void takeDamage(int amount) { hp -= amount; }
    @Override public boolean isAlive() { return hp > 0; }
    @Override public Rectangle getHitBox() { return new Rectangle((int) x - RADIUS, (int) y - RADIUS, RADIUS * 2, RADIUS * 2); }
    @Override public Color getColor() { return new Color(255, 100, 0); }
}
