package app.core.actions.impl.utility;

import app.H2OSim;
import app.SimContext;
import app.core.actions.Action;
import app.core.events.Event;

public class RescheduleEvent implements Action {
    Event e;
    double interval;

    public RescheduleEvent(Event e, double interval) {
        this.e = e;
        this.interval = interval;
    }

    @Override
    public void execute(SimContext context) {
        //aggiunge il prossimo evento per la raccolta delle stats
        e.setTime(e.getTime() + H2OSim.SAMPLING_INTERVAL);
        context.getScheduler().addEvent(e);
    }
}
