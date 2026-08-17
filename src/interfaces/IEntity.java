package interfaces;
import java.awt.Color;
import java.awt.Graphics;

public interface IEntity {
    void applySpeedMultiplier(double speedMultiplier);
    void render(Graphics g);
    int getX();
    int getY();
    int getHp();
    int getMaxHp();
    int getSpeed();

    void takeDamage(int amount);
    boolean isAlive();
    java.awt.Rectangle getHitBox();
    Color getColor();
}
