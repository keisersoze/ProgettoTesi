package app.core.actions.impl.stats;

import app.core.events.Event;
import app.sim.SimContext;
import app.core.actions.Action;
import app.stats.impl.StatsSample;

public class UpdateStats implements Action {

    public static final String id="UPDATESTATS";

    int i;

    public UpdateStats() {
        i = 0;
    }

    @Override
    public void execute(Event event) {

        SimContext context = event.getContext();

        context.getCollector().update(Thread.currentThread().getName(), new StatsSample(i));
        i++;
    }

}
