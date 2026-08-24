package gamepanels;

import interfaces.INavigatableMenu;
import interfaces.INavigatableMenuItem;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class NavigatableMenuBase extends PanelBase implements INavigatableMenu {

    protected static final Font ITEM_FONT = new Font("Monospaced", Font.BOLD, 28);
    protected static final int FIRST_ITEM_Y = 260;
    protected static final int ITEM_SPACING = 50;

    private final List<INavigatableMenuItem> items = new ArrayList<>();
    private int selectedIndex = 0;

    protected NavigatableMenuBase() {
        super(Map.of());

        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        moveSelection(-1);
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        moveSelection(1);
                        break;
                    case KeyEvent.VK_ENTER:
                    case KeyEvent.VK_SPACE:
                        activateSelected();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void addMenuItem(String name, Runnable action) {
        items.add(new SimpleMenuItem(name, action));
    }

    @Override
    public List<INavigatableMenuItem> getMenuItems() {
        return Collections.unmodifiableList(items);
    }

    private void moveSelection(int delta) {
        if (items.isEmpty()) return;
        selectedIndex = (selectedIndex + delta + items.size()) % items.size();
        repaint();
    }

    private void activateSelected() {
        if (selectedIndex >= 0 && selectedIndex < items.size()) {
            items.get(selectedIndex).getAction().run();
        }
    }

    private int itemY(int index) {
        return FIRST_ITEM_Y + index * ITEM_SPACING;
    }

    protected void drawMenuItems(Graphics g) {
        g.setFont(ITEM_FONT);
        FontMetrics fm = g.getFontMetrics();
        for (int i = 0; i < items.size(); i++) {
            boolean selected = i == selectedIndex;
            g.setColor(selected ? Color.YELLOW : Color.WHITE);
            String label = selected ? "> " + items.get(i).getName() + " <" : items.get(i).getName();
            g.drawString(label, (getWidth() - fm.stringWidth(label)) / 2, itemY(i));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMenuItems(g);
    }
}
