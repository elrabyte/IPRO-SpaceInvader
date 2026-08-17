package models;
import java.awt.*;
import java.util.List;

import interfaces.IPlayerEntity;

public class PlayerShip implements IPlayerEntity {
    private int x, y;
    private static final int maxHp = 3;
    private int hp = maxHp;
    private static final int SIZE = 20;
    private static final int PANEL_W = 800;
    private static final int PANEL_H = 600;
    private static final int BOTTOM_THIRD_TOP = PANEL_H * 2 / 3;
    private static final int BaseSpeed = 4;
    private int speed = BaseSpeed;
    private int currentShootCooldown = 0;
    private static final int shootCooldown = 20; // ticks

    public PlayerShip() {
        x = PANEL_W / 2;
        y = PANEL_H - 60;
    }

    @Override
    public void move(int dx, int dy) {
        x = Math.max(SIZE, Math.min(PANEL_W - SIZE, x + dx * speed));
        y = Math.max(BOTTOM_THIRD_TOP + SIZE, Math.min(PANEL_H - SIZE, y + dy * speed));
    }

    @Override
    public void shoot(List<Projectile> projectiles) {
        tryShoot(projectiles);
    }

    private boolean tryShoot(List<Projectile> projectiles) {
        if (currentShootCooldown <= 0) {
            projectiles.add(new Projectile(x, y - SIZE, -10, true));
            currentShootCooldown = shootCooldown;
            return true;
        }
        return false;
    }

    public void tick() {
        if (currentShootCooldown > 0) currentShootCooldown--;
    }

    public void reset() {
        x = PANEL_W / 2;
        y = PANEL_H - 60;
        hp = maxHp;
        currentShootCooldown = 0;
    }

    @Override
    public void takeDamage(int amount) { hp -= amount; }

    @Override
    public int getMaxHp() { return maxHp; }

    @Override
    public int getHp() { return hp; }

    @Override
    public int getX() { return x; }

    @Override
    public int getY() { return y; }

    public Rectangle getBounds() {
        return new Rectangle(x - SIZE, y - SIZE, SIZE * 2, SIZE * 2);
    }

    @Override public Rectangle getHitBox() { return getBounds(); }
    @Override public boolean isAlive() { return hp > 0; }
    @Override public int getSpeed() { return speed; }

    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        int[] xp = {x, x - SIZE, x + SIZE};
        int[] yp = {y - SIZE, y + SIZE, y + SIZE};
        g.fillPolygon(xp, yp, 3);
        // HP pips
        for (int i = 0; i < hp; i++) {
            g.setColor(Color.RED);
            g.fillRect(10 + i * 14, 10, 10, 10);
        }
    }

    @Override
    public void applySpeedMultiplier(double speedMultiplier) {
        speed = (int)(BaseSpeed + speedMultiplier);
    }

    @Override public Color getColor() { return Color.GREEN; }
}
