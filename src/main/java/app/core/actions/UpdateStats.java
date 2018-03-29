package app.core.actions;

import app.SimContext;
import app.stats.StatsSample;

public class UpdateStats implements Action {
    int i;

    public UpdateStats() {
        i = 0;
    }

    @Override
    public void execute(SimContext context) {
        context.getCollector().update(Thread.currentThread().getName(), new StatsSample(i));
        i++;
    }

}
