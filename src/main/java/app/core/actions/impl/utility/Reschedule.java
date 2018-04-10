package app.core.actions.impl.utility;

import app.core.actions.Action;
import app.core.events.Event;
import app.sim.SimContext;

public class Reschedule implements Action {

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
