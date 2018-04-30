package app.core.h20.actions.logic;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

public class HandleReception implements Action {
    @Override
    public void execute (Event event) {
        SimContext context = event.getContext();
        Transmission transmission = event.getTransmission();
        Sensor receiver = transmission.getReceiver();

        if (!receiver.isOccupied()) {    // Se posso ricevere allora inizio a ricevere
            receiver.setReceiving(true);

            double time = transmission.getFrame().getSize() / H20Sim.SENSOR_BANDWIDTH;
            Event e = context.getCoreFactory().getEvent(EventTypes.EndReceptionEvent, time, context, transmission); // Viene creato un evento per la fine della ricezione
            context.getScheduler().addEvent(e);

        } else {
            transmission.setArrived(true); //TODO: da togliere
            transmission.getFrame().getTransmissions().remove(transmission); // Elimina la trasmissione
        }
    }
}
