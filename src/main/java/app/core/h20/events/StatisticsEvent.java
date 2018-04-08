package app.core.events.impl;

import app.sim.SimContext;
import app.core.actions.impl.stats.UpdateStats;
import app.core.events.Event;

public class StatisticsEvent extends BaseEvent implements Event {

    public StatisticsEvent(double time, SimContext context) {
        super(time, context);
    }
}
