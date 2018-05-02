package app.core.h20Reliable.ack;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.core.h20.actions.logic.HandleTransmission;
import app.factory.EventTypes;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

public class HandleAckTransmission extends HandleTransmission implements Action {
    @Override
    public void execute (Event event) {
        SimContext context = event.getContext();
        Transmission transmission = event.getTransmission();
        Sensor sender = transmission.getSender();
        Sensor receiver = transmission.getReceiver();

        double time = receiver.getEuclideanDistance(sender) / H20Sim.SOUND_SPEED;
        Event newEvent = context.getCoreFactory().getEvent(EventTypes.AckReceptionEvent, 0, context, transmission);
        context.getScheduler().addEvent(newEvent);

        time = H20Sim.ACK_SIZE / H20Sim.SENSOR_BANDWIDTH;    // Schedulo tra quanto finisco di trasmettere l'Ack
        Event newEvent2 = context.getCoreFactory().getEvent(EventTypes.EndAckTransmissionEvent, 0, context, transmission);
        context.getScheduler().addEvent(newEvent2);

        /*
        receiver.setWaiting(false);

        if (CSMA(receiver, context)) {
            receiver.setTransmitting(true);


        }else {
            receiver.setWaiting(true);
            Event newEvent = context.getCoreFactory().getEvent(EventTypes.AckTransmissionEvent, 0.1, context, transmission);
            context.getScheduler().addEvent(newEvent);
        }*/

    }

}
