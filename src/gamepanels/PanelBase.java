package gamepanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.Map;
import javax.swing.*;

public class PanelBase extends JPanel implements interfaces.IPanel {

    private final Map<Integer, Runnable> shortcutActions;

    public PanelBase(Map<Integer, Runnable> shortcutActions) {
        this.shortcutActions = shortcutActions;
        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                var action = shortcutActions.getOrDefault(e.getKeyCode(), null);
                if (action != null) action.run();
            }
        });

        setPreferredSize(new Dimension(gamepanels.GamePanel.screenWidth, gamepanels.GamePanel.screenHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    @Override
    public Map<Integer, Runnable> GetShortcutActions() {
        return shortcutActions;
    }
}
