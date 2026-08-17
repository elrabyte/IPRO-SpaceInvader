package interfaces;

public interface IPlayerEntity extends IEntity, IShooting {
    void move(int dx, int dy);
}
