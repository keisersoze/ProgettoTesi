package app.core.h20Reliable;

import app.core.Action;
import app.core.Event;
import app.factory.ActionTypes;
import app.factory.EventTypes;
import app.sim.SimContext;

import javax.naming.Context;

public class HandleTransmission implements Action{

    @Override
    public void execute(Event e) {
        SimContext context= e.getContext();

        Event newEvent= context.getCoreFactory().getEvent(EventTypes.TransmissionEvent,10,context,e.getFrame(),e.getSensor(),e.getInt());
        context.getScheduler().addEvent(newEvent);
        ((HandleEndAckReception)context.getCoreFactory().getAction(ActionTypes.HandleEndAckReception)).add(e.getSensor(),newEvent);
    }
}
