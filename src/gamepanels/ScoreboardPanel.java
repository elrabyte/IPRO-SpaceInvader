package gamepanels;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ScoreboardPanel extends PanelBase {

    private static final int MAX_ENTRIES = 10;
    private final List<Integer> scores = new ArrayList<>();

    public ScoreboardPanel(Runnable onReturnToMenu) {
        super(Map.of(
            KeyEvent.VK_ENTER, onReturnToMenu,
            KeyEvent.VK_ESCAPE, onReturnToMenu
        ));
    }

    public void addScore(int score) {
        scores.add(score);
        scores.sort(Collections.reverseOrder());
        while (scores.size() > MAX_ENTRIES) scores.remove(scores.size() - 1);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        var guiHelper = new helpers.GuiDrawHelper(g);
        guiHelper.DrawTitle("SCOREBOARD");

        final int lineHeight = 32;

        g.setFont(new Font("Monospaced", Font.PLAIN, 22));
        FontMetrics fm = g.getFontMetrics();
        int y = 170;
        if (scores.isEmpty()) {
            g.setColor(Color.WHITE);
            String none = "No scores yet";
            g.drawString(none, (getWidth() - fm.stringWidth(none)) / 2, y);
        } else {
            for (int i = 0; i < scores.size(); i++) {
                g.setColor(i == 0 ? Color.YELLOW : Color.WHITE);
                String line = String.format("%2d. %d", i + 1, scores.get(i));
                g.drawString(line, (getWidth() - fm.stringWidth(line)) / 2, y);
                y += lineHeight;
            }
        }
        
        guiHelper.DrawHint("Press ENTER or ESC to return to Menu");
    }
}
