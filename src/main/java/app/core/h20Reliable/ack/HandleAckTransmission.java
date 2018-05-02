package app.core.h20Reliable.ack;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.core.h20.actions.logic.HandleEndReception;
import app.core.h20.actions.logic.HandleTransmission;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;
import app.utils.MyLib;

import static org.apache.commons.math3.util.FastMath.log;

public class HandleAckTransmission extends HandleTransmission implements Action {
    @Override
    public void execute(Event e) {
        SimContext context = e.getContext();
        Transmission transmission = e.getTransmission();
        Sensor sender = transmission.getSender();
        Sensor receiver = transmission.getReceiver();
        sender.setWaiting(false);

        if (CSMA(sender, context)) {
            receiver.setTransmitting(true);
            double time = receiver.getEuclideanDistance(sender) / H20Sim.SOUND_SPEED;
            Event newEvent = context.getCoreFactory().getEvent(EventTypes.AckReceptionEvent, time, context, transmission);
            context.getScheduler().addEvent(newEvent);

            time = 380 / H20Sim.SENSOR_BANDWIDTH;    // Schedulo tra quanto finisco di trasmettere l'Ack
            Event newEvent2 = context.getCoreFactory().getEvent(EventTypes.EndAckTransmissionEvent, time, context, transmission); //TODO numhop da sistemare
            context.getScheduler().addEvent(newEvent2);

        }else {
            receiver.setWaiting(true);
            double time = -log(context.getMarsenneTwister().nextDouble()) / H20Sim.LAMDA;
            Event newEvent = context.getCoreFactory().getEvent(EventTypes.AckTransmissionEvent, time, context, transmission);
            context.getScheduler().addEvent(e);
        }

    }

}
