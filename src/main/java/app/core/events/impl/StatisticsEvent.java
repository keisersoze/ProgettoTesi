package app.core.events.impl;

import app.core.events.Event;
import app.sim.SimContext;

public class StatisticsEvent extends BaseEvent implements Event {

    public StatisticsEvent (double time, SimContext context) {
        super(time, context);
    }
}
