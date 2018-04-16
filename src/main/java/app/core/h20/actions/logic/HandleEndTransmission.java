package app.core.h20.actions.logic;

import app.H2OSim;
import app.core.Action;
import app.core.Event;
import app.factory.h20.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;


public class HandleEndTransmission implements Action {

    public HandleEndTransmission () {
    }

    @Override
    public void execute (Event event) {

        SimContext context = event.getContext();
        Transmission transmission = event.getTransmission();
        Frame frame = transmission.getFrame();
        Sensor receiver = transmission.getReceiver();

        if (transmission.isSuccessfull()) {
            if (!transmission.getReceiver().isSink()) {
                if (transmission.getSender().getY() + H2OSim.THRESHOLD < transmission.getReceiver().getY()) {   // Decido se ritrasmettere in base alla profondità
                    Event e = context.getCoreFactory().getEvent(EventTypes.TrasmissionEvent, 0,context, frame, receiver);
                    context.getScheduler().addEvent(e);
                }
            } else {
                context.frameArrived(transmission.getFrame());
            }
        }
    }
}
