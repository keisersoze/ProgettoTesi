package app.core.h20.actions.stats;

import app.core.Action;
import app.core.Event;
import app.sim.SimContext;
import app.stats.h20.StatsSample;

public class UpdateStats implements Action {

    @Override
    public void execute (Event event) {

        SimContext context = event.getContext();
        context.getCollector().update(Thread.currentThread().getName(), new StatsSample(context));
        context.setPercentageCompleted();

    }

}
