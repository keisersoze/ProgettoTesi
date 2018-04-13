package app.core.h20.events;

import app.sim.SimContext;

public class ArrivalEvent extends BaseEvent {
    public ArrivalEvent(double time, SimContext context) {
        super(time, context);
    }
}
