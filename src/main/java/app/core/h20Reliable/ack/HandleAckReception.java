package app.core.h20Reliable.ack;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

public class HandleAckReception implements Action {
    @Override
    public void execute(Event e) {
        SimContext context = e.getContext();
        Transmission transmission = e.getTransmission();
        Sensor receiver = transmission.getReceiver();
        Sensor sender = transmission.getSender();

        if (!sender.isOccupied()) {
            sender.setReceiving(true);

            double size = H20Sim.ACK_SIZE;
            double time = size / H20Sim.SENSOR_BANDWIDTH;

            Frame ackFrame= context.getModelFactory().getFrame(size,receiver,context.getSimTime());

            Transmission ackTransmission = context.getModelFactory().getTransmission(receiver,sender, ackFrame,0);

            Event newEvent = context.getCoreFactory().getEvent(EventTypes.EndAckReceptionEvent, time, context, ackTransmission,sender);
            context.getScheduler().addEvent(newEvent);

        } else {
            transmission.setArrived(true); //TODO: da togliere
            transmission.getFrame().getTransmissions().remove(transmission);
        }

    }
}
