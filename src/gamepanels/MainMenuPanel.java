package gamepanels;

import java.awt.Graphics;
public class MainMenuPanel extends NavigableMenuBase {

    public void addItem(String label, Runnable action) {
        addMenuItem(label, action);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var guiHelper = new helpers.GuiDrawHelper(g);
        guiHelper.DrawTitle("SPACE INVADER", 160);
       
    }
}
