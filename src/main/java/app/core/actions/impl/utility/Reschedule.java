package app.core.actions.impl.utility;

import app.sim.SimContext;
import app.core.actions.Action;
import app.core.events.Event;

public class Reschedule implements Action {
    Event e;
    double interval;

    public Reschedule(Event e, double interval) {
        this.e = e;
        this.interval = interval;
    }

    @Override
    public void execute(SimContext context) {
        //aggiunge il prossimo evento per la raccolta delle stats
        e.updateTime(interval);
        context.getScheduler().addEvent(e);
    }
}
