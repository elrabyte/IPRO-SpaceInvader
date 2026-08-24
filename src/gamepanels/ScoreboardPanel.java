package gamepanels;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import models.ScoreEntry;

public class ScoreboardPanel extends PanelBase {

    private static final int MAX_ENTRIES = 10;
    private static final Path SCOREBOARD_FILE = Path.of("src", "scoreboard.json");
    private static final Gson GSON = new Gson();
    private final List<ScoreEntry> scores = new ArrayList<>();

    public ScoreboardPanel(Runnable onReturnToMenu) {
        super(Map.of(
            KeyEvent.VK_ENTER, onReturnToMenu,
            KeyEvent.VK_ESCAPE, onReturnToMenu
        ));
        loadScores();
    }

    public void addScore(String name, int score) {
        scores.add(new ScoreEntry(normalizeName(name), score));
        rankScores();
        saveScores();
        repaint();
    }

    private void loadScores() {
        if (!Files.exists(SCOREBOARD_FILE)) return;

        try {
            String json = Files.readString(SCOREBOARD_FILE, StandardCharsets.UTF_8);
            List<ScoreEntry> loadedScores = GSON.fromJson(json, new TypeToken<List<ScoreEntry>>() {}.getType());
            if (loadedScores != null) scores.addAll(loadedScores);
            rankScores();
        } catch (IOException | JsonSyntaxException exception) {
            System.err.println("Could not load scoreboard: " + exception.getMessage());
        }
    }

    private void saveScores() {
        try {
            Files.writeString(SCOREBOARD_FILE, GSON.toJson(scores), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.err.println("Could not save scoreboard: " + exception.getMessage());
        }
    }

    private void rankScores() {
        scores.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed());
        while (scores.size() > MAX_ENTRIES) scores.remove(scores.size() - 1);
    }

    private String normalizeName(String name) {
        if (name == null || name.trim().isEmpty()) return "Player";
        return name.trim();
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
                ScoreEntry entry = scores.get(i);
                String line = String.format("%2d. %-16s %d", i + 1, entry.getName(), entry.getScore());
                g.drawString(line, (getWidth() - fm.stringWidth(line)) / 2, y);
                y += lineHeight;
            }
        }
        
        guiHelper.DrawHint("Press ENTER or ESC to return to Menu");
    }
}
