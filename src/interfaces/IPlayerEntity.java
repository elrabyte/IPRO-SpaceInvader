package interfaces;

import models.PlayerShipMovementMapping;

import java.util.List;
import java.util.Set;

public interface IPlayerEntity extends IEntity, IShooting {
    void handleMovement(Set<Integer> keysDown);
    PlayerShipMovementMapping getMovementMapping();

    void handleShooting(Set<Integer> keysDown, List<IProjectile> projectiles);
}
