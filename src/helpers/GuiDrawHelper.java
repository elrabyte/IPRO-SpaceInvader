package helpers;

import gamepanels.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class GuiDrawHelper {
    
    private final java.awt.Graphics g;
    public GuiDrawHelper(java.awt.Graphics g) {
        this.g = g;
    }
    public void DrawHint(String text) {
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Monospaced", Font.PLAIN, 16));
        FontMetrics hintFm = g.getFontMetrics();
        g.drawString(text, (GamePanel.screenWidth - hintFm.stringWidth(text)) / 2, GamePanel.screenHeight - 40);
        
    }

    public void DrawTitle(String text) {
        DrawTitle(text, 100);
    }

    public void DrawTitle(String text, int y) {
        g.setColor(Color.CYAN);
        g.setFont(new Font("Monospaced", Font.BOLD, 40));
        FontMetrics titleFm = g.getFontMetrics();
        g.drawString(text, (GamePanel.screenWidth - titleFm.stringWidth(text)) / 2, y);
    }
}
