package gamepanels;

import java.awt.Graphics;
public class MainMenuPanel extends NavigatableMenuBase {

    public void addItem(String label, Runnable action) {
        addMenuItem(label, action);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var guiHelper = new helpers.GuiDrawHelper(g);
        guiHelper.DrawTitle("SPACE INVADER", 160);
        guiHelper.DrawHint("Use W/S or Up/Down to navigate, Enter to select");
       
    }
}
