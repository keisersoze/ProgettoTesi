package app.core.h20Reliable;

import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;
import app.utils.MyLib;

public class HandleEndAckReception extends HandleEndReception implements Action {
    @Override
    public void execute(Event e) {
        SimContext context = e.getContext();
        Transmission transmission = e.getTransmission();
        Frame frame = transmission.getFrame();
        Sensor receiver = transmission.getReceiver();
        Sensor sender = transmission.getSender();
        int numHop = transmission.getHop() + 1;

        receiver.setReceiving(false);
        transmission.getFrame().getTransmissionHistory().remove(transmission);

        if (transmission.isSuccessfull()) {
            if (!transmission.getReceiver().isSink()) {
                if (protocol(transmission)) {
                    Event newEvent = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent, MyLib.random(.2f, .4f), context, frame, receiver, numHop);
                    context.getScheduler().addEvent(e);
                }
            } else {
                context.getFramesArrived().get(frame).addLast(context.getSimTime() - frame.getArrivalTime());
            }
        }
        Event newEvent = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent, MyLib.random(.2f, .4f), context, frame, receiver, numHop);
        context.getScheduler().addEvent(e);
    }
}
