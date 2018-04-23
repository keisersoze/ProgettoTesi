package app.core.h20G;

import app.utils.Canvas;
import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
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
        Sensor sender = transmission.getSender();
        Sensor receiver = transmission.getReceiver();
        int numHop = transmission.getHop() + 1;

        receiver.setReceiving(false);
        transmission.setArrived(true);
        transmission.getFrame().getTransmissionHistory().remove(transmission);

        if (transmission.isSuccessfull()) {
            if (!receiver.isSink()) {
                if (sender.getY() + H20Sim.THRESHOLD < receiver.getY()) {   // Decido se ritrasmettere in base alla profondità
                    Event e = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent, 0, context, frame, receiver, numHop);
                    context.getScheduler().addEvent(e);
                }
            } else {
                context.getFramesArrived().get(frame).addLast(context.getSimTime() - frame.getArrivalTime());
            }
        }

    }
}
