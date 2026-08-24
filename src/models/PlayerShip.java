package models;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;

import interfaces.IPlayerEntity;
import interfaces.IProjectile;

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
    public void handleMovement(Set<Integer> keysDown) {
        PlayerShipMovementMapping mapping = getMovementMapping();
        int dx = 0, dy = 0;
        if (keysDown.contains(mapping.MoveLeft)) dx -= 1;
        if (keysDown.contains(mapping.MoveRight)) dx += 1;
        if (keysDown.contains(mapping.MoveUp)) dy -= 1;
        if (keysDown.contains(mapping.MoveDown)) dy += 1;
        move(dx, dy);
    }

    @Override
    public PlayerShipMovementMapping getMovementMapping() {
        return new PlayerShipMovementMapping(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
    }

    @Override
    public void handleShooting(Set<Integer> keysDown, List<IProjectile> projectiles) {
        PlayerShipMovementMapping mapping = getMovementMapping();
        if (keysDown.contains(mapping.Shoot)) {
            shoot(projectiles);
        }
    }

    public void move(int dx, int dy) {
        x = Math.max(SIZE, Math.min(PANEL_W - SIZE, x + dx * speed));
        y = Math.max(BOTTOM_THIRD_TOP + SIZE, Math.min(PANEL_H - SIZE, y + dy * speed));
    }

    @Override
    public void shoot(List<IProjectile> projectiles) {
        if (currentShootCooldown <= 0) {
            projectiles.add(new PlayerSingleShotProjectile(x, y - SIZE));
            currentShootCooldown = shootCooldown;
        }
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
        DrawHealthBar(g);

    }

    private void DrawHealthBar(Graphics g) {
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
