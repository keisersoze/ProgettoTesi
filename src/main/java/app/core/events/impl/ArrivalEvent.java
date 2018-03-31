package app.core.events.impl;

import app.SimContext;
import app.core.actions.impl.logic.HandleArrival;
import app.core.actions.impl.utility.RescheduleExpRandom;

public class ArrivalEvent extends BaseEvent {
    public ArrivalEvent(double time, SimContext context, float rate) {
        super(time, context);
        addAction(new HandleArrival());
        addAction(new RescheduleExpRandom(rate,this));
    }
}
