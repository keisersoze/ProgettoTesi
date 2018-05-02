package app.core.h20G;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;
import app.utils.Canvas;
import app.utils.MyLib;


public class HandleEndReception extends app.core.h20.actions.logic.HandleEndReception implements Action {

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
        transmission.getFrame().getTransmissions().remove(transmission);

        if (transmission.isSuccessfull()) {
            if (!receiver.isSink()) {
                if (MyLib.deterministicProtocol(transmission)) {   // Decido se ritrasmettere in base alla profondità
                    double time = H20Sim.SLOW_RETRANSMITION == true? MyLib.random(0.2f,0.4f) : 0;
                    Event e = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent,time, context, frame, receiver, numHop);
                    context.getScheduler().addEvent(e);
                }
            } else {
                context.getFramesArrived().get(frame).addLast(context.getSimTime() - frame.getArrivalTime());
            }
        }

    }
}
