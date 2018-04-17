package app.core.h20G;

import app.Canvas;
import app.H2OSim;
import app.core.Action;
import app.core.Event;
import app.factory.h20.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;


public class HandleEndReception implements Action {

    private Canvas canvas;

    public HandleEndReception (Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void execute (Event event) {

        SimContext context = event.getContext();
        Transmission transmission = event.getTransmission();
        Frame frame = transmission.getFrame();
        Sensor receiver = transmission.getReceiver();
        int numHop = transmission.getHop() + 1;

        receiver.setReceiving(false);
        transmission.setArrived(true);

        if (transmission.isSuccessfull()) {
            if (!transmission.getReceiver().isSink()) {
                if (transmission.getSender().getY() + H2OSim.THRESHOLD < transmission.getReceiver().getY()) {   // Decido se ritrasmettere in base alla profondità
                    Event e = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent, 0, context, frame, receiver, numHop);
                    context.getScheduler().addEvent(e);
                }
            }
        }

    }
}
