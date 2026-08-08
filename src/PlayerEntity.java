public interface PlayerEntity {
    void move(int dx, int dy);
    void shoot();
    int getHp();
    void takeDamage(int amount);
    int getX();
    int getY();
}
