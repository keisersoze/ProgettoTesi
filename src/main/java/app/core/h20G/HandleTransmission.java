package app.core.h20G;

import app.H20Sim;
import app.core.Event;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;
import app.utils.Canvas;

import java.util.Map;

import static org.apache.commons.math3.util.FastMath.log;

public class HandleTransmission extends app.core.h20.actions.logic.HandleTransmission {

    private Canvas canvas;

    public HandleTransmission (Canvas canvas) {
        super();
        this.canvas = canvas;
    }

    @Override
    public void execute (Event event) {

        SimContext context = event.getContext();
        Frame frame = event.getFrame();
        Sensor sender = event.getSensor();
        int numHop = event.getInt();

        sender.setWaiting(false);

        if (CSMA(sender, context)) {
            sender.setTransmitting(true);
            getSensorTransmissionMap().put(sender,context.getSimTime());
            for (Sensor receiver : sender.getNeighbors()) {
                Transmission transmission = context.getModelFactory().getTransmission(sender, receiver, frame, numHop);
                transmission.setTime(context.getSimTime());    // Per il calcolo di quanto sta avanzando la trasmissione (la linea)

                frame.getTransmissions().add(transmission);

                double time = sender.getEuclideanDistance(receiver) / H20Sim.SOUND_SPEED;
                Event e = context.getCoreFactory().getEvent(EventTypes.ReceptionEvent, time, context, transmission);
                context.getScheduler().addEvent(e);

                canvas.enqueue(() -> canvas.newTransmission(frame, transmission));
            }

            double time = frame.getSize() / H20Sim.SENSOR_BANDWIDTH;
            Event e = context.getCoreFactory().getEvent(EventTypes.EndTransmissionEvent, time, context, sender);
            context.getScheduler().addEvent(e);
        } else {
            //CSMA non persistente
            sender.setWaiting(true);
            double time = -log(context.getMarsenneTwister().nextDouble()) / context.getLambda();   //TODO : da capire se va bene oppure se cambiarlo
            Event e = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent, time, context, frame, sender, numHop);
            context.getScheduler().addEvent(e);
        }
    }

}
