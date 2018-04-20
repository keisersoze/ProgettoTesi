package app.core.h20G;

import app.Canvas;
import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

public class HandleTransmission implements Action {

    private Canvas canvas;

    public HandleTransmission (Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void execute (Event event) {

        SimContext context = event.getContext();
        Frame frame = event.getFrame();
        Sensor sender = event.getSensor();
        int numHop = event.getInt();

        sender.setTransmitting(true);

        for (Sensor receiver : sender.getNeighbors()) {

            double time = sender.getEuclideanDistance(receiver) / H20Sim.SOUND_SPEED;

            Transmission transmission = context.getModelFactory().getTransmission(sender, receiver, frame, numHop);
            transmission.setTime(context.getSimTime());    // Per il calcolo di quanto sta avanzando la trasmissione (la linea)

            Event e = context.getCoreFactory().getEvent(EventTypes.ReceivingTransmissionEvent, time, context, transmission);

            //if (transmission.getSender().getY() + H2OSim.THRESHOLD < transmission.getReceiver().getY()) {
            canvas.enqueue(() -> canvas.newTransmission(frame, transmission));
            //}

            context.getScheduler().addEvent(e);
        }

        double time = frame.getSize() / H20Sim.SENSOR_BANDWIDTH;
        Event e = context.getCoreFactory().getEvent(EventTypes.EndTransmissionEvent, time, context, sender);
        context.getScheduler().addEvent(e);

    }

}
