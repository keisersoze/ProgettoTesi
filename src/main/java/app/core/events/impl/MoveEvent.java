package app.core.events.impl;

import app.sim.SimContext;

public class MoveEvent extends BaseEvent {

    public MoveEvent(double time, SimContext context) {
        super(time, context);
    }
}
