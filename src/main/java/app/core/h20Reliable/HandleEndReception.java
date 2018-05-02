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
    public void execute(Event e) {
        SimContext context = e.getContext();
        Transmission transmission = e.getTransmission();
        Frame frame = transmission.getFrame();
        Sensor receiver = transmission.getReceiver();

        receiver.setReceiving(false);
        transmission.getFrame().getTransmissions().remove(transmission);

        Event newEvent = context.getCoreFactory().getEvent(EventTypes.AckTransmissionEvent, 0, context,transmission);
        context.getScheduler().addEvent(newEvent);
        if (transmission.isSuccessfull()) {

            /*if (protocol(transmission)) {

            }*/
            if (transmission.getReceiver().isSink()) {
                context.getFramesArrived().get(frame).addLast(context.getSimTime() - frame.getArrivalTime());
            }
        }
        
    }
}
