package app.core.h20Reliable.ack;

import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.sim.SimContext;
import app.utils.MyLib;

public class HandleEndAckTransmission implements Action {
    @Override
    public void execute(Event event) {
        SimContext context = event.getContext();
        Frame frame = event.getTransmission().getFrame();
        Sensor receiver = event.getTransmission().getReceiver();
        int numHop = event.getTransmission().getHop()+1;


        receiver.setTransmitting(false);

        Event e = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent, 0, context, frame, receiver, numHop);
        context.getScheduler().addEvent(e);
    }
}
