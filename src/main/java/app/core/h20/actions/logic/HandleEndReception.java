package app.core.h20.actions.logic;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;


public class HandleEndReception implements Action {

    public HandleEndReception () {
    }

    @Override
    public void execute (Event event) {

        SimContext context = event.getContext();
        Transmission transmission = event.getTransmission();
        Frame frame = transmission.getFrame();
        Sensor receiver = transmission.getReceiver();
        int numHop = transmission.getHop() + 1;

        receiver.setReceiving(false);
        transmission.getFrame().getTransmissionHistory().remove(transmission);

        if (transmission.isSuccessfull()) {
            if (!transmission.getReceiver().isSink()) {
                if (transmission.getSender().getY() + H20Sim.THRESHOLD < transmission.getReceiver().getY()) {   // Decido se ritrasmettere in base alla profondità
                    Event e = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent, 0, context, frame, receiver, numHop);
                    context.getScheduler().addEvent(e);
                }
            } else {

                context.getFramesArrived().get(frame).addLast(context.getSimTime() - frame.getArrivalTime());
            }
        }
    }
}
