package gamepanels;

import interfaces.INavigatableMenuItem;

public class SimpleMenuItem implements INavigatableMenuItem {
    private final String name;
    private final Runnable action;
    SimpleMenuItem(String name, Runnable action) { this.name = name; this.action = action; }
    @Override public String getName() { return name; }
    @Override public Runnable getAction() { return action; }
}
