package app.core.h20.events;

import app.sim.SimContext;
import app.core.Event;

public class StatisticsEvent extends BaseEvent implements Event {

    public StatisticsEvent(double time, SimContext context) {
        super(time, context);
    }
}
