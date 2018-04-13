package app.core.h20.events;

import app.core.Event;
import app.sim.SimContext;

public class StatisticsEvent extends BaseEvent implements Event {

    public StatisticsEvent(double time, SimContext context) {
        super(time, context);
    }
}
