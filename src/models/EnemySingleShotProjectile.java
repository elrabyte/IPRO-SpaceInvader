package models;
import java.awt.*;

public class EnemySingleShotProjectile extends SingleShotProjectile {
    public EnemySingleShotProjectile(int x, int y, int speed) {
        super(x, y, speed);
    }

    @Override public boolean isFromPlayer() { return false; }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(x - WIDTH / 2, y, WIDTH, HEIGHT);
    }
}
