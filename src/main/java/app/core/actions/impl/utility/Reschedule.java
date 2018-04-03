package app.core.actions.impl.utility;

import app.sim.SimContext;
import app.core.actions.Action;
import app.core.events.Event;

public class Reschedule implements Action {

    public static final String id="RESCHEDULE";

    double interval;

    public Reschedule(double interval) {
        this.interval = interval;
    }

    @Override
    public void execute(Event event) {

        SimContext context = event.getContext();

        //aggiunge il prossimo evento per la raccolta delle stats
        event.updateTime(interval);
        context.getScheduler().addEvent(event);
    }
}
