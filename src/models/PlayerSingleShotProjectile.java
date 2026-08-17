package models;
import java.awt.*;

public class PlayerSingleShotProjectile extends SingleShotProjectile {
    private static final int SPEED = -10; // upward

    public PlayerSingleShotProjectile(int x, int y) {
        super(x, y, SPEED);
    }

    @Override public boolean isFromPlayer() { return true; }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x - WIDTH / 2, y, WIDTH, HEIGHT);
    }
}
