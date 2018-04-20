package app.core.h20.actions.logic;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

public class HandleTransmission implements Action {


    public HandleTransmission () {
    }

    @Override
    public void execute (Event event) {
        SimContext context = event.getContext();
        Frame frame = event.getFrame();
        Sensor sender = event.getSensor();
        int numHop = event.getInt();

        sender.setTransmitting(true);

        for (Sensor receiver : sender.getNeighbors()) {     // Per tutti i sensori che possono ricevere viene creato un nuovo evento
            Transmission transmission = context.getModelFactory().getTransmission(sender, receiver, frame, numHop);
            frame.getTransmissionHistory().add(transmission);

            double time = sender.getEuclideanDistance(receiver) / H20Sim.SOUND_SPEED;   // Tra quanto schedulo l'evento
            Event e = context.getCoreFactory().getEvent(EventTypes.ReceptionEvent, time, context, transmission);
            context.getScheduler().addEvent(e);
        }
        double time = frame.getSize() / H20Sim.SENSOR_BANDWIDTH;    // Schedulo tra quanto finisco di trasmettere
        Event e = context.getCoreFactory().getEvent(EventTypes.EndTransmissionEvent, time, context, sender);    // Creo un evento per la fine della trasmissione
        context.getScheduler().addEvent(e);

    }

}
