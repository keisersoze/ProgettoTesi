package app.core.actions;

import app.SimContext;
import app.H2OSim;
import app.core.events.Event;

public class RescheduleEvent implements Action {
    Event e;

    public RescheduleEvent(Event e, double interval) {
        this.e = e;
    }

    @Override
    public void execute(SimContext context) {
        //aggiunge il prossimo evento per la raccolta delle stats
        //Event stats_evt = new StatisticsEvent(e.getTime() + H2OSim.SAMPLING_INTERVAL);
        e.setTime(e.getTime()+ H2OSim.SAMPLING_INTERVAL);
        context.getScheduler().addEvent(e);
    }
}
