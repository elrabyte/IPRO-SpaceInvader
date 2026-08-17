public interface PlayerEntity {
    void move(int dx, int dy);
    void shoot(java.util.List<Projectile> projectiles);
    int getHp();
    void takeDamage(int amount);
    int getX();
    int getY();
}
