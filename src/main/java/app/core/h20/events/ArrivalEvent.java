package app.core.events.impl;

import app.sim.SimContext;

public class ArrivalEvent extends BaseEvent {
    public ArrivalEvent(double time, SimContext context) {
        super(time, context);
    }
}
