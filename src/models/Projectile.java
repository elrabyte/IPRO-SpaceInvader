package models;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import gamepanels.GamePanel;

public class Projectile {
    private int x, y;
    private final int speed;
    private final boolean fromPlayer;
    private boolean active = true;
    private static final int width = 4, height = 10;
    private static final int CLEANUP_THRESHOLD = GamePanel.screenHeight + 50; // beyond the bottom of the screen

    public Projectile(int x, int y, int speed, boolean fromPlayer) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.fromPlayer = fromPlayer;
    }

    public void update() {
        y += speed;
        if (y < -height || y > CLEANUP_THRESHOLD) active = false;
    }

    public void render(Graphics g) {
        g.setColor(fromPlayer ? Color.CYAN : Color.ORANGE);
        g.fillRect(x - width / 2, y, width, height);
    }

    public boolean isFromPlayer() { return fromPlayer; }
    public boolean isActive() { return active; }
    public void deactivate() { active = false; }
    public Rectangle getBounds() { return new Rectangle(x - width / 2, y, width, height); }
}
