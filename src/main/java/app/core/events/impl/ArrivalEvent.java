package app.core.events.impl;

import app.core.actions.impl.utility.RescheduleExpRandom;
import app.sim.SimContext;
import app.core.actions.impl.logic.HandleArrival;

public class ArrivalEvent extends BaseEvent {
    public ArrivalEvent(double time, SimContext context) {
        super(time, context);
        addAction(new HandleArrival());
        addAction(new RescheduleExpRandom());
    }
}
