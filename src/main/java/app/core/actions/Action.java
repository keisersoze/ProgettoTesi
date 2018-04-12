package app.core.actions;

import app.core.events.Event;

public interface Action {
    void execute (Event e);
}
