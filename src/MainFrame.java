

import gamepanels.GamePanel;
import gamepanels.MainMenuPanel;
import gamepanels.ScoreboardPanel;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

    private static final String MENU = "menu", GAME = "game", SCOREBOARD = "scoreboard";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    private final MainMenuPanel menuPanel;
    private final ScoreboardPanel scoreboardPanel;
    private GamePanel gamePanel;

    public MainFrame() {
        super("Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        //Init Panels
        menuPanel = new MainMenuPanel();
        menuPanel.addItem("Play", this::startGame);
        menuPanel.addItem("Scoreboard", this::showScoreboard);
        menuPanel.addItem("Exit", () -> System.exit(0));

        scoreboardPanel = new ScoreboardPanel(this::showMenu);

        cards.add(menuPanel, MENU);
        cards.add(scoreboardPanel, SCOREBOARD);

        add(cards);
        pack();
        setLocationRelativeTo(null);
    }

    private void startGame() {
        // Recreate the panel each round so game state is always fresh.
        if (gamePanel != null) cards.remove(gamePanel);
        gamePanel = new GamePanel(this::onGameOver, this::onExitToMenu);
        cards.add(gamePanel, GAME);
        cardLayout.show(cards, GAME);
        gamePanel.requestFocusInWindow();
    }

    private void showMenu() {
        cardLayout.show(cards, MENU);
        menuPanel.requestFocusInWindow();
    }

    private void showScoreboard() {
        cardLayout.show(cards, SCOREBOARD);
        scoreboardPanel.requestFocusInWindow();
    }

    public void onGameOver() {
        int score = gamePanel.getScore();
        scoreboardPanel.addScore(score);
        showScoreboard();
    }

    public void onExitToMenu() {
        showMenu();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
            frame.menuPanel.requestFocusInWindow();
        });
    }
}
