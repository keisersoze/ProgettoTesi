package app.core.events.impl;

import app.SimContext;

public class TrasmissionEvent extends BaseEvent{
    public TrasmissionEvent(double time, SimContext context) {
        super(time, context);
    }
}
