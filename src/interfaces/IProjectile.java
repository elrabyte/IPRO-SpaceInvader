package interfaces;

public interface IProjectile {
    void updateState();
    boolean isFromPlayer();
    java.awt.Rectangle getHitBox();
    void render(java.awt.Graphics g);
    int getSpeed();
}
