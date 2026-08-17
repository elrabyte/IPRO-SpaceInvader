package interfaces;
import java.util.List;

import models.Projectile;

public interface IEnemy extends IEntity {
    void update(int playerX, int playerY, List<Projectile> projectiles);
}
