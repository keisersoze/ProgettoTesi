package app.core.h20.actions.logic;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;
import app.utils.MyLib;
import org.apache.commons.math3.random.MersenneTwister;


public class HandleEndReception implements Action {

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
                if (protocol(transmission)) {   // Decido se ritrasmettere in base alla profondità
                    Event e = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent, MyLib.random(.2f, .4f), context, frame, receiver, numHop);
                    context.getScheduler().addEvent(e);
                }
            } else {
                context.getFramesArrived().get(frame).addLast(context.getSimTime() - frame.getArrivalTime());
            }
        }
    }

    protected static boolean protocol (Transmission transmission) {
        if (transmission.getHop() > 3) {
            return false;
        }
        switch (H20Sim.PROTOCOL) {
            case "Deterministic":
                return transmission.getSender().getY() + H20Sim.THRESHOLD < transmission.getReceiver().getY();
            case "Probabilistic":
                return new MersenneTwister().nextDouble() < 0.3;
            case "":
                return transmission.getSender().getY() + H20Sim.THRESHOLD < transmission.getReceiver().getY() && new MersenneTwister().nextDouble() < 0.7;
            default:
                return true;
        }
    }
}
