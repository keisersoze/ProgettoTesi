package app.core.h20Reliable;

import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
import app.sim.SimContext;

import javax.naming.Context;

public class HandleTransmission  extends app.core.h20.actions.logic.HandleTransmission implements Action{

    @Override
    public void execute(Event e) {
        super.execute(e);
        SimContext context= e.getContext();
        Event newEvent= context.getCoreFactory().getEvent(EventTypes.TransmissionEvent,6,context,e.getFrame(),e.getSensor(),e.getInt());
        context.getScheduler().addEvent(newEvent);
    }
}
