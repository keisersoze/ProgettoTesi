package app.core.h20.events;

import app.sim.SimContext;

public class MoveEvent extends BaseEvent {

    public MoveEvent(double time, SimContext context) {
        super(time, context);
    }
}
