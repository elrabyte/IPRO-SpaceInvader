package gamepanels;
import interfaces.IEnemy;
import interfaces.IProjectile;
import interfaces.IShooting;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import models.Asteroid;
import models.EnemyShip;
import models.PlayerShip;

public class GamePanel extends JPanel implements ActionListener {

    public static final int screenWidth = 800, screenHeight = 600;
    private static final int TICK_MS = 16; // ~60fps

    private final javax.swing.Timer gameTimer;
    private final PlayerShip player;
    private final List<IEnemy> enemies = new ArrayList<>();
    private final List<IProjectile> projectiles = new ArrayList<>();

    private int score = 0;
    private int tickCount = 0;
    private int spawnCooldown = 120;
    private boolean gameOver = false;
    private double speedMultiplier = 1.0;

    private final Set<Integer> keysDown = new HashSet<>();
    private static final Random RNG = new Random();

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setFocusable(true);

        player = new PlayerShip();

        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                keysDown.add(e.getKeyCode());
                if (gameOver && e.getKeyCode() == KeyEvent.VK_R) restart();
            }
            @Override public void keyReleased(KeyEvent e) { keysDown.remove(e.getKeyCode()); }
        });

        gameTimer = new javax.swing.Timer(TICK_MS, this);
        gameTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) update();
        rerender();
    }

    private void rerender() {
        repaint();
    }

    private void update() {
        tickCount++;
        
        handlePlayerMovement();
        player.applySpeedMultiplier(speedMultiplier);

        player.tick();

        // --- Player shooting ---
        if (keysDown.contains(KeyEvent.VK_SPACE)) player.shoot(projectiles);

        addScorePerTimePassed();

        handleSpeedMultiplier();

        spawnEnemies();
        addScorePerTimePassed();

        updateEnemies();
        updateProjectiles();

        handleCollision();

        // --- Remove dead/out-of-bounds entities ---
        enemies.removeIf(en -> !en.isAlive() || en.getY() > screenHeight + 40);
        projectiles.removeIf(p -> !p.isActive());

        // --- Check game over ---
        if (player.getHp() <= 0) {
            gameOver = true;
            gameTimer.stop();
        }
    }

    private void updateEnemies() {
        for (IEnemy enemy : enemies) {
            enemy.applySpeedMultiplier(speedMultiplier);
            enemy.updateState(player.getX(), player.getY());
            if (enemy instanceof IShooting) {
                ((IShooting) enemy).shoot(projectiles);
            }
        }
    }
    private void updateProjectiles() {
        for (IProjectile p : projectiles) p.updateState();
    }
    private void handleSpeedMultiplier() {
        speedMultiplier = 1.0 + tickCount / 1000.0;
    }

    private void handlePlayerMovement() {
        int dx = 0, dy = 0;
        if (keysDown.contains(KeyEvent.VK_A)) dx -= 1;
        if (keysDown.contains(KeyEvent.VK_D)) dx += 1;
        if (keysDown.contains(KeyEvent.VK_W)) dy -= 1;
        if (keysDown.contains(KeyEvent.VK_S)) dy += 1;
        player.move(dx, dy);
    }

    private void handleCollision(){
        // --- Collision: player projectile vs enemy ---
        for (IProjectile p : projectiles) {
            if (!p.isActive() || !p.isFromPlayer()) continue;
            for (IEnemy enemy : enemies) {
                if (enemy.isAlive() && p.getHitBox().intersects(enemy.getHitBox())) {
                    enemy.takeDamage(1);
                    p.deactivate();
                    if (!enemy.isAlive()) score += 10;
                    break;
                }
            }
        }

        // --- Collision: enemy projectile vs player ---
        Rectangle playerBounds = player.getBounds();
        for (IProjectile p : projectiles) {
            if (!p.isActive() || p.isFromPlayer()) continue;
            if (p.getHitBox().intersects(playerBounds)) {
                player.takeDamage(1);
                p.deactivate();
            }
        }

        // --- Collision: enemy body vs player ---
        for (IEnemy enemy : enemies) {
            if (enemy.isAlive() && enemy.getHitBox().intersects(playerBounds)) {
                player.takeDamage(1);
                enemy.takeDamage(enemy.getHp()); // destroy enemy on collision
                score += 10;
            }
        }
    }

    private void addScorePerTimePassed(){
            if (tickCount % 60 == 0) score++;
    }

    private void spawnEnemies() {
        spawnCooldown--;
        if (spawnCooldown <= 0) {
            int spawnX = 40 + RNG.nextInt(screenWidth - 80);
            if (RNG.nextBoolean()) {
                enemies.add(new EnemyShip(spawnX, -20));
            } else {
                enemies.add(new Asteroid(spawnX, -20, player.getX(), player.getY()));
            }
            // Decrease interval over time, minimum 40 ticks
            spawnCooldown = Math.max(40, 120 - tickCount / 60);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw divider line for bottom third
        g.setColor(new Color(40, 40, 80));
        g.drawLine(0, screenHeight * 2 / 3, screenWidth, screenHeight * 2 / 3);

        // Draw enemies
        for (IEnemy enemy : enemies) enemy.render(g);

        // Draw projectiles
        for (IProjectile p : projectiles) p.render(g);

        // Draw player
        player.render(g);

        drawHUD(g);

        drawGameOverScreen(g);
    }
    private void drawHUD(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        final int hudX = screenWidth - 160;
        int lineHeight = 20;
        int hudY = 24;
        g.drawString("Score: " + score, hudX, hudY);
        hudY += lineHeight;
        g.drawString("HP: " + player.getHp(), hudX, hudY);
        hudY += lineHeight;
        g.drawString("Speed: " + String.format("%.2f", speedMultiplier), hudX, hudY);
    }

    private void drawGameOverScreen(Graphics g) {
        if(!gameOver) return;

        g.setColor(new Color(0, 0, 0, 160));
            g.fillRect(0, 0, screenWidth, screenHeight);
            g.setColor(Color.RED);
            g.setFont(new Font("Monospaced", Font.BOLD, 48));
            String msg = "GAME OVER";
            FontMetrics fm = g.getFontMetrics();
            g.drawString(msg, (screenWidth - fm.stringWidth(msg)) / 2, screenHeight / 2 - 20);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 24));
            String scoreMsg = "Final Score: " + score;
            fm = g.getFontMetrics();
            g.drawString(scoreMsg, (screenWidth - fm.stringWidth(scoreMsg)) / 2, screenHeight / 2 + 24);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Monospaced", Font.PLAIN, 18));
            String replayMsg = "Press [R] to Play Again";
            fm = g.getFontMetrics();
            g.drawString(replayMsg, (screenWidth - fm.stringWidth(replayMsg)) / 2, screenHeight / 2 + 60);
    }

    private void restart() {
        player.reset();
        enemies.clear();
        projectiles.clear();
        keysDown.clear();
        score = 0;
        tickCount = 0;
        spawnCooldown = 120;
        gameOver = false;
        gameTimer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Space Invaders");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            GamePanel panel = new GamePanel();
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
            panel.requestFocusInWindow();
        });
    }
}
