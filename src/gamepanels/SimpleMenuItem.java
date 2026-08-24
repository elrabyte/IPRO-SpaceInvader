package gamepanels;

import interfaces.INavigableMenuItem;

public class SimpleMenuItem implements INavigableMenuItem {
    private final String name;
    private final Runnable action;
    SimpleMenuItem(String name, Runnable action) { this.name = name; this.action = action; }
    @Override public String getName() { return name; }
    @Override public Runnable getAction() { return action; }
}
