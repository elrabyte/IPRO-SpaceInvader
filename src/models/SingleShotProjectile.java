package models;
import java.awt.*;
import gamepanels.GamePanel;
import interfaces.IProjectile;

public abstract class SingleShotProjectile implements IProjectile {
    protected int x, y;
    protected final int speed;
    protected boolean active = true;
    protected static final int WIDTH = 4, HEIGHT = 10;
    private static final int CLEANUP_THRESHOLD = GamePanel.screenHeight + 50;

    protected SingleShotProjectile(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    @Override
    public void updateState() {
        y += speed;
        if (y < -HEIGHT || y > CLEANUP_THRESHOLD) active = false;
    }

    @Override public boolean isActive() { return active; }
    @Override public void deactivate() { active = false; }
    @Override public Rectangle getHitBox() { return new Rectangle(x - WIDTH / 2, y, WIDTH, HEIGHT); }
    @Override public int getSpeed() { return speed; }
}
