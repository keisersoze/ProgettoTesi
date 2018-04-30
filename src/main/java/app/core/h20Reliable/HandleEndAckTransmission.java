package app.core.h20Reliable;

import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
import app.sim.SimContext;
import app.utils.MyLib;

public class HandleEndAckTransmission implements Action {
    @Override
    public void execute(Event event) {
        SimContext context = event.getContext();

        event.getSensor().setTransmitting(false);
        Event e = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent, 0, context, null, null, 0);
        context.getScheduler().addEvent(e);
    }
}
