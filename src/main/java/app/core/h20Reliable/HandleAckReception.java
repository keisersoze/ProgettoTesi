package app.core.h20Reliable;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

public class HandleAckReception implements Action {
    @Override
    public void execute(Event e) {
        SimContext context = e.getContext();
        Transmission transmission = e.getTransmission();
        Sensor receiver = transmission.getReceiver();

        if (!receiver.isOccupied()) {
            receiver.setReceiving(true);

            double time = 380 / H20Sim.SENSOR_BANDWIDTH;
            Event newEvent = context.getCoreFactory().getEvent(EventTypes.EndAckReceptionEvent, time, context, transmission);
            context.getScheduler().addEvent(newEvent);

        } else {
            transmission.setArrived(true); //TODO: da togliere
            transmission.getFrame().getTransmissions().remove(transmission);
        }

    }
}