package app.core.h20.actions.utility;

import app.sim.SimContext;
import app.core.Action;
import app.core.Event;

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
