package interfaces;
import models.Projectile;

public interface IPlayerEntity extends IEntity {
    void move(int dx, int dy);
    void shoot(java.util.List<Projectile> projectiles);
}
