package interfaces;

public interface IProjectile {
    void updateState();
    boolean isFromPlayer();
    boolean isActive();
    void deactivate();
    java.awt.Rectangle getHitBox();
    void render(java.awt.Graphics g);
    int getSpeed();
}
