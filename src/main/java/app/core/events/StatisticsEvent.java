package app.core.events;

import app.SimContext;
import app.core.actions.UpdateStats;

public class StatisticsEvent extends BaseEvent implements Event {

    public StatisticsEvent(double time, SimContext context) {
        super(time, context);
        addListener(new UpdateStats());
    }
}
