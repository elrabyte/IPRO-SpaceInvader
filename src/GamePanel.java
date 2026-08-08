import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener {

    private static final int W = 800, H = 600;
    private static final int TICK_MS = 16; // ~60fps

    private final javax.swing.Timer gameTimer;
    private final PlayerShip player;
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Projectile> projectiles = new ArrayList<>();

    private int score = 0;
    private int tickCount = 0;
    private int spawnCooldown = 120;
    private boolean gameOver = false;

    private final Set<Integer> keysDown = new HashSet<>();
    private static final Random RNG = new Random();

    public GamePanel() {
        setPreferredSize(new Dimension(W, H));
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
        repaint();
    }

    private void update() {
        tickCount++;

        // --- Player movement ---
        int dx = 0, dy = 0;
        if (keysDown.contains(KeyEvent.VK_A) || keysDown.contains(KeyEvent.VK_LEFT))  dx -= 1;
        if (keysDown.contains(KeyEvent.VK_D) || keysDown.contains(KeyEvent.VK_RIGHT)) dx += 1;
        if (keysDown.contains(KeyEvent.VK_W) || keysDown.contains(KeyEvent.VK_UP))    dy -= 1;
        if (keysDown.contains(KeyEvent.VK_S) || keysDown.contains(KeyEvent.VK_DOWN))  dy += 1;
        player.move(dx, dy);
        player.tick();
        player.tryShoot(projectiles);

        // --- Score: +1 per second (60 ticks) ---
        if (tickCount % 60 == 0) score++;

        // --- Speed multiplier increases over time ---
        double speedMult = 1.0 + tickCount / 1800.0;

        // --- Spawn enemies ---
        spawnCooldown--;
        if (spawnCooldown <= 0) {
            int spawnX = 40 + RNG.nextInt(W - 80);
            if (RNG.nextBoolean()) {
                enemies.add(new EnemyShip(spawnX, -20));
            } else {
                enemies.add(new Asteroid(spawnX, -20, player.getX(), player.getY()));
            }
            // Decrease interval over time, minimum 40 ticks
            spawnCooldown = Math.max(40, 120 - tickCount / 60);
        }

        // --- Update enemies ---
        for (Enemy enemy : enemies) {
            enemy.update(player.getX(), player.getY(), speedMult, projectiles);
        }

        // --- Update projectiles ---
        for (Projectile p : projectiles) p.update();

        // --- Collision: player projectile vs enemy ---
        for (Projectile p : projectiles) {
            if (!p.isActive() || !p.isFromPlayer()) continue;
            for (Enemy enemy : enemies) {
                if (enemy.isAlive() && p.getBounds().intersects(enemy.getBounds())) {
                    enemy.takeDamage(1);
                    p.deactivate();
                    if (!enemy.isAlive()) score += 10;
                    break;
                }
            }
        }

        // --- Collision: enemy projectile vs player ---
        Rectangle playerBounds = player.getBounds();
        for (Projectile p : projectiles) {
            if (!p.isActive() || p.isFromPlayer()) continue;
            if (p.getBounds().intersects(playerBounds)) {
                player.takeDamage(1);
                p.deactivate();
            }
        }

        // --- Collision: enemy body vs player ---
        for (Enemy enemy : enemies) {
            if (enemy.isAlive() && enemy.getBounds().intersects(playerBounds)) {
                player.takeDamage(1);
                enemy.takeDamage(enemy.getHp()); // destroy enemy on collision
                score += 10;
            }
        }

        // --- Remove dead/out-of-bounds entities ---
        enemies.removeIf(en -> !en.isAlive() || en.getY() > H + 40);
        projectiles.removeIf(p -> !p.isActive());

        // --- Check game over ---
        if (player.getHp() <= 0) {
            gameOver = true;
            gameTimer.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw divider line for bottom third
        g.setColor(new Color(40, 40, 80));
        g.drawLine(0, H * 2 / 3, W, H * 2 / 3);

        // Draw enemies
        for (Enemy enemy : enemies) enemy.draw(g);

        // Draw projectiles
        for (Projectile p : projectiles) p.draw(g);

        // Draw player
        player.draw(g);

        // HUD
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.drawString("Score: " + score, W - 140, 24);
        g.drawString("HP: " + player.getHp(), W - 140, 44);

        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 160));
            g.fillRect(0, 0, W, H);
            g.setColor(Color.RED);
            g.setFont(new Font("Monospaced", Font.BOLD, 48));
            String msg = "GAME OVER";
            FontMetrics fm = g.getFontMetrics();
            g.drawString(msg, (W - fm.stringWidth(msg)) / 2, H / 2 - 20);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 24));
            String scoreMsg = "Final Score: " + score;
            fm = g.getFontMetrics();
            g.drawString(scoreMsg, (W - fm.stringWidth(scoreMsg)) / 2, H / 2 + 24);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Monospaced", Font.PLAIN, 18));
            String replayMsg = "Press [R] to Play Again";
            fm = g.getFontMetrics();
            g.drawString(replayMsg, (W - fm.stringWidth(replayMsg)) / 2, H / 2 + 60);
        }
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
