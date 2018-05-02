package app.core.h20Reliable;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.factory.ActionTypes;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

import java.util.List;

import static org.apache.commons.math3.util.FastMath.log;

public class HandleTransmission extends app.core.h20.actions.logic.HandleTransmission implements Action{

    @Override
    public void execute(Event event) {
        SimContext context = event.getContext();
        Frame frame = event.getFrame();
        Sensor sender = event.getSensor();
        int numHop = event.getInt();
        sender.setWaiting(false);
        if (CSMA(sender, context)) {
            sender.setTransmitting(true);

            for (Sensor receiver : sender.getNeighbors()) {     // Per tutti i sensori che possono ricevere viene creato un nuovo evento
                Transmission transmission = context.getModelFactory().getTransmission(sender, receiver, frame, numHop);
                transmission.setTime(context.getSimTime());
                frame.getTransmissions().add(transmission);

                double time = sender.getEuclideanDistance(receiver) / H20Sim.SOUND_SPEED;   // Tra quanto schedulo l'evento
                Event e = context.getCoreFactory().getEvent(EventTypes.ReceptionEvent, time, context, transmission);
                context.getScheduler().addEvent(e);
            }

            double time = frame.getSize() / H20Sim.SENSOR_BANDWIDTH;    // Schedulo tra quanto finisco di trasmettere
            Event e = context.getCoreFactory().getEvent(EventTypes.EndTransmissionEvent, time, context, sender);    // Creo un evento per la fine della trasmissione
            context.getScheduler().addEvent(e);

            Event newEvent= context.getCoreFactory().getEvent(EventTypes.AckVerifyEvent,time+ H20Sim.ACK_TIMEOUT,context,frame,sender,numHop);
            context.getScheduler().addEvent(newEvent);

            List<Sensor> sensors = ((HandleEndAckReception)context.getCoreFactory().getAction(ActionTypes.HandleEndAckReception)).getSensors();
            if (!sensors.contains(sender))
                sensors.add(sender);

        }else {
            // CSMA non persistente
            sender.setWaiting(true);
            double time = -log(context.getMarsenneTwister().nextDouble()) / H20Sim.LAMDA;   //TODO : da capire se va bene oppure se cambiarlo
            Event e = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent, time, context, frame, sender, numHop);
            context.getScheduler().addEvent(e);
        }


    }
}
