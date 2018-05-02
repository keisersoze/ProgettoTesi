package app.core.h20Reliable;

import app.core.Action;
import app.core.Event;
import app.factory.ActionTypes;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

import javax.naming.Context;

public class HandleEndReception extends app.core.h20.actions.logic.HandleEndReception implements Action {
    @Override
    public void execute(Event event) {
        SimContext context = event.getContext();
        Transmission transmission = event.getTransmission();
        Frame frame = transmission.getFrame();
        Sensor receiver = transmission.getReceiver();
        int numHop = event.getTransmission().getHop()+1;

        receiver.setReceiving(false);
        transmission.getFrame().getTransmissions().remove(transmission);

        if (transmission.isSuccessfull()) {
            Event newEvent = context.getCoreFactory().getEvent(EventTypes.EndAckReceptionEvent, 0, context,null,transmission.getSender());
            context.getScheduler().addEvent(newEvent);
            if (!transmission.getReceiver().isSink()) {
                if (protocol(transmission)) {
                    Event e = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent,0, context, frame, receiver, numHop);
                    context.getScheduler().addEvent(e);
                }
            } else {
                context.getFramesArrived().get(frame).addLast(context.getSimTime() - frame.getArrivalTime());
            }
        }
        
    }
}
